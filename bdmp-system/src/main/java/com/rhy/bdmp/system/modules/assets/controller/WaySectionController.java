package com.rhy.bdmp.system.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaysectionService;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionRouteVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionVo;
import com.rhy.bdmp.system.modules.assets.service.IWaySectionService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @auther xiabei
 * @Description 路段 前端控制器
 * @date 2021/4/14
 */
@Api(tags = {"路段管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/waysection")
public class WaySectionController {

    @Resource
    private IWaySectionService waySectionService;

    @Resource
    private IBaseWaysectionService baseWaysectionService;

    @ApiOperation("查询路段(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "orgId", value = "组织ID", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "waySectionName", value = "路段名称", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "waynetId", value = "所属路网", dataType = "string", paramType = "query", example = "")
    })
    @GetMapping(value = "/queryPage")
    public RespResult queryPage(@RequestParam(required = false) Integer currentPage,
                                @RequestParam(required = false) Integer size,
                                @RequestParam(required = false) Boolean isUseUserPermissions,
                                @RequestParam(required = false) String orgId,
                                @RequestParam(required = false) String waySectionName,
                                @RequestParam(required = false) String waynetId) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        Page<WaysectionVo> page = waySectionService.queryPage(currentPage, size, isUseUserPermissions, orgId, waySectionName,waynetId);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "查询运营路段(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Waysection>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Waysection> pageUtil =  baseWaysectionService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation("新增路段")
    @PostMapping
    public RespResult create(@Validated @RequestBody WaysectionRouteVo waysection) {
        waySectionService.create(waysection);
        log.info(LogUtil.buildUpParams("新增路段", LogTypeEnum.OPERATE.getCode(), waysection.getWaysectionId()));
        return RespResult.ok();
    }

    @ApiOperation("删除高速路段")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> waysectionIds) {
        waySectionService.delete(waysectionIds);
        return RespResult.ok();
    }

    @ApiOperation("修改路段")
    @PutMapping
    public RespResult update(@Validated @RequestBody WaysectionRouteVo waysection) {
        waySectionService.update(waysection);
        log.info(LogUtil.buildUpParams("修改路段", LogTypeEnum.OPERATE.getCode(), waysection.getWaysectionId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "查看路段(根据ID)")
    @GetMapping(value = "/{waysectionId}")
    public RespResult detail(@PathVariable("waysectionId") String waysectionId) {
        WaysectionRouteVo waysection = waySectionService.detail(waysectionId);
        return RespResult.ok(waysection);
    }
}
