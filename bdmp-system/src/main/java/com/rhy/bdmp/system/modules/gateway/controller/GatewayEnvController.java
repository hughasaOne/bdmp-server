package com.rhy.bdmp.system.modules.gateway.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayEnvVO;
import com.rhy.bdmp.system.modules.gateway.service.GatewayEnvService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author author
 * @version V1.0
 * @description 前端控制器
 * @date 2022-06-22 09:40
 **/
@Api(tags = {"管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/env")
public class GatewayEnvController {

    @Resource
    private GatewayEnvService baseTBdmpGatewayEnvService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<GatewayEnvVO>> list() {
        return RespResult.ok(baseTBdmpGatewayEnvService.getServiceEnv());
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody GatewayEnvVO gatewayEnvVo) {
        int result = baseTBdmpGatewayEnvService.create(gatewayEnvVo);
        if (result > 0){
            log.info(LogUtil.buildUpParams("新增路由环境成功", LogTypeEnum.OPERATE.getCode(), gatewayEnvVo.getId()));
        }
        else {
            log.warn(LogUtil.buildUpParams("新增路由环境失败", LogTypeEnum.OPERATE.getCode(), null));
        }
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody GatewayEnvVO gatewayEnvVo) {
        int result = baseTBdmpGatewayEnvService.update(gatewayEnvVo);
        if (result > 0){
            log.info(LogUtil.buildUpParams("修改路由环境成功", LogTypeEnum.OPERATE.getCode(), gatewayEnvVo.getId()));
        }
        else {
            log.warn(LogUtil.buildUpParams("修改路由环境失败", LogTypeEnum.OPERATE.getCode(), gatewayEnvVo.getId()));
        }
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping()
    public RespResult delete(@RequestParam("id") String id) {
        if (baseTBdmpGatewayEnvService.delete(id)) {
            log.info(LogUtil.buildUpParams("删除路由环境成功", LogTypeEnum.OPERATE.getCode(), id));
            return RespResult.ok();
        } else {
            log.warn(LogUtil.buildUpParams("删除路由环境失败", LogTypeEnum.OPERATE.getCode(), id));
            return RespResult.error("删除环境失败");
        }
    }

    @ApiOperation("获取当前环境")
    @GetMapping("/getCurrentEnvId")
    public RespResult getCurrentEnvId() {
        GatewayEnvVO env = baseTBdmpGatewayEnvService.getCurrentEnvId();
        return RespResult.ok(env);
    }
}
