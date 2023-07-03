package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DevicePedalalarm;
import com.rhy.bdmp.base.modules.assets.dao.BaseDevicePedalalarmDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDevicePedalalarmService;
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
 * @description 脚踏报警器 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDevicePedalalarmServiceImpl extends ServiceImpl<BaseDevicePedalalarmDao, DevicePedalalarm> implements IBaseDevicePedalalarmService {

    /**
     * 脚踏报警器列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DevicePedalalarm> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DevicePedalalarm> query = new Query<DevicePedalalarm>(queryVO);
            QueryWrapper<DevicePedalalarm> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DevicePedalalarm> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DevicePedalalarm> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 脚踏报警器列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DevicePedalalarm> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DevicePedalalarm> query = new Query<DevicePedalalarm>(queryVO);
            Page<DevicePedalalarm> page = query.getPage();
            QueryWrapper<DevicePedalalarm> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DevicePedalalarm>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看脚踏报警器(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public DevicePedalalarm detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        DevicePedalalarm devicePedalalarm = getBaseMapper().selectById(deviceId);
        return devicePedalalarm;
    }

    /**
     * 新增脚踏报警器
     * @param devicePedalalarm
     * @return
     */
    @Override
    public int create(DevicePedalalarm devicePedalalarm) {
        if (StrUtil.isNotBlank(devicePedalalarm.getDeviceId())) {
            throw new BadRequestException("A new DevicePedalalarm cannot already have an deviceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        devicePedalalarm.setCreateBy(currentUser);
        devicePedalalarm.setCreateTime(currentDateTime);
        devicePedalalarm.setUpdateBy(currentUser);
        devicePedalalarm.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(devicePedalalarm);
        return result;
    }

    /**
     * 修改脚踏报警器
     * @param devicePedalalarm
     * @return
     */
    @Override
    public int update(DevicePedalalarm devicePedalalarm) {
       if (!StrUtil.isNotBlank(devicePedalalarm.getDeviceId())) {
           throw new BadRequestException("A new DevicePedalalarm not exist deviceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       devicePedalalarm.setUpdateBy(currentUser);
       devicePedalalarm.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(devicePedalalarm);
       return result;
    }

    /**
     * 删除脚踏报警器
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
