package com.rhy.bdmp.open.modules.assets.controller.base;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.open.modules.assets.domain.vo.*;
import com.rhy.bdmp.open.modules.assets.service.IAssetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @description
* @author jiangzhimin
* @date 2021-12-22 09:50
* @version V1.0
**/
@Api(tags = "台账-基础数据服务")
@RestController
@RequestMapping("/bdmp/public/assets")
@Slf4j
public class AssetsBaseController {

    @Resource
    private IAssetsService assetsService;


    @ApiOperation(value = "2.4.1 设备查询（运营）", position = 4)
    @PostMapping("/getDataDevice")
    public RespResult<PageUtil<DeviceVo>> getDataDevice(@RequestBody @Validated DataDeviceBo dataDeviceBo) {
        if (null == dataDeviceBo.getIsUseUserPermissions()){
            dataDeviceBo.setIsUseUserPermissions(true);
        }
        return RespResult.ok(assetsService.getDataDevice(dataDeviceBo));
    }

    @ApiOperation(value = "2.4.2 设备查询（厂商）", position = 4)
    @PostMapping("/getManufacturerDevice")
    public RespResult<PageUtil<DeviceVo>> getManufacturerDevice(@RequestBody @Validated ManufacturerDeviceBo manufacturerDeviceBo) {
        return RespResult.ok(assetsService.getManufacturerDevice(manufacturerDeviceBo));
    }

