package com.rhy.bdmp.open.modules.dict.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.dict.domain.bo.DeviceDictBo;
import com.rhy.bdmp.open.modules.dict.domain.bo.DictBo;
import com.rhy.bdmp.open.modules.dict.service.IDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author weicaifu
 */
@Api(tags = "字典服务")
@RestController
@RequestMapping("/bdmp/public/dict")
public class DictController {

    @Resource
    private IDictService dictService;

    @ApiOperation(value = "系统字典", position = 1)
    @PostMapping("/system/tree/v1")
    public RespResult getSystemTree(){
        return RespResult.ok(dictService.getSystemTree());
    }

    @ApiOperation(value = "设备字典", position = 2)
    @PostMapping("/device/tree/v1")
    public RespResult getDeviceTree(@RequestBody(required = false) DeviceDictBo deviceDictBo){
        return RespResult.ok(dictService.getDeviceTree(deviceDictBo));
    }

    @ApiOperation(value = "获取设备小类列表", position = 2)
    @GetMapping("/device/dict/list/v1")
    public RespResult getDeviceDictList(@RequestParam String deviceType){
        return RespResult.ok(dictService.getDeviceDictList(deviceType));
    }

    @ApiOperation(value = "获取字典列表", position = 2)
    @PostMapping("/tree/v1")
    public RespResult getDictTree(@RequestBody DictBo dictBo){
        return RespResult.ok(dictService.getDictTree(dictBo));
    }

    @ApiOperation(value = "获取字典详情", position = 3)
    @GetMapping("/detail/v1")
    public RespResult getDictDetail(@RequestParam String code){
        return RespResult.ok(dictService.getDictDetail(code));
    }
}
