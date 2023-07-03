package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesBridgeService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
@Api(tags = {"桥梁管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/facilities/bridge")
public class FacilitiesBridgeController {
    @Resource
    private IFacilitiesBridgeService facilitiesBridgeService;

    @ApiOperation("新增桥梁")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesBridge facilitiesBridge){
        facilitiesBridgeService.create(facilitiesBridge);
        log.info(LogUtil.buildUpParams("新增桥梁", LogTypeEnum.OPERATE.getCode(), facilitiesBridge.getBridgeId()));
        return RespResult.ok();
    }

    @ApiOperation("删除桥梁")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> bridgeIds){
        facilitiesBridgeService.delete(bridgeIds);
        log.info(LogUtil.buildUpParams("删除桥梁", LogTypeEnum.OPERATE.getCode(), bridgeIds));
        return RespResult.ok();
    }

}
