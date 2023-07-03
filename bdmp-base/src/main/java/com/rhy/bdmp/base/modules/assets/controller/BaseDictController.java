package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictService;
import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 台账字典 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"台账字典管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/dict")
public class BaseDictController {

	@Resource
	private IBaseDictService baseDictService;

    @ApiOperation(value = "查询台账字典", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Dict>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Dict> result = baseDictService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询台账字典(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Dict>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Dict> pageUtil =  baseDictService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看台账字典(根据ID)")
    @GetMapping(value = "/{dictId}")
    public RespResult<Dict> detail(@PathVariable("dictId") String dictId) {
        Dict dict = baseDictService.detail(dictId);
        return RespResult.ok(dict);
    }

    @ApiOperation("新增台账字典")
    @PostMapping
    public RespResult create(@Validated @RequestBody Dict dict){
        baseDictService.create(dict);
        return RespResult.ok();
    }

    @ApiOperation("修改台账字典")
    @PutMapping
    public RespResult update(@Validated @RequestBody Dict dict){
        baseDictService.update(dict);
        return RespResult.ok();
    }

    @ApiOperation("删除台账字典")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> dictIds){
        baseDictService.delete(dictIds);
        return RespResult.ok();
    }
}
