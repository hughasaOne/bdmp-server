package com.rhy.bdmp.open.modules.auth.feignclient;


import com.rhy.bcp.common.resutl.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/7/10
 * @description:  认证中心接口客户端
 * @version: 1.0.0
 */
@FeignClient("bdmp-auth")
public interface AuthFeignService {

    /**
     * 登录认证
     * @return
     */
    @PostMapping("/oauth/token")
    Result userAuth(@ApiIgnore @RequestParam Map<String, String> parameters);


    @PostMapping("/oauth/test")
    Result test(@ApiIgnore @RequestParam Map<String, String> parameters);
    /**
     * 系统推出
     * @return
     */
    @DeleteMapping("/oauth/logout")
    Result userLogout();

}
