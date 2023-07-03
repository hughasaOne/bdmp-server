package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
public interface IDeviceFlamestateService {

    /**
     * 新增感温器
     * @param deviceFlamestate
     * @return
     */
    int create(DeviceFlamestate deviceFlamestate);
}