    // 2.7.2.1 组织-数据资产树（集团-运营公司）
    @ApiOperation(value = "2.7.2.1 集团-运营公司 (数据资产树)", position = 8)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "isAsync", value = "是否异步加载", dataType = "boolean", required = false,defaultValue = "false")
    })
    @GetMapping("/getGroupOrgTree")
    public RespResult<List<Tree<String>>> getGroupOrgTree(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                          @RequestParam(required = false) String orgId,
                                                          @RequestParam(required = false) String nodeType,
                                                          @RequestParam(required = false) Boolean isAsync) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        isAsync = null != isAsync && isAsync;
        return RespResult.ok(assetsService.getGroupOrgTree(isUseUserPermissions, orgId, nodeType,isAsync));
    }

    // 2.7.2.2 组织路段-数据资产树（集团-运营公司-路段）
    @ApiOperation(value = "2.7.2.2 组织路段-数据资产树（集团-运营公司-路段） (数据资产树)", position = 9)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "isAsync", value = "是否异步加载", dataType = "boolean", required = false,defaultValue = "false")
    })
    @GetMapping("/getGroupOrgWayTree")
    public RespResult<List<Tree<String>>> getGroupOrgWayTree(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                             @RequestParam(required = false) String orgId,
                                                             @RequestParam(required = false) String nodeType,
                                                             @RequestParam(required = false) Boolean isAsync) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        isAsync = null != isAsync && isAsync;
        return RespResult.ok(assetsService.getGroupOrgWayTree(isUseUserPermissions, orgId, nodeType,isAsync));
    }

    // 2.7.2.3 设施一级
    @ApiOperation(value = "2.7.2.3 数据资产树 设施一级", position = 10)
    @PostMapping("/getFac")
    public RespResult<List<Tree<String>>> getFac(@RequestBody DataFacBo dataFacBo) {
        if (null == dataFacBo.getIsUseUserPermissions()){
            dataFacBo.setIsUseUserPermissions(true);
        }
        if (null == dataFacBo.getIsAsync()){
            dataFacBo.setIsAsync(false);
        }
        return RespResult.ok(assetsService.getFac(dataFacBo));
    }

    // 2.7.2.4 设施二级
    @ApiOperation(value = "2.7.2.4 数据资产树 设施二级", position = 11)
    @PostMapping("/getSubFac")
    public RespResult<List<Tree<String>>> getSubFac(@RequestBody DataFacBo dataFacBo) {
        if (null == dataFacBo.getIsUseUserPermissions()){
            dataFacBo.setIsUseUserPermissions(true);
        }
        if (null == dataFacBo.getIsAsync()){
            dataFacBo.setIsAsync(false);
        }
        return RespResult.ok(assetsService.getSubFac(dataFacBo));
    }

    @ApiOperation(value = "2.7.3.1  厂商资产树（集团-公司）", position = 12)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "isAsync", value = "是否异步加载", dataType = "boolean", required = false,defaultValue = "false")
    })
    @GetMapping("/getGroupOrgManufacturerTree")
    public RespResult<List<Tree<String>>> getGroupOrgManufacturerTree(@RequestParam(required = false) String orgId,
                                                                      @RequestParam(required = false) String nodeType,
                                                                      @RequestParam(required = false) Boolean isAsync) {
        isAsync = null != isAsync && isAsync;
        return RespResult.ok(assetsService.getGroupOrgManufacturerTree(orgId,nodeType,isAsync));
    }

    @ApiOperation(value = "2.7.3.2  厂商资产树（集团-公司-路段）", position = 13)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "isAsync", value = "是否异步加载", dataType = "boolean", required = false,defaultValue = "false")
    })
    @GetMapping("/getGroupOrgWayManufacturerTree")
    public RespResult<List<Tree<String>>> getGroupOrgWayManufacturerTree(@RequestParam(required = false) String orgId,
                                                                         @RequestParam(required = false) String nodeType,
                                                                         @RequestParam(required = false) Boolean isAsync) {
        isAsync = null != isAsync && isAsync;
        return RespResult.ok(assetsService.getGroupOrgWayManufacturerTree(orgId,nodeType,isAsync));
    }

    @ApiOperation(value = "2.7.3.3  厂商资产树（设施一级）", position = 14)
    @PostMapping("/getFacManufacturerTree")
    public RespResult<List<Tree<String>>> getFacManufacturer(@RequestBody ManufacturerFacBo manufacturerFacBo) {
        if (null == manufacturerFacBo.getIsAsync()){
            manufacturerFacBo.setIsAsync(false);
        }
        return RespResult.ok(assetsService.getFacManufacturer(manufacturerFacBo));
    }

    @ApiOperation(value = "2.7.3.4  厂商资产树（设施二级）", position = 15)
    @PostMapping("/getSubFacManufacturerTree")
    public RespResult<List<Tree<String>>> getSubFacManufacturerTree(@RequestBody ManufacturerFacBo manufacturerFacBo) {
        if (null == manufacturerFacBo.getIsAsync()){
            manufacturerFacBo.setIsAsync(false);
        }
        return RespResult.ok(assetsService.getSubFacManufacturerTree(manufacturerFacBo));
    }

    @ApiOperation(value = "2.80.1 查询字典列表(根据字典目录code查询)", position = 37)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictDirCodes", value = "字典目录code（13XXXX、12XXXX等）", dataType = "array", required = true)
    })
    @PostMapping("/api/dict/getCodeList")
    public RespResult getCodeList(@RequestBody(required = true) Set<String> dictDirCodes,@RequestParam(value = "dictName",required = false) String dictName) {
        return RespResult.ok(assetsService.getCodeList(dictDirCodes,dictName));
    }

    @ApiOperation(value = "2.80.2 查询机构列表(根据机构类型查询)", position = 38)
    @PostMapping("/api/org/getOrgList")
    public RespResult getOrgList(@RequestBody @Validated OrgListBo orgListBo) {
        return RespResult.ok(assetsService.getOrgList(orgListBo));
    }

    @ApiOperation(value = "2.80.3 查询机构详情（根据orgId）", position = 39)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织id", dataType = "string", required = true)
    })
    @GetMapping("/api/org/getOrgInfoById")
    public RespResult<OrgVo> getOrgInfoById(@RequestParam(required = true) String orgId) {
        return RespResult.ok(assetsService.getOrgInfoById(orgId));
    }

    @ApiOperation(value = "2.80.4 根据机构id查询机构子部门（根据orgId）", position = 39)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "组织id", dataType = "string", required = true)
    })
    @GetMapping("/api/org/getOrgSonById")
    public RespResult getOrgSonById(@RequestParam(required = true) String orgId) {
        return RespResult.ok(assetsService.getOrgSonById(orgId));
    }

    @ApiOperation(value = "2.80.4.1 查询设备详情（根据设备编号查询）", position = 39)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceCode", value = "设备编号", dataType = "string", required = true)
    })
    @GetMapping("/api/device/getDeviceByCode")
    public RespResult<DeviceVo> getDeviceByCode(@RequestParam(required = true) String deviceCode) {
        return RespResult.ok(assetsService.getDeviceByCode(deviceCode));
    }

    @ApiOperation(value = "2.80.4.2 查询设备详情（根据设备编号查询）（网关专用）", position = 39)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceCode", value = "设备编号", dataType = "string", required = true)
    })
    @GetMapping("/api/device/getDeviceGateway")
    public RespResult<DeviceGatewayVo> getDeviceGateway(@RequestParam(required = true) String deviceCode) {
        return RespResult.ok(assetsService.getDeviceGateway(deviceCode));
    }

    @ApiOperation(value = "2.80.4.3 查询设备详情（根据设备id查询）", position = 39)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", dataType = "string", required = true)
    })
    @GetMapping("/api/device/getDeviceById")
    public RespResult<DeviceVo> getDeviceById(@RequestParam(required = true) String deviceId) {
        return RespResult.ok(assetsService.getDeviceById(deviceId));
    }

    @ApiOperation(value = "2.80.6 查询设备的外接设备", position = 41)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", dataType = "string", required = true)
    })
    @GetMapping("/api/device/getExternalDevice")
    public RespResult<GatewayExternalVo> getExternalDevice(@RequestParam(required = true) String deviceId) {
        return RespResult.ok(assetsService.getExternalDevice(deviceId));
    }

    @ApiOperation(value = "2.80.7 查询设施列表", position = 42)
    @PostMapping("/api/fac/getFacList")
    public RespResult getFacList(@RequestBody @Validated FacListBo facListBo) {
        if (null == facListBo.getIsUseUserPermissions()){
            facListBo.setIsUseUserPermissions(true);
        }
        return RespResult.ok(assetsService.getFacList(facListBo));
    }

    @ApiOperation(value = "2.80.8 查询设施详情", position = 43)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "facId", value = "设施id", dataType = "string", required = true)
    })
    @GetMapping("/api/fac/getFacById")
    public RespResult<FacilitiesVo> getFacById(@RequestParam String facId) {
        return RespResult.ok(assetsService.getFacById(facId));
    }

    @ApiOperation(value = "2.80.9 查询路段列表", position = 44)
    @PostMapping("/api/way/getWayList")
    public RespResult<PageUtil<Waysection>> getWayList(@RequestBody @Validated WayListBo wayListBo) {
        if (null == wayListBo.getIsUseUserPermissions()){
            wayListBo.setIsUseUserPermissions(true);
        }
        return RespResult.ok(new PageUtil<Waysection>(assetsService.getWayList(wayListBo)));
    }

    @ApiOperation(value = "2.80.10 查询路段详情", position = 45)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wayId", value = "查询路段详情", dataType = "string", required = true)
    })
    @GetMapping("/api/way/getWayById")
    public RespResult<Waysection> getWayById(@RequestParam String wayId) {
        return RespResult.ok(assetsService.getWayById(wayId));
    }

    @ApiOperation(value = "2.80.11 设备字典分类查询", position = 46)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchType", value = "查询类型（1:设备总类，2:设备类型）", dataType = "int", required = false,example = "1"),
            @ApiImplicitParam(name = "typeId", value = "设备总类/设备类型的id", dataType = "string", required = false),
            @ApiImplicitParam(name = "name", value = "设备字典类型的名称", dataType = "string", required = false)
    })
    @GetMapping("/api/device/dict/getDeviceDict")
    public RespResult getDeviceDict(@RequestParam(required = false) Integer searchType,
                                    @RequestParam(required = false) String typeId,
                                    @RequestParam(required = false) String name) {
        return RespResult.ok(assetsService.getDeviceDict(searchType,typeId,name));
    }

    @ApiOperation(value = "2.80.12 车道设备列表", position = 47)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "laneId", value = "车道ID", dataType = "string", required = true)
    })
    @GetMapping("/api/device/getLaneDeviceList")
    public RespResult<List<LaneDeviceVo>> getLaneDeviceList(@RequestParam(required = true) String laneId) {
        return RespResult.ok(assetsService.getLaneDeviceList(laneId));
    }

    @ApiOperation(value = "2.81.00 设施分类统计及列表明细", position = 48)
    @PostMapping("/api/fac/statistics")
    public RespResult<Map<String,Object>> facStatistics(@Validated @RequestBody FacStatisticsBo facStatisticsBo) {
        boolean isUseUserPermissions = null == facStatisticsBo.getIsUseUserPermissions() || facStatisticsBo.getIsUseUserPermissions();
        facStatisticsBo.setIsUseUserPermissions(isUseUserPermissions);
        return RespResult.ok(assetsService.facStatistics(facStatisticsBo));
    }

    @ApiOperation(value = "2.81.01 设备分类统计及列表明细", position = 48)
    @PostMapping("/api/device/statistics")
    public RespResult<Map<String,Object>> deviceStatistics(@Validated @RequestBody DeviceStatisticsBo deviceStatisticsBo) {
        boolean isUseUserPermissions = null == deviceStatisticsBo.getIsUseUserPermissions() || deviceStatisticsBo.getIsUseUserPermissions();
        deviceStatisticsBo.setIsUseUserPermissions(isUseUserPermissions);
        return RespResult.ok(assetsService.deviceStatistics(deviceStatisticsBo));
    }

}