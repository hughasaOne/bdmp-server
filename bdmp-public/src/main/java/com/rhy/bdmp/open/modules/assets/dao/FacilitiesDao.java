package com.rhy.bdmp.open.modules.assets.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.open.modules.assets.domain.po.FacilitiesTollStationLane;
import com.rhy.bdmp.open.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.open.modules.assets.domain.vo.FacilitiesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yanggj
 * @Description: 设施
 * @Date: 2021/9/27 17:09
 * @Version: 1.0.0
 */
@Mapper
public interface FacilitiesDao {

    /**
     * 获取收费站总况信息接口
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @param facType              设施类型
     * @return HashMap
     */
    HashMap<String, String> getTollStationStatInfo(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                   @Param("userId") String userId,
                                                   @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                   @Param("orgId") String orgId,
                                                   @Param("nodeType") String nodeType,
                                                   @Param("facType") String facType);

    /**
     * 查询收费站详情
     *
     * @param stationId 收费站id
     * @return HashMap
     */
    TollStationDetailBO getTollStationDetail(String stationId);

    /**
     * 桥梁分类统计信息
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @param facType              设施类型
     * @return List
     */
    List<BridgeStatBo> getBridgeClassifyStat(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                             @Param("userId") String userId,
                                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                             @Param("orgId") String orgId,
                                             @Param("nodeType") String nodeType,
                                             @Param("facType") String facType);

    /**
     * 按设施类型分类统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @param facilitiesTypes      设施类型
     * @return List
     */
    List<FacStatByTypeBO> getFacStatByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                           @Param("userId") String userId,
                                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                           @Param("orgId") String orgId,
                                           @Param("nodeType") String nodeType,
                                           @Param("facilitiesTypes") List<String> facilitiesTypes);

    /**
     * 查询桥梁统计信息
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @return com.rhy.bdmp.open.modules.assets.domain.bo.FacStatByTypeBO
     */
    List<FacStatByTypeBO> getFacBridgeStatByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                 @Param("userId") String userId,
                                                 @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                 @Param("orgId") String orgId,
                                                 @Param("nodeType") String nodeType);

    /**
     * 根据设施类型 获取设施列表
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @param facTypes             设施类型
     * @return List
     */
    List<Facilities> getFacInfoListByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                          @Param("userId") String userId,
                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                          @Param("orgId") String orgId,
                                          @Param("nodeType") String nodeType,
                                          @Param("facTypes") List<String> facTypes);

