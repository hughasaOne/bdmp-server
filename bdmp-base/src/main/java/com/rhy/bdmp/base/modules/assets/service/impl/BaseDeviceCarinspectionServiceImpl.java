package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceCarinspectionDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceCarinspectionService;
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
 * @description 车检器 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDeviceCarinspectionServiceImpl extends ServiceImpl<BaseDeviceCarinspectionDao, DeviceCarinspection> implements IBaseDeviceCarinspectionService {

    /**
     * 车检器列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceCarinspection> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceCarinspection> query = new Query<DeviceCarinspection>(queryVO);
            QueryWrapper<DeviceCarinspection> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceCarinspection> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceCarinspection> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 车检器列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceCarinspection> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceCarinspection> query = new Query<DeviceCarinspection>(queryVO);
            Page<DeviceCarinspection> page = query.getPage();
            QueryWrapper<DeviceCarinspection> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceCarinspection>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看车检器(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public DeviceCarinspection detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        DeviceCarinspection deviceCarinspection = getBaseMapper().selectById(deviceId);
        return deviceCarinspection;
    }

    /**
     * 新增车检器
     * @param deviceCarinspection
     * @return
     */
    @Override
    public int create(DeviceCarinspection deviceCarinspection) {
        if (StrUtil.isNotBlank(deviceCarinspection.getDeviceId())) {
            throw new BadRequestException("A new DeviceCarinspection cannot already have an deviceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceCarinspection.setCreateBy(currentUser);
        deviceCarinspection.setCreateTime(currentDateTime);
        deviceCarinspection.setUpdateBy(currentUser);
        deviceCarinspection.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceCarinspection);
        return result;
    }

    /**
     * 修改车检器
     * @param deviceCarinspection
     * @return
     */
    @Override
    public int update(DeviceCarinspection deviceCarinspection) {
       if (!StrUtil.isNotBlank(deviceCarinspection.getDeviceId())) {
           throw new BadRequestException("A new DeviceCarinspection not exist deviceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceCarinspection.setUpdateBy(currentUser);
       deviceCarinspection.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceCarinspection);
       return result;
    }

    /**
     * 删除车检器
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
