package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesService;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 路段设施 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"路段设施管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/facilities")
public class BaseFacilitiesController {

	@Resource
	private IBaseFacilitiesService baseFacilitiesService;

    @ApiOperation(value = "查询路段设施", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Facilities>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Facilities> result = baseFacilitiesService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询路段设施(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Facilities>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Facilities> pageUtil =  baseFacilitiesService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看路段设施(根据ID)")
    @GetMapping(value = "/{facilitiesId}")
    public RespResult<Facilities> detail(@PathVariable("facilitiesId") String facilitiesId) {
        Facilities facilities = baseFacilitiesService.detail(facilitiesId);
        return RespResult.ok(facilities);
    }

    @ApiOperation("新增路段设施")
    @PostMapping
    public RespResult create(@Validated @RequestBody Facilities facilities){
        baseFacilitiesService.create(facilities);
        return RespResult.ok();
    }

    @ApiOperation("修改路段设施")
    @PutMapping
    public RespResult update(@Validated @RequestBody Facilities facilities){
        baseFacilitiesService.update(facilities);
        return RespResult.ok();
    }

    @ApiOperation("删除路段设施")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> facilitiesIds){
        baseFacilitiesService.delete(facilitiesIds);
        return RespResult.ok();
    }
}
