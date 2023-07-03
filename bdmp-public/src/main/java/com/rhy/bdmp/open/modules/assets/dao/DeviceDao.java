package com.rhy.bdmp.open.modules.assets.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Device;
import com.rhy.bdmp.open.modules.assets.domain.to.CameraListTo;
import com.rhy.bdmp.open.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.LaneDeviceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanggj
 * @version V1.0
 * @description 设备 数据操作接口
 * @date 2021-09-24 15:36
 **/
@Mapper
public interface DeviceDao {

    /**
     * 根据设施id获取设备列表
     *
     * @param facilitiesId 设施id
     * @return List
     */
    List<Device> selectByFacId(@Param("facilitiesId") String facilitiesId);

    /**
     * 获取设备基本信息
     *
     * @param deviceId 设备id
     * @return /
     */
    Map<String, String> getDeviceDetail(@Param("deviceId") String deviceId);

    /**
     * 获取收费站下的设备列表
     *
     * @param stationId 收费站id
     * @return List
     */
    List<TollStationDeviceBO> getDeviceByTollStation(@Param("stationId") String stationId);

    /**
     * 获取设备类型分类信息
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @return List
     */
    List<DeviceStatByTypeBO> getDeviceStatByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                 @Param("userId") String userId,
                                                 @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                 @Param("orgId") String orgId,
                                                 @Param("nodeType") String nodeType,
                                                 @Param("search") String search,
                                                 @Param("userType") Integer userType);

    /**
     * 根据设备类型 获取设备列表
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @param code                 设备类型
     * @return List
     */
    List<Device> getDeviceInfoListByType(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                         @Param("userId") String userId,
                                         @Param("userType") Integer userType,
                                         @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                         @Param("orgId") String orgId,
                                         @Param("nodeType") String nodeType,
                                         @Param("code") String code,
                                         @Param("search") String search);

    /**
     * 获取设施下的视频资源
     *
     * @param facId 设施id
     * @return List
     */
    List<Device> selectCameraByFacId(@Param("facId") String facId);

    /**
     * 获取某系统类型下 设备数量
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @param systemIds            系统列表
     * @return List
     */
    List<DeviceStatisticsInfo> getDeviceCountBySystem(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                      @Param("userId") String userId,
                                                      @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                      @Param("orgId") String orgId,
                                                      @Param("nodeType") String nodeType,
                                                      @Param("systemIds") List<String> systemIds);

    /**
     * 获取设备工作状态总况信息接口
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @return HashMap
     */
    HashMap<String, String> getDeviceStatInfo(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                              @Param("userId") String userId,
                                              @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                              @Param("orgId") String orgId,
                                              @Param("nodeType") String nodeType);

    /**
     * 获取附近摄像机资源
     * @param longitude     经度
     * @param latitude      纬度
     * @param distanceKm    范围里程
     * @param wayIds        路段ID集
     * @param userType        根据用户类型是否为“演示用户”，决定是否只显示在线视频资源，以及不为收费亭内的视频资源
     * @param resourceIds        必须要有的视频资源ID集
     * @return
     */
    List<CameraResourceNearBO> getCameraByPoint(@Param("longitude") Double longitude,
                                                 @Param("latitude") Double latitude,
                                                 @Param("distanceKm") Double distanceKm,
                                                 @Param("wayIds") String wayIds,
                                                 @Param("userType") Integer userType,
                                                 @Param("resourceIds") String resourceIds,
                                                 @Param("excludeLocationTypeArray") String[] excludeLocationTypeArray);

    /**
     * 获取附近设备资源
     * @param codes         设备类型集(多个英文逗号拼接)
     * @param longitude     经度
     * @param latitude      纬度
     * @param distanceKm    范围里程
     * @param wayIds        路段ID集
     * @return
     */
    List<DeviceResourceNearBO> getDeviceByPoint(@Param("codes") String codes,
                                                @Param("longitude") Double longitude,
                                                @Param("latitude") Double latitude,
                                                @Param("distanceKm") Double distanceKm,
                                                @Param("wayIds") String wayIds);

    Page<DeviceVo> getDevice(IPage<DeviceVo> page,
                           @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                           @Param("userId") String userId,
                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                           @Param("nodeType") String nodeType,
                           @Param("orgId") String orgId,
                           @Param("deviceTypes") List<String> deviceTypes,
                           @Param("deviceDictId")  String deviceDictId,
                             @Param("deviceName")  String deviceName);

    /**
     *
     * @param userManufacturer 用户厂商
     * @param nodeType 节点类型
     * @param orgId 节点id
     * @param deviceTypes 设备类型
     * @param start 起始
     * @param end 结束
     * @return List<?> 分页数据与数据总数
     */
    List<?> getManufacturerDevice(@Param("userManufacturer") String userManufacturer,
                                          @Param("nodeType") String nodeType,
                                          @Param("orgId") String orgId,
                                          @Param("deviceTypes") List<String> deviceTypes,
                                          @Param("deviceDictId")  String deviceDictId,
                                          @Param("deviceName")  String deviceName,
                                          @Param("start")  Integer start,
                                          @Param("end")  Integer end);

    /**
     * 2.80.4.1 查询设备详情（根据设备编号查询）
     * @param deviceCode 设备编号
     */
    DeviceVo getDeviceByCode(@Param("deviceCode") String deviceCode);

    DeviceVo getDeviceByDeviceId(@Param("deviceId") String deviceId);

    /**
     *  2.80.4.2 根据设备编号查询网关
     * @param deviceCode 设备编号
     */
    DeviceVo getDeviceGateway(@Param("deviceCode") String deviceCode);

    /**
     * 2.80.4.3 查询设备详情（根据设备id查询）
     * @param deviceId 设备id
     */
    DeviceVo getDeviceById(@Param("deviceId") String deviceId);

    /**
     * 2.80.6 查询网关设备的外接设备
     * @param deviceId 设备编号
     */
    List<DeviceVo> getExternalDevice(@Param("deviceId") String deviceId);

    /**
     * 车道设备列表
     * @param laneId 车道id
     */
    List<LaneDeviceVo> getLaneDeviceList(@Param("laneId") String laneId);

    List<Map<String, Object>> deviceStatistics(@Param("deviceStatisticsBo") DeviceStatisticsBo deviceStatisticsBo,
                                               @Param("userPermissions") UserPermissions userPermissions);

    /*
     * @description
     * @author jiangzhimin
     * @date 2022-05-10 23:12
     */
    List<Map<String, Object>> cameraStatistics(@Param("deviceStatisticsBo") DeviceStatisticsBo deviceStatisticsBo,
                                               @Param("userPermissions") UserPermissions userPermissions,
                                               @Param("userType") Integer userType);

    /**
     * 根据方向、路段查询摄像头
     */
    List<CameraListTo> getUnidirectionalCameraList(@Param("cameraByCenterOffNoBo") CameraByCenterOffNoBo cameraByCenterOffNoBo);

    /**
     * 根据方向、路段查询摄像头
     */
    List<CameraListTo> getBilaterallyCameraList(@Param("cameraByCenterOffNoBo") CameraByCenterOffNoBo cameraByCenterOffNoBo);
}
