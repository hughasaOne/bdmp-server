package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictSystemService;
import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
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
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
@Api(tags = {"系统字典管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/assets/dict-system")
public class BaseDictSystemController {

	@Resource
	private IBaseDictSystemService baseDictSystemService;

    @ApiOperation(value = "查询系统字典", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DictSystem>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DictSystem> result = baseDictSystemService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询系统字典(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DictSystem>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DictSystem> pageUtil =  baseDictSystemService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看系统字典(根据ID)")
    @GetMapping(value = "/{systemId}")
    public RespResult<DictSystem> detail(@PathVariable("systemId") String systemId) {
        DictSystem dictSystem = baseDictSystemService.detail(systemId);
        return RespResult.ok(dictSystem);
    }

    @ApiOperation("新增系统字典")
    @PostMapping
    public RespResult create(@Validated @RequestBody DictSystem dictSystem){
        baseDictSystemService.create(dictSystem);
        return RespResult.ok();
    }

    @ApiOperation("修改系统字典")
    @PutMapping
    public RespResult update(@Validated @RequestBody DictSystem dictSystem){
        baseDictSystemService.update(dictSystem);
        return RespResult.ok();
    }

    @ApiOperation("删除系统字典")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> systemIds){
        baseDictSystemService.delete(systemIds);
        return RespResult.ok();
    }
}
