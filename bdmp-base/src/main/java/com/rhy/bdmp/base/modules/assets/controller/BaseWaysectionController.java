package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaysectionService;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 运营路段 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"运营路段管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/waysection")
public class BaseWaysectionController {

	@Resource
	private IBaseWaysectionService baseWaysectionService;

    @ApiOperation(value = "查询运营路段", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Waysection>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Waysection> result = baseWaysectionService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询运营路段(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Waysection>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Waysection> pageUtil =  baseWaysectionService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看运营路段(根据ID)")
    @GetMapping(value = "/{waysectionId}")
    public RespResult<Waysection> detail(@PathVariable("waysectionId") String waysectionId) {
        Waysection waysection = baseWaysectionService.detail(waysectionId);
        return RespResult.ok(waysection);
    }

    @ApiOperation("新增运营路段")
    @PostMapping
    public RespResult create(@Validated @RequestBody Waysection waysection){
        baseWaysectionService.create(waysection);
        return RespResult.ok();
    }

    @ApiOperation("修改运营路段")
    @PutMapping
    public RespResult update(@Validated @RequestBody Waysection waysection){
        baseWaysectionService.update(waysection);
        return RespResult.ok();
    }

    @ApiOperation("删除运营路段")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> waysectionIds){
        baseWaysectionService.delete(waysectionIds);
        return RespResult.ok();
    }
}
