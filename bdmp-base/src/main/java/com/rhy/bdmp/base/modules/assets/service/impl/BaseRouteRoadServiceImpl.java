package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.RouteRoad;
import com.rhy.bdmp.base.modules.assets.dao.BaseRouteRoadDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseRouteRoadService;
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
 * @description 路段 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseRouteRoadServiceImpl extends ServiceImpl<BaseRouteRoadDao, RouteRoad> implements IBaseRouteRoadService {

    /**
     * 路段列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<RouteRoad> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<RouteRoad> query = new Query<RouteRoad>(queryVO);
            QueryWrapper<RouteRoad> queryWrapper = query.getQueryWrapper();
                List<RouteRoad> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<RouteRoad> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 路段列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<RouteRoad> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<RouteRoad> query = new Query<RouteRoad>(queryVO);
            Page<RouteRoad> page = query.getPage();
            QueryWrapper<RouteRoad> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<RouteRoad>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看路段(根据ID)
     * @param roadId
     * @return
     */
    @Override
    public RouteRoad detail(String roadId) {
        if (!StrUtil.isNotBlank(roadId)) {
            throw new BadRequestException("not exist roadId");
        }
        RouteRoad routeRoad = getBaseMapper().selectById(roadId);
        return routeRoad;
    }

    /**
     * 新增路段
     * @param routeRoad
     * @return
     */
    @Override
    public int create(RouteRoad routeRoad) {
        if (StrUtil.isNotBlank(routeRoad.getRoadId())) {
            throw new BadRequestException("A new RouteRoad cannot already have an roadId");
        }
        int result = getBaseMapper().insert(routeRoad);
        return result;
    }

    /**
     * 修改路段
     * @param routeRoad
     * @return
     */
    @Override
    public int update(RouteRoad routeRoad) {
       if (!StrUtil.isNotBlank(routeRoad.getRoadId())) {
           throw new BadRequestException("A new RouteRoad not exist roadId");
       }
       int result = getBaseMapper().updateById(routeRoad);
       return result;
    }

    /**
     * 删除路段
     * @param roadIds
     * @return
     */
    @Override
    public int delete(Set<String> roadIds) {
        int result = getBaseMapper().deleteBatchIds(roadIds);
        return result;
    }

}
