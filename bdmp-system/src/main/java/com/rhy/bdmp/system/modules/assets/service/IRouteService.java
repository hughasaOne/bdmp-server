package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionRouteVo;

import java.util.List;
import java.util.Set;

/**
 * Created on 2021/10/29.
 *
 * @author duke
 */
public interface IRouteService {
    /**
     * 创建路线
     * @param route
     * @return
     */
    int create(Route route);
    /**
     * 删除路线
     * @param routeIds
     * @return
     */
    int delete(Set<String> routeIds);

    /**
     *删除关联路段的所有路线
     * @param wayIds
     * @return
     */
    int batchDelete(Set<String> wayIds);


    /**
     * 根据路段id查询所有关联路线
     * @param wayIds
     * @return
     */
    List<Route> queryAll(String wayIds);

    /**
     * 关联更新路段
     * @param waysection
     * @return
     */
    int batchUpdate(WaysectionRouteVo waysection);
}
