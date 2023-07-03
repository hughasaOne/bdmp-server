package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictBrandService;
import com.rhy.bdmp.base.modules.assets.domain.po.DictBrand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 品牌字典 前端控制器
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
@Api(tags = {"品牌字典管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/assets/dict-brand")
public class BaseDictBrandController {

	@Resource
	private IBaseDictBrandService baseDictBrandService;

    @ApiOperation(value = "查询品牌字典", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DictBrand>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DictBrand> result = baseDictBrandService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询品牌字典(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DictBrand>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DictBrand> pageUtil =  baseDictBrandService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看品牌字典(根据ID)")
    @GetMapping(value = "/{brandId}")
    public RespResult<DictBrand> detail(@PathVariable("brandId") String brandId) {
        DictBrand dictBrand = baseDictBrandService.detail(brandId);
        return RespResult.ok(dictBrand);
    }

    @ApiOperation("新增品牌字典")
    @PostMapping
    public RespResult create(@Validated @RequestBody DictBrand dictBrand){
        baseDictBrandService.create(dictBrand);
        return RespResult.ok();
    }

    @ApiOperation("修改品牌字典")
    @PutMapping
    public RespResult update(@Validated @RequestBody DictBrand dictBrand){
        baseDictBrandService.update(dictBrand);
        return RespResult.ok();
    }

    @ApiOperation("删除品牌字典")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> brandIds){
        baseDictBrandService.delete(brandIds);
        return RespResult.ok();
    }
}
