package com.rhy.bdmp.system.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceQueryVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceVo;
import com.rhy.bdmp.system.modules.sys.service.ResourceService;
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

@Api(tags = {"资源管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/resource")
public class ResourceController {
    @Resource
    private ResourceService resourceService;

    @ApiOperation(value = "查询资源", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = resourceService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<com.rhy.bdmp.base.modules.sys.domain.po.Resource>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    @ApiOperation(value = "查看资源(根据ID)")
    @GetMapping(value = "/{resourceId}")
    public RespResult detail(@PathVariable("resourceId") String resourceId) {
        ResourceVo resource = resourceService.detail(resourceId);
        return RespResult.ok(resource);
    }

    /**
     * @Description: 删除资源
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @ApiOperation("删除资源")
    @Log("资源：删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> resourceIds) {
        resourceService.delete(resourceIds);
        log.info(LogUtil.buildUpParams("删除资源", LogTypeEnum.OPERATE.getCode(),resourceIds));
        return RespResult.ok();
    }

    /**
     * @Description: 新增资源
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增资源")
    @Log("资源：新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody com.rhy.bdmp.base.modules.sys.domain.po.Resource resource) {
        resourceService.create(resource);
        log.info(LogUtil.buildUpParams("新增资源",LogTypeEnum.OPERATE.getCode(),resource.getResourceId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改资源
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改资源")
    @Log("资源：修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody com.rhy.bdmp.base.modules.sys.domain.po.Resource resource) {
        resourceService.update(resource);
        log.info(LogUtil.buildUpParams("修改资源",LogTypeEnum.OPERATE.getCode(),resource.getResourceId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询当前用户拥有的目录、菜单（返回资源树节点）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", example = "true"),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "string", required = true)
    })
    @GetMapping(value = "/findResourcesByCurrentUser")
    public RespResult findResourcesByCurrentUser(@RequestParam(required = false) Boolean isUseUserPermissions, @RequestParam(required = true) String appId) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        List<NodeVo> result = resourceService.findResourcesByCurrentUser(isUseUserPermissions, appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询当前用户拥有的目录、菜单、按钮（返回资源树节点）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", example = "true"),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "string", required = true)
    })
    @GetMapping(value = "/findResourcesByCurrentUser2")
    public RespResult findResourcesByCurrentUser2(@RequestParam(required = false) Boolean isUseUserPermissions, @RequestParam(required = true) String appId) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        List<NodeVo> result = resourceService.findResourcesByCurrentUser2(isUseUserPermissions, appId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "用户菜单树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", example = "true"),
            @ApiImplicitParam(name = "appId", value = "应用ID", dataType = "string", required = true)
    })
    @GetMapping(value = "/getUserMenuTree")
    public RespResult getUserMenuTree(@RequestParam(required = false) Boolean isUseUserPermissions, @RequestParam(required = true) String appId) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        return RespResult.ok(resourceService.getUserMenuTree(isUseUserPermissions, appId));
    }

    @ApiOperation(value = "查询资源树节点")
    @PostMapping(value = "/findAppResourceTree")
    public RespResult findAppResourceTree(@Validated @RequestBody ResourceQueryVo resourceQueryVo) {
        List<NodeVo> result = resourceService.findAppResourceTree(resourceQueryVo);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询资源子节点（根据应用ID、资源ID）")
    @PostMapping(value = "/findResourceChildren")
    public RespResult findResourceChildren(@RequestParam(value = "appId", required = true) String appId, @RequestParam(value = "includeId", required = false) String includeId, @RequestParam(value = "parentId", required = false) String parentId, @RequestBody(required = false) QueryVO queryVO) {
        List<ResourceVo> result = resourceService.findResourceChildren(appId, parentId, includeId, queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation("根据父节点获取资源列表")
    @PostMapping(value = "/listByParentId")
    public RespResult<List<ResourceVo>> listByParentId(@RequestBody(required = false) QueryVO queryVO){
        List<ResourceVo> orgVoLIst =  resourceService.listByParentId(queryVO);
        return RespResult.ok(orgVoLIst);
    }

}
