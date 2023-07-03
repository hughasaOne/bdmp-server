package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceBox;

import java.util.List;

/**
 * 终端箱服务接口
 * @author 魏财富
 */
public interface DeviceBoxService {
    /**
     * 同步终端箱系统的终端箱信息
     */
    Boolean synBoxInfo();
}
