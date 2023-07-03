package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
public interface IDeviceIntelligenceboardService {

    /**
     * 新增情报板
     * @param deviceIntelligenceboard
     * @return
     */
    int create(DeviceIntelligenceboard deviceIntelligenceboard);
}
