package com.rhy.bdmp.system.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictDeviceService;
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
 * @author yanggj
 * @version V1.0
 * @description 设备字典 前端控制器
 * @date 2021-10-26 09:05
 **/
@Api(tags = {"设备字典管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/device/dict")
public class DictDeviceController {

    @Resource
    private IDictDeviceService deviceDictService;

    @ApiOperation("获取设备字典")
    @PostMapping("/getDictDevice")
    public RespResult getDictDevice(@Validated @RequestBody DictBO dictBO){
        return RespResult.ok(deviceDictService.getDictDevice(dictBO));
    }

    @ApiOperation("查询字典列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "设备字典查询条件", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/list")
    public RespResult<List<DictDevice>> list(@RequestParam(required = false) String param) {
        List<DictDevice> result = deviceDictService.list(param);
        return RespResult.ok(result);
    }

    @ApiOperation("查询字典(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "param", value = "设备字典查询条件", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/page")
    public RespResult<PageUtils> page(@RequestParam(required = false, defaultValue = "1") Integer currentPage,
                                      @RequestParam(required = false, defaultValue = "20") Integer size,
                                      @RequestParam(required = false) String param) {
        Page<DictDevice> result = deviceDictService.queryPage(currentPage, size, param);
        return RespResult.ok(new PageUtils(result));
    }

    @ApiOperation("获取子节点")
    @GetMapping("/getChildren")
    public RespResult getChildren(@RequestParam(value = "parentId") String parentId) {
        return RespResult.ok(deviceDictService.getChildren(parentId));
    }

    @ApiOperation(value = "查看设备字典(根据ID)")
    @GetMapping(value = "/{deviceDictId}")
    public RespResult detail(@PathVariable("deviceDictId") String deviceDictId) {
        return RespResult.ok(deviceDictService.detail(deviceDictId));
    }

    @ApiOperation("新增设备字典")
    @PostMapping
    public RespResult<?> create(@Validated @RequestBody DictDevice deviceDict) {
        boolean result = deviceDictService.create(deviceDict);
        if (result) {
            log.info(LogUtil.buildUpParams("新增设备字典", LogTypeEnum.OPERATE.getCode(), deviceDict.getDeviceDictId()));
            return RespResult.ok();
        }
        return RespResult.error("新增设备字典失败");
    }

    @ApiOperation("修改设备字典")
    @PutMapping
    public RespResult<?> update(@Validated @RequestBody DictDevice deviceDict) {
        boolean result = deviceDictService.update(deviceDict);
        if (result) {
            log.info(LogUtil.buildUpParams("修改设备字典", LogTypeEnum.OPERATE.getCode(), deviceDict.getDeviceDictId()));
            return RespResult.ok();
        }
        return RespResult.error("修改设备字典失败");
    }

    @ApiOperation("删除设备字典")
    @DeleteMapping
    public RespResult<?> delete(@RequestBody Set<String> deviceDictIds) {
        int result = deviceDictService.delete(deviceDictIds);
        log.info(LogUtil.buildUpParams("修改设备字典", LogTypeEnum.OPERATE.getCode(), deviceDictIds));
        return RespResult.ok();
    }
}
