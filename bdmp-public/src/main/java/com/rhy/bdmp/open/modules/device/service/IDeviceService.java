package com.rhy.bdmp.open.modules.device.service;

import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.device.domain.bo.*;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.device.domain.vo.StatDeviceNumByTypeVo;

import java.util.List;

public interface IDeviceService extends AbstractDeviceService {
    List<StatDeviceNumByTypeVo> statDeviceNumByType(StatDeviceNumByTypeBo commonBo);

    PageUtil<DeviceVo> getDevicePage(DevicePageBo devicePageBo);

    Object getAllNodeTree(AllNodeTreeBo allNodeTreeBo);

    DeviceVo belongToDevice(String deviceId);
}
