package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rhy.bdmp.base.modules.assets.dao.BaseDevicePedalalarmDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DevicePedalalarm;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.service.IDevicePedalalarmService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
@Service
public class DevicePedalalarmServiceImpl implements IDevicePedalalarmService {

    @Resource
    private BaseDevicePedalalarmDao baseDevicePedalalarmDao;

    /**
     * 新增脚踏报警器
     * @param devicePedalalarm
     * @return
     */
    @Override
    public int create(DevicePedalalarm devicePedalalarm) {
        if (!StrUtil.isNotBlank(devicePedalalarm.getDeviceId())) {
            throw new BadRequestException("没有获取到设备ID，无法新增");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        devicePedalalarm.setCreateBy(currentUser);
        devicePedalalarm.setCreateTime(currentDateTime);
        devicePedalalarm.setUpdateBy(currentUser);
        devicePedalalarm.setUpdateTime(currentDateTime);
        if (null == devicePedalalarm.getDatastatusid()){
            devicePedalalarm.setDatastatusid(1);
        }
        int result = baseDevicePedalalarmDao.insert(devicePedalalarm);
        return result;
    }
}
