package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesTunnelService;
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
@Api(tags = {"隧道管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/facilities/tunnel")
public class FacilitiesTunnelController {
    @Resource
    private IFacilitiesTunnelService facilitiesTunnelService;

    @ApiOperation("新增隧道")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesTunnel facilitiesTunnel){
        facilitiesTunnelService.create(facilitiesTunnel);
        log.info(LogUtil.buildUpParams("新增隧道", LogTypeEnum.OPERATE.getCode(), facilitiesTunnel.getTunnelId()));
        return RespResult.ok();
    }

    @ApiOperation("删除隧道")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> tunnelIds){
        facilitiesTunnelService.delete(tunnelIds);
        log.info(LogUtil.buildUpParams("删除隧道", LogTypeEnum.OPERATE.getCode(), tunnelIds));
        return RespResult.ok();
    }
}
