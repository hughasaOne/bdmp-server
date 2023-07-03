package com.rhy.bdmp.system.modules.assets.controller;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryBindingDataBo;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryDeviceBo;
import com.rhy.bdmp.system.modules.assets.service.IDeviceCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author weicaifu
 */
@Api(tags = {"设备分类管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/device/category")
public class DeviceCategoryController {
    @Resource
    private IDeviceCategoryService categoryService;

    @ApiOperation("设备分类树")
    @GetMapping(value = "/tree")
    public RespResult<List<Tree<String>>> getCategoryTree() {
        return RespResult.ok(categoryService.getCategoryTree());
    }

    @ApiOperation("设备分类选择项")
    @GetMapping(value = "/item")
    public RespResult getCategoryItem() {
        return RespResult.ok(categoryService.getCategoryItem());
    }

    @ApiOperation("详情")
    @GetMapping(value = "/detail")
    public RespResult detail(@RequestParam String categoryId) {
        return RespResult.ok(categoryService.detail(categoryId));
    }

    @ApiOperation("查询分类下绑定的数据")
    @PostMapping(value = "/bindingData")
    public RespResult getBindingData(@RequestBody CategoryBindingDataBo bindingDataBo) {
        return RespResult.ok(categoryService.getBindingData(bindingDataBo));
    }

    @ApiOperation("查找设备")
    @PostMapping(value = "/device")
    public RespResult device(@RequestBody @Validated CategoryDeviceBo deviceBo) {
        return RespResult.ok(categoryService.device(deviceBo));
    }

    @ApiOperation("新增")
    @PostMapping(value = "/add")
    public RespResult add(@RequestBody DeviceCategory deviceCategory) {
        categoryService.add(deviceCategory);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping()
    public RespResult update(@RequestBody DeviceCategory deviceCategory) {
        categoryService.update(deviceCategory);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping()
    public RespResult delete(@RequestBody Set<String> ids) {
        categoryService.delete(ids);
        return RespResult.ok();
    }
}
