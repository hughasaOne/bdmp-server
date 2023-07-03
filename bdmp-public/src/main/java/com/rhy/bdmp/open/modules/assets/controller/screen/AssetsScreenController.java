package com.rhy.bdmp.open.modules.assets.controller.screen;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.service.IAssetsService;
import com.rhy.bdmp.open.modules.assets.service.IDeviceService;
import com.rhy.bdmp.open.modules.assets.service.IFacilitiesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 资产服务接口
 * @Date: 2021/9/27 8:58
 * @Version: 1.0.0
 */
@Api(tags = "台账-大屏数据服务")
@RestController
@RequestMapping("/bdmp/public/assets")
@Slf4j
public class AssetsScreenController {

    @Resource
    private IAssetsService assetsService;

    @Resource
    private IFacilitiesService facilitiesService;

    @Resource
    private IDeviceService deviceService;

    /**
     * 获取高速总里程接口
     *
     * @return totalMileage 高速总里程，单位公里
     */
    @ApiOperation(value = "2.1获取高速总里程接口", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID，运营公司ID 或 路段ID(为空则选择用户有权限的所有路段总里程)", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/expressway/getMileageInfo")
    public RespResult<Map<String, Object>> getMileageInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                          @RequestParam(required = false) String orgId,
                                                          @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        Map<String, Object> resultMap = assetsService.getTotalMileage(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(resultMap);
    }

    /**
     * 获取收费站总况信息接口
     * 获取当前登录用户的收费站总数、正常运行收费站数量、临时关闭收费站数量、ETC车道数、MTC车道数
     * @return TollStationInfoBO
     */
    @ApiOperation(value = "2.2获取收费站总况信息接口", position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID，运营公司ID 或 路段ID(为空则选择用户有权限的所有路段总里程)", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/tollStation/getInfo")
    public RespResult<TollStationInfoBO> getTollStationInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                            @RequestParam(required = false) String orgId,
                                                            @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        TollStationInfoBO result = assetsService.getTollStationInfo(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    /**
     * 查询收费站详情
     * 通过收费站ID查询收费站详情
     *
     * @return TollStationDetailBO
     */
    @ApiOperation(value = "2.3查询收费站详情", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationId", value = "收费站编码", dataType = "string", required = true)
    })
    @PostMapping("/tollStation/getDetail")
    public RespResult<TollStationDetailBO> getTollStationDetail(@RequestParam(required = true) String stationId) {
        TollStationDetailBO result = assetsService.getTollStationDetail(stationId);
        return RespResult.ok(result);
    }


