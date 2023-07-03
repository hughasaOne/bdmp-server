package com.rhy.bdmp.system.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppVo;
import com.rhy.bdmp.system.modules.sys.service.AppService;
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

@Api(tags = {"应用管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/app")
public class AppController {

    @Resource
    private AppService appService;

    @ApiOperation(value = "查询应用信息", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = appService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<App>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    @ApiOperation(value = "查看应用信息(根据ID)")
    @GetMapping(value = "/{appId}")
    public RespResult detail(@PathVariable("appId") String appId) {
        AppVo app = appService.detail(appId);
        return RespResult.ok(app);
    }

    /**
     * @Description: 删除应用
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    @ApiOperation("删除应用信息")
    @Log("app：app删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> appIds) {
        appService.delete(appIds);
        log.info(LogUtil.buildUpParams("删除应用", LogTypeEnum.OPERATE.getCode(),appIds));
        return RespResult.ok();
    }

    /**
     * @Description: 新增应用
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增应用信息")
    @Log("app：app新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody App app) {
        appService.create(app);
        log.info(LogUtil.buildUpParams("新增应用", LogTypeEnum.OPERATE.getCode(),app.getAppId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改应用
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改应用信息")
    @Log("app：app修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody App app) {
        appService.update(app);
        log.info(LogUtil.buildUpParams("修改应用", LogTypeEnum.OPERATE.getCode(),null));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询应用类型与应用树节点")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true")
    })
    @GetMapping(value = "/findAppTypeAndAppTree")
    public RespResult findAppTypeAndAppTree(@RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        List<NodeVo> result = appService.findAppTypeAndAppTree(isUseUserPermissions);
        return RespResult.ok(result);
    }

}
