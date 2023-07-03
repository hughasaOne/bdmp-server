package com.rhy.bdmp.system.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.DictBrand;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictBrandService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2021/11/1.
 *
 * @author duke
 */
@Api(tags = {"品牌字典管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/dict/brand")
public class DictBrandController {

    @Resource
    private IDictBrandService dictBrandService;

    @ApiOperation("获取品牌字典")
    @PostMapping("/getDictBrand")
    public RespResult getDictBrand(@Validated @RequestBody DictBO dictBO){
        return RespResult.ok(dictBrandService.getDictBrand(dictBO));
    }

    @ApiOperation("新增品牌字典")
    @PostMapping
    public RespResult<Boolean> create(@Validated @RequestBody DictBrand dictBrand) {
        boolean result = dictBrandService.create(dictBrand);
        log.info(LogUtil.buildUpParams("新增品牌字典", LogTypeEnum.OPERATE.getCode(), dictBrand.getBrandId()));
        return RespResult.ok(result);
    }

    @ApiOperation("修改品牌字典")
    @PutMapping
    public RespResult<Boolean> update(@Validated @RequestBody DictBrand dictBrand) {
        log.info(LogUtil.buildUpParams("修改品牌字典", LogTypeEnum.OPERATE.getCode(), dictBrand.getBrandId()));
        return RespResult.ok(dictBrandService.update(dictBrand));
    }

    @ApiOperation("删除品牌字典")
    @DeleteMapping
    public RespResult<Integer> delete(@RequestBody Set<String> brandIds) {
        log.info(LogUtil.buildUpParams("删除品牌字典", LogTypeEnum.OPERATE.getCode(), brandIds));
        return RespResult.ok(dictBrandService.delete(brandIds));
    }

    @ApiOperation(value = "查看品牌字典(根据ID)")
    @GetMapping(value = "/{brandId}")
    public RespResult<Map<String,Object>> detail(@PathVariable("brandId") String brandId) {
        Map<String,Object> dictBrand = dictBrandService.detail(brandId);
        return RespResult.ok(dictBrand);
    }

    @ApiOperation("查询品牌字典(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "param", value = "品牌字典查询条件", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/page")
    public RespResult<PageUtils> page(@RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                      @RequestParam(required = false, defaultValue = "20") Integer size,
                                      @RequestParam(required = false) String param) {
        Page<DictBrand> result = dictBrandService.queryPage(currentPage, size, param);
        return RespResult.ok(new PageUtils(result));
    }

    @ApiOperation("查询所有品牌字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "品牌字典查询条件", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/list")
    public RespResult<List<DictBrand>> list(@RequestParam(required = false) String param) {
        List<DictBrand> result = dictBrandService.list(param);
        return RespResult.ok(result);
    }

    @ApiOperation("根据父节点查询子集")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父节点id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getDictListByParentId")
    public RespResult<List<Map<String,Object>>> getDictListByParentId(@RequestParam String parentId){
        return RespResult.ok(dictBrandService.getDictListByParentId(parentId));
    }
}
