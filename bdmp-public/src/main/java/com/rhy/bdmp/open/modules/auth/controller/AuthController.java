package com.rhy.bdmp.open.modules.auth.controller;

import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.resutl.CommonResult;
import com.rhy.bcp.common.resutl.Result;
import com.rhy.bdmp.open.modules.auth.feignclient.AuthFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/7/10
 * @description:  认证中心公共服务
 * @version: 1.0
 */
@Api(tags = "认证中心")
@RestController
@RequestMapping("bdmp/public/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    @Resource
    private AuthFeignService authFeignService;

    /**
     * 登录认证
     * @return
     */
    @ApiOperation("登录认证生成token")
    @PostMapping("/token")
    public Result userAuth(@RequestParam Map<String, String> parameters){
        return  authFeignService.userAuth(parameters);
    }

    /**
     * 系统退出/
     * @return
     */
    @ApiOperation("退出系统")
    @DeleteMapping("/logout")
    public Result userLogout(){
        return authFeignService.userLogout();
    }


    @ApiOperation("测试")
    @PostMapping("/test")
    public Result test(@ApiIgnore @RequestParam Map<String, String> parameters){
        return authFeignService.test(parameters);
    }

}
