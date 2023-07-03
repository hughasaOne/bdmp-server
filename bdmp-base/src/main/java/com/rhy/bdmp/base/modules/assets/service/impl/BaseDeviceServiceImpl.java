package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceService;
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
 * @description 设备 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDeviceServiceImpl extends ServiceImpl<BaseDeviceDao, Device> implements IBaseDeviceService {

    /**
     * 设备列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Device> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Device> query = new Query<Device>(queryVO);
            QueryWrapper<Device> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Device> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 设备列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Device> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Device> query = new Query<Device>(queryVO);
            Page<Device> page = query.getPage();
            QueryWrapper<Device> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Device>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看设备(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public Device detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        Device device = getBaseMapper().selectById(deviceId);
        return device;
    }

    /**
     * 新增设备
     * @param device
     * @return
     */
    @Override
    public int create(Device device) {
        if (StrUtil.isNotBlank(device.getDeviceId())) {
            throw new BadRequestException("A new Device cannot already have an deviceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        device.setCreateBy(currentUser);
        device.setCreateTime(currentDateTime);
        device.setUpdateBy(currentUser);
        device.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(device);
        return result;
    }

    /**
     * 修改设备
     * @param device
     * @return
     */
    @Override
    public int update(Device device) {
       if (!StrUtil.isNotBlank(device.getDeviceId())) {
           throw new BadRequestException("A new Device not exist deviceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       device.setUpdateBy(currentUser);
       device.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(device);
       return result;
    }

    /**
     * 删除设备
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
