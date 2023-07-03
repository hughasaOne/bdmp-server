package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesBridgeService;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 桥梁 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"桥梁管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/facilities-bridge")
public class BaseFacilitiesBridgeController {

	@Resource
	private IBaseFacilitiesBridgeService baseFacilitiesBridgeService;

    @ApiOperation(value = "查询桥梁", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<FacilitiesBridge>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<FacilitiesBridge> result = baseFacilitiesBridgeService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询桥梁(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<FacilitiesBridge>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<FacilitiesBridge> pageUtil =  baseFacilitiesBridgeService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看桥梁(根据ID)")
    @GetMapping(value = "/{bridgeId}")
    public RespResult<FacilitiesBridge> detail(@PathVariable("bridgeId") String bridgeId) {
        FacilitiesBridge facilitiesBridge = baseFacilitiesBridgeService.detail(bridgeId);
        return RespResult.ok(facilitiesBridge);
    }

    @ApiOperation("新增桥梁")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesBridge facilitiesBridge){
        baseFacilitiesBridgeService.create(facilitiesBridge);
        return RespResult.ok();
    }

    @ApiOperation("修改桥梁")
    @PutMapping
    public RespResult update(@Validated @RequestBody FacilitiesBridge facilitiesBridge){
        baseFacilitiesBridgeService.update(facilitiesBridge);
        return RespResult.ok();
    }

    @ApiOperation("删除桥梁")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> bridgeIds){
        baseFacilitiesBridgeService.delete(bridgeIds);
        return RespResult.ok();
    }
}
