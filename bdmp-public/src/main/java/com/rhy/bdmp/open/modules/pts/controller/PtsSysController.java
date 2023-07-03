package com.rhy.bdmp.open.modules.pts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bdmp.open.modules.pts.domain.vo.UserPermissionVo;
import com.rhy.bdmp.open.modules.pts.service.IPtsSysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yanggj
 * @Description: 公众出行服务-系统
 * @Date: 2021/12/15 14:37
 * @Version: 1.0.0
 */
@Api(tags = "公众出行服务-系统")
@Slf4j
@RestController
@RequestMapping("/bdmp/public/sys")
public class PtsSysController {

    private final IPtsSysService ptsSysService;

    public PtsSysController(IPtsSysService ptsSysService) {
        this.ptsSysService = ptsSysService;
    }

    @ApiOperation(value = "2.90.1 组织结构列表", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "orgType", value = "节点类型", dataType = "string", paramType = "query", required = false, example = "000300"),
    })
    @PostMapping("/org")
    public RespResult<PageUtils> queryPageOrg(@RequestParam(required = false) Integer currentPage,
                                              @RequestParam(required = false) Integer size,
                                              @RequestParam(required = false) String orgType) {
        Page<?> page = ptsSysService.queryPageOrg(currentPage, size,orgType);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "2.90.2 角色列表", position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "appId", value = "应用id", dataType = "string", paramType = "query", required = true, example = "1"),
    })
    @PostMapping("/role")
    public RespResult<PageUtils> queryPageRole(@RequestParam(required = false) Integer currentPage,
                                               @RequestParam(required = false) Integer size,
                                               @RequestParam(required = true) String appId) {
        Page<?> page = ptsSysService.queryPageRole(currentPage, size, appId);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "2.90.3 用户列表", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "appId", value = "应用id", dataType = "string", paramType = "query", required = true, example = "1"),
    })
    @PostMapping("/user")
    public RespResult<PageUtils> queryPageUser(@RequestParam(required = false) Integer currentPage,
                                               @RequestParam(required = false) Integer size,
                                               @RequestParam(required = true) String appId) {
        Page<?> page = ptsSysService.queryPageUser(currentPage, size, appId);
        return RespResult.ok(new PageUtils(page));
    }

    /**
     * 查询用户拥有的组织机构权限
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "string"),
            @ApiImplicitParam(name = "appId", value = "应用id", dataType = "string")
    })
    @ApiOperation(value = "2.90.4 查询用户拥有的组织机构权限", position = 4)
    @GetMapping(value = "/getUserOrgPermission")
    public RespResult<List<UserPermissionVo>> getUserOrgPermission(@RequestParam(required = false) String userId,
                                                                   @RequestParam(required = false) String appId) {
        return RespResult.ok(ptsSysService.getUserOrgPermission(userId,appId));
    }


}
