package com.rhy.bdmp.base.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStationLane;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesTollStationLaneService;
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
 * @author jiangzhimin
 * @date 2021-12-02 10:06
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/assets/facilities-toll-station-lane")
public class BaseFacilitiesTollStationLaneController {

	@Resource
	private IBaseFacilitiesTollStationLaneService baseFacilitiesTollStationLaneService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<FacilitiesTollStationLane>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<FacilitiesTollStationLane> result = baseFacilitiesTollStationLaneService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<FacilitiesTollStationLane>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<FacilitiesTollStationLane> pageUtil =  baseFacilitiesTollStationLaneService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{id}")
    public RespResult<FacilitiesTollStationLane> detail(@PathVariable("id") String id) {
        FacilitiesTollStationLane facilitiesTollStationLane = baseFacilitiesTollStationLaneService.detail(id);
        return RespResult.ok(facilitiesTollStationLane);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesTollStationLane facilitiesTollStationLane){
        baseFacilitiesTollStationLaneService.create(facilitiesTollStationLane);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody FacilitiesTollStationLane facilitiesTollStationLane){
        baseFacilitiesTollStationLaneService.update(facilitiesTollStationLane);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> ids){
        baseFacilitiesTollStationLaneService.delete(ids);
        return RespResult.ok();
    }
}
