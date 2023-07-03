package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceEmergencycallDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.service.IDeviceEmergencycallService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
@Service
public class DeviceEmergencycallServiceImpl implements IDeviceEmergencycallService {

    @Resource
    private BaseDeviceEmergencycallDao baseDeviceEmergencycallDao;

    /**
     * 新增紧急电话
     * @param deviceEmergencycall
     * @return
     */
    @Override
    public int create(DeviceEmergencycall deviceEmergencycall) {
        if (!StrUtil.isNotBlank(deviceEmergencycall.getDeviceId())) {
            throw new BadRequestException("没有获取到设备ID，无法新增");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceEmergencycall.setCreateBy(currentUser);
        deviceEmergencycall.setCreateTime(currentDateTime);
        deviceEmergencycall.setUpdateBy(currentUser);
        deviceEmergencycall.setUpdateTime(currentDateTime);
        if (null == deviceEmergencycall.getDatastatusid()){
            deviceEmergencycall.setDatastatusid(1);
        }
        int result = baseDeviceEmergencycallDao.insert(deviceEmergencycall);
        return result;
    }
}
