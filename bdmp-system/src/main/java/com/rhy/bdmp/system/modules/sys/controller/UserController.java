package com.rhy.bdmp.system.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.domain.vo.*;
import com.rhy.bdmp.system.modules.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(tags = {"用户管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/user")
public class UserController {

    @Resource
    public UserService userService;

    /**
     * 查询用户拥有的组织机构权限
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "string"),
            @ApiImplicitParam(name = "appId", value = "应用id", dataType = "string")
    })
    @ApiOperation(value = "查询用户拥有的组织机构权限")
    @GetMapping(value = "/getUserOrgPermission")
    public RespResult<List<UserPermissionVo>> getUserOrgPermission(@RequestParam(required = false) String userId,
                                                                   @RequestParam(required = false) String appId) {
        return RespResult.ok(userService.getUserOrgPermission(userId,appId));
    }

    @ApiOperation(value = "查询用户", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = userService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<User>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    @ApiOperation(value = "查看用户(根据ID)")
    @GetMapping(value = "/{userId}")
    public RespResult detail(@PathVariable("userId") String userId) {
        UserVo user = userService.detail(userId);
        return RespResult.ok(user);
    }

    /**
     * @Description: 删除用户
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    @ApiOperation("删除用户")
    @Log("用户：删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> userIds) {
        userService.delete(userIds);
        log.info(LogUtil.buildUpParams("删除用户",LogTypeEnum.OPERATE.getCode(), userIds));
        return RespResult.ok();
    }

    /**
     * @Description: 新增用户
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增用户")
    @Log("用户：新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody User user) {
        userService.create(user);
        log.info(LogUtil.buildUpParams("新增用户", LogTypeEnum.OPERATE.getCode(),user.getUserId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改用户
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改用户")
    @Log("用户：修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody User user) {
        userService.update(user);
        log.info(LogUtil.buildUpParams("修改用户",LogTypeEnum.OPERATE.getCode(), user.getUserId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改用户配置
     * @Author: jiangzhimin
     * @Date: 2022/1/25
     */
    @Log("用户：配置修改")
    @ApiOperation("修改用户配置")
    @PutMapping(value = "/config")
    public RespResult updateUserConfig(@RequestBody Object userConfig) {
        userService.updateUserConfig(userConfig);
        log.info(LogUtil.buildUpParams("修改用户配置",LogTypeEnum.OPERATE.getCode(),WebUtils.getUserId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "更新用户应用访问权限（根据用户ID）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "string"),
            @ApiImplicitParam(name = "appIds", value = "应用ID集合", dataType = "list")
    })
    @Log("用户：应用访问权限修改")
    @PostMapping(value = "/updateUserAppByUserId")
    public RespResult updateUserAppByUserId(@RequestParam(value = "userId", required = true) String userId, @RequestBody Set<String> appIds) {
        //参数userId、appIds
        userService.updateUserAppByUserId(userId, appIds);
        log.info(LogUtil.buildUpParams("更新用户应用访问权限",LogTypeEnum.OPERATE.getCode(),userId));
        return RespResult.ok();
    }

    @ApiOperation(value = "更新用户角色权限（根据用户ID）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userRoleQueryMap", value = "{\"userId\":\"111\",\"roleIds\":[\"t\",\"a\"],\"appId\":\"111\"}", required = true)
    })
    @Log("用户：角色权限修改")
    @PostMapping(value = "/updateUserRoleByUserId")
    public RespResult updateUserRoleByUserId(@RequestBody Map<String, Object> userRoleQueryMap) {
        //传参userId、appId、roleIds
        userService.updateUseRoleByUserId(userRoleQueryMap);
        log.info(LogUtil.buildUpParams("更新用户角色权限",LogTypeEnum.OPERATE.getCode(),userRoleQueryMap.get("userId")));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询用户应用访问权限（根据用户ID,返回应用ID集合）")
    @GetMapping(value = "/findUserAppByUserId/{userId}")
    public RespResult findUserAppByUserId(@PathVariable(value = "userId", required = true) String userId) {
        List<String> result = userService.findAppIdsByUserId(userId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询当前用户的应用访问权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping(value = "/findCurrentUserApp")
    public RespResult findCurrentUserApp(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        List<App> result = userService.findCurrentUserApp(isUseUserPermissions);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询用户角色权限（根据用户ID和应用ID,返回角色ID集合）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "String", paramType = "query", required = false, example = "")
    })
    @GetMapping(value = "/findUserRoleByUserId")
    public RespResult findUserRoleByUserId(@RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "appId", required = true) String appId) {
        List<String> result = userService.findRoleIdsByUserIdAndAppId(userId, appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询用户组织树节点")
    @GetMapping(value = "/findUserOrgTree")
    public RespResult findUserOrgTree(@RequestParam(value = "appId", required = false) String appId) {
        List<NodeVo> result = userService.findUserOrgTree(appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询用户的应用数据权限（根据用户ID,返回应用ID集合）")
    @GetMapping(value = "/findAppPermissionIdsByUserId/{userId}")
    public RespResult findAppPermissionIdsByUserId(@PathVariable(value = "userId", required = true) String userId,
                                                   @RequestParam(value = "appId",required = false) String appId) {
        List<String> result = userService.findAppPermissionIdsByUserId(userId,appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询用户的台账数据权限（根据用户ID,返回台账对应权限ID集合和权限级别[1:机构，2:路段，3:设施]）")
    @GetMapping(value = "/findAssetsPermissionIdsByUserId/{userId}")
    public RespResult findAssetsPermissionIdsByUserId(@PathVariable(value = "userId", required = true) String userId,
                                                      @RequestParam(value = "appId",required = false) String appId) {
        Map<String, Object> result = userService.findAssetsPermissionIdsByUserId(userId,appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "更新用户的应用数据权限（根据用户ID、应用ID）")
    @Log("用户：应用数据权限修改")
    @PostMapping(value = "/updateAppPermission")
    public RespResult updateAppPermission(@Validated @RequestBody SysUserDataQueryVo sysUserDataQueryVo) {
        userService.updateAppPermission(sysUserDataQueryVo);
        log.info(LogUtil.buildUpParams("更新应用数据权限",LogTypeEnum.OPERATE.getCode(),sysUserDataQueryVo.getUserId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "更新用户的台账数据权限（根据用户ID、应用ID、权限级别）")
    @Log("用户：台账数据权限修改")
    @PostMapping(value = "/updateAssetsPermission")
    public RespResult updateAssetsPermission(@Validated @RequestBody AssetsUserDataQueryVo assetsUserDataQueryVo) {
        userService.updateAssetsPermission(assetsUserDataQueryVo);
        log.info(LogUtil.buildUpParams("更新用户台账数据权限",LogTypeEnum.OPERATE.getCode(),assetsUserDataQueryVo.getUserId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "更新用户的组织机构权限")
    @Log("用户：组织机构权限修改")
    @PostMapping(value = "/updateUserOrgPermission")
    public RespResult updateUserOrgPermission(@RequestBody SysUserDataQueryVo sysUserDataQueryVo) {
        userService.updateUserOrgPermission(sysUserDataQueryVo);
        log.info(LogUtil.buildUpParams("更新用户组织机构权限成功",LogTypeEnum.OPERATE.getCode(), sysUserDataQueryVo.getUserId()));
        return RespResult.ok();
    }

    @ApiOperation("修改密码")
    @Log("用户：密码修改")
    @PutMapping(value = "/updatePass")
    public RespResult updatePass(@RequestBody UserPassVo userPassVo) throws Exception {
        userService.updatePass(userPassVo);
        log.info(LogUtil.buildUpParams("修改密码",LogTypeEnum.OPERATE.getCode(),userPassVo.getUserId()));
        return RespResult.ok();
    }

    @ApiOperation("重置密码（用户ID、新密码）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "newPass", value = "新密码", dataType = "String", paramType = "query", required = false, example = "")
    })
    @Log("用户：密码重置")
    @PutMapping(value = "/resetPassword")
    public RespResult resetPassword(@RequestParam(value = "userId", required = true) String userId, @RequestParam(value = "newPass", required = true) String newPass) throws Exception {
        userService.resetPassword(userId, newPass);
        log.info(LogUtil.buildUpParams("重置密码",LogTypeEnum.OPERATE.getCode(),userId));
        return RespResult.ok();
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping(value = "/getCurrentUser")
    public RespResult getCurrentUser() {
        UserVo user = userService.detail(WebUtils.getUserId());
        user.setPassword("");
        return RespResult.ok(user);
    }

    @ApiOperation(value = "网关转发测试")
    @GetMapping(value = "/test")
    public RespResult test() {
        return RespResult.ok("test");
    }
}