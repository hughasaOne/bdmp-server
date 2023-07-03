package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceIntelligenceboardDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.service.IDeviceIntelligenceboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
@Service
public class DeviceIntelligenceboardServiceImpl implements IDeviceIntelligenceboardService {

    @Resource
    private BaseDeviceIntelligenceboardDao baseDeviceIntelligenceboardDao;

    /**
     * 新增情报板
     * @param deviceIntelligenceboard
     * @return
     */
    @Override
    public int create(DeviceIntelligenceboard deviceIntelligenceboard) {
        if (!StrUtil.isNotBlank(deviceIntelligenceboard.getDeviceId())) {
            throw new BadRequestException("没有获取到设备ID，无法新增");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceIntelligenceboard.setCreateBy(currentUser);
        deviceIntelligenceboard.setCreateTime(currentDateTime);
        deviceIntelligenceboard.setUpdateBy(currentUser);
        deviceIntelligenceboard.setUpdateTime(currentDateTime);
        if (null == deviceIntelligenceboard.getDatastatusid()){
            deviceIntelligenceboard.setDatastatusid(1);
        }
        int result = baseDeviceIntelligenceboardDao.insert(deviceIntelligenceboard);
        return result;
    }
}
