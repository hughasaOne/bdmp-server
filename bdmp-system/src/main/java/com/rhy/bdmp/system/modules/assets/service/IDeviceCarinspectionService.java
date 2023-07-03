package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
public interface IDeviceCarinspectionService {

    /**
     * 新增车检器
     * @param deviceCarinspection
     * @return
     */
    int create(DeviceCarinspection deviceCarinspection);
}
