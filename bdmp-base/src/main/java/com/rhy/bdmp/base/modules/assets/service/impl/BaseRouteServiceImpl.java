package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.rhy.bdmp.base.modules.assets.dao.BaseRouteDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseRouteService;
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
 * @description 路线 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseRouteServiceImpl extends ServiceImpl<BaseRouteDao, Route> implements IBaseRouteService {

    /**
     * 路线列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Route> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Route> query = new Query<Route>(queryVO);
            QueryWrapper<Route> queryWrapper = query.getQueryWrapper();
                List<Route> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 路线列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Route> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Route> query = new Query<Route>(queryVO);
            Page<Route> page = query.getPage();
            QueryWrapper<Route> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Route>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看路线(根据ID)
     * @param routeId
     * @return
     */
    @Override
    public Route detail(String routeId) {
        if (!StrUtil.isNotBlank(routeId)) {
            throw new BadRequestException("not exist routeId");
        }
        Route route = getBaseMapper().selectById(routeId);
        return route;
    }

    /**
     * 新增路线
     * @param route
     * @return
     */
    @Override
    public int create(Route route) {
        if (StrUtil.isNotBlank(route.getRouteId())) {
            throw new BadRequestException("A new Route cannot already have an routeId");
        }
        int result = getBaseMapper().insert(route);
        return result;
    }

    /**
     * 修改路线
     * @param route
     * @return
     */
    @Override
    public int update(Route route) {
       if (!StrUtil.isNotBlank(route.getRouteId())) {
           throw new BadRequestException("A new Route not exist routeId");
       }
       int result = getBaseMapper().updateById(route);
       return result;
    }

    /**
     * 删除路线
     * @param routeIds
     * @return
     */
    @Override
    public int delete(Set<String> routeIds) {
        int result = getBaseMapper().deleteBatchIds(routeIds);
        return result;
    }

}
