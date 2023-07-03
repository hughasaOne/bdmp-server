package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceExternal;
import com.rhy.bdmp.system.modules.assets.domain.bo.DeviceExternalBo;
import com.rhy.bdmp.system.modules.assets.service.IDeviceExternalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Api(tags = {"外设管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/device/external")
public class DeviceExternalController {

    @Resource
    private IDeviceExternalService externalService;

    @ApiOperation(value = "获取外接设备列表")
    @GetMapping(value = "/list")
    public RespResult list(@RequestParam String deviceId) {
        return RespResult.ok(externalService.list(deviceId));
    }

    @ApiOperation(value = "获取可选择的设备列表")
    @PostMapping(value = "/getDeviceList")
    public RespResult<List<Device>> getDeviceList(@RequestBody DeviceExternalBo externalBo) {
        return RespResult.ok(externalService.getDeviceList(externalBo));
    }

    @ApiOperation(value = "添加外设")
    @PostMapping(value = "/add")
    public RespResult add(@RequestBody List<DeviceExternal> deviceExternalList) {
        externalService.add(deviceExternalList);
        return RespResult.ok();
    }

    @ApiOperation(value = "删除外设")
    @PostMapping(value = "/delete")
    public RespResult delete(@RequestParam String deviceId,@RequestBody Set<String> externalIds) {
        externalService.delete(deviceId,externalIds);
        return RespResult.ok();
    }
}
