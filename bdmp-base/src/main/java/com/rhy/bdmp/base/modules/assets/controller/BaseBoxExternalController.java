package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseBoxExternalService;
import com.rhy.bdmp.base.modules.assets.domain.po.BoxExternal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 终端箱外接设备信息表 前端控制器
 * @author yanggj
 * @date 2021-09-24 15:36
 * @version V1.0
 **/
@Api(tags = {"终端箱外接设备信息表管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/assets/box-external")
public class BaseBoxExternalController {

	@Resource
	private IBaseBoxExternalService baseBoxExternalService;

    @ApiOperation(value = "查询终端箱外接设备信息表", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<BoxExternal>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<BoxExternal> result = baseBoxExternalService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询终端箱外接设备信息表(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<BoxExternal>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<BoxExternal> pageUtil =  baseBoxExternalService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看终端箱外接设备信息表(根据ID)")
    @GetMapping(value = "/{externalId}")
    public RespResult<BoxExternal> detail(@PathVariable("externalId") String externalId) {
        BoxExternal boxExternal = baseBoxExternalService.detail(externalId);
        return RespResult.ok(boxExternal);
    }

    @ApiOperation("新增终端箱外接设备信息表")
    @PostMapping
    public RespResult create(@Validated @RequestBody BoxExternal boxExternal){
        baseBoxExternalService.create(boxExternal);
        return RespResult.ok();
    }

    @ApiOperation("修改终端箱外接设备信息表")
    @PutMapping
    public RespResult update(@Validated @RequestBody BoxExternal boxExternal){
        baseBoxExternalService.update(boxExternal);
        return RespResult.ok();
    }

    @ApiOperation("删除终端箱外接设备信息表")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> externalIds){
        baseBoxExternalService.delete(externalIds);
        return RespResult.ok();
    }
}
