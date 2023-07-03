package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaynetService;
import com.rhy.bdmp.system.modules.assets.service.IWaynetService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @auther xiabei
 * @Description 路网 前端控制器
 * @date 2021/4/14
 */
@Api(tags = {"高速路网管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/waynet")
public class WaynetController {

    @Resource
    private IWaynetService waynetService;

    @Resource
    private IBaseWaynetService baseWaynetService;

    @ApiOperation("删除高速路网")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> waynetIds) {
        waynetService.delete(waynetIds);
        log.info(LogUtil.buildUpParams("删除高速路网", LogTypeEnum.OPERATE.getCode(), waynetIds));
        return RespResult.ok();
    }

    @ApiOperation("新增高速路网")
    @PostMapping
    public RespResult create(@RequestBody Waynet waynet) {
        waynetService.create(waynet);
        log.info(LogUtil.buildUpParams("新增高速路网", LogTypeEnum.OPERATE.getCode(), waynet.getWaynetId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询高速路网(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Waynet>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Waynet> pageUtil =  waynetService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查询高速路网", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        PageUtil<Waynet> pageUtil =  baseWaynetService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "获取高速路网列表")
    @GetMapping(value = "/getWaynetList")
    public RespResult getWaynetList() {
        return RespResult.ok(waynetService.getWaynetList());
    }

    @ApiOperation(value = "查看高速路网(根据ID)")
    @GetMapping(value = "/{waynetId}")
    public RespResult detail(@PathVariable("waynetId") String waynetId) {
        Waynet waynet = baseWaynetService.detail(waynetId);
        return RespResult.ok(waynet);
    }

    @ApiOperation("修改高速路网")
    @PutMapping
    public RespResult update(@Validated @RequestBody Waynet waynet){
        baseWaynetService.update(waynet);
        log.info(LogUtil.buildUpParams("修改高速路网", LogTypeEnum.OPERATE.getCode(), waynet.getWaynetId()));
        return RespResult.ok();
    }

}
