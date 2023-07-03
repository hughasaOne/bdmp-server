package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseAppService;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 应用信息 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"应用信息管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/app")
public class BaseAppController {

	@Resource
	private IBaseAppService baseAppService;

    @ApiOperation(value = "查询应用信息", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<App>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<App> result = baseAppService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询应用信息(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<App>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<App> pageUtil =  baseAppService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看应用信息(根据ID)")
    @GetMapping(value = "/{appId}")
    public RespResult<App> detail(@PathVariable("appId") String appId) {
        App app = baseAppService.detail(appId);
        return RespResult.ok(app);
    }

    @ApiOperation("新增应用信息")
    @PostMapping
    public RespResult create(@Validated @RequestBody App app){
        baseAppService.create(app);
        return RespResult.ok();
    }

    @ApiOperation("修改应用信息")
    @PutMapping
    public RespResult update(@Validated @RequestBody App app){
        baseAppService.update(app);
        return RespResult.ok();
    }

    @ApiOperation("删除应用信息")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> appIds){
        baseAppService.delete(appIds);
        return RespResult.ok();
    }
}
