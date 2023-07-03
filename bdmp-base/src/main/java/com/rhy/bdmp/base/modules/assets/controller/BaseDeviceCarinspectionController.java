package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceCarinspectionService;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 车检器 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"车检器管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-carinspection")
public class BaseDeviceCarinspectionController {

	@Resource
	private IBaseDeviceCarinspectionService baseDeviceCarinspectionService;

    @ApiOperation(value = "查询车检器", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DeviceCarinspection>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DeviceCarinspection> result = baseDeviceCarinspectionService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询车检器(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DeviceCarinspection>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DeviceCarinspection> pageUtil =  baseDeviceCarinspectionService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看车检器(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<DeviceCarinspection> detail(@PathVariable("deviceId") String deviceId) {
        DeviceCarinspection deviceCarinspection = baseDeviceCarinspectionService.detail(deviceId);
        return RespResult.ok(deviceCarinspection);
    }

    @ApiOperation("新增车检器")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceCarinspection deviceCarinspection){
        baseDeviceCarinspectionService.create(deviceCarinspection);
        return RespResult.ok();
    }

    @ApiOperation("修改车检器")
    @PutMapping
    public RespResult update(@Validated @RequestBody DeviceCarinspection deviceCarinspection){
        baseDeviceCarinspectionService.update(deviceCarinspection);
        return RespResult.ok();
    }

    @ApiOperation("删除车检器")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDeviceCarinspectionService.delete(deviceIds);
        return RespResult.ok();
    }
}
