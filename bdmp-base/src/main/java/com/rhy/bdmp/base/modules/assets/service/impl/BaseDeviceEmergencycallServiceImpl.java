package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceEmergencycallDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceEmergencycallService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 紧急电话 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDeviceEmergencycallServiceImpl extends ServiceImpl<BaseDeviceEmergencycallDao, DeviceEmergencycall> implements IBaseDeviceEmergencycallService {

    /**
     * 紧急电话列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceEmergencycall> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceEmergencycall> query = new Query<DeviceEmergencycall>(queryVO);
            QueryWrapper<DeviceEmergencycall> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceEmergencycall> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceEmergencycall> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 紧急电话列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceEmergencycall> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceEmergencycall> query = new Query<DeviceEmergencycall>(queryVO);
            Page<DeviceEmergencycall> page = query.getPage();
            QueryWrapper<DeviceEmergencycall> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceEmergencycall>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看紧急电话(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public DeviceEmergencycall detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        DeviceEmergencycall deviceEmergencycall = getBaseMapper().selectById(deviceId);
        return deviceEmergencycall;
    }

    /**
     * 新增紧急电话
     * @param deviceEmergencycall
     * @return
     */
    @Override
    public int create(DeviceEmergencycall deviceEmergencycall) {
        if (StrUtil.isNotBlank(deviceEmergencycall.getDeviceId())) {
            throw new BadRequestException("A new DeviceEmergencycall cannot already have an deviceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceEmergencycall.setCreateBy(currentUser);
        deviceEmergencycall.setCreateTime(currentDateTime);
        deviceEmergencycall.setUpdateBy(currentUser);
        deviceEmergencycall.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceEmergencycall);
        return result;
    }

    /**
     * 修改紧急电话
     * @param deviceEmergencycall
     * @return
     */
    @Override
    public int update(DeviceEmergencycall deviceEmergencycall) {
       if (!StrUtil.isNotBlank(deviceEmergencycall.getDeviceId())) {
           throw new BadRequestException("A new DeviceEmergencycall not exist deviceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceEmergencycall.setUpdateBy(currentUser);
       deviceEmergencycall.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceEmergencycall);
       return result;
    }

    /**
     * 删除紧急电话
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
