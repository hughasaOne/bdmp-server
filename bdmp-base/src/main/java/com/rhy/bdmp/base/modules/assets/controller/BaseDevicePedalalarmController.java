package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDevicePedalalarmService;
import com.rhy.bdmp.base.modules.assets.domain.po.DevicePedalalarm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 脚踏报警器 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"脚踏报警器管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-pedalalarm")
public class BaseDevicePedalalarmController {

	@Resource
	private IBaseDevicePedalalarmService baseDevicePedalalarmService;

    @ApiOperation(value = "查询脚踏报警器", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DevicePedalalarm>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DevicePedalalarm> result = baseDevicePedalalarmService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询脚踏报警器(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DevicePedalalarm>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DevicePedalalarm> pageUtil =  baseDevicePedalalarmService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看脚踏报警器(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<DevicePedalalarm> detail(@PathVariable("deviceId") String deviceId) {
        DevicePedalalarm devicePedalalarm = baseDevicePedalalarmService.detail(deviceId);
        return RespResult.ok(devicePedalalarm);
    }

    @ApiOperation("新增脚踏报警器")
    @PostMapping
    public RespResult create(@Validated @RequestBody DevicePedalalarm devicePedalalarm){
        baseDevicePedalalarmService.create(devicePedalalarm);
        return RespResult.ok();
    }

    @ApiOperation("修改脚踏报警器")
    @PutMapping
    public RespResult update(@Validated @RequestBody DevicePedalalarm devicePedalalarm){
        baseDevicePedalalarmService.update(devicePedalalarm);
        return RespResult.ok();
    }

    @ApiOperation("删除脚踏报警器")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDevicePedalalarmService.delete(deviceIds);
        return RespResult.ok();
    }
}
