package com.rhy.bdmp.portal.modules.sso.controller;

import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.resutl.CommonResult;
import com.rhy.bdmp.portal.modules.sso.service.ISSOClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: lipeng
 * @Date: 2021/5/11
 * @description:  业务系统单点登录证控制器
 * @version: 1.0.0
 */

@Api(tags = "业务系统单点登录")
@RestController
@RequestMapping("bdmp/portal/sso")
@AllArgsConstructor
@Slf4j
public class SSOClientController {

    @Resource
    private ISSOClientService ssoClientService;

    @ApiOperation(value = "测试")
    @GetMapping("/test")
    public CommonResult ssoTest(){
        return CommonResult.ok("test");
    }


    @ApiOperation(value = "根据应用ID获取应用跳转地址")
    @GetMapping (value = "/getAppURL/{appId}")
    public CommonResult getAppURL(@PathVariable("appId") String appId) {
        String result = ssoClientService.getAppUrl(appId);
        CommonResult  commonResult= JSONUtil.toBean(result, CommonResult.class);
        return commonResult;
    }
}
