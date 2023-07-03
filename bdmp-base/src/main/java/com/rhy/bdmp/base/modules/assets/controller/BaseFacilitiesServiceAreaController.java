package com.rhy.bdmp.base.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesServiceAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description  前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/facilities-service-area")
public class BaseFacilitiesServiceAreaController {

	@Resource
	private IBaseFacilitiesServiceAreaService baseFacilitiesServiceAreaService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<FacilitiesServiceArea>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<FacilitiesServiceArea> result = baseFacilitiesServiceAreaService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<FacilitiesServiceArea>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<FacilitiesServiceArea> pageUtil =  baseFacilitiesServiceAreaService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{serviceAreaId}")
    public RespResult<FacilitiesServiceArea> detail(@PathVariable("serviceAreaId") String serviceAreaId) {
        FacilitiesServiceArea facilitiesServiceArea = baseFacilitiesServiceAreaService.detail(serviceAreaId);
        return RespResult.ok(facilitiesServiceArea);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesServiceArea facilitiesServiceArea){
        baseFacilitiesServiceAreaService.create(facilitiesServiceArea);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody FacilitiesServiceArea facilitiesServiceArea){
        baseFacilitiesServiceAreaService.update(facilitiesServiceArea);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<Integer> serviceAreaIds){
        baseFacilitiesServiceAreaService.delete(serviceAreaIds);
        return RespResult.ok();
    }
}
