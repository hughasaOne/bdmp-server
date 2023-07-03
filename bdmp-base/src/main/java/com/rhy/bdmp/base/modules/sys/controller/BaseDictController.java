package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseDictService;
import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 系统字典 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"系统字典管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/dict")
public class BaseDictController {

	@Resource
	private IBaseDictService baseDictService;

    @ApiOperation(value = "查询系统字典", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Dict>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Dict> result = baseDictService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询系统字典(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Dict>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Dict> pageUtil =  baseDictService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看系统字典(根据ID)")
    @GetMapping(value = "/{dictId}")
    public RespResult<Dict> detail(@PathVariable("dictId") String dictId) {
        Dict dict = baseDictService.detail(dictId);
        return RespResult.ok(dict);
    }

    @ApiOperation("新增系统字典")
    @PostMapping
    public RespResult create(@Validated @RequestBody Dict dict){
        baseDictService.create(dict);
        return RespResult.ok();
    }

    @ApiOperation("修改系统字典")
    @PutMapping
    public RespResult update(@Validated @RequestBody Dict dict){
        baseDictService.update(dict);
        return RespResult.ok();
    }

    @ApiOperation("删除系统字典")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> dictIds){
        baseDictService.delete(dictIds);
        return RespResult.ok();
    }
}
