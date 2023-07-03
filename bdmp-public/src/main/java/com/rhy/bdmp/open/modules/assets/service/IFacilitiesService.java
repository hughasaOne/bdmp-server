package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.open.modules.assets.domain.vo.FacilitiesVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 设施业务处理
 * @Date: 2021/9/28 8:52
 * @Version: 1.0.0
 */
public interface IFacilitiesService {

    /**
     * 获取收费站总况信息接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return TollStationInfoBO
     */
    TollStationInfoBO getTollStationStatInfo(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 查询收费站详情
     *
     * @param stationId 收费站id
     * @return TollStationDetailBO
     */
    TollStationDetailBO getTollStationDetail(String stationId);

    /**
     * 桥梁统计信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return BridgeClassifyStatBo
     */
    BridgeClassifyStatBo getBridgeClassifyStat(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 设施类型分类
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONArray
     */
    JSONArray getFacStatByType(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 设施类型分类-简要列表信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @param code                 设施类型编号
     * @return List
     */
//    List<?> getFacInfoListByType(boolean isUseUserPermissions, String orgId, String nodeType, String code);

    /**
     * 获取门架总况信息接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return /
     */
    JSONObject getGantryInfo(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 门架基本信息
     *
     * @param deviceNumber 门架编号
     * @return HashMap
     */
    GantryDetailBO getGantryDetail(String deviceNumber);

    /**
     * 获取设施
     *
     * @param facilitiesId   设施id
     * @param facilitiesType 设施类型
     * @return Facilities
     */
    Facilities selectByIdAndType(String facilitiesId, String facilitiesType);

    /**
     * 获取隧道总况信息展示
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return HashMap
     */
    HashMap<String, Object> getTunnelStatByType(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取桥梁信息
     *
     * @param facId 桥梁id
     * @return FacilitiesBridge
     */
    FacilitiesBridge selectBridgeById(String facId);

    /**
     * 获取服务区详情
     *
     * @param geoId 设施id
     * @return JSONObject
     */
    JSONObject queryServiceAreas(String geoId);

    /**
     * 获取隧道详情
     *
     * @param geoId 设施id
     * @return JSONObject
     */
    JSONObject queryTunnelDetail(String geoId);

    GantryBO getGantry(String deviceNumber);


    BridgeInfo getBridgeInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    TunnelInfo getTunnelInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    ServiceAreaInfo getServiceAreaInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    List<Map<String, Object>> listBridgeCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search);

    List<FacilitiesInfoBO> listBridge(Boolean isUseUserPermissions, String orgId, String nodeType, String search, String dictKeys);

    List<FacilitiesInfoBO> listBridge1(FacStatisticsBo facStatisticsBo, String dictKeys);

    //  查询桥梁信息（过江大桥
    List<FacilitiesInfoBO> listBridgeByCrossRiver(Boolean isUseUserPermissions, String orgId, String nodeType);

    List<Map<String, Object>> listTollStationCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listTollStation(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys);

    List<FacilitiesInfoBO> listTollStation1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys);

    List<Map<String, Object>> listServiceAreaCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listServiceArea(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys);

    List<FacilitiesInfoBO> listServiceArea1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys);

    List<Map<String, Object>> listDoorFrameCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listDoorFrame(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys);

    List<FacilitiesInfoBO> listDoorFrame1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys);

    List<Map<String, Object>> listTunnelCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listTunnel(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys);

    List<FacilitiesInfoBO> listTunnel1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys);

    /**
     * 获取附近的可视对讲资源
     *
     * @param longitude  经度
     * @param latitude   纬度
     * @param distanceKm 范围里程
     * @param wayIds     路段ID集
     * @return
     */
    List<Map<String, String>> getContactByPoint(Double longitude, Double latitude, Double distanceKm, String wayIds);

    /**
     * 获取附近设施资源
     *
     * @param codes      设施类型集(多个英文逗号拼接)
     * @param longitude  经度
     * @param latitude   纬度
     * @param distanceKm 范围里程
     * @param wayIds     路段ID集
     * @return
     */
    Map<String, List<FacResourceNearBO>> getFacByPoint(String codes, Double longitude, Double latitude, Double distanceKm, String wayIds,String bridgeLevel);

    /**
     * 设施分组统计(除桥梁、隧道)
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param facilitiesTypes      设施类型(除开桥梁、隧道)
     */
    List<Map<String, Object>> facilitiesGroupByNodeType(Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel, String orgId, String nodeType, List<String> facilitiesTypes);

    /**
     * 隧道分组统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param facilitiesTypes      设施类型(隧道)
     */
    List<Map<String, Object>> tunnelGroupByNodeType(Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel, String orgId, String nodeType, List<String> facilitiesTypes);

    /**
     * 桥梁分组统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param facilitiesTypes      设施类型(桥梁)
     */
    List<Map<String, Object>> bridgeGroupByNodeType(Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel, String orgId, String nodeType, List<String> facilitiesTypes);

    /**
     * 2.80.7 查询设施列表
     */
    Object getFacList(FacListBo facListBo);

    /**
     * 2.80.8 查询设施详情
     *
     * @param facId 设施id
     */
    FacilitiesVo getFacById(String facId);

    /**
     * 设施分类统计及列表明细
     */
    Map<String, Object> facStatistics(FacStatisticsBo facStatisticsBo, String nodeType, List<FacilitiesInfoBO> temp);
}
