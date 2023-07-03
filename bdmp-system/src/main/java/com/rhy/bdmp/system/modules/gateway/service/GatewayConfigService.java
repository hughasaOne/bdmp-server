package com.rhy.bdmp.system.modules.gateway.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayRouteVO;

import java.util.List;
import java.util.Set;

/**
 * @author author
 * @version V1.0
 * @description 服务接口
 * @date 2022-05-30 17:13
 **/
public interface GatewayConfigService extends IService<GatewayRouteVO> {

    /**
     * 列表查询
     *
     * @param queryVO 查询条件
     * @return
     */
    List<GatewayRouteVO> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     *
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<GatewayRouteVO> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     *
     * @param id
     * @return
     */
    GatewayRouteVO detail(String id);

    /**
     * 新增
     *
     * @param gatewayRouteVO
     * @return
     */
    int create(GatewayRouteVO gatewayRouteVO);

    /**
     * 修改
     *
     * @param gatewayRouteVO
     * @return
     */
    int update(GatewayRouteVO gatewayRouteVO);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    int delete(Set<String> ids);

    /**
     * 获取nacos中的服务列表
     *
     * @return json
     */
    JSONObject getProperty();
}
