package com.rhy.bdmp.quartz.modules.syslog.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysLogService;
import com.rhy.bdmp.quartz.modules.syslog.domain.bo.SysLogExt;
import com.rhy.bdmp.quartz.modules.syslog.service.ISysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;


/**
 * @description  前端控制器
 * @author shuaichao
 * @date 2022-03-15 11:32
 * @version V1.0
 **/
@Api(tags = {"定时任务日志管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/quartz/sys-log")
public class SysLogController {


    @Resource
    ISysLogService sysLogService ;
    @Resource
    IBaseSysLogService baseSysLogService;




    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page/{currentPage}/{size}")
    public RespResult<PageUtil<SysLogExt>> page(@RequestBody SysLogExt sysLogExt, @PathVariable Integer currentPage, @PathVariable Integer size) {
        Page<SysLogExt> pageUtil =  sysLogService.page(sysLogExt,currentPage,size);
        return RespResult.ok(new PageUtil<>(pageUtil));
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> sysLogIds){
        baseSysLogService.delete(sysLogIds);
        return RespResult.ok();
    }

}
