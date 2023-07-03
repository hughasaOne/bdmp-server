package com.rhy.bdmp.system.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.AppType;
import com.rhy.bdmp.base.modules.sys.service.IBaseAppTypeService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.domain.vo.AppTypeVo;
import com.rhy.bdmp.system.modules.sys.service.AppTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Api(tags = {"应用类别管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/appType")
public class AppTypeController {

    @Resource
    private AppTypeService appTypeService;
    @Resource
    private IBaseAppTypeService baseAppTypeService;

    @ApiOperation(value = "查询应用类别", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = appTypeService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<AppType>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    /**
     * @Description: 删除应用类别
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @ApiOperation("删除应用类别")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> appTypeIds) {
        appTypeService.delete(appTypeIds);
        log.info(LogUtil.buildUpParams("删除应用类型", LogTypeEnum.OPERATE.getCode(),appTypeIds));
        return RespResult.ok();
    }

    /**
     * @Description: 新增应用类别
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增应用类别")
    @PostMapping
    public RespResult create(@Validated @RequestBody AppType appType) {
        appTypeService.create(appType);
        log.info(LogUtil.buildUpParams("新增应用类型", LogTypeEnum.OPERATE.getCode(),appType.getAppTypeId()));
        return RespResult.ok();
    }

    /**
     * @Description: 修改应用类别
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改应用类别")
    @PutMapping
    public RespResult update(@Validated @RequestBody AppType appType) {
        appTypeService.update(appType);
        log.info(LogUtil.buildUpParams("修改应用类型", LogTypeEnum.OPERATE.getCode(),appType.getAppTypeId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "查询应用类型子节点(应用类型懒加载)[根据应用类型ID]")
    @PostMapping(value = "/findAppTypeChildren")
    public RespResult findAppTypeChildren(@RequestParam(value = "includeId", required = false) String includeId, @RequestParam(value = "parentId", required = false) String parentId, @RequestBody(required = false) QueryVO queryVO) {
        List<AppTypeVo> result = appTypeService.findAppTypeChildren(parentId, includeId, queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查看应用类别(根据ID)")
    @GetMapping(value = "/{appTypeId}")
    public RespResult<AppType> detail(@PathVariable("appTypeId") String appTypeId) {
        AppType appType = baseAppTypeService.detail(appTypeId);
        return RespResult.ok(appType);
    }
}
