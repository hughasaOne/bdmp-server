package com.rhy.bdmp.open.modules.user.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bdmp/public/user")
public class LoginController {

    @ApiOperation("去登录页")
    @GetMapping("/to-login/v1")
    public String toLogin(@RequestParam String toUrl) {
        return "login/index";
    }
}
