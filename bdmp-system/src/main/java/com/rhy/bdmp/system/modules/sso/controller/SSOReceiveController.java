package com.rhy.bdmp.system.modules.sso.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.sso.service.ISSOReceiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/5/11
 * @description:  单点登录请求接收端控制器
 * @version: 1.0.0
 */

@Api(tags = "单点登录")
@RestController
@RequestMapping("bdmp/system/sso")
@AllArgsConstructor
@Slf4j
public class SSOReceiveController {

    @Resource
    private ISSOReceiveService ssoReceiveService;

    @SneakyThrows
    @ApiOperation(value = "接收协同门户单点登录请求")
    @PostMapping (value = "/receivePortalRequest")
    public RespResult receivePortalRequest(@RequestParam(required = false ,value = "appToken") String appToken) {
        Object result = ssoReceiveService.getHomeUrl(appToken);
        return RespResult.ok(result);
    }

}
