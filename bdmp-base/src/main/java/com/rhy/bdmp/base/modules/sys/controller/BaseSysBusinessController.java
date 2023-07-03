package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysBusinessService;
import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
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
 * @date 2022-03-21 10:14
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/quartz/syslog/sys-business")
public class BaseSysBusinessController {

	@Resource
	private IBaseSysBusinessService baseSysBusinessService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<SysBusiness>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<SysBusiness> result = baseSysBusinessService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<SysBusiness>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<SysBusiness> pageUtil =  baseSysBusinessService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{businessId}")
    public RespResult<SysBusiness> detail(@PathVariable("businessId") String businessId) {
        SysBusiness sysBusiness = baseSysBusinessService.detail(businessId);
        return RespResult.ok(sysBusiness);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody SysBusiness sysBusiness){
        baseSysBusinessService.create(sysBusiness);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody SysBusiness sysBusiness){
        baseSysBusinessService.update(sysBusiness);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> businessIds){
        baseSysBusinessService.delete(businessIds);
        return RespResult.ok();
    }
}
