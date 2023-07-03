package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaysectionPlanService;
import com.rhy.bdmp.base.modules.assets.domain.po.WaysectionPlan;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 计划里程 前端控制器
 * @author duke
 * @date 2021-11-12 10:58
 * @version V1.0
 **/
@Api(tags = {"计划里程管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/assets/waysection-plan")
public class BaseWaysectionPlanController {

	@Resource
	private IBaseWaysectionPlanService baseWaysectionPlanService;

    @ApiOperation(value = "查询计划里程", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<WaysectionPlan>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<WaysectionPlan> result = baseWaysectionPlanService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询计划里程(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<WaysectionPlan>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<WaysectionPlan> pageUtil =  baseWaysectionPlanService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看计划里程(根据ID)")
    @GetMapping(value = "/{id}")
    public RespResult<WaysectionPlan> detail(@PathVariable("id") String id) {
        WaysectionPlan waysectionPlan = baseWaysectionPlanService.detail(id);
        return RespResult.ok(waysectionPlan);
    }

    @ApiOperation("新增计划里程")
    @PostMapping
    public RespResult create(@Validated @RequestBody WaysectionPlan waysectionPlan){
        baseWaysectionPlanService.create(waysectionPlan);
        return RespResult.ok();
    }

    @ApiOperation("修改计划里程")
    @PutMapping
    public RespResult update(@Validated @RequestBody WaysectionPlan waysectionPlan){
        baseWaysectionPlanService.update(waysectionPlan);
        return RespResult.ok();
    }

    @ApiOperation("删除计划里程")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> ids){
        baseWaysectionPlanService.delete(ids);
        return RespResult.ok();
    }
}
