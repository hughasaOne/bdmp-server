package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceFlamestateDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.service.IDeviceFlamestateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
@Service
public class DeviceFlamestateServiceImpl implements IDeviceFlamestateService {

    @Resource
    private BaseDeviceFlamestateDao baseDeviceFlamestateDao;

    /**
     * 新增感温器
     * @param deviceFlamestate
     * @return
     */
    @Override
    public int create(DeviceFlamestate deviceFlamestate) {
        if (!StrUtil.isNotBlank(deviceFlamestate.getDeviceId())) {
            throw new BadRequestException("没有获取到设备ID，无法新增");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceFlamestate.setCreateBy(currentUser);
        deviceFlamestate.setCreateTime(currentDateTime);
        deviceFlamestate.setUpdateBy(currentUser);
        deviceFlamestate.setUpdateTime(currentDateTime);
        if (null == deviceFlamestate.getDatastatusid()){
            deviceFlamestate.setDatastatusid(1);
        }
        int result = baseDeviceFlamestateDao.insert(deviceFlamestate);
        return result;
    }
}
