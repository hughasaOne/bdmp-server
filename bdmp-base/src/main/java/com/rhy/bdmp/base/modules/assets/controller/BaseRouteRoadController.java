package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseRouteRoadService;
import com.rhy.bdmp.base.modules.assets.domain.po.RouteRoad;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 路段 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"路段管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/route-road")
public class BaseRouteRoadController {

	@Resource
	private IBaseRouteRoadService baseRouteRoadService;

    @ApiOperation(value = "查询路段", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<RouteRoad>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<RouteRoad> result = baseRouteRoadService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询路段(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<RouteRoad>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<RouteRoad> pageUtil =  baseRouteRoadService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看路段(根据ID)")
    @GetMapping(value = "/{roadId}")
    public RespResult<RouteRoad> detail(@PathVariable("roadId") String roadId) {
        RouteRoad routeRoad = baseRouteRoadService.detail(roadId);
        return RespResult.ok(routeRoad);
    }

    @ApiOperation("新增路段")
    @PostMapping
    public RespResult create(@Validated @RequestBody RouteRoad routeRoad){
        baseRouteRoadService.create(routeRoad);
        return RespResult.ok();
    }

    @ApiOperation("修改路段")
    @PutMapping
    public RespResult update(@Validated @RequestBody RouteRoad routeRoad){
        baseRouteRoadService.update(routeRoad);
        return RespResult.ok();
    }

    @ApiOperation("删除路段")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> roadIds){
        baseRouteRoadService.delete(roadIds);
        return RespResult.ok();
    }
}
