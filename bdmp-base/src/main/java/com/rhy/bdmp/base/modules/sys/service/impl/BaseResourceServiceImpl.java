package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.rhy.bdmp.base.modules.sys.dao.BaseResourceDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseResourceService;
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
 * @description 资源 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseResourceServiceImpl extends ServiceImpl<BaseResourceDao, Resource> implements IBaseResourceService {

    /**
     * 资源列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Resource> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Resource> query = new Query<Resource>(queryVO);
            QueryWrapper<Resource> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Resource> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 资源列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Resource> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Resource> query = new Query<Resource>(queryVO);
            Page<Resource> page = query.getPage();
            QueryWrapper<Resource> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Resource>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看资源(根据ID)
     * @param resourceId
     * @return
     */
    @Override
    public Resource detail(String resourceId) {
        if (!StrUtil.isNotBlank(resourceId)) {
            throw new BadRequestException("not exist resourceId");
        }
        Resource resource = getBaseMapper().selectById(resourceId);
        return resource;
    }

    /**
     * 新增资源
     * @param resource
     * @return
     */
    @Override
    public int create(Resource resource) {
        if (StrUtil.isNotBlank(resource.getResourceId())) {
            throw new BadRequestException("A new Resource cannot already have an resourceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        resource.setCreateBy(currentUser);
        resource.setCreateTime(currentDateTime);
        resource.setUpdateBy(currentUser);
        resource.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(resource);
        return result;
    }

    /**
     * 修改资源
     * @param resource
     * @return
     */
    @Override
    public int update(Resource resource) {
       if (!StrUtil.isNotBlank(resource.getResourceId())) {
           throw new BadRequestException("A new Resource not exist resourceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       resource.setUpdateBy(currentUser);
       resource.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(resource);
       return result;
    }

    /**
     * 删除资源
     * @param resourceIds
     * @return
     */
    @Override
    public int delete(Set<String> resourceIds) {
        int result = getBaseMapper().deleteBatchIds(resourceIds);
        return result;
    }

}
