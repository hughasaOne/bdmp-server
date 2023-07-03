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
import com.rhy.bdmp.base.modules.assets.dao.BaseVpResourceDao;
import com.rhy.bdmp.base.modules.assets.domain.po.VpResource;
import com.rhy.bdmp.base.modules.assets.service.IBaseVpResourceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 可视对讲资源表 服务实现
 * @author weicaifu
 * @date 2022-10-12 11:19
 * @version V1.0
 **/
@Service
public class BaseVpResourceServiceImpl extends ServiceImpl<BaseVpResourceDao, VpResource> implements IBaseVpResourceService {

    /**
     * 可视对讲资源表列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<VpResource> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<VpResource> query = new Query<VpResource>(queryVO);
            QueryWrapper<VpResource> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<VpResource> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<VpResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 可视对讲资源表列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<VpResource> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<VpResource> query = new Query<VpResource>(queryVO);
            Page<VpResource> page = query.getPage();
            QueryWrapper<VpResource> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<VpResource>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看可视对讲资源表(根据ID)
     * @param id
     * @return
     */
    @Override
    public VpResource detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        VpResource vpResource = getBaseMapper().selectById(id);
        return vpResource;
    }

    /**
     * 新增可视对讲资源表
     * @param vpResource
     * @return
     */
    @Override
    public int create(VpResource vpResource) {
        if (StrUtil.isNotBlank(vpResource.getId())) {
            throw new BadRequestException("A new VpResource cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        vpResource.setCreateBy(currentUser);
        vpResource.setCreateTime(currentDateTime);
        vpResource.setUpdateBy(currentUser);
        vpResource.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(vpResource);
        return result;
    }

    /**
     * 修改可视对讲资源表
     * @param vpResource
     * @return
     */
    @Override
    public int update(VpResource vpResource) {
       if (!StrUtil.isNotBlank(vpResource.getId())) {
           throw new BadRequestException("A new VpResource not exist id");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       vpResource.setUpdateBy(currentUser);
       vpResource.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(vpResource);
       return result;
    }

    /**
     * 删除可视对讲资源表
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
