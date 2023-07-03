package com.rhy.bdmp.collect.modules.camera.controller;

import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.collect.modules.camera.domain.bo.Camera;
import com.rhy.bdmp.collect.modules.camera.domain.vo.CameraResponse;
import com.rhy.bdmp.collect.modules.camera.service.ICameraService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @description 采集视频资源数据
 * @author jiangzhimin
 * @date 2021-08-02 17:39
 */
@Api(tags = "采集视频资源数据")
@RestController
@RequestMapping("/bdmp/collect/camera")
public class CameraController {

    @Resource
    private ICameraService cameraService;

    @ApiOperation("获取节点树(云台)")
    @PostMapping("/cameratree")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "isAll", value = "是否全部", required = true, dataType = "boolean", paramType = "query"),
                    @ApiImplicitParam(name = "resId", value = "资源层级ID", required = false, dataType = "string", paramType = "query")
            }
    )
    public RespResult<List<Camera>> cameraTree(@RequestParam(required = true) boolean isAll, String resId) throws InterruptedException {
        resId = StrUtil.isBlank(resId) ? "" : resId;
        return RespResult.ok(cameraService.cameraTree(isAll, resId));
    }

    @ApiOperation("视频列表(腾路)")
    @GetMapping("/tlVideoList")
    @ResponseBody
    public RespResult<CameraResponse> tlVideoTest(String pageSize, String page, String type){
        return RespResult.ok(cameraService.tlVideo(pageSize,page,type));
    }

    @ApiOperation("同步视频（yt）")
    @GetMapping("/sync/yt")
    @ResponseBody
    public RespResult syncYT(){
        cameraService.syncCameraByYt();
        return RespResult.ok();
    }

    @ApiOperation("同步视频（tl）")
    @GetMapping("/sync/tl")
    @ResponseBody
    public RespResult syncTL(){
        cameraService.syncCameraByTl();
        return RespResult.ok();
    }

    @ApiOperation("同步视频(sy)")
    @GetMapping("/sync/sy")
    public RespResult syncSY(){
        return RespResult.ok(cameraService.syncCameraBySY());
    }
}
