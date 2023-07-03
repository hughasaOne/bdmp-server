package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.rhy.bdmp.base.modules.sys.dao.BaseAppTypeDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseAppTypeService;
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
 * @description 应用类别 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseAppTypeServiceImpl extends ServiceImpl<BaseAppTypeDao, AppType> implements IBaseAppTypeService {

    /**
     * 应用类别列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<AppType> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<AppType> query = new Query<AppType>(queryVO);
            QueryWrapper<AppType> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<AppType> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<AppType> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 应用类别列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<AppType> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<AppType> query = new Query<AppType>(queryVO);
            Page<AppType> page = query.getPage();
            QueryWrapper<AppType> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<AppType>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看应用类别(根据ID)
     * @param appTypeId
     * @return
     */
    @Override
    public AppType detail(String appTypeId) {
        if (!StrUtil.isNotBlank(appTypeId)) {
            throw new BadRequestException("not exist appTypeId");
        }
        AppType appType = getBaseMapper().selectById(appTypeId);
        return appType;
    }

    /**
     * 新增应用类别
     * @param appType
     * @return
     */
    @Override
    public int create(AppType appType) {
        if (StrUtil.isNotBlank(appType.getAppTypeId())) {
            throw new BadRequestException("A new AppType cannot already have an appTypeId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        appType.setCreateBy(currentUser);
        appType.setCreateTime(currentDateTime);
        appType.setUpdateBy(currentUser);
        appType.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(appType);
        return result;
    }

    /**
     * 修改应用类别
     * @param appType
     * @return
     */
    @Override
    public int update(AppType appType) {
       if (!StrUtil.isNotBlank(appType.getAppTypeId())) {
           throw new BadRequestException("A new AppType not exist appTypeId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       appType.setUpdateBy(currentUser);
       appType.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(appType);
       return result;
    }

    /**
     * 删除应用类别
     * @param appTypeIds
     * @return
     */
    @Override
    public int delete(Set<String> appTypeIds) {
        int result = getBaseMapper().deleteBatchIds(appTypeIds);
        return result;
    }

}
