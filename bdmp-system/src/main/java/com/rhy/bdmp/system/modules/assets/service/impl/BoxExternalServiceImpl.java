package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bdmp.base.modules.assets.domain.po.BoxExternal;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.service.IBaseBoxExternalService;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceService;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.dao.BoxExternalDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.SavePeripheralsBo;
import com.rhy.bdmp.system.modules.assets.domain.vo.BoxEnablePortVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.BoxPortInfoVo;
import com.rhy.bdmp.system.modules.assets.service.BoxExternalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 终端箱外接设备业务逻辑实现层
 * @author 魏财富
 */
@Service
public class BoxExternalServiceImpl implements BoxExternalService {
    @Resource
    private BoxExternalDao externalDao;

    @Resource
    private IBaseDeviceService deviceService;

    @Resource
    private IBaseBoxExternalService externalService;

    /**
     * 获取可用的端口号和可用的外设
     * @param boxId 终端箱Id
     * @param facId 设施Id
     */
    @Override
    public BoxEnablePortVo getEnablePortsAndPer(String boxId,String facId) {
        //查询出可用的端口号
        Device device = deviceService.getOne(new QueryWrapper<Device>().eq("device_id",boxId).eq("device_type","130700"));
        List<BoxExternal> boxExternalList = null;
        if (null != device){
            boxExternalList = externalService.list(new QueryWrapper<BoxExternal>().eq("sn", device.getDeviceCode()));
        }else{
            throw new BadRequestException("当前选择的设备不是终端箱或不存在");
        }
        BoxEnablePortVo boxEnablePortVo = new BoxEnablePortVo();
        //装载可用设备
        boxEnablePortVo.setEnablePeripherals(externalDao.getEnablePeripherals(facId));
        if(0 >= boxExternalList.size()){
            //如果boxExternal为null，表示当前终端箱的端口都可以使用
            // 默认值为true,直接返回
            return boxEnablePortVo;
        }else {
            //boxExternalList不为null，将已有的端口设置为false
            for(BoxExternal boxExternal : boxExternalList){
                String portNum = boxExternal.getPortNum();
                switch (portNum){
                    case "1":
                        boxEnablePortVo.setEnablePortOne(false);
                        break;
                    case "2":
                        boxEnablePortVo.setEnablePortTwo(false);
                        break;
                    case "3":
                        boxEnablePortVo.setEnablePortThree(false);
                        break;
                    case "4":
                        boxEnablePortVo.setEnablePortFour(false);
                        break;
                    case "5":
                        boxEnablePortVo.setEnablePortFive(false);
                        break;
                    case "6":
                        boxEnablePortVo.setEnablePortSix(false);
                        break;
                    case "7":
                        boxEnablePortVo.setEnablePortSeven(false);
                        break;
                    case "8":
                        boxEnablePortVo.setEnablePortEight(false);
                        break;
                    default:
                        break;
                }
            }
            return boxEnablePortVo;
        }
    }

    /**
     * 查询当前终端箱的端口情况（端口号+已关联的设备信息）
     * @param deviceId 终端箱Id
     * @param sn 终端箱sn
     */
    @Override
    public List<BoxPortInfoVo> getPortInfo(String deviceId,String sn) {
        List<BoxExternal> boxExternalList = null;
        if (StrUtil.isNotBlank(sn)){
            boxExternalList = externalService.list(new QueryWrapper<BoxExternal>().eq("sn", sn));
        }else{
            Device device = deviceService.getById(deviceId);
            if (null == device){
                throw new BadRequestException("当前终端箱不存在");
            }
            boxExternalList = externalService.list(new QueryWrapper<BoxExternal>().eq("sn", device.getDeviceCode()));
        }
        List<BoxPortInfoVo> boxPortInfoVoList = new ArrayList<>();
        if (boxExternalList.isEmpty()){
            //1.boxExternalList == null 给一个默认值
            for(int i = 1; i < 9; i++) {
                BoxPortInfoVo boxPortInfoVo = new BoxPortInfoVo();
                boxPortInfoVo.setPortNum(String.valueOf(i));
                boxPortInfoVoList.add(boxPortInfoVo);
            }
            return boxPortInfoVoList;
        }else{
            // 2.boxExternalList != null 将查询到的信息封装（按端口1-8），没有端口号的给默认值
            //查询外设信息
            List<Map<String,String>> externalInfoList = externalDao.getExternalInfo(deviceId,sn);
            // 封装外设信息
            for (Map<String, String> map : externalInfoList) {
                BoxPortInfoVo boxPortInfoVo = new BoxPortInfoVo();
                boxPortInfoVo.setPortNum(map.get("port_num"));
                boxPortInfoVo.setDeviceId(map.get("deviceId"));
                boxPortInfoVo.setExternalId(map.get("external_id"));
                boxPortInfoVo.setBoxIp(map.get("boxIp"));
                boxPortInfoVo.setDeviceIp(map.get("deviceIp"));
                boxPortInfoVo.setDeviceCode(map.get("device_code"));
                boxPortInfoVo.setAssetsNo(map.get("assets_no"));
                boxPortInfoVo.setDeviceName(map.get("device_name"));
                boxPortInfoVo.setDeviceFac(map.get("facilities_name"));
                boxPortInfoVo.setDeviceFacId(map.get("deviceFacId"));
                boxPortInfoVo.setDeviceType(map.get("device_type"));
                boxPortInfoVo.setDeviceTypeName(map.get("deviceTypeName"));
                boxPortInfoVo.setLocation(map.get("location"));
                boxPortInfoVo.setDeviceSys(map.get("systemName"));
                boxPortInfoVo.setDeviceSysId(map.get("deviceSysId"));
                boxPortInfoVoList.add(boxPortInfoVo);
            }
            // 给没有外设的端口默认值
            List<BoxPortInfoVo> defaultBoxPortInfoVoList = new ArrayList<>();
            List<BoxPortInfoVo> defaultBoxPortInfoVoList1 = new ArrayList<>();
            for (int i = 1; i < 9; i++) {
                BoxPortInfoVo boxPortInfoVo1 = new BoxPortInfoVo();
                boxPortInfoVo1.setPortNum(String.valueOf(i));
                defaultBoxPortInfoVoList.add(boxPortInfoVo1);
                defaultBoxPortInfoVoList1.add(boxPortInfoVo1);
            }
            // 去除已经有外设的端口
            for (BoxPortInfoVo boxPortInfoVo : defaultBoxPortInfoVoList) {
                for (BoxPortInfoVo portInfoVo : boxPortInfoVoList) {
                    if (boxPortInfoVo.getPortNum().equals(portInfoVo.getPortNum()) && StrUtil.isNotBlank(portInfoVo.getDeviceCode())){
                        //当前端口已经有外设
                        defaultBoxPortInfoVoList1.remove(boxPortInfoVo);
                    }
                }
            }
            //将剩余的默认值添加返回的集合
            for(BoxPortInfoVo portInfoVo : defaultBoxPortInfoVoList1){
                boxPortInfoVoList.add(portInfoVo);
            }
            //排序
            List<BoxPortInfoVo> sortedBoxPortInfoVoList = boxPortInfoVoList.stream().sorted(Comparator.comparing(BoxPortInfoVo::getPortNum))
                    .collect(Collectors.toList());
            return sortedBoxPortInfoVoList;
        }
    }

