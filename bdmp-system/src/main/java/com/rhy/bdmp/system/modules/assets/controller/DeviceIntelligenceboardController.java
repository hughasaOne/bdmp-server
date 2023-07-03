package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard;
import com.rhy.bdmp.system.modules.assets.service.IDeviceIntelligenceboardService;
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
@Api(tags = {"情报板管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/device/intelligenceboard")
public class DeviceIntelligenceboardController {

    @Resource
    private IDeviceIntelligenceboardService deviceIntelligenceboardService;

    @ApiOperation("新增情报板")
    @PostMapping
    public RespResult create(@Validated @RequestBody DeviceIntelligenceboard deviceIntelligenceboard) {
        deviceIntelligenceboardService.create(deviceIntelligenceboard);
        log.info(LogUtil.buildUpParams("新增情报板", LogTypeEnum.OPERATE.getCode(), deviceIntelligenceboard.getDeviceId()));
        return RespResult.ok();
    }
}
