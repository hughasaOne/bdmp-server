package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseRouteService;
import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 路线 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"路线管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/route")
public class BaseRouteController {

	@Resource
	private IBaseRouteService baseRouteService;

    @ApiOperation(value = "查询路线", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Route>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Route> result = baseRouteService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询路线(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Route>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Route> pageUtil =  baseRouteService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看路线(根据ID)")
    @GetMapping(value = "/{routeId}")
    public RespResult<Route> detail(@PathVariable("routeId") String routeId) {
        Route route = baseRouteService.detail(routeId);
        return RespResult.ok(route);
    }

    @ApiOperation("新增路线")
    @PostMapping
    public RespResult create(@Validated @RequestBody Route route){
        baseRouteService.create(route);
        return RespResult.ok();
    }

    @ApiOperation("修改路线")
    @PutMapping
    public RespResult update(@Validated @RequestBody Route route){
        baseRouteService.update(route);
        return RespResult.ok();
    }

    @ApiOperation("删除路线")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> routeIds){
        baseRouteService.delete(routeIds);
        return RespResult.ok();
    }
}
