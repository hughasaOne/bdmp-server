package com.rhy.bdmp.collect.modules.jdxt.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.collect.modules.jdxt.service.IJdxtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;


/**
 * @description 机电系统数据采集
 * @author jiangzhimin
 * @date 2021-08-02 17:39
 */
@Api(tags = "机电系统数据采集")
@RestController
@RequestMapping("/bdmp/collect/jdxt")
public class JdxtController {

    @Resource
    private IJdxtService jdxtService;

    @ApiOperation(value = "同步运营公司", position = 1)
    @GetMapping("/syncOperatingCompany")
    public RespResult syncOperatingCompany() {
        Boolean result = jdxtService.syncOperatingCompany();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步运营路段", position = 2)
    @GetMapping("/syncWaySection")
    public RespResult syncWaySection() {
        Boolean result = jdxtService.syncWaySection();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步设施", position = 3)
    @GetMapping("/syncFacilities")
    public RespResult syncFacilities() {
        Boolean result = jdxtService.syncFacilities();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步设备", position = 4)
    @GetMapping("/syncDevice")
    public RespResult syncDevice() {
        Boolean result = jdxtService.syncDevice();
        return RespResult.ok(result);
    }

//    @ApiOperation(value = "同步视频资源（腾路）", position = 5)
//    @GetMapping("/syncVideoResourceTl")
//    public RespResult syncVideoResourceTl() {
//        Boolean result = jdxtService.syncVideoResourceTl();
//        return RespResult.ok(result);
//    }
//
//    @ApiOperation(value = "同步视频资源（云台）", position = 6)
//    @GetMapping("/syncVideoResourceYt")
//    public RespResult syncVideoResourceYt() {
//        Boolean result = jdxtService.syncVideoResourceYt();
//        return RespResult.ok(result);
//    }

    // 字典相关：字典、品牌、设备、系统
    @ApiOperation(value = "同步字典", position = 7)
    @GetMapping("/syncDict")
    public RespResult syncDict() {
        Boolean result = jdxtService.syncDict();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步字典（品牌）", position = 8)
    @GetMapping("/syncDictBrand")
    public RespResult syncDictBrand() {
        Boolean result = jdxtService.syncDictBrand();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步字典（设备）", position = 9)
    @GetMapping("/syncDictDevice")
    public RespResult syncDictDevice() {
        Boolean result = jdxtService.syncDictDevice();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步字典（系统）", position = 10)
    @GetMapping("/syncDictSystem")
    public RespResult syncDictSystem() {
        Boolean result = jdxtService.syncDictSystem();
        return RespResult.ok(result);
    }

    // 扩展相关：路段、收费站、服务区、桥梁、隧道、门架
    @ApiOperation(value = "同步路段", position = 11)
    @GetMapping("/syncRoute")
    public RespResult syncRoute() {
        Boolean result = jdxtService.syncRoute();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步收费站", position = 12)
    @GetMapping("/syncTollStation")
    public RespResult syncTollStation() {
        Boolean result = jdxtService.syncTollStation();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步服务区", position = 13)
    @GetMapping("/syncServiceArea")
    public RespResult syncServiceArea() {
        Boolean result = jdxtService.syncServiceArea();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步桥梁", position = 14)
    @GetMapping("/syncBridge")
    public RespResult syncBridge() {
        Boolean result = jdxtService.syncBridge();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步隧道", position = 15)
    @GetMapping("/syncTunnel")
    public RespResult syncTunnel() {
        Boolean result = jdxtService.syncTunnel();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步门架", position = 16)
    @GetMapping("/syncGantry")
    public RespResult syncGantry() {
        Boolean result = jdxtService.syncGantry();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "同步收费站车道", position = 17)
    @GetMapping("/syncTollStationLane")
    public RespResult syncTollStationLane() {
        Boolean result = jdxtService.syncTollStationLane();
        return RespResult.ok(result);
    }

    @ApiOperation(value = "根据运营公司Id同步运营公司", position = 18)
    @GetMapping("/syncOperatingCompanyArgs")
    public RespResult syncOperatingCompanyArgs(@ApiParam("运营公司id") @RequestParam Set<String> companyIds) {
        Boolean result = jdxtService.syncOperatingCompanyArgs(companyIds);
        return RespResult.ok(result);
    }


    @ApiOperation(value = "根据运营路段Id同步运营路段", position = 19)
    @GetMapping("/syncWaySectionArgs")
    public RespResult syncWaySectionArgs(@ApiParam("运营路段Id") @RequestParam(required = false) Set<String> waysectionIds) {
        Boolean result = jdxtService.syncWaySectionArgs(null, waysectionIds);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "根据设施Id同步设施", position = 20)
    @GetMapping("/syncFacilitiesArgs")
    public RespResult syncFacilitiesArgs(@ApiParam("运营设施Id") @RequestParam Set<String> facilitiesIds) {
        Boolean result = jdxtService.syncFacilitiesArgs(null, facilitiesIds);
        return RespResult.ok(result);
    }
}
