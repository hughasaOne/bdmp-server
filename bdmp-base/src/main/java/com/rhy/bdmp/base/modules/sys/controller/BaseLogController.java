package com.rhy.bdmp.base.modules.sys.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.Log;
import com.rhy.bdmp.base.modules.sys.service.IBaseLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;


/**
 * @description 日志 前端控制器
 * @author weicaifu
 * @date 2022-10-17 17:21
 * @version V1.0
 **/
@Api(tags = {"日志管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/log")
public class BaseLogController {

	@Resource
	private IBaseLogService baseLogService;

    @ApiOperation(value = "查询日志", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Log>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Log> result = baseLogService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询日志(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Log>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Log> pageUtil =  baseLogService.page(queryVO);
        return RespResult.ok(pageUtil);
    }
/*
    @ApiOperation(value = "查看日志(根据ID)")
    @GetMapping(value = "/{logId}")
    public RespResult<Log> detail(@PathVariable("logId") String logId) {
        Log log = baseLogService.detail(logId);
        return RespResult.ok(log);
    }

    @ApiOperation("新增日志")
    @PostMapping
    public RespResult create(@Validated @RequestBody Log log){
        baseLogService.create(log);
        return RespResult.ok();
    }

    @ApiOperation("修改日志")
    @PutMapping
    public RespResult update(@Validated @RequestBody Log log){
        baseLogService.update(log);
        return RespResult.ok();
    }

    @ApiOperation("删除日志")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> logIds){
        baseLogService.delete(logIds);
        return RespResult.ok();
    }*/
}
