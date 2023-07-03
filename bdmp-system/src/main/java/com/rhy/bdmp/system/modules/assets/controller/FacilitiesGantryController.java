package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesGantryService;
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
@Api(tags = {"门架管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/facilities/gantry")
public class FacilitiesGantryController {
    @Resource
    private IFacilitiesGantryService facilitiesGantryService;

    @ApiOperation("新增门架")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesGantry facilitiesGantry){
        facilitiesGantryService.create(facilitiesGantry);
        log.info(LogUtil.buildUpParams("新增门架", LogTypeEnum.OPERATE.getCode(), facilitiesGantry.getGantryId()));
        return RespResult.ok();
    }

    @ApiOperation("删除门架")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> gantryIds){
        facilitiesGantryService.delete(gantryIds);
        log.info(LogUtil.buildUpParams("删除门架", LogTypeEnum.OPERATE.getCode(), gantryIds));
        return RespResult.ok();
    }
}
