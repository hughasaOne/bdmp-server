package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 路线 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseRouteService extends IService<Route> {

    /**
     * 路线列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Route> list(QueryVO queryVO);

    /**
     * 路线列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Route> page(QueryVO queryVO);

    /**
     * 查看路线(根据ID)
     * @param routeId
     * @return
     */
    Route detail(String routeId);

    /**
     * 新增路线
     * @param route
     * @return
     */
    int create(Route route);

    /**
     * 修改路线
     * @param route
     * @return
     */
    int update(Route route);

    /**
     * 删除路线
     * @param routeIds
     * @return
     */
    int delete(Set<String> routeIds);


}
