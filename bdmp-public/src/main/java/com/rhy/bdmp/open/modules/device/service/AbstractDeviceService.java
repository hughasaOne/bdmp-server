package com.rhy.bdmp.open.modules.device.service;

import com.rhy.bdmp.open.modules.device.domain.bo.DeviceDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceListBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;

import java.util.List;

public interface AbstractDeviceService {
    DeviceVo detail(DeviceDetailBo deviceDetailBo);

    List<?> list(DeviceListBo deviceListBo);
}
