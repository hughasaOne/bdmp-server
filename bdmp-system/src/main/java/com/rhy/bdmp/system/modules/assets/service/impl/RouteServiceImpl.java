package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.rhy.bdmp.base.modules.assets.service.IBaseRouteService;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionRouteVo;
import com.rhy.bdmp.system.modules.assets.service.IRouteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created on 2021/10/29.
 *
 * @author duke
 */
@Service
public class RouteServiceImpl implements IRouteService {
    @Resource
    private IBaseRouteService routeService;

    @Override
    public int create(Route route) {
        return routeService.create(route);
    }

    @Override
    public int delete(Set<String> routeIds) {
        return routeService.delete(routeIds);
    }

    @Override
    public int batchDelete(Set<String> wayIds) {
        LambdaQueryWrapper<Route> wrapper = Wrappers.lambdaQuery();
        wrapper.in(Route::getOperatingWaysectionId, wayIds);
        return routeService.getBaseMapper().delete(wrapper);
    }

    @Override
    public List<Route> queryAll(String wayIds) {
        LambdaQueryWrapper<Route> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Route::getOperatingWaysectionId, wayIds);
        return routeService.getBaseMapper().selectList(wrapper);
    }

    @Override
    public int batchUpdate(WaysectionRouteVo waysection) {
        int count = routeService.getBaseMapper().selectCount(
                Wrappers.lambdaQuery(Route.class)
                        .eq(Route::getOperatingWaysectionId, waysection.getWaysectionId()));
        if(count > 0){
            //清楚旧数据
            this.batchDelete(CollectionUtil.newHashSet(waysection.getWaysectionId()));
        }
        //添加新数据
        List<Route> routeList = waysection.getRouteList();
        if(CollectionUtil.isNotEmpty(routeList)){
            routeList.forEach(e->{
                e.setOperatingWaysectionId(waysection.getWaysectionId());
                this.create(e);
            });
        }
        return 0;
    }
}
