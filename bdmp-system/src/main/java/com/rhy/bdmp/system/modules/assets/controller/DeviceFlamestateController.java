package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate;
import com.rhy.bdmp.system.modules.assets.service.IDeviceFlamestateService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/15
 */
@Api(tags = {"感温器管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/device/flamestate")
public class DeviceFlamestateController {

    @Resource
    private IDeviceFlamestateService deviceFlamestateService;

    @ApiOperation("新增感温器")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceFlamestate deviceFlamestate) {
        deviceFlamestateService.create(deviceFlamestate);
        log.info(LogUtil.buildUpParams("新增感温器", LogTypeEnum.OPERATE.getCode(), deviceFlamestate.getDeviceId()));
        return RespResult.ok();
    }
}
