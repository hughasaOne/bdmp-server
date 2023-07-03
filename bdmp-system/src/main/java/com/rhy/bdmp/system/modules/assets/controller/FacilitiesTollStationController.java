package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesTollStationService;
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
@Api(tags = {"收费站管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/facilities/tollStation")
public class FacilitiesTollStationController {
    @Resource
    private IFacilitiesTollStationService facilitiesTollStationService;

    @ApiOperation("新增收费站")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesTollStation facilitiesTollStation){
        facilitiesTollStationService.create(facilitiesTollStation);
        log.info(LogUtil.buildUpParams("新增收费站", LogTypeEnum.OPERATE.getCode(), facilitiesTollStation.getTollStationId()));
        return RespResult.ok();
    }

    @ApiOperation("删除收费站")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> tollStationIds){
        facilitiesTollStationService.delete(tollStationIds);
        log.info(LogUtil.buildUpParams("删除收费站", LogTypeEnum.OPERATE.getCode(), tollStationIds));
        return RespResult.ok();
    }
}
