package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceService;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 设备 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"设备管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device")
public class BaseDeviceController {

	@Resource
	private IBaseDeviceService baseDeviceService;

    @ApiOperation(value = "查询设备", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Device>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Device> result = baseDeviceService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询设备(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Device>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Device> pageUtil =  baseDeviceService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看设备(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<Device> detail(@PathVariable("deviceId") String deviceId) {
        Device device = baseDeviceService.detail(deviceId);
        return RespResult.ok(device);
    }

    @ApiOperation("新增设备")
    @PostMapping
    public RespResult create(@Validated @RequestBody Device device){
        baseDeviceService.create(device);
        return RespResult.ok();
    }

    @ApiOperation("修改设备")
    @PutMapping
    public RespResult update(@Validated @RequestBody Device device){
        baseDeviceService.update(device);
        return RespResult.ok();
    }

    @ApiOperation("删除设备")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDeviceService.delete(deviceIds);
        return RespResult.ok();
    }
}
