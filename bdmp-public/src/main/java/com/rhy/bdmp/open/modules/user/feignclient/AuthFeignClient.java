package com.rhy.bdmp.open.modules.user.feignclient;


import com.rhy.bcp.common.resutl.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

@FeignClient(name = "bdmp-auth",contextId = "AuthFeignClient")
public interface AuthFeignClient {

    /**
     * 登录认证
     */
    @PostMapping("/oauth/token")
    Result login(@ApiIgnore @RequestParam Map<String, String> parameters);
}
