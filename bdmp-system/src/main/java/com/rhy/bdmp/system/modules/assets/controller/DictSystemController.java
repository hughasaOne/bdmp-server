package com.rhy.bdmp.system.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictSystemService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
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

/**
 * Created on 2021/11/1.
 *
 * @author duke
 */
@Api(tags = {"系统字典管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/dict/system")
public class DictSystemController {
    @Resource
    private IDictSystemService dictSystemService;

    @ApiOperation("新增系统字典")
    @PostMapping
    public RespResult<Boolean> create(@Validated @RequestBody DictSystem dictSystem) {
        dictSystemService.create(dictSystem);
        log.info(LogUtil.buildUpParams("新增系统字典", LogTypeEnum.OPERATE.getCode(), dictSystem.getSystemId()));
        return RespResult.ok();
    }

    @ApiOperation("修改系统字典")
    @PutMapping
    public RespResult<Boolean> update(@Validated @RequestBody DictSystem dictSystem) {
        log.info(LogUtil.buildUpParams("修改系统字典", LogTypeEnum.OPERATE.getCode(), dictSystem.getSystemId()));
        return RespResult.ok(dictSystemService.update(dictSystem));
    }

    @ApiOperation("删除系统字典")
    @DeleteMapping
    public RespResult<Integer> delete(@RequestBody Set<String> systemIds) {
        log.info(LogUtil.buildUpParams("修改系统字典", LogTypeEnum.OPERATE.getCode(), systemIds));
        return RespResult.ok(dictSystemService.delete(systemIds));
    }

    @ApiOperation(value = "查看系统字典(根据ID)")
    @GetMapping(value = "/{systemId}")
    public RespResult<DictSystem> detail(@PathVariable("systemId") String systemId) {
        DictSystem dictSystem = dictSystemService.detail(systemId);
        return RespResult.ok(dictSystem);
    }

    @ApiOperation("查询系统字典(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "param", value = "系统字典查询条件", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/page")
    public RespResult<PageUtils> page(@RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                      @RequestParam(required = false, defaultValue = "20") Integer size,
                                      @RequestParam(required = false) String param) {
        Page<DictSystem> result = dictSystemService.queryPage(currentPage, size, param);
        return RespResult.ok(new PageUtils(result));
    }

    @ApiOperation("查询所有系统字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "系统字典查询条件", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/list")
    public RespResult<List<DictSystem>> list(@RequestParam(required = false) String param) {
        List<DictSystem> result = dictSystemService.list(param);
        return RespResult.ok(result);
    }

    @ApiOperation("获取系统字典")
    @PostMapping("/getDictSystem")
    public RespResult getDictSystem(@Validated @RequestBody DictBO dictBO) {
        return RespResult.ok(dictSystemService.getDictSystem(dictBO));
    }

}
