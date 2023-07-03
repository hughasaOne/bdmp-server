package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
public interface IDeviceEmergencycallService {

    /**
     * 新增紧急电话
     * @param deviceEmergencycall
     * @return
     */
    int create(DeviceEmergencycall deviceEmergencycall);
}
