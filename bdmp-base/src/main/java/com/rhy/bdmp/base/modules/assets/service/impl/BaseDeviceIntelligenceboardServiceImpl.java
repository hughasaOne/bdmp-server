package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceIntelligenceboardDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceIntelligenceboardService;
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
 * @description 情报板 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDeviceIntelligenceboardServiceImpl extends ServiceImpl<BaseDeviceIntelligenceboardDao, DeviceIntelligenceboard> implements IBaseDeviceIntelligenceboardService {

    /**
     * 情报板列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceIntelligenceboard> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceIntelligenceboard> query = new Query<DeviceIntelligenceboard>(queryVO);
            QueryWrapper<DeviceIntelligenceboard> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceIntelligenceboard> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceIntelligenceboard> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 情报板列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceIntelligenceboard> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceIntelligenceboard> query = new Query<DeviceIntelligenceboard>(queryVO);
            Page<DeviceIntelligenceboard> page = query.getPage();
            QueryWrapper<DeviceIntelligenceboard> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceIntelligenceboard>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看情报板(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public DeviceIntelligenceboard detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        DeviceIntelligenceboard deviceIntelligenceboard = getBaseMapper().selectById(deviceId);
        return deviceIntelligenceboard;
    }

    /**
     * 新增情报板
     * @param deviceIntelligenceboard
     * @return
     */
    @Override
    public int create(DeviceIntelligenceboard deviceIntelligenceboard) {
        if (StrUtil.isNotBlank(deviceIntelligenceboard.getDeviceId())) {
            throw new BadRequestException("A new DeviceIntelligenceboard cannot already have an deviceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceIntelligenceboard.setCreateBy(currentUser);
        deviceIntelligenceboard.setCreateTime(currentDateTime);
        deviceIntelligenceboard.setUpdateBy(currentUser);
        deviceIntelligenceboard.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceIntelligenceboard);
        return result;
    }

    /**
     * 修改情报板
     * @param deviceIntelligenceboard
     * @return
     */
    @Override
    public int update(DeviceIntelligenceboard deviceIntelligenceboard) {
       if (!StrUtil.isNotBlank(deviceIntelligenceboard.getDeviceId())) {
           throw new BadRequestException("A new DeviceIntelligenceboard not exist deviceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceIntelligenceboard.setUpdateBy(currentUser);
       deviceIntelligenceboard.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceIntelligenceboard);
       return result;
    }

    /**
     * 删除情报板
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
