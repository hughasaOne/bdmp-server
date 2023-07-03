package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaynetService;
import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 高速路网 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"高速路网管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/waynet")
public class BaseWaynetController {

	@Resource
	private IBaseWaynetService baseWaynetService;

    @ApiOperation(value = "查询高速路网", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Waynet>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Waynet> result = baseWaynetService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询高速路网(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Waynet>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Waynet> pageUtil =  baseWaynetService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看高速路网(根据ID)")
    @GetMapping(value = "/{waynetId}")
    public RespResult<Waynet> detail(@PathVariable("waynetId") String waynetId) {
        Waynet waynet = baseWaynetService.detail(waynetId);
        return RespResult.ok(waynet);
    }

    @ApiOperation("新增高速路网")
    @PostMapping
    public RespResult create(@Validated @RequestBody Waynet waynet){
        baseWaynetService.create(waynet);
        return RespResult.ok();
    }

    @ApiOperation("修改高速路网")
    @PutMapping
    public RespResult update(@Validated @RequestBody Waynet waynet){
        baseWaynetService.update(waynet);
        return RespResult.ok();
    }

    @ApiOperation("删除高速路网")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> waynetIds){
        baseWaynetService.delete(waynetIds);
        return RespResult.ok();
    }
}
