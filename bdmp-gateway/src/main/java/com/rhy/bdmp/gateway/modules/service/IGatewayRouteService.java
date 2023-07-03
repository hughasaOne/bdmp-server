package com.rhy.bdmp.gateway.modules.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.gateway.modules.domain.bo.GetRoutesBo;
import com.rhy.bdmp.gateway.modules.domain.po.GatewayRoutePo;

import java.util.List;

public interface IGatewayRouteService extends IService<GatewayRoutePo> {
    Boolean routeRegister(String loadFrom);
    PageUtil<GatewayRoutePo> getRoutes(GetRoutesBo getRoutesBo);
}