    /**
     * 查询当前终端箱具体端口情况
     * @param boxId 终端箱Id
     * @param portNum 端口号
     */
    @Override
    public Map<String,String> getPortInfoByPortAndId(String boxId, String portNum){
        return externalDao.getPortInfoByPortAndId(boxId,portNum);
    }


    /**
     * 新增外接设备
     */
    @Override
    public Boolean save(List<SavePeripheralsBo> savePeripheralsBoList) {
        List<BoxExternal> boxExternals = new ArrayList<>();
        for (SavePeripheralsBo savePeripheralsBo : savePeripheralsBoList) {
            String boxId = savePeripheralsBo.getBoxId();
            String deviceId = savePeripheralsBo.getDeviceId();
            String portNum = savePeripheralsBo.getPortNum().toString();

            BoxExternal boxExternal = new BoxExternal();
            //拿到外设
            Device peripherals = deviceService.getById(deviceId);
            //查询需要添加外设的终端箱
            Device device = deviceService.getById(boxId);
            if (null != device){
                boxExternal.setSn(device.getDeviceCode());
                boxExternal.setExternalSn(peripherals.getDeviceCode());
                //维护其他字段
                boxExternal.setPortNum(portNum);
                boxExternal.setCreateBy(WebUtils.getUserId());
                boxExternal.setCreateTime(new Date());
                boxExternal.setDatastatusid(1);
                boxExternal.setSort(1);
                boxExternal.setUpdateBy(WebUtils.getUserId());
                boxExternal.setUpdateTime(new Date());
            }else {
                throw new BadRequestException("当前选择的设备不存在");
            }
            boxExternals.add(boxExternal);
        }
        return externalService.saveBatch(boxExternals);
    }



    /**
     * 修改外接设备
     * @param boxId 终端箱Id
     * @param portNum 端口号
     * @param externalId 外设Id
     */
    @Override
    public Integer update(String boxId, String portNum,String deviceId, String externalId) {
        BoxExternal boxExternal = new BoxExternal();
        //拿到外设
        Device peripherals = deviceService.getById(deviceId);
        //查询需要修改外设的终端箱
        Device device = deviceService.getById(boxId);
        if (null != device){
            boxExternal.setExternalId(externalId);
            boxExternal.setSn(device.getDeviceCode());
            boxExternal.setExternalSn(peripherals.getDeviceCode());
            //维护其他字段
            boxExternal.setPortNum(portNum);
            boxExternal.setCreateBy(WebUtils.getUserId());
            boxExternal.setCreateTime(new Date());
            boxExternal.setDatastatusid(1);
            boxExternal.setSort(1);
            boxExternal.setUpdateBy(WebUtils.getUserId());
            boxExternal.setUpdateTime(new Date());
        }else {
            throw new BadRequestException("当前选择的设备不存在");
        }
        return externalService.update(boxExternal);
    }

    /**
     * 解除外设关系
     * @param externalIds 外设表Id
     */
    @Override
    public Integer delPerRelation(Set<String> externalIds) {
        return externalService.delete(externalIds);
    }

    /**
     * 测试终端箱与外设的网络是否能够通信
     * @param deviceIp 外设Ip
     */
    @Override
    public Boolean pingTest(String deviceIp) {
        //超时应该在3钞以上
        int timeOut = 10000 ;
        // 当返回值是true时，说明host是可用的，false则不可。
        boolean enableDevice = false;
        try {
            enableDevice = InetAddress.getByName(deviceIp).isReachable(timeOut);
        } catch (IOException e) {
            String[] msg = e.toString().split(":");
            throw new BadRequestException("未知的Ip:"+msg[1]);
        }
        if (enableDevice){
            return enableDevice;
        }else {
            throw new BadRequestException("测试失败");
        }
    }

}
