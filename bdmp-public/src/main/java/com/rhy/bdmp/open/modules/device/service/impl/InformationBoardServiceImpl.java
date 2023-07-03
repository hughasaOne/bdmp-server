package com.rhy.bdmp.open.modules.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;
import com.rhy.bdmp.open.modules.device.dao.InformationBoardDao;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceListBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.device.domain.vo.InformationBoardVo;
import com.rhy.bdmp.open.modules.device.service.IDeviceService;
import com.rhy.bdmp.open.modules.device.service.IInformationBoardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("informationBoardServiceImpl")
public class InformationBoardServiceImpl implements IInformationBoardService {
    @Resource
    private InformationBoardDao informationBoardDao;

    @Resource(name = "deviceServiceImplV1")
    private IDeviceService deviceService;

    @Override
    public InformationBoardVo detail(DeviceDetailBo deviceDetailBo) {
        DeviceIntelligenceboard deviceIntelligenceboard = informationBoardDao.selectById(deviceDetailBo.getDeviceId());
        DeviceVo deviceDetail = deviceService.detail(deviceDetailBo);
        InformationBoardVo informationBoardVo = new InformationBoardVo();
        BeanUtil.copyProperties(deviceIntelligenceboard,informationBoardVo);
        BeanUtil.copyProperties(deviceDetail,informationBoardVo);
        return informationBoardVo;
    }

    @Override
    public List<InformationBoardVo> list(DeviceListBo commonBo) {
        // 基础数据
        List<DeviceVo> deviceList = (List<DeviceVo>) deviceService.list(commonBo);
        if (CollUtil.isEmpty(deviceList)){
            return null;
        }

        List<String> deviceIds = deviceList.stream()
                .map(device -> device.getDeviceId())
                .collect(Collectors.toList());
        QueryWrapper<DeviceIntelligenceboard> wrapper = new QueryWrapper<>();
        wrapper.in("device_id",deviceIds);
        // 情报板的特有数据
        List<DeviceIntelligenceboard> intelligenceboardList = informationBoardDao.selectList(wrapper);

        List<InformationBoardVo> informationBoardVoList = new ArrayList<>();
        InformationBoardVo informationBoardVo;
        if (CollUtil.isEmpty(intelligenceboardList)){
            // copy基础数据
            for (DeviceVo deviceVo : deviceList) {
                informationBoardVo = new InformationBoardVo();
                BeanUtil.copyProperties(deviceVo,informationBoardVo);
                informationBoardVoList.add(informationBoardVo);
            }
            return informationBoardVoList;
        }

        // copy情报板特有数据
        for (DeviceIntelligenceboard deviceIntelligenceboard : intelligenceboardList) {
            informationBoardVo = new InformationBoardVo();
            BeanUtil.copyProperties(deviceIntelligenceboard,informationBoardVo);
            informationBoardVoList.add(informationBoardVo);
        }

        // copy基础数据
        for (DeviceVo deviceVo : deviceList) {
            List<InformationBoardVo> tempList = informationBoardVoList.stream()
                    .filter(temp -> StrUtil.equals(temp.getDeviceId(), deviceVo.getDeviceId()))
                    .collect(Collectors.toList());
            if (CollUtil.isEmpty(tempList)){
                break;
            }
            InformationBoardVo informationBoardVo1 = tempList.get(0);
            BeanUtil.copyProperties(deviceVo,informationBoardVo1);
        }
        return informationBoardVoList;
    }
}
