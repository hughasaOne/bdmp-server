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
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceCategoryDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceCategoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 设备分类 服务实现
 * @author weicaifu
 * @date 2022-10-17 10:45
 * @version V1.0
 **/
@Service
public class BaseDeviceCategoryServiceImpl extends ServiceImpl<BaseDeviceCategoryDao, DeviceCategory> implements IBaseDeviceCategoryService {

    /**
     * 设备分类列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceCategory> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceCategory> query = new Query<DeviceCategory>(queryVO);
            QueryWrapper<DeviceCategory> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceCategory> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 设备分类列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceCategory> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceCategory> query = new Query<DeviceCategory>(queryVO);
            Page<DeviceCategory> page = query.getPage();
            QueryWrapper<DeviceCategory> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceCategory>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看设备分类(根据ID)
     * @param id
     * @return
     */
    @Override
    public DeviceCategory detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        DeviceCategory deviceCategory = getBaseMapper().selectById(id);
        return deviceCategory;
    }

    /**
     * 新增设备分类
     * @param deviceCategory
     * @return
     */
    @Override
    public int create(DeviceCategory deviceCategory) {
        if (StrUtil.isNotBlank(deviceCategory.getId())) {
            throw new BadRequestException("A new DeviceCategory cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceCategory.setCreateBy(currentUser);
        deviceCategory.setCreateTime(currentDateTime);
        deviceCategory.setUpdateBy(currentUser);
        deviceCategory.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceCategory);
        return result;
    }

    /**
     * 修改设备分类
     * @param deviceCategory
     * @return
     */
    @Override
    public int update(DeviceCategory deviceCategory) {
       if (!StrUtil.isNotBlank(deviceCategory.getId())) {
           throw new BadRequestException("A new DeviceCategory not exist id");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceCategory.setUpdateBy(currentUser);
       deviceCategory.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceCategory);
       return result;
    }

    /**
     * 删除设备分类
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
