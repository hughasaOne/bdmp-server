package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseResourceService;
import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/**
 * @description 资源 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"资源管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/resource")
public class BaseResourceController {

	@javax.annotation.Resource
	private IBaseResourceService baseResourceService;

    @ApiOperation(value = "查询资源", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Resource>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Resource> result = baseResourceService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询资源(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Resource>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Resource> pageUtil =  baseResourceService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看资源(根据ID)")
    @GetMapping(value = "/{resourceId}")
    public RespResult<Resource> detail(@PathVariable("resourceId") String resourceId) {
        Resource resource = baseResourceService.detail(resourceId);
        return RespResult.ok(resource);
    }

    @ApiOperation("新增资源")
    @PostMapping
    public RespResult create(@Validated @RequestBody Resource resource){
        baseResourceService.create(resource);
        return RespResult.ok();
    }

    @ApiOperation("修改资源")
    @PutMapping
    public RespResult update(@Validated @RequestBody Resource resource){
        baseResourceService.update(resource);
        return RespResult.ok();
    }

    @ApiOperation("删除资源")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> resourceIds){
        baseResourceService.delete(resourceIds);
        return RespResult.ok();
    }
}