    /**
     * 查询收费站下设备列表
     * 通过收费站ID查询收费站设备列表
     *
     * @return List
     */
    @ApiOperation(value = "2.4查询收费站下设备列表", position = 4)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stationId", value = "收费站编码", dataType = "string", required = true)
    })
    @PostMapping("/tollStation/getLists")
    public RespResult<List<?>> getDeviceByTollStation(@RequestParam(required = true) String stationId) {
        List<Object> result = assetsService.getDeviceByTollStation(stationId);
        return RespResult.ok(result);
    }

    /**
     * 获取设备总况信息接口
     * 获取当前登录用户所属机构管辖下的设备总数和设备故障数
     *
     * @return total:设备总数	faultNumber:设备故障数
     */
    @ApiOperation(value = "2.5获取设备总况信息接口", position = 5)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/device/getInfo")
    public RespResult<HashMap<String, String>> getDeviceStatInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                                 @RequestParam(required = false) String orgId,
                                                                 @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        HashMap<String, String> result = assetsService.getDeviceStatInfo(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    /**
     * 获取设备类型分布统计接口
     * 获取当前登录用户所属机构管辖下的设备类型分布统计信息
     *
     * @return name:系统名称,value:设备数量
     */
    @ApiOperation(value = "2.6获取设备类型分布统计接口", position = 6)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/device/getDeviceTypeStatistics")
    public RespResult<JSONObject> getDeviceTypeStatistics(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                          @RequestParam(required = false) String orgId,
                                                          @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        JSONObject result = assetsService.getDeviceTypeStatistics(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    /**
     * 按 运营集团-运营公司-路段-地理位置 获取  机构  目录树
     * 获取当前登录用户 有权限  运营集团-运营公司-路段-地理位置 获取目录树
     *
     * @return name:系统名称,value:设备数量
     */
    @ApiOperation(value = "2.7.1按运营集团-运营公司-路段-地理位置获取机构目录树(业务资产树)", position = 7)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/getComponeyWayGeo")
    public RespResult<List<Tree<String>>> getComponeyWayGeo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                            @RequestParam(required = false) String orgId,
                                                            @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<Tree<String>> result = assetsService.getComponeyWayGeo(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.8桥梁统计信息", position = 16)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/api/bridge/getInfo")
    public RespResult<BridgeClassifyStatBo> getBridgeInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                          @RequestParam(required = false) String orgId,
                                                          @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        BridgeClassifyStatBo result = assetsService.getBridgeClassifyStat(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.9设施类型分类", position = 17)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID或运营公司ID或路段ID 参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/api/hoiGeographyinfo/getType")
    public RespResult<JSONArray> getFacStatByType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                  @RequestParam(required = false) String orgId,
                                                  @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        JSONArray result = assetsService.getFacStatByType(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.10设施类型分类-简要列表信息", position = 18)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID或运营公司ID或路段ID 参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "code", value = "设施类型", dataType = "string", required = true),
            @ApiImplicitParam(name = "search", value = "设施名模糊查询", dataType = "string", required = false)
    })
    @PostMapping("/api/hoiGeographyinfo/getInfo")
    public RespResult<List<?>> getFacInfoListByType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                    @RequestParam(required = false) String orgId,
                                                    @RequestParam(required = false) String nodeType,
                                                    @RequestParam(required = true) String code,
                                                    @RequestParam(required = false) String search) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<?> result = assetsService.getFacInfoListByType(isUseUserPermissions, orgId, nodeType, code, search);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.11设备类型分类", position = 19)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "search", value = "关键字模糊查询（设备名或桩号）", dataType = "string", required = false)
    })
    @PostMapping("/api/hopDevicerecord/getType")
    public RespResult<JSONArray> getDeviceStatByType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                     @RequestParam(required = false) String orgId,
                                                     @RequestParam(required = false) String nodeType,
                                                     @RequestParam(required = false) String search) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        JSONArray result = assetsService.getDeviceStatByType(isUseUserPermissions, orgId, nodeType, search);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.12设备类型分类-简要列表信息", position = 20)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "code", value = "设备类型", dataType = "string", required = true),
            @ApiImplicitParam(name = "search", value = "关键字模糊查询（设备名或桩号）", dataType = "string", required = false)
    })
    @PostMapping("/api/hopDevicerecord/getInfo")
    public RespResult<List<?>> getDeviceInfoListByType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                       @RequestParam(required = false) String orgId,
                                                       @RequestParam(required = false) String nodeType,
                                                       @RequestParam(required = true) String code,
                                                       @RequestParam(required = false) String search) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<?> result = assetsService.getDeviceInfoListByType(isUseUserPermissions, orgId, nodeType, code, search);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.13获取门架总况信息接口", position = 21)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/api/hoiGeographyinfo/getGantryInfo")
    public RespResult<JSONObject> getGantryInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                @RequestParam(required = false) String orgId,
                                                @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        JSONObject result = assetsService.getGantryInfo(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.14门架基本信息", position = 22)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceNumber", value = "门架编号", dataType = "string", required = true)
    })
    @PostMapping("/api/bdmpGantryinfo/getGantryDetail")
    public RespResult<GantryDetailBO> getGantryDetail(@RequestParam(required = true) String deviceNumber) {
        GantryDetailBO result = assetsService.getGantryDetail(deviceNumber);
        return RespResult.ok(result);
    }

    // 获取门架下设备数据（比如工控机、RSU天线 、牌识）
    @ApiOperation(value = "2.15门架设备树", position = 23)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceNumber", value = "门架编号", dataType = "string", required = true)
    })
    @PostMapping("/api/hopDevicerecord/getGantryTree")
    public RespResult<GantryTree> getGantryTree(@RequestParam(required = true) String deviceNumber) {
        GantryTree result = assetsService.getGantryTree(deviceNumber);
        return RespResult.ok(result);
    }

    // 获取当前登录用户所属机构管辖下的隧道统计信息
    @ApiOperation(value = "2.16获取隧道总况信息展示", position = 24)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/api/bdmpAssetsFacilitiesTunnel/getTunnelInfo")
    public RespResult<HashMap<String, Object>> getTunnelInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                             @RequestParam(required = false) String orgId,
                                                             @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        HashMap<String, Object> result = assetsService.getTunnelStatByType(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.17获取设备基本信息", position = 25)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceId", value = "设备id", dataType = "string", required = true)
    })
    @PostMapping("/api/hopDevicerecord/getDeviceDetail")
    public RespResult<DeviceBO> getDeviceDetail(@RequestParam(required = true) String deviceId) {
        DeviceBO result = assetsService.getDeviceDetail(deviceId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.18获取当前用户下的运营公司、路段信息、地理位置信息", position = 26)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeType", value = "节点类型(运营公司:1/路段:2/设施:3)", dataType = "string", required = false)
    })
    @PostMapping("/permissionCommon/getAllMsg")
    public RespResult<JSONObject> getAllMsg(@RequestParam(required = false) String nodeType) {
        JSONObject result = assetsService.getAllMsg(nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.19设备及时维修率", position = 27)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/simpems/fault/hopFaultStatistics/getRepairRateStatistics")
    public RespResult<JSONObject> getRepairRateStatistics(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                          @RequestParam(required = false) String orgId,
                                                          @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        JSONObject result = assetsService.getRepairRateStatistics(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.20获取路段权限信息", position = 28)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/hoiWaysection/queryWaySection")
    public RespResult<List<?>> queryWaySection(@RequestParam(required = false) Boolean isUseUserPermissions,
                                               @RequestParam(required = false) String orgId,
                                               @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<?> result = assetsService.queryWaySection(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.21获取桥梁上视频资源", position = 29)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "geographyinfoId", value = "桥梁id", dataType = "string", required = true)
    })
    @PostMapping("/api/bridge/getBridgeDetail")
    public RespResult<JSONObject> getCameraForBridge(@RequestParam(required = true) String geographyinfoId) {
        JSONObject result = assetsService.getCameraForBridge(geographyinfoId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.22获取视频资源列表(科技&腾路&上云)", position = 30)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/bdmpAssetsCameraResource/queryCameras")
    public RespResult<?> listCameraResource(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                                 @RequestParam(required = false) String orgId,
                                                                 @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        CameraResourceGroupBO cameraResourceGroupBO = assetsService.listCameraResource(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(cameraResourceGroupBO);
    }

    @ApiOperation(value = "2.222获取视频资源ID列表(科技&腾路)", position = 30)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/bdmpAssetsCameraResource/queryCameraIds")
    public RespResult<?> listCameraId(@RequestParam(required = false) Boolean isUseUserPermissions,
                                            @RequestParam(required = false) String orgId,
                                            @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        Map<String, Object> resultMap = assetsService.listCameraId(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(resultMap);
    }

    @ApiOperation(value = "2.23获取视频资源列表(科技)", position = 31)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/bdmpAssetsCameraResourceYt/queryCameras")
    public RespResult<List<CameraResourceYtBO>> listCameraResourceYt(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                                     @RequestParam(required = false) String orgId,
                                                                     @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<CameraResourceYtBO> result = assetsService.listCameraResourceYt(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.24获取视频资源详情(科技)", position = 32)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "视频资源id", dataType = "string", required = true)
    })
    @GetMapping("/bdmpAssetsCameraResourceYt/queryDetail/{id}")
    public RespResult<CameraResourceYtBO> queryYtDetail(@PathVariable(required = true) String id) {
        CameraResourceYtBO result = assetsService.queryYtDetail(id);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.25获取视频资源列表(腾路)", position = 33)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @PostMapping("/bdmpAssetsCameraResourceTl/queryCameras")
    public RespResult<List<CameraResourceTlBO>> listCameraResourceTl(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                                     @RequestParam(required = false) String orgId,
                                                                     @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<CameraResourceTlBO> result = assetsService.listCameraResourceTl(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.26获取视频资源详情(腾路-地图展示)", position = 34)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "视频资源id", dataType = "string", required = true)
    })
    @GetMapping("/bdmpAssetsCameraResourceTl/queryTLDetail/{id}")
    public RespResult<CameraResourceTlBO> queryTlDetail(@PathVariable(required = true) String id) {
        CameraResourceTlBO result = assetsService.queryTlDetail(id);
        return RespResult.ok(result);
    }


    @ApiOperation(value = "2.27获取服务区详情", position = 35)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "geoId", value = "地理位置信息id(设施id)", dataType = "string", required = true)
    })
    @PostMapping("/bdmpAssetsFacilitiesServiceArea/queryServiceAreas")
    public RespResult<JSONObject> queryServiceAreas(@RequestParam(required = true) String geoId) {
        JSONObject result = assetsService.queryServiceAreas(geoId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.28获取隧道详情", position = 36)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "geoId", value = "地理位置信息id(设施id)", dataType = "string", required = true)
    })
    @PostMapping("/api/bdmpAssetsFacilitiesTunnel/queryTunnelDetail")
    public RespResult<JSONObject> queryTunnelDetail(@RequestParam(required = true) String geoId) {
        JSONObject result = assetsService.queryTunnelDetail(geoId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.29 高速-获取预计总里程", position = 37)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID，运营公司ID 或 路段ID(为空则选择用户有权限的所有路段总里程)", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/expressway/totalMileage/plan")
    public RespResult<WayPlanBO> getPlanTotalMileage(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                     @RequestParam(required = false) String orgId,
                                                     @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        WayPlanBO result = assetsService.getPlanTotalMileage(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.30 高速-获取预计里程详情", position = 38)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID，运营公司ID (为空则选择用户有权限的所有路段总里程)", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/expressway/totalMileage/plan/detail")
    public RespResult<List<WayPlanDetailBO>> getPlanTotalMileageDetail(Boolean isUseUserPermissions, String orgId, String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<WayPlanDetailBO> result = assetsService.getPlanTotalMileageDetail(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    // 说明：获取各公司桥隧占比。注意省、运营公司、路段三个用户类别登录时的数据。省登录时展示各个分公司管辖里程。桥隧比=（桥梁里程+隧道里程）/总里程
    @ApiOperation(value = "2.31 高速-获取各公司桥隧占比", position = 39)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID，运营公司ID (为空则选择用户有权限的所有路段总里程)", dataType = "string", required = true),
            @ApiImplicitParam(name = "nodeType", value = " 节点类型(0:集团、1:运营公司、2:路段)", dataType = "string", required = true),
            @ApiImplicitParam(name = "searchType", value = "查询类型(运营公司：1、路段：2),默认为:1", dataType = "string", required = false)
    })
    @GetMapping("/expressway/statistical/bridgeTunnelRate")
    public RespResult<?> getBridgeTunnelRate(@RequestParam(required = false) Boolean isUseUserPermissions,
                                             @RequestParam(required = true) String orgId,
                                             @RequestParam(required = true) String nodeType,
                                             @RequestParam(required = false) String searchType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        searchType = StrUtil.isBlank(searchType) ? "1" : searchType;
        JSONArray result = assetsService.getBridgeTunnelRate(isUseUserPermissions, orgId, nodeType, searchType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.32 高速-获取机构基础信息", position = 40)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID，运营公司ID 或 路段ID(为空则选择用户有权限的所有路段总里程)", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/expressway/org/info")
    public RespResult<?> getCompanyInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                        @RequestParam(required = true) String orgId,
                                        @RequestParam(required = true) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        JSONObject result = assetsService.getCompanyInfo(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.33 高速-获取路段基本信息", position = 41)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "路段", dataType = "string", required = true),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = true)
    })
    @GetMapping("/expressway/waysection/info")
    public RespResult<?> getWaysectionInfo(@RequestParam(required = false) Boolean isUseUserPermissions,
                                           @RequestParam(required = true) String orgId,
                                           @RequestParam(required = true) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        WaySectionInfo result = assetsService.getWaysectionInfo(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.34.2 根据设施id获取视频播放地址", position = 41)
    @PostMapping("/expressway/camera/url")
    public RespResult<?> getUrlByFacId(@RequestBody GetUrlByFacIdBo getUrlByFacIdBo) {
        return RespResult.ok(assetsService.getUrlByFacId(getUrlByFacIdBo));
    }

    @ApiOperation(value = "2.35.1 路产-获取路产设施细分项统计", position = 42)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID或运营公司ID或路段ID 参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "search", value = "设施名或桩号模糊查询", dataType = "string", required = false),
            @ApiImplicitParam(name = "code", value = "设施类型", dataType = "string", required = true)
    })
    @PostMapping("/expressway/facDictType/list")
    public RespResult<?> getFacDictTypeByType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                              @RequestParam(required = false) String orgId,
                                              @RequestParam(required = false) String nodeType,
                                              @RequestParam(required = true) String code,
                                              @RequestParam(required = false) String search) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<DictType> result = assetsService.getFacDictTypeByType(isUseUserPermissions, orgId, nodeType, code, search);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.35.2 路产-获取路产设施细分项明细列表", position = 43)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "集团ID或运营公司ID或路段ID 参数为空 则选择用户有权限的位置下所有设备总况信息", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "search", value = "设施名或桩号模糊查询", dataType = "string", required = false),
            @ApiImplicitParam(name = "code", value = "设施类型", dataType = "string", required = true),
            @ApiImplicitParam(name = "dictKeys", value = "设施分类对应字典类型", dataType = "string", required = false)
    })
    @PostMapping("/expressway/facDictType/fac/list")
    public RespResult<?> getFacInfoListByDictType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                  @RequestParam(required = false) String orgId,
                                                  @RequestParam(required = false) String nodeType,
                                                  @RequestParam(required = true) String code,
                                                  @RequestParam(required = false) String search,
                                                  @RequestParam(required = false) String dictKeys) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<FacilitiesInfoBO> result = assetsService.getFacInfoListByDictType(isUseUserPermissions, orgId, nodeType, code, search, dictKeys);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "2.36 设施数量统计(按公司、路段)", position = 44)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "路段", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false),
            @ApiImplicitParam(name = "name", value = "名称", dataType = "string", required = false),
            @ApiImplicitParam(name = "byType", value = "显示类型（公司、路段）", dataType = "integer", required = true),
            @ApiImplicitParam(name = "pageSize", value = "分页大小（0:分页）", dataType = "integer", required = true),
            @ApiImplicitParam(name = "pageNumber", value = "当前页码", dataType = "integer", required = false)
    })
    @GetMapping("/expressway/stat/fac/num")
    public RespResult<?> getFacStat(@RequestParam(required = false) Boolean isUseUserPermissions,
                                    @RequestParam(required = false) String orgId,
                                    @RequestParam(required = false) String nodeType,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = true) Integer byType,
                                    @RequestParam(required = true) Integer pageSize,
                                    @RequestParam(required = false) Integer pageNumber) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        Object object = assetsService.getFacStat(isUseUserPermissions, orgId, nodeType, name, byType, pageSize, pageNumber);
        return RespResult.ok(object);
    }

    @ApiOperation(value = "2.37 获取附近视频资源", position = 45)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "double", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "double", required = true),
            @ApiImplicitParam(name = "distanceKm", value = "范围里程（公里）", dataType = "double", required = true),
            @ApiImplicitParam(name = "wayIds", value = "路段ID集（英文逗号分割的字符串）", dataType = "string", required = false),
            @ApiImplicitParam(name = "resourceIds", value = "资源ID集（英文逗号分割的字符串）", dataType = "string", required = false),
            @ApiImplicitParam(name = "excludeLocationTypes", value = "查询摄像头时要排除的位置类型（英文逗号分割的字符串），位置类型(10:收费站、11:收费站广场、12: :收费亭内、 20:隧道、  30:外场、40:服务区)", dataType = "string", required = false)
    })
    @PostMapping("/expressway/near/camera")
    public RespResult<?> getCameraByPoint(@RequestParam(required = true) Double longitude,
                                          @RequestParam(required = true) Double latitude,
                                          @RequestParam(required = true) Double distanceKm,
                                          @RequestParam(required = false) String wayIds,
                                          @RequestParam(required = false) String resourceIds,
                                          @RequestParam(required = false) String excludeLocationTypes) {
        Map<String, List<CameraResourceNearBO>> cameraResourceNearBOGroupList = deviceService.getCameraByPoint(longitude, latitude, distanceKm, wayIds,resourceIds,excludeLocationTypes);
        return RespResult.ok(cameraResourceNearBOGroupList);
    }

    @ApiOperation(value = "2.38 获取附近设备资源", position = 46)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codes", value = "设备类型集（英文逗号分割的字符串）", dataType = "string", required = true),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "double", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "double", required = true),
            @ApiImplicitParam(name = "distanceKm", value = "范围里程（公里）", dataType = "double", required = true),
            @ApiImplicitParam(name = "wayIds", value = "路段ID集（英文逗号分割的字符串）", dataType = "string", required = false),
            @ApiImplicitParam(name = "resourceIds", value = "资源ID集（英文逗号分割的字符串）", dataType = "string", required = false),
            @ApiImplicitParam(name = "excludeFacTypes", value = "要排除的设施类型集（英文逗号分割的字符串）", dataType = "string", required = false),
            @ApiImplicitParam(name = "excludeLocationTypes", value = "查询摄像头时要排除的位置类型（英文逗号分割的字符串），位置类型(10:收费站、11:收费站广场、12: :收费亭内、 20:隧道、  30:外场、40:服务区)", dataType = "string", required = false)
    })
    @PostMapping("/expressway/near/device")
    public RespResult<?> getDeviceByPoint(@RequestParam(required = true) String codes,
                                          @RequestParam(required = true) Double longitude,
                                          @RequestParam(required = true) Double latitude,
                                          @RequestParam(required = true) Double distanceKm,
                                          @RequestParam(required = false) String wayIds,
                                          @RequestParam(required = false) String resourceIds,
                                          @RequestParam(required = false) String excludeFacTypes,
                                          @RequestParam(required = false) String excludeLocationTypes) {
        Map<String, ?> deviceResourceNearBOGroupList = deviceService.getDeviceByPoint(codes, longitude, latitude, distanceKm, wayIds,resourceIds,excludeFacTypes,excludeLocationTypes);
        return RespResult.ok(deviceResourceNearBOGroupList);
    }

    @ApiOperation(value = "2.38.1 获取附近的可视对讲资源", position = 47)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "double", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "double", required = true),
            @ApiImplicitParam(name = "distanceKm", value = "范围里程（公里）", dataType = "double", required = true),
            @ApiImplicitParam(name = "wayIds", value = "路段ID集（英文逗号分割的字符串）", dataType = "string", required = false)
    })
    @PostMapping("/expressway/near/contact")
    public RespResult<?> getVpResourceByPoint(@RequestParam(required = true) Double longitude,
                                       @RequestParam(required = true) Double latitude,
                                       @RequestParam(required = true) Double distanceKm,
                                       @RequestParam(required = false) String wayIds) {
        List<Map<String, String>> contact = facilitiesService.getContactByPoint(longitude, latitude, distanceKm, wayIds);
        return RespResult.ok(contact);
    }

    @ApiOperation(value = "2.39 获取附近设施资源", position = 47)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codes", value = "设施类型集（英文逗号分割的字符串）", dataType = "string", required = true),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "double", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "double", required = true),
            @ApiImplicitParam(name = "distanceKm", value = "范围里程（公里）", dataType = "double", required = true),
            @ApiImplicitParam(name = "wayIds", value = "路段ID集（英文逗号分割的字符串）", dataType = "string", required = false),
            @ApiImplicitParam(name = "bridgeLevel", value = "桥梁等级(1:特大桥,2:大桥,3:中桥,4:小桥)", dataType = "string", required = false)
    })
    @PostMapping("/expressway/near/fac")
    public RespResult<?> getFacByPoint(@RequestParam(required = true) String codes,
                                       @RequestParam(required = true) Double longitude,
                                       @RequestParam(required = true) Double latitude,
                                       @RequestParam(required = true) Double distanceKm,
                                       @RequestParam(required = false) String wayIds,
                                       @RequestParam(required = false) String bridgeLevel) {
        Map<String, List<FacResourceNearBO>> facResourceNearBOGroupList = facilitiesService.getFacByPoint(codes, longitude, latitude, distanceKm, wayIds,bridgeLevel);
        return RespResult.ok(facResourceNearBOGroupList);
    }

    @ApiOperation(value = "2.39 按桩号、方向、路段获取附近摄像头", position = 47)
    @PostMapping("/expressway/centerOffNo/near/camera")
    public RespResult<List<Map<String, Object>>> getCameraByCenterOffNo(@Validated @RequestBody CameraByCenterOffNoBo cameraByCenterOffNoBo) {
        return RespResult.ok(deviceService.getCameraByCenterOffNo(cameraByCenterOffNoBo));
    }

    @ApiOperation(value = "2.40 获取视频资源详情(云台 + 腾路 + 上云)", position = 48)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "视频ID",required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dataSource", value = "数据来源[1:云台 2:腾路 3:上云]",required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/bdmpAssetsCameraResource/queryDetail")
    public RespResult<CameraResourceBO> queryDetail(@RequestParam(required = true) String id, @RequestParam(required = true) String dataSource) {
        if ("1".equals(dataSource)){
            CameraResourceYtBO result = assetsService.queryYtDetail(id);
            if (null != result){
                CameraResourceBO cameraResourceBO = new CameraResourceBO();
                cameraResourceBO.setCameraId(result.getId());
                cameraResourceBO.setCameraName(result.getName());
                cameraResourceBO.setOnlineStatus(String.valueOf(result.getStatus()));
                cameraResourceBO.setLongitude(result.getCoordX());
                cameraResourceBO.setLatitude(result.getCoordY());
                cameraResourceBO.setWayName(result.getWayName());
                cameraResourceBO.setInfoName(result.getLocation());
                cameraResourceBO.setDataSource(dataSource);
                cameraResourceBO.setCompName(result.getCompName());
                cameraResourceBO.setOriWaysectionNo(result.getOriWaysectionNo());
                cameraResourceBO.setShortName(result.getShortName());
                cameraResourceBO.setHasPTZ(result.getHasPtz());
                return RespResult.ok(cameraResourceBO);
            }
        }
        else if ("2".equals(dataSource)){
            CameraResourceTlBO result = assetsService.queryTlDetail(id);
            if (null != result){
                CameraResourceBO cameraResourceBO = new CameraResourceBO();
                cameraResourceBO.setCameraId(result.getCameraId());
                cameraResourceBO.setCameraName(result.getCameraName());
                cameraResourceBO.setOnlineStatus(result.getOnlineStatus());
                cameraResourceBO.setLongitude(result.getLongitude());
                cameraResourceBO.setLatitude(result.getLatitude());
                cameraResourceBO.setWayName(result.getWayName());
                cameraResourceBO.setInfoName(result.getInfoName());
                cameraResourceBO.setDataSource(dataSource);
                cameraResourceBO.setCompName(result.getCompName());
                cameraResourceBO.setOriWaysectionNo(result.getOriWaysectionNo());
                cameraResourceBO.setShortName(result.getShortName());
                return RespResult.ok(cameraResourceBO);
            }
        }
        else if ("3".equals(dataSource)){
            CameraResourceSyBO result = assetsService.querySyDetail(id);
            if (null != result){
                CameraResourceBO cameraResourceBO = new CameraResourceBO();
    /*            cameraResourceBO.setCameraId(result.getId());
                cameraResourceBO.setCameraName(result.getName());
                cameraResourceBO.setOnlineStatus(String.valueOf(result.getStatus()));
                cameraResourceBO.setLongitude(result.getCoordX());
                cameraResourceBO.setLatitude(result.getCoordY());
                cameraResourceBO.setWayName(result.getWayName());
                cameraResourceBO.setInfoName(result.getLocation());
                cameraResourceBO.setDataSource(dataSource);
                cameraResourceBO.setCompName(result.getCompName());
                cameraResourceBO.setOriWaysectionNo(result.getOriWaysectionNo());
                cameraResourceBO.setCameraIp(result.getIp());
                cameraResourceBO.setShortName(result.getShortName());*/
                return RespResult.ok(cameraResourceBO);
            }
        }
        else {
            throw new BadRequestException("视频来源[dataSource],参数不对");
        }
        return RespResult.ok();
    }


    @ApiOperation(value = "2.41 获取里程列表（运营公司、路段）", position = 49)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型(运营公司：1，路段：2)", dataType = "integer", required = true),
            @ApiImplicitParam(name = "orgId", value = "运营公司ID(当节点类型为nodeType=2时,允许再传一个orgId)", dataType = "string", required = false)
    })
    @PostMapping("/api/mileage/list")
    public RespResult<?> queryMileageList(@RequestParam(required = false) Boolean isUseUserPermissions,
                                          @RequestParam(required = true) Integer nodeType,
                                          @RequestParam(required = false) String orgId) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<MileageBo> mileageBoList = assetsService.queryMileageList(isUseUserPermissions, nodeType, orgId);
        return RespResult.ok(mileageBoList);
    }

    @ApiOperation(value = "2.43 设施分组统计", position = 50)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "节点ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = true),
            @ApiImplicitParam(name = "nodeType", value = "设施类型(收费站:32330200,门架:32330700,桥梁:32330600,隧道:32330400,服务区:32330800)", dataType = "string", required = true)
    })
    @GetMapping("/api/fac/groupStat")
    public RespResult<?> tollStationGroupByNodeType(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                    @RequestParam(required = true) String orgId,
                                                    @RequestParam(required = true) String nodeType,
                                                    @RequestParam(required = true) String code) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<Map<String, Object>> resultMapList = assetsService.facilitiesGroupByNodeType(isUseUserPermissions, orgId, nodeType, code);
        return RespResult.ok(resultMapList);
    }

    @ApiOperation(value = "2.44 机构员工统计", position = 51)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "节点ID", dataType = "string", required = true),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = true)
    })
    @GetMapping("/api/org/employees/stat")
    public RespResult<?> employeesStat(@RequestParam(required = false) Boolean isUseUserPermissions,
                                       @RequestParam(required = true) String orgId,
                                       @RequestParam(required = true) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        Map<String, Object> resultMap = assetsService.employeesStat(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(resultMap);
    }

    @ApiOperation(value = "2.45 查询桥梁信息（过江大桥)", position = 52)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否带用户权限", dataType = "boolean", required = false),
            @ApiImplicitParam(name = "orgId", value = "节点ID", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "节点类型", dataType = "string", required = false)
    })
    @GetMapping("/api/list/bridge/crossRiver")
    public RespResult<?> listBridgeByCrossRiver(@RequestParam(required = false) Boolean isUseUserPermissions,
                                                @RequestParam(required = false) String orgId,
                                                @RequestParam(required = false) String nodeType) {
        isUseUserPermissions = null == isUseUserPermissions || isUseUserPermissions;
        List<FacilitiesInfoBO> facilitiesInfoBOList = assetsService.listBridgeByCrossRiver(isUseUserPermissions, orgId, nodeType);
        return RespResult.ok(facilitiesInfoBOList);
    }

    @ApiOperation(value = "2.5 根据路段查询特定的组织（运营集团、运营公司、监控中心、集控点）", position = 52)
    @GetMapping("/api/org/some/list")
    public RespResult<?> getOrgByWay(@RequestParam String wayId,@RequestParam(required = false) String orgTypes) {
        return RespResult.ok(assetsService.getOrgByWay(wayId,orgTypes));
    }

}
