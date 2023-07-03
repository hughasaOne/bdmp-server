package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.assets.domain.bo.SavePeripheralsBo;
import com.rhy.bdmp.system.modules.assets.service.BoxExternalService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 终端箱外接设备前端控制器
 * @author 魏财富
 */
@Slf4j
@RestController
@ResponseBody
@Api(tags = {"终端箱外接设备"})
@RequestMapping("/bdmp/system/assets/device/box/external")
public class BoxExternalController {
    @Resource
    private BoxExternalService externalService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "boxId",value = "终端箱id",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "facId",value = "设施Id",dataType = "String",paramType = "query"),
    })
    @ApiOperation("查询终端箱可用的端口号和可用的设备")
    @GetMapping("/getEnablePortsAndPer")
    public RespResult getEnablePortsAndPer(@RequestParam(required = true) String boxId,@RequestParam(required = true) String facId){
        log.info(LogUtil.buildUpParams("查看终端箱外接设备", LogTypeEnum.ACCESS.getCode(), boxId));
        return RespResult.ok(externalService.getEnablePortsAndPer(boxId,facId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId",value = "终端箱Id",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "sn",value = "终端箱sn",dataType = "String",paramType = "query"),
    })
    @ApiOperation("查询当前终端箱端口情况")
    @GetMapping("/getPortInfo")
    public RespResult getPortInfo(@RequestParam(required = false) String deviceId,@RequestParam(required = false) String sn){
        log.info(LogUtil.buildUpParams("查看终端箱端口使用情况", LogTypeEnum.ACCESS.getCode(), sn));
        return RespResult.ok(externalService.getPortInfo(deviceId,sn));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "boxId",value = "终端箱Id",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "portNum",value = "端口号",dataType = "String",paramType = "query"),
    })
    @ApiOperation("查询当前终端箱具体端口情况")
    @GetMapping("/getPortInfoByPortAndId")
    public RespResult getPortInfoByPortAndId(@RequestParam(required = true) String boxId, @RequestParam(required = true) String portNum){
        return RespResult.ok(externalService.getPortInfoByPortAndId(boxId,portNum));
    }

    @ApiOperation("终端箱新增外接设备")
    @PostMapping("/save")
    public RespResult save(@RequestBody List<SavePeripheralsBo> savePeripheralsBoList){
        log.info(LogUtil.buildUpParams("新增终端箱外接设备", LogTypeEnum.OPERATE.getCode(), null));
        return RespResult.ok(externalService.save(savePeripheralsBoList));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "boxId",value = "终端箱id",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "portNum",value = "终端箱端口号",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "deviceId",value = "外设Id",dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "externalId",value = "外设表Id",dataType = "String",paramType = "query")
    })
    @ApiOperation("终端箱修改外接设备")
    @PutMapping("/update")
    public RespResult update(@RequestParam(required = true) String boxId,@RequestParam(required = true) String portNum,@RequestParam(required = true) String deviceId,@RequestParam(required = true) String externalId){
        log.info(LogUtil.buildUpParams("修改终端箱外接设备", LogTypeEnum.OPERATE.getCode(), null));
        return RespResult.ok(externalService.update(boxId,portNum,deviceId,externalId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "externalIds",value = "外设表Id",dataType = "String",paramType = "query")
    })
    @ApiOperation("解除外设关系")
    @DeleteMapping("/delPerRelation")
    public RespResult delPerRelation(@RequestBody Set<String> externalIds){
        log.info(LogUtil.buildUpParams("解除外设关系", LogTypeEnum.OPERATE.getCode(), null));
        return RespResult.ok(externalService.delPerRelation(externalIds));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceIp",value = "外设Ip",dataType = "String",paramType = "query")
    })
    @ApiOperation("测试终端箱与外设的网络是否能够通信")
    @GetMapping("/pingTest")
    public RespResult pingTest(@RequestParam(required = true) String deviceIp){
        return RespResult.ok(externalService.pingTest(deviceIp));
    }

}
