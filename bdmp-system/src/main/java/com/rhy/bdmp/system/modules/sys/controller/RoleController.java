package com.rhy.bdmp.system.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.domain.vo.RoleVo;
import com.rhy.bdmp.system.modules.sys.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Api(tags = {"角色管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation(value = "查询角色", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = roleService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<Role>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    @ApiOperation(value = "查看角色(根据ID)")
    @GetMapping(value = "/{roleId}")
    public RespResult detail(@PathVariable("roleId") String roleId) {
        RoleVo role = roleService.detail(roleId);
        return RespResult.ok(role);
    }

    /**
     * @Description: 删除角色
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @ApiOperation("删除角色")
    @Log("角色：删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> roleIds) {
        roleService.delete(roleIds);
        log.info(LogUtil.buildUpParams("删除角色", LogTypeEnum.OPERATE.getCode(),roleIds));
        return RespResult.ok();
    }


    /**
     * @Description: 新增角色
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增角色")
    @Log("角色：新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody Role role) {
        roleService.create(role);
        log.info(LogUtil.buildUpParams("新增角色",LogTypeEnum.OPERATE.getCode(), role.getRoleId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改角色
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改角色")
    @Log("角色：修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody Role role) {
        roleService.update(role);
        log.info(LogUtil.buildUpParams("修改角色",LogTypeEnum.OPERATE.getCode(), role.getRoleId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "更新用户角色权限（根据角色ID）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID"),
            @ApiImplicitParam(name = "appId", value = "应用ID"),
            @ApiImplicitParam(name = "userIds", value = "用户ID集合")
    })
    @Log("角色：用户角色权限更新")
    @PostMapping(value = "/updateUserRoleByRoleId")
    public RespResult updateUserRoleByUserId(@RequestParam(value = "roleId", required = true) String roleId, @RequestParam(value = "appId", required = false) String appId, @RequestBody Set<String> userIds) {
        roleService.updateUseRoleByRoleId(roleId, appId, userIds);
        log.info(LogUtil.buildUpParams("更新用户角色权限",LogTypeEnum.OPERATE.getCode(), null));
        return RespResult.ok();
    }

    @ApiOperation(value = "更新角色资源权限（根据角色ID、应用ID）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID"),
            @ApiImplicitParam(name = "appId", value = "应用ID"),
            @ApiImplicitParam(name = "resourceIds", value = "资源ID集合")
    })
    @Log("角色：用户角色资源权限更新")
    @PostMapping(value = "/updateRoleResourceByRoleId")
    public RespResult updateRoleResourceByRoleId(@RequestParam(value = "roleId", required = true) String roleId, @RequestParam(value = "appId", required = true) String appId, @RequestBody Set<String> resourceIds) {
        roleService.updateRoleResourceByRoleIdAndAppId(roleId, appId, resourceIds);
        log.info(LogUtil.buildUpParams("更新角色资源权限",LogTypeEnum.OPERATE.getCode(), null));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询用户角色权限（根据角色ID，返回用户ID集合）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "String", paramType = "query", required = false, example = "")
    })
    @GetMapping(value = "/findUserRoleByRoleId")
    public RespResult findUserRoleByRoleId(@RequestParam(value = "roleId", required = true) String roleId, @RequestParam(value = "appId", required = false) String appId) {
        List<String> result = roleService.findUserIdsByRoleIdAndAppId(roleId, appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "根据角色分配资源权限时查询角色所拥有的资源权限（根据角色ID,返回资源ID集合）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "String", paramType = "query", required = false, example = "")
    })
    @GetMapping(value = "/findRoleResourceByRoleId")
    public RespResult findRoleResourceByRoleId(@RequestParam(value = "roleId", required = true) String roleId, @RequestParam(value = "appId", required = true) String appId) {
        List<String> result = roleService.findResourceIdsByRoleIdAndAppId(roleId, appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询应用角色树节点")
    @PostMapping(value = "/findAppRoleTree")
    public RespResult findAppRoleTree(@RequestBody Set<String> appIds) {
        List<NodeVo> result = roleService.findAppRoleTree(appIds);
        return RespResult.ok(result);
    }

}
