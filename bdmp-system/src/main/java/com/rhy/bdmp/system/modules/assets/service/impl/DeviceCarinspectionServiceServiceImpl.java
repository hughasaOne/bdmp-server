package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceCarinspectionDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.service.IDeviceCarinspectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
@Service
public class DeviceCarinspectionServiceServiceImpl implements IDeviceCarinspectionService {

    @Resource
    private BaseDeviceCarinspectionDao baseDeviceCarinspectionDao;

    /**
     * 新增车检器
     * @param deviceCarinspection
     * @return
     */
    @Override
    public int create(DeviceCarinspection deviceCarinspection) {
        if (!StrUtil.isNotBlank(deviceCarinspection.getDeviceId())) {
            throw new BadRequestException("没有获取到设备ID，无法新增");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceCarinspection.setCreateBy(currentUser);
        deviceCarinspection.setCreateTime(currentDateTime);
        deviceCarinspection.setUpdateBy(currentUser);
        deviceCarinspection.setUpdateTime(currentDateTime);
        if (null == deviceCarinspection.getDatastatusid()){
            deviceCarinspection.setDatastatusid(1);
        }
        int result = baseDeviceCarinspectionDao.insert(deviceCarinspection);
        return result;
    }
}
