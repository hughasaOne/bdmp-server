package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseAppTypeService;
import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 应用类别 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"应用类别管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/app-type")
public class BaseAppTypeController {

	@Resource
	private IBaseAppTypeService baseAppTypeService;

    @ApiOperation(value = "查询应用类别", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<AppType>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<AppType> result = baseAppTypeService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询应用类别(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<AppType>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<AppType> pageUtil =  baseAppTypeService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看应用类别(根据ID)")
    @GetMapping(value = "/{appTypeId}")
    public RespResult<AppType> detail(@PathVariable("appTypeId") String appTypeId) {
        AppType appType = baseAppTypeService.detail(appTypeId);
        return RespResult.ok(appType);
    }

    @ApiOperation("新增应用类别")
    @PostMapping
    public RespResult create(@Validated @RequestBody AppType appType){
        baseAppTypeService.create(appType);
        return RespResult.ok();
    }

    @ApiOperation("修改应用类别")
    @PutMapping
    public RespResult update(@Validated @RequestBody AppType appType){
        baseAppTypeService.update(appType);
        return RespResult.ok();
    }

    @ApiOperation("删除应用类别")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> appTypeIds){
        baseAppTypeService.delete(appTypeIds);
        return RespResult.ok();
    }
}
