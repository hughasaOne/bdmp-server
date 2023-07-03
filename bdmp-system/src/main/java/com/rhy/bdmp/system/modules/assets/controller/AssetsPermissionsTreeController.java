package com.rhy.bdmp.system.modules.assets.controller;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.assets.service.IAssetsPermissionsTreeService;
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

/**
 * @author jiangzhimin
 * @version V1.0
 * @description
 * @date 2021-04-19 11:23
 **/
@Api(tags = {"台账资源-权限树"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/permissions/tree")
public class AssetsPermissionsTreeController {

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    /**
     * 用户组织机构权限树
     */
    @ApiOperation(value = "用户组织机构权限树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping(value = "/getUserOrgTree")
    public RespResult getUserOrgTree(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        return RespResult.ok(assetsPermissionsTreeService.getUserOrgTree(isUseUserPermissions));
    }

    @ApiOperation("组织树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping("org")
    public RespResult assetsOrgTree(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        return RespResult.ok(assetsPermissionsTreeService.getAssetsOrgTree(isUseUserPermissions));
    }

    @ApiOperation("组织路段树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping("org/way")
    public RespResult assetsOrgWayTree(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        return RespResult.ok(assetsPermissionsTreeService.getAssetsOrgWayTree(isUseUserPermissions));
    }

/*    @ApiOperation("组织路段设施树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "isShowChildren", value = "是否获取子设施", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping("org/way/fac")
    public RespResult assetsOrgWayFacTree(@RequestParam(required = false) Boolean isUseUserPermissions, @RequestParam(required = false) Boolean isShowChildren) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        // 默认不显示子设施
        isShowChildren = null == isShowChildren ? false : isShowChildren;
        return RespResult.ok(assetsPermissionsTreeService.getAssetsOrgWayFacTree(isUseUserPermissions, isShowChildren));
    }*/

    @ApiOperation("组织路段树-设备管理使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping("org/way2")
    public RespResult assetsOrgWay2Tree(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        return RespResult.ok(assetsPermissionsTreeService.getAssetsOrgWay2Tree(isUseUserPermissions));
    }

    @ApiOperation("组织-路段-设施树根据名称查询(前端异步查询)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping("org/way/fac/search")
    public RespResult asyncTree(@RequestParam(required = true) String search,@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        return RespResult.ok(assetsPermissionsTreeService.asyncTree(search,isUseUserPermissions));
    }

    @ApiOperation("组织路段设施树-到一级设施")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping("org/way/fac")
    public RespResult assetsOrgWayFacTree(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        return RespResult.ok(assetsPermissionsTreeService.getAssetsOrgWayFacTree(isUseUserPermissions));
    }

    @ApiOperation("根据路段查找设施")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "waysectionId", value = "路段id", dataType = "string", paramType = "query")
    })
    @GetMapping("/fac")
    public RespResult<List<Tree<String>>> getFacByWayId(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                        @RequestParam(required = true) String waysectionId) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<Tree<String>> facShortVO = assetsPermissionsTreeService.getFacByWayId(isUseUserPermissions, waysectionId);
        return RespResult.ok(facShortVO);
    }

    @ApiOperation("根据设施查找子设施")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "facilitiesId", value = "路段id", dataType = "string", paramType = "query")
    })
    @GetMapping("/fac/child")
    public RespResult<List<Tree<String>>> getChildFacilities(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                             @RequestParam(required = true) String facilitiesId) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<Tree<String>> facShortVO = assetsPermissionsTreeService.getChildFacilities(isUseUserPermissions, facilitiesId);
        return RespResult.ok(facShortVO);
    }

}