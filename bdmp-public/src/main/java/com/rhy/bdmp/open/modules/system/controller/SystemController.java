package com.rhy.bdmp.open.modules.system.controller;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.system.domain.vo.MicroUserVo;
import com.rhy.bdmp.open.modules.system.domain.vo.UserPassVo;
import com.rhy.bdmp.open.modules.system.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: jiangzhimin
 * @Date: 2021/7/20
 * @description: 基础数据公共服务
 * @version: 1.0
 */
@Api(tags = "用户服务接口")
@RestController
@RequestMapping("/bdmp/public/user")
@AllArgsConstructor
@Slf4j
public class SystemController {


    @Resource
    private SystemService systemService;

    /**
     * 获取认证用户信息
     *
     * @return
     */
    @ApiOperation("获取认证用户信息")
    @GetMapping("/info")
    public RespResult microUserInfo() {
        MicroUserVo microUserVo = systemService.getMicroUserInfo();
        return RespResult.ok(microUserVo);
    }

    /**
     * 获取认证用户对应应用系统菜单权限
     *
     * @return
     */
    @ApiOperation("获取认证用户对应应用系统菜单权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "string", required = true)
    })
    @GetMapping("/resources")
    public RespResult findResourcesByCurrentUser(@RequestParam(required = true) String appId) {
        List<NodeVo> resourcesByCurrentUser2 = systemService.findResourcesByCurrentUser2(true, appId);
        return RespResult.ok(resourcesByCurrentUser2);
    }

    @ApiOperation(value = "用户菜单树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", example = "true"),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "string", required = true)
    })
    @GetMapping(value = "/getUserMenuTree")
    public RespResult getUserMenuTree(@RequestParam(required = false) Boolean isUseUserPermissions, @RequestParam(required = true) String appId) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        return RespResult.ok(systemService.getUserMenuTree(isUseUserPermissions, appId));
    }

    @ApiOperation(value = "组织用户树")
    @GetMapping(value = "/getOrgUserTree")
    public RespResult<List<Tree<String>>> getOrgUserTree() {
        return RespResult.ok(systemService.getOrgUserTree());
    }

    @ApiOperation("修改密码")
    @PutMapping(value = "/updatePass")
    public RespResult updatePass(@RequestBody UserPassVo userPassVo) throws Exception {
        systemService.updatePass(userPassVo);
        return RespResult.ok();
    }


}
