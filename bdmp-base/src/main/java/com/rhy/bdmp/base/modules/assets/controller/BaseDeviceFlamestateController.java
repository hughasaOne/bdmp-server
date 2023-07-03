package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceFlamestateService;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 感温器 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"感温器管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-flamestate")
public class BaseDeviceFlamestateController {

	@Resource
	private IBaseDeviceFlamestateService baseDeviceFlamestateService;

    @ApiOperation(value = "查询感温器", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DeviceFlamestate>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DeviceFlamestate> result = baseDeviceFlamestateService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询感温器(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DeviceFlamestate>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DeviceFlamestate> pageUtil =  baseDeviceFlamestateService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看感温器(根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult<DeviceFlamestate> detail(@PathVariable("deviceId") String deviceId) {
        DeviceFlamestate deviceFlamestate = baseDeviceFlamestateService.detail(deviceId);
        return RespResult.ok(deviceFlamestate);
    }

    @ApiOperation("新增感温器")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceFlamestate deviceFlamestate){
        baseDeviceFlamestateService.create(deviceFlamestate);
        return RespResult.ok();
    }

    @ApiOperation("修改感温器")
    @PutMapping
    public RespResult update(@Validated @RequestBody DeviceFlamestate deviceFlamestate){
        baseDeviceFlamestateService.update(deviceFlamestate);
        return RespResult.ok();
    }

    @ApiOperation("删除感温器")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceIds){
        baseDeviceFlamestateService.delete(deviceIds);
        return RespResult.ok();
    }
}
