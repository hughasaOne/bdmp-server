package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceIntelligenceboardService;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 情报板 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"情报板管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-intelligenceboard")
public class BaseDeviceIntelligenceboardController {

	@Resource
	private IBaseDeviceIntelligenceboardService baseDeviceIntelligenceboardService;

    @ApiOperation(value = "查询情报板", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DeviceIntelligenceboard>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DeviceIntelligenceboard> result = baseDeviceIntelligenceboardService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询情报板(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DeviceIntelligenceboard>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DeviceIntelligenceboard> pageUtil =  baseDeviceIntelligenceboardService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看情报板(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<DeviceIntelligenceboard> detail(@PathVariable("deviceId") String deviceId) {
        DeviceIntelligenceboard deviceIntelligenceboard = baseDeviceIntelligenceboardService.detail(deviceId);
        return RespResult.ok(deviceIntelligenceboard);
    }

    @ApiOperation("新增情报板")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceIntelligenceboard deviceIntelligenceboard){
        baseDeviceIntelligenceboardService.create(deviceIntelligenceboard);
        return RespResult.ok();
    }

    @ApiOperation("修改情报板")
    @PutMapping
    public RespResult update(@Validated @RequestBody DeviceIntelligenceboard deviceIntelligenceboard){
        baseDeviceIntelligenceboardService.update(deviceIntelligenceboard);
        return RespResult.ok();
    }

    @ApiOperation("删除情报板")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDeviceIntelligenceboardService.delete(deviceIds);
        return RespResult.ok();
    }
}
