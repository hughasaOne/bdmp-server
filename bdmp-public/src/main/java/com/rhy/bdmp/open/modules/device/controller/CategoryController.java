package com.rhy.bdmp.open.modules.device.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.device.domain.bo.CategoryDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceCategoryBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceCategoryVo;
import com.rhy.bdmp.open.modules.device.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author weicaifu
 */
@Api(tags = "设备分类服务")
@RestController
@RequestMapping("/bdmp/public/device/category")
public class CategoryController {
    @Resource
    private ICategoryService categoryService;

    @ApiOperation(value = "获取设备分类", position = 1)
    @PostMapping("/v1")
    public RespResult<List<DeviceCategoryVo>> getCategoryList(@RequestBody DeviceCategoryBo deviceCategoryBo){
        return RespResult.ok(categoryService.getCategoryList(deviceCategoryBo));
    }

    @ApiOperation(value = "获取设备分类详情", position = 2)
    @PostMapping("/detail/v1")
    public RespResult<DeviceCategoryVo> getCategoryDetail(@RequestBody CategoryDetailBo categoryBo){
        return RespResult.ok(categoryService.getCategoryDetail(categoryBo));
    }
}
