package com.rhy.bdmp.quartz.modules.sysbusiness.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysBusinessService;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.bo.SysBusinessExt;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.vo.SysBusinessExtVo;
import com.rhy.bdmp.quartz.modules.sysbusiness.service.ISysBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @description  前端控制器
 * @author shuaichao
 * @date 2022-03-15 11:38
 * @version V1.0
 **/
@Api(tags = {"定时任务业务类型管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/quartz/sys-business")
public class SysBusinessController {

	@Resource
	private IBaseSysBusinessService baseSysBusinessService;

	@Resource
    private ISysBusinessService sysBusinessService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<SysBusinessExt>> list(@RequestBody SysBusiness sysBusiness) {
        List<SysBusinessExt> result = sysBusinessService.list(sysBusiness);
        return RespResult.ok(result);
    }





    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{sysBusinessId}")
    public RespResult<SysBusiness> detail(@PathVariable("sysBusinessId") String sysBusinessId) {
        SysBusiness sysBusiness = baseSysBusinessService.detail(sysBusinessId);
        return RespResult.ok(sysBusiness);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody SysBusiness sysBusiness){
        sysBusinessService.create(sysBusiness);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody SysBusiness sysBusiness){
        sysBusinessService.update(sysBusiness);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody List<String> sysBusinessIds){
        sysBusinessService.delete(sysBusinessIds);
        return RespResult.ok();
    }

    @ApiOperation("获取树形数据")
    @GetMapping("/treeList")
    public RespResult treeList(){
        return RespResult.ok(sysBusinessService.treeList());
    }

}
