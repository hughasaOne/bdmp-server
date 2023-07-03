package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseCameraResourceYtService;
import com.rhy.bdmp.base.modules.assets.domain.po.CameraResourceYt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 视频资源(云台) 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"视频资源(云台)管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/camera-resource-yt")
public class BaseCameraResourceYtController {

	@Resource
	private IBaseCameraResourceYtService baseCameraResourceYtService;

    @ApiOperation(value = "查询视频资源(云台)", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<CameraResourceYt>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<CameraResourceYt> result = baseCameraResourceYtService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询视频资源(云台)(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<CameraResourceYt>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<CameraResourceYt> pageUtil =  baseCameraResourceYtService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看视频资源(云台)(根据ID)")
    @GetMapping(value = "/{id}")
    public RespResult<CameraResourceYt> detail(@PathVariable("id") String id) {
        CameraResourceYt cameraResourceYt = baseCameraResourceYtService.detail(id);
        return RespResult.ok(cameraResourceYt);
    }

    @ApiOperation("新增视频资源(云台)")
    @PostMapping
    public RespResult create(@Validated @RequestBody CameraResourceYt cameraResourceYt){
        baseCameraResourceYtService.create(cameraResourceYt);
        return RespResult.ok();
    }

    @ApiOperation("修改视频资源(云台)")
    @PutMapping
    public RespResult update(@Validated @RequestBody CameraResourceYt cameraResourceYt){
        baseCameraResourceYtService.update(cameraResourceYt);
        return RespResult.ok();
    }

    @ApiOperation("删除视频资源(云台)")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> ids){
        baseCameraResourceYtService.delete(ids);
        return RespResult.ok();
    }
}
