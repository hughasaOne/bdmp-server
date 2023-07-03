package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceBoxService;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceBox;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 终端箱基本信息表 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"终端箱基本信息表管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-box")
public class BaseDeviceBoxController {

	@Resource
	private IBaseDeviceBoxService baseDeviceBoxService;

    @ApiOperation(value = "查询终端箱基本信息表", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DeviceBox>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DeviceBox> result = baseDeviceBoxService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询终端箱基本信息表(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DeviceBox>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DeviceBox> pageUtil =  baseDeviceBoxService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看终端箱基本信息表(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<DeviceBox> detail(@PathVariable("deviceId") String deviceId) {
        DeviceBox deviceBox = baseDeviceBoxService.detail(deviceId);
        return RespResult.ok(deviceBox);
    }

    @ApiOperation("新增终端箱基本信息表")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceBox deviceBox){
        baseDeviceBoxService.create(deviceBox);
        return RespResult.ok();
    }

    @ApiOperation("修改终端箱基本信息表")
    @PutMapping
    public RespResult update(@Validated @RequestBody DeviceBox deviceBox){
        baseDeviceBoxService.update(deviceBox);
        return RespResult.ok();
    }

    @ApiOperation("删除终端箱基本信息表")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDeviceBoxService.delete(deviceIds);
        return RespResult.ok();
    }
}
