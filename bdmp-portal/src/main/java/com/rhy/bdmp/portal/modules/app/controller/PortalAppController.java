package com.rhy.bdmp.portal.modules.app.controller;

import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bcp.common.resutl.CommonResult;
import com.rhy.bdmp.portal.modules.app.service.PortalAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = {"应用查询"})
@Slf4j
@RestController
@RequestMapping("/bdmp/portal/app")
public class PortalAppController {

    @Resource
    private PortalAppService appService;

    @ApiOperation(value = "查询当前用户的应用访问权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping(value = "/findAppByCurrentUser")
    public CommonResult findAppByCurrentUser(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        List<App> result = appService.findAppByCurrentUser(isUseUserPermissions);
        return CommonResult.ok().put("data", result);
    }
}
