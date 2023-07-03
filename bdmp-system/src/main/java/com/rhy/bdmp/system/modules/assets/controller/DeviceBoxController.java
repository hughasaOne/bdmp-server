package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.system.modules.assets.service.DeviceBoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 终端箱前端控制器
 * @author 魏财富
 */
@RestController
@ResponseBody
@Api(tags = {"终端箱"})
@RequestMapping("/bdmp/system/assets/device/box")
public class DeviceBoxController {
    @Resource
    private DeviceBoxService boxService;

    /**
     * 新增终端箱信息
     */
    @ApiOperation("同步终端箱系统的终端箱信息")
    @GetMapping("/synBoxInfo")
    public RespResult synBoxInfo(){
        return RespResult.ok(boxService.synBoxInfo());
    }
}
