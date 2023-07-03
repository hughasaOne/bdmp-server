package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseCameraResourceTlService;
import com.rhy.bdmp.base.modules.assets.domain.po.CameraResourceTl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 视频资源(腾路) 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"视频资源(腾路)管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/camera-resource-tl")
public class BaseCameraResourceTlController {

	@Resource
	private IBaseCameraResourceTlService baseCameraResourceTlService;

    @ApiOperation(value = "查询视频资源(腾路)", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<CameraResourceTl>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<CameraResourceTl> result = baseCameraResourceTlService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询视频资源(腾路)(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<CameraResourceTl>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<CameraResourceTl> pageUtil =  baseCameraResourceTlService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看视频资源(腾路)(根据ID)")
    @GetMapping(value = "/{cameraId}")
    public RespResult<CameraResourceTl> detail(@PathVariable("cameraId") String cameraId) {
        CameraResourceTl cameraResourceTl = baseCameraResourceTlService.detail(cameraId);
        return RespResult.ok(cameraResourceTl);
    }

    @ApiOperation("新增视频资源(腾路)")
    @PostMapping
    public RespResult create(@Validated @RequestBody CameraResourceTl cameraResourceTl){
        baseCameraResourceTlService.create(cameraResourceTl);
        return RespResult.ok();
    }

    @ApiOperation("修改视频资源(腾路)")
    @PutMapping
    public RespResult update(@Validated @RequestBody CameraResourceTl cameraResourceTl){
        baseCameraResourceTlService.update(cameraResourceTl);
        return RespResult.ok();
    }

    @ApiOperation("删除视频资源(腾路)")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> cameraIds){
        baseCameraResourceTlService.delete(cameraIds);
        return RespResult.ok();
    }
}
