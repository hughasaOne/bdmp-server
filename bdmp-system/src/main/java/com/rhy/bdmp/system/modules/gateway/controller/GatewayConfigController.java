package com.rhy.bdmp.system.modules.gateway.controller;

import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayRouteVO;
import com.rhy.bdmp.system.modules.gateway.service.GatewayConfigService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @author author
 * @version V1.0
 * @description 前端控制器
 * @date 2022-05-30 17:13
 **/
@Api(tags = {"网关配置"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/gateway")
public class GatewayConfigController {

    @Resource
    private GatewayConfigService gatewayConfigService;

    @ApiOperation(value = "查询网关配置", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<GatewayRouteVO>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<GatewayRouteVO> result = gatewayConfigService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<GatewayRouteVO>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<GatewayRouteVO> pageUtil = gatewayConfigService.page(queryVO);
        log.info(LogUtil.buildUpParams("查看数据库路由列表", LogTypeEnum.ACCESS.getCode(), null));
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{id}")
    public RespResult<GatewayRouteVO> detail(@PathVariable("id") String id) {
        GatewayRouteVO gatewayRouteVO = gatewayConfigService.detail(id);
        log.info(LogUtil.buildUpParams("查看数据库路由", LogTypeEnum.ACCESS.getCode(), JSONUtil.createObj().putOnce("routeId", id)));
        return RespResult.ok(gatewayRouteVO);
    }

    @ApiOperation("新增网关配置")
    @PostMapping
    public RespResult create(@Validated @RequestBody GatewayRouteVO gatewayRouteVO) {
        gatewayConfigService.create(gatewayRouteVO);
        log.info(LogUtil.buildUpParams("新增路由", LogTypeEnum.OPERATE.getCode(), gatewayRouteVO.getId()));
        return RespResult.ok();
    }

    @ApiOperation("修改网关配置")
    @PutMapping
    public RespResult update(@Validated @RequestBody GatewayRouteVO gatewayRouteVO) {
        gatewayConfigService.update(gatewayRouteVO);
        log.info(LogUtil.buildUpParams("修改路由", LogTypeEnum.OPERATE.getCode(), gatewayRouteVO.getId()));
        return RespResult.ok();
    }

    @ApiOperation("删除网关配置")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> ids) {
        gatewayConfigService.delete(ids);
        log.info(LogUtil.buildUpParams("删除路由", LogTypeEnum.OPERATE.getCode(), ids));
        return RespResult.ok();
    }

    @ApiOperation("获取nacos中的服务列表")
    @GetMapping("/getSystem")
    public RespResult getProperty() {
        log.info(LogUtil.buildUpParams("查看nacos服务列表", LogTypeEnum.ACCESS.getCode(), null));
        return RespResult.ok(gatewayConfigService.getProperty());
    }


}