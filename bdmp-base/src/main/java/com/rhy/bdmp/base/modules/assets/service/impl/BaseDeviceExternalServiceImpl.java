package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceExternalDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceExternal;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceExternalService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 外接设备 服务实现
 * @author weicaifu
 * @date 2022-10-28 16:43
 * @version V1.0
 **/
@Service
public class BaseDeviceExternalServiceImpl extends ServiceImpl<BaseDeviceExternalDao, DeviceExternal> implements IBaseDeviceExternalService {

    /**
     * 外接设备列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceExternal> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceExternal> query = new Query<DeviceExternal>(queryVO);
            QueryWrapper<DeviceExternal> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceExternal> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceExternal> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 外接设备列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceExternal> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceExternal> query = new Query<DeviceExternal>(queryVO);
            Page<DeviceExternal> page = query.getPage();
            QueryWrapper<DeviceExternal> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceExternal>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看外接设备(根据ID)
     * @param id
     * @return
     */
    @Override
    public DeviceExternal detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        DeviceExternal deviceExternal = getBaseMapper().selectById(id);
        return deviceExternal;
    }

    /**
     * 新增外接设备
     * @param deviceExternal
     * @return
     */
    @Override
    public int create(DeviceExternal deviceExternal) {
        if (StrUtil.isNotBlank(deviceExternal.getId())) {
            throw new BadRequestException("A new DeviceExternal cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceExternal.setCreateBy(currentUser);
        deviceExternal.setCreateTime(currentDateTime);
        deviceExternal.setUpdateBy(currentUser);
        deviceExternal.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceExternal);
        return result;
    }

    /**
     * 修改外接设备
     * @param deviceExternal
     * @return
     */
    @Override
    public int update(DeviceExternal deviceExternal) {
       if (!StrUtil.isNotBlank(deviceExternal.getId())) {
           throw new BadRequestException("A new DeviceExternal not exist id");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceExternal.setUpdateBy(currentUser);
       deviceExternal.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceExternal);
       return result;
    }

    /**
     * 删除外接设备
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