//    /**
//     * 获取设施-桥梁列表
//     *
//     * @param isUseUserPermissions 是否使用用户权限
//     * @param userId               用户id
//     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
//     * @param orgId                节点编号
//     * @param nodeType             节点类型
//     * @return List
//     */
//    List<Facilities> getFacInfoBridgeListByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
//                                          @Param("userId") String userId,
//                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
//                                          @Param("orgId") String orgId,
//                                          @Param("nodeType") String nodeType);

    /**
     * 获取门架详情
     *
     * @param deviceNumber 门架编号
     * @return HashMap
     */
    GantryDetailBO getGantryDetail(@Param("deviceNumber") String deviceNumber);

    /**
     * 根据设施id和类型查找设施
     *
     * @param facilitiesId   设施id
     * @param facilitiesType 设施类型
     * @return Facilities
     */
    Facilities selectByIdAndType(@Param("facilitiesId") String facilitiesId,
                                 @Param("facilitiesType") String facilitiesType);

    /**
     * 获取隧道总况信息 按隧道类型分类统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @return List
     */
    List<TunnelStatInfo> getTunnelStatByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                             @Param("userId") String userId,
                                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                             @Param("orgId") String orgId,
                                             @Param("nodeType") String nodeType);

    /**
     * 根据桥梁id获取桥梁基本信息
     *
     * @param facId 桥梁id
     * @return FacilitiesBridge
     */
    FacilitiesBridge selectBridgeById(@Param("facId") String facId);

    /**
     * 根据geoId查询服务区
     *
     * @param geoId /
     * @return List
     */
    List<Map<String, Object>> selectServiceAreaByGeoId(@Param("geoId") String geoId);

    /**
     * 获取设施
     *
     * @param geoId   设施id
     * @param facType 设施类型
     * @return HashMap
     */
    HashMap<String, String> getFacByIdAndType(@Param("geoId") String geoId, @Param("facType") String facType);

    /**
     * 根据geoId查询隧道信息
     *
     * @param geoId /
     * @return List
     */
    List<FacilitiesTunnel> selectTunnelByGeoId(@Param("geoId") String geoId);

    GantryBO getGantry(@Param("deviceNumber") String deviceNumber);

    List<Map<String, Object>> getBridgeInfoByWay();

    List<Map<String, Object>> getTunnelInfoByWay();

    BridgeInfo getBridgeInfo(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                             @Param("userId") String userId,
                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                             @Param("orgId") String orgId,
                             @Param("nodeType") String nodeType);


    TunnelInfo getTunnelInfo(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                             @Param("userId") String userId,
                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                             @Param("orgId") String orgId,
                             @Param("nodeType") String nodeType);


    ServiceAreaInfo getServiceAreaInfo(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                       @Param("userId") String userId,
                                       @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                       @Param("orgId") String orgId,
                                       @Param("nodeType") String nodeType);


    List<Map<String, Object>> listBridgeCount(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                              @Param("userId") String userId,
                                              @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                              @Param("orgId") String orgId,
                                              @Param("nodeType") String nodeType,
                                              @Param("search") String search);

    List<FacilitiesInfoBO> listBridge(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                      @Param("userId") String userId,
                                      @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                      @Param("orgId") String orgId,
                                      @Param("nodeType") String nodeType,
                                      @Param("search") String search,
                                      @Param("dictKeys") String dictKeys);

    List<FacilitiesInfoBO> listBridge1(@Param("facStatisticsBo") FacStatisticsBo facStatisticsBo,
                                       @Param("userPermissions") UserPermissions userPermissions,
                                       @Param("dictKeys") String dictKeys);

    // 查询桥梁信息（过江大桥)
    List<FacilitiesInfoBO> listBridgeByCrossRiver(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                  @Param("userId") String userId,
                                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                  @Param("orgId") String orgId,
                                                  @Param("nodeType") String nodeType);

    List<Map<String, Object>> listTollStationCount(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                   @Param("userId") String userId,
                                                   @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                   @Param("orgId") String orgId,
                                                   @Param("nodeType") String nodeType,
                                                   @Param("search") String search,
                                                   @Param("facilitiesTypes") List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listTollStation(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                           @Param("userId") String userId,
                                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                           @Param("orgId") String orgId,
                                           @Param("nodeType") String nodeType,
                                           @Param("search") String search,
                                           @Param("facilitiesTypes") List<String> facilitiesTypes,
                                           @Param("dictKeys") String dictKeys);

    List<FacilitiesInfoBO> listTollStation1(@Param("facStatisticsBo") FacStatisticsBo facStatisticsBo,
                                            @Param("userPermissions") UserPermissions userPermissions,
                                            @Param("facilitiesTypes") List<String> facilitiesTypes,
                                            @Param("dictKeys") String dictKeys);

    List<Map<String, Object>> listServiceAreaCount(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                   @Param("userId") String userId,
                                                   @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                   @Param("orgId") String orgId,
                                                   @Param("nodeType") String nodeType,
                                                   @Param("search") String search,
                                                   @Param("facilitiesTypes") List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listServiceArea(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                           @Param("userId") String userId,
                                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                           @Param("orgId") String orgId,
                                           @Param("nodeType") String nodeType,
                                           @Param("search") String search,
                                           @Param("facilitiesTypes") List<String> facilitiesTypes,
                                           @Param("dictKeys") String dictKeys);

    List<FacilitiesInfoBO> listServiceArea1(@Param("facStatisticsBo") FacStatisticsBo facStatisticsBo,
                                            @Param("userPermissions") UserPermissions userPermissions,
                                            @Param("facilitiesTypes") List<String> facilitiesTypes,
                                            @Param("dictKeys") String dictKeys);

    List<Map<String, Object>> listDoorFrameCount(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                 @Param("userId") String userId,
                                                 @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                 @Param("orgId") String orgId,
                                                 @Param("nodeType") String nodeType,
                                                 @Param("search") String search,
                                                 @Param("facilitiesTypes") List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listDoorFrame(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                         @Param("userId") String userId,
                                         @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                         @Param("orgId") String orgId,
                                         @Param("nodeType") String nodeType,
                                         @Param("search") String search,
                                         @Param("facilitiesTypes") List<String> facilitiesTypes,
                                         @Param("dictKeys") String dictKeys);

    List<FacilitiesInfoBO> listDoorFrame1(@Param("facStatisticsBo") FacStatisticsBo facStatisticsBo,
                                          @Param("userPermissions") UserPermissions userPermissions,
                                          @Param("facilitiesTypes") List<String> facilitiesTypes,
                                          @Param("dictKeys") String dictKeys);

    List<Map<String, Object>> listTunnelCount(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                              @Param("userId") String userId,
                                              @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                              @Param("orgId") String orgId,
                                              @Param("nodeType") String nodeType,
                                              @Param("search") String search,
                                              @Param("facilitiesTypes") List<String> facilitiesTypes);

    List<FacilitiesInfoBO> listTunnel(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                      @Param("userId") String userId,
                                      @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                      @Param("orgId") String orgId,
                                      @Param("nodeType") String nodeType,
                                      @Param("search") String search,
                                      @Param("facilitiesTypes") List<String> facilitiesTypes,
                                      @Param("dictKeys") String dictKeys);

    List<FacilitiesInfoBO> listTunnel1(@Param("facStatisticsBo") FacStatisticsBo facStatisticsBo,
                                       @Param("userPermissions") UserPermissions userPermissions,
                                       @Param("facilitiesTypes") List<String> facilitiesTypes,
                                       @Param("dictKeys") String dictKeys);

    /**
     * 获取附近桥梁资源
     *
     * @param longitude  经度
     * @param latitude   纬度
     * @param distanceKm 范围里程
     * @param wayIds     路段ID集
     * @returnB
     */
    List<FacResourceNearBO> getBridgeByPoint(@Param("longitude") Double longitude,
                                             @Param("latitude") Double latitude,
                                             @Param("distanceKm") Double distanceKm,
                                             @Param("wayIds") String wayIds,
                                             @Param("bridgeLevel") String bridgeLevel);

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
    List<FacResourceNearBO> getFacByPoint(@Param("codes") String codes,
                                          @Param("longitude") Double longitude,
                                          @Param("latitude") Double latitude,
                                          @Param("distanceKm") Double distanceKm,
                                          @Param("wayIds") String wayIds);

    /**
     * 获取收费站车道详情
     *
     * @param tollStationId 收费站ID
     * @return
     */
    List<FacilitiesTollStationLane> getLaneByTollStationId(@Param("tollStationId") String tollStationId);


    /**
     * 设施分组统计(除桥梁)
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param facilitiesTypes      设施类型(除桥梁、隧道)
     */
    List<Map<String, Object>> facilitiesGroupByNodeType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                        @Param("userId") String userId,
                                                        @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                        @Param("orgId") String orgId,
                                                        @Param("nodeType") String nodeType,
                                                        @Param("facilitiesTypes") List<String> facilitiesTypes);


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
    List<Map<String, Object>> tunnelGroupByNodeType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                    @Param("userId") String userId,
                                                    @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                    @Param("orgId") String orgId,
                                                    @Param("nodeType") String nodeType,
                                                    @Param("facilitiesTypes") List<String> facilitiesTypes);

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
    List<Map<String, Object>> bridgeGroupByNodeType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                    @Param("userId") String userId,
                                                    @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                    @Param("orgId") String orgId,
                                                    @Param("nodeType") String nodeType,
                                                    @Param("facilitiesType") List<String> facilitiesTypes);

    /**
     * 2.80.7 查询设施列表
     *
     * @param isUseUserPermissions 是否使用权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @param facilitiesTypes      设施类型
     */
    Page<FacilitiesVo> getFacList(Page<Facilities> page,
                                  @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType,
                                  @Param("facilitiesTypes") Set<String> facilitiesTypes);

    List<FacilitiesVo> getFacList(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType,
                                  @Param("facilitiesTypes") Set<String> facilitiesTypes);

    /**
     * 2.80.8 查询设施详情
     *
     * @param facId 设施id
     */
    FacilitiesVo getFacById(@Param("facId") String facId);

    /**
     * 获取隧道上下行门架id
     */
    Map<String, String> getTunnelGantry(@Param("geoId") String geoId);

    /**
     * 获取员工列表
     *
     * @param stationId 收费站id
     */
    List<Map<String, Object>> getEmployees(@Param("stationId") String stationId);

    List<Map<String, Object>> facStatistics(@Param("facStatisticsBo") FacStatisticsBo facStatisticsBo,
                                            @Param("userPermissions") UserPermissions userPermissions);
    /**
     * 获取附近的可视对讲资源
     * @param tollStation 设施
     * @return
     */
    List<Map<String, String>> getContactByPoint(@Param("tollStationList") List<FacResourceNearBO> tollStation);

    List<String> getFacIdByCode(@Param("facCodes") List<String> facCodes);

    String getControlPointTel(@Param("stationId") String stationId);

    List<Map<String, String>> getControlPointTels(@Param("facilitiesInfoBOS") List<FacilitiesInfoBO> facilitiesInfoBOS);
}
