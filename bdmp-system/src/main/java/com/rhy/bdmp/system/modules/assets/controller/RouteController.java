package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.rhy.bdmp.system.modules.assets.service.IRouteService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created on 2021/11/1.
 *
 * @author duke
 */
@Api(tags = {"路线管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/route")
public class RouteController {
    @Resource
    private IRouteService routeService;

    @ApiOperation("新增路线")
    @PostMapping
    public RespResult create(@Validated @RequestBody Route route) {
        routeService.create(route);
        log.info(LogUtil.buildUpParams("新增路线", LogTypeEnum.OPERATE.getCode(), route.getRouteId()));
        return RespResult.ok();
    }
}
