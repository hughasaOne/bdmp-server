package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceBox;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceBoxService;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceService;
import com.rhy.bdmp.system.modules.assets.dao.DeviceBoxDao;
import com.rhy.bdmp.system.modules.assets.domain.vo.BoxVo;
import com.rhy.bdmp.system.modules.assets.service.DeviceBoxService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 终端箱服务实现
 * @author 魏财富
 */
@Slf4j
@Service
public class DeviceBoxServiceImpl implements DeviceBoxService {
    @Resource
    private IBaseDeviceService deviceService;

    @Resource
    private IBaseDeviceBoxService deviceBoxService;

    @Resource
    private DeviceBoxDao boxDao;

    /**
     * 同步终端箱系统的终端箱信息
     * 1：从箱子系统查询出基础数据平台没有的box，做新增
     * 2：查询出箱子系统和基础数据平台都有的box，做更新
     */
    @Transactional
    @Override
    public Boolean synBoxInfo(){
        //查询出基础数据平台没有的box，做新增
        List<BoxVo> diffBox = boxDao.getDiffBox();
        String batchNum = IdUtil.simpleUUID();
        log.info("同步终端箱,待新增设备数 {},批次号 {}, {}",diffBox.size(),batchNum,LogUtil.buildUpParams("",LogTypeEnum.STATS.getCode(), null));
        //新增
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = new Date();
        if (!diffBox.isEmpty()){
            // 查询主表是否存在sn
            Set<String> codes = new HashSet<>();
            List<Device> saveDevList = new ArrayList<Device>();
            for (BoxVo box : diffBox) {
                //新增时，同时维护Device表
                box.setDeviceId("");
                Device device = new Device();
                device.setDeviceName(box.getDevName());
                device.setSeriaNumber(box.getSn());
                device.setDeviceType("130700");
                device.setIp(box.getBordIp());
                device.setManufacturer(box.getManufacturer());
                device.setCreateTime(box.getCreateTime());
                device.setCreateBy(box.getCreateBy());
                device.setUpdateTime(currentDateTime);
                device.setUpdateBy(currentUser);
                Double lat = null == box.getLat() ? 0 : box.getLat();
                Double lon = null == box.getLon() ? 0 : box.getLat();
                if (lat > (0.000001)){
                    device.setLatitude(String.valueOf(box.getLat()));
                }
                if (lon > (0.000001)){
                    device.setLongitude(String.valueOf(box.getLon()));
                }
                codes.add(box.getSn());
                saveDevList.add(device);
            }
            List<Device> existedDevice = null;
            if (CollUtil.isNotEmpty(codes)){
                existedDevice = deviceService.list(new QueryWrapper<Device>().in("seria_number", codes));
            }

            //vo转po
            List<DeviceBox> addBoxList = new ArrayList<>();
            for (BoxVo box : diffBox) {
                DeviceBox addBox = new DeviceBox();
                addBox.setSn(box.getSn());
                addBox.setRom(box.getRom());
                addBox.setScm(box.getScm());
                addBox.setReclosing(box.getReclosing());
                addBox.setDevName(box.getDevName());
                addBox.setBordIp(box.getBordIp());
                addBox.setManufacturer(box.getManufacturer());
                addBox.setTel(box.getTel());
                addBox.setLinkType(box.getLinkType());
                addBox.setAirSwitchVersion(box.getAirSwitchVersion());
                addBox.setSort(box.getSort());
                addBox.setDatastatusid(box.getDatastatusid());
                addBox.setCreateTime(currentDateTime);
                addBox.setCreateBy(box.getCreateBy());
                addBox.setUpdateTime(currentDateTime);
                addBox.setUpdateBy(currentUser);
                if (CollUtil.isNotEmpty(existedDevice)){
                    for (Device device : existedDevice) {
                        if (device.getSeriaNumber().equals(box.getSn())){
                            addBox.setDeviceId(device.getDeviceId());
                            break;
                        }
                    }
                }
                addBoxList.add(addBox);
            }
            deviceBoxService.saveOrUpdateBatch(addBoxList);

            for (DeviceBox box : addBoxList) {
                for (Device device : saveDevList) {
                    if (box.getSn().equals(device.getSeriaNumber())){
                        device.setDeviceId(box.getDeviceId());
                        break;
                    }
                }
            }
            deviceService.saveOrUpdateBatch(saveDevList);
        }

        //查询出基础数据平台和box系统都有的box，做更新
        List<BoxVo> sameBox = boxDao.getSameBox();
        log.info("同步终端箱,待更新设备数 {},批次号 {}, {}",sameBox.size(),batchNum,LogUtil.buildUpParams("",LogTypeEnum.STATS.getCode(), null));
        // 修改
        if (!sameBox.isEmpty()){
            List<Device> updateDevList = new ArrayList<>();
            Set<String> sns = new HashSet<>();
            for (BoxVo box : sameBox) {
                sns.add(box.getSn());
            }
            //拿到相似平台的id赋值给相似的box
            List<Device> deviceList = deviceService.list(new QueryWrapper<Device>().in("seria_number", sns));
            // 维护 device数据
            for (Device device : deviceList) {
                for (BoxVo box : sameBox) {
                    if (device.getDeviceCode().equals(box.getSn())){
                        box.setDeviceId(device.getDeviceId());
                        device.setDeviceName(box.getDevName());
                        device.setSeriaNumber(box.getSn());
                        device.setDeviceType("130700");
                        device.setIp(box.getBordIp());
                        device.setManufacturer(box.getManufacturer());
                        device.setCreateTime(box.getCreateTime());
                        device.setCreateBy(box.getCreateBy());
                        device.setUpdateTime(currentDateTime);
                        device.setUpdateBy(box.getUpdateBy());
                        updateDevList.add(device);
                        Double lat = null == box.getLat() ? 0 : box.getLat();
                        Double lon = null == box.getLon() ? 0 : box.getLat();
                        if (lat > (0.000001)){
                            device.setLatitude(String.valueOf(box.getLat()));
                        }
                        if (lon > (0.000001)){
                            device.setLongitude(String.valueOf(box.getLon()));
                        }
                        break;
                    }
                }
            }
            // vo转po
            List<DeviceBox> updateBoxList = new ArrayList<>();
            for (BoxVo box : sameBox) {
                DeviceBox updateBox = new DeviceBox();
                updateBox.setDeviceId(box.getDeviceId());
                updateBox.setSn(box.getSn());
                updateBox.setRom(box.getRom());
                updateBox.setScm(box.getScm());
                updateBox.setReclosing(box.getReclosing());
                updateBox.setDevName(box.getDevName());
                updateBox.setBordIp(box.getBordIp());
                updateBox.setManufacturer(box.getManufacturer());
                updateBox.setTel(box.getTel());
                updateBox.setLinkType(box.getLinkType());
                updateBox.setAirSwitchVersion(box.getAirSwitchVersion());
                updateBox.setSort(box.getSort());
                updateBox.setDatastatusid(box.getDatastatusid());
                updateBox.setCreateTime(box.getCreateTime());
                updateBox.setCreateBy(box.getCreateBy());
                updateBox.setUpdateTime(currentDateTime);
                updateBox.setUpdateBy(currentUser);
                updateBoxList.add(updateBox);
            }
            deviceBoxService.saveOrUpdateBatch(updateBoxList);
            deviceService.saveOrUpdateBatch(updateDevList);
        }
        log.info("同步终端箱,批次号 {}, {}",batchNum,LogUtil.buildUpParams("", LogTypeEnum.OPERATE.getCode(), null));
        return true;
    }
}
