package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysLogService;
import com.rhy.bdmp.base.modules.sys.domain.po.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description  前端控制器
 * @author shuaichao
 * @date 2022-03-17 11:39
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/quartz/syslog/sys-log")
public class BaseSysLogController {

	@Resource
	private IBaseSysLogService baseSysLogService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<SysLog>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<SysLog> result = baseSysLogService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<SysLog>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<SysLog> pageUtil =  baseSysLogService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{sysLogId}")
    public RespResult<SysLog> detail(@PathVariable("sysLogId") String sysLogId) {
        SysLog sysLog = baseSysLogService.detail(sysLogId);
        return RespResult.ok(sysLog);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody SysLog sysLog){
        baseSysLogService.create(sysLog);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody SysLog sysLog){
        baseSysLogService.update(sysLog);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> sysLogIds){
        baseSysLogService.delete(sysLogIds);
        return RespResult.ok();
    }
}
