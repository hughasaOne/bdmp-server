package com.rhy.bdmp.gateway.modules.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.gateway.modules.domain.bo.GetRoutesBo;
import com.rhy.bdmp.gateway.modules.domain.po.GatewayRoutePo;
import com.rhy.bdmp.gateway.modules.service.IGatewayRouteService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bdmp/gateway")
public class GatewayConfigController {

    @Resource
    private IGatewayRouteService gatewayConfigService;

    @GetMapping("/reloadRoute")
    public RespResult<Boolean> reloadRoute(@RequestParam(required = false) String loadFrom){
        return RespResult.ok(gatewayConfigService.routeRegister(loadFrom));
    }

    @PostMapping("/getRoutes")
    public RespResult<PageUtil<GatewayRoutePo>> getRoutes(@Validated @RequestBody GetRoutesBo getRoutesBo){
        return RespResult.ok(gatewayConfigService.getRoutes(getRoutesBo));
    }
}
