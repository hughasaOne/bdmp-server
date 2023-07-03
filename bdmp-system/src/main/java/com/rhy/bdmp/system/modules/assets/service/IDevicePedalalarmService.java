package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DevicePedalalarm;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
public interface IDevicePedalalarmService {
    /**
     * 新增脚踏报警器
     * @param devicePedalalarm
     * @return
     */
    int create(DevicePedalalarm devicePedalalarm);
}
