package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysJobService;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
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
 * @date 2022-03-17 11:38
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/quartz/sysjob/sys-job")
public class BaseSysJobController {

	@Resource
	private IBaseSysJobService baseSysJobService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<SysJob>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<SysJob> result = baseSysJobService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<SysJob>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<SysJob> pageUtil =  baseSysJobService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{sysJobId}")
    public RespResult<SysJob> detail(@PathVariable("sysJobId") String sysJobId) {
        SysJob sysJob = baseSysJobService.detail(sysJobId);
        return RespResult.ok(sysJob);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody SysJob sysJob){
        baseSysJobService.create(sysJob);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody SysJob sysJob){
        baseSysJobService.update(sysJob);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> sysJobIds){
        baseSysJobService.delete(sysJobIds);
        return RespResult.ok();
    }
}
