package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.RouteRoad;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 路段 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseRouteRoadService extends IService<RouteRoad> {

    /**
     * 路段列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<RouteRoad> list(QueryVO queryVO);

    /**
     * 路段列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<RouteRoad> page(QueryVO queryVO);

    /**
     * 查看路段(根据ID)
     * @param roadId
     * @return
     */
    RouteRoad detail(String roadId);

    /**
     * 新增路段
     * @param routeRoad
     * @return
     */
    int create(RouteRoad routeRoad);

    /**
     * 修改路段
     * @param routeRoad
     * @return
     */
    int update(RouteRoad routeRoad);

    /**
     * 删除路段
     * @param roadIds
     * @return
     */
    int delete(Set<String> roadIds);


}
