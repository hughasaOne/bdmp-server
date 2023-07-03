package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesServiceAreaService;
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
@Api(tags = {"服务区管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/facilities/serviceArea")
public class FacilitiesServiceAreaController {
    @Resource
    private IFacilitiesServiceAreaService facilitiesServiceAreaService;

    @ApiOperation("新增服务区")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesServiceArea facilitiesServiceArea){
        facilitiesServiceAreaService.create(facilitiesServiceArea);
        log.info(LogUtil.buildUpParams("新增服务区", LogTypeEnum.OPERATE.getCode(), facilitiesServiceArea.getServiceAreaId()));
        return RespResult.ok();
    }

    @ApiOperation("删除服务区")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> serviceAreaIds){
        facilitiesServiceAreaService.delete(serviceAreaIds);
        log.info(LogUtil.buildUpParams("删除服务区", LogTypeEnum.OPERATE.getCode(), serviceAreaIds));
        return RespResult.ok();
    }
}
