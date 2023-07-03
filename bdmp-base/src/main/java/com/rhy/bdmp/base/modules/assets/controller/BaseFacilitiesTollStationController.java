package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesTollStationService;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;
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
//@RequestMapping("//bdmp/system/assets/facilities-toll-station")
public class BaseFacilitiesTollStationController {

	@Resource
	private IBaseFacilitiesTollStationService baseFacilitiesTollStationService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<FacilitiesTollStation>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<FacilitiesTollStation> result = baseFacilitiesTollStationService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<FacilitiesTollStation>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<FacilitiesTollStation> pageUtil =  baseFacilitiesTollStationService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{tollStationId}")
    public RespResult<FacilitiesTollStation> detail(@PathVariable("tollStationId") String tollStationId) {
        FacilitiesTollStation facilitiesTollStation = baseFacilitiesTollStationService.detail(tollStationId);
        return RespResult.ok(facilitiesTollStation);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesTollStation facilitiesTollStation){
        baseFacilitiesTollStationService.create(facilitiesTollStation);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody FacilitiesTollStation facilitiesTollStation){
        baseFacilitiesTollStationService.update(facilitiesTollStation);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> tollStationIds){
        baseFacilitiesTollStationService.delete(tollStationIds);
        return RespResult.ok();
    }
}
