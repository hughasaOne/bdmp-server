package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceEmergencycallService;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 紧急电话 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"紧急电话管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-emergencycall")
public class BaseDeviceEmergencycallController {

	@Resource
	private IBaseDeviceEmergencycallService baseDeviceEmergencycallService;

    @ApiOperation(value = "查询紧急电话", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DeviceEmergencycall>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DeviceEmergencycall> result = baseDeviceEmergencycallService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询紧急电话(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DeviceEmergencycall>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DeviceEmergencycall> pageUtil =  baseDeviceEmergencycallService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看紧急电话(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<DeviceEmergencycall> detail(@PathVariable("deviceId") String deviceId) {
        DeviceEmergencycall deviceEmergencycall = baseDeviceEmergencycallService.detail(deviceId);
        return RespResult.ok(deviceEmergencycall);
    }

    @ApiOperation("新增紧急电话")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceEmergencycall deviceEmergencycall){
        baseDeviceEmergencycallService.create(deviceEmergencycall);
        return RespResult.ok();
    }

    @ApiOperation("修改紧急电话")
    @PutMapping
    public RespResult update(@Validated @RequestBody DeviceEmergencycall deviceEmergencycall){
        baseDeviceEmergencycallService.update(deviceEmergencycall);
        return RespResult.ok();
    }

    @ApiOperation("删除紧急电话")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDeviceEmergencycallService.delete(deviceIds);
        return RespResult.ok();
    }
}
