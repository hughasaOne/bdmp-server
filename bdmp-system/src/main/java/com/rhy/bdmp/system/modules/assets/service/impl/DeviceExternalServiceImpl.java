package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceExternal;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceExternalService;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceService;
import com.rhy.bdmp.system.modules.assets.dao.DeviceExternalDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.DeviceExternalBo;
import com.rhy.bdmp.system.modules.assets.service.IDeviceExternalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DeviceExternalServiceImpl implements IDeviceExternalService {

    @Resource
    private IBaseDeviceService deviceService;

    @Resource
    private IBaseDeviceExternalService externalService;

    @Resource
    private DeviceExternalDao externalDao;

    @Override
    public List<Map<String, Object>> list(String deviceId) {
        return externalDao.list(deviceId);
    }

    @Override
    public List<Device> getDeviceList(DeviceExternalBo externalBo) {
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<Device>();
        queryWrapper.select(Device::getDeviceId,Device::getDeviceName,Device::getSeriaNumber,Device::getDeviceCode);
        queryWrapper.eq(Device::getFacilitiesId, externalBo.getFacId());
        queryWrapper.ne(Device::getDeviceId,externalBo.getDeviceId());
        queryWrapper.notIn(CollUtil.isNotEmpty(externalBo.getExcludeId()),Device::getDeviceId,externalBo.getExcludeId());
        return deviceService.list(queryWrapper);
    }

    @Override
    public void add(List<DeviceExternal> deviceExternalList) {
        externalService.saveBatch(deviceExternalList);
    }

    @Override
    public void delete(String deviceId,Set<String> externalIds) {
        externalDao.delete(deviceId,externalIds);
    }
}
