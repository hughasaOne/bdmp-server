package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Device;
import com.rhy.bdmp.open.modules.assets.domain.vo.DeviceGatewayVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.GatewayExternalVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.LaneDeviceVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 设备业务
 * @Date: 2021/9/28 8:54
 * @Version: 1.0.0
 */
public interface IDeviceService {
    /**
     * 获取设施下的设备
     *
     * @param facilitiesId 设施编号
     * @return List
     */
    List<Device> selectByFacId(String facilitiesId);

    /**
     * 查询收费站下设备列表
     *
     * @param stationId 收费站id
     * @return List
     */
    JSONArray getDeviceByTollStation(String stationId);

    /**
     * 根据id查设备
     *
     * @param deviceId 设备id
     * @return Device
     */
    Map<String, String> getDeviceDetail(String deviceId);

    /**
     * 根据设施id查设备
     *
     * @param facId 设施id
     * @return List
     */
    List<Device> selectCameraByFacId(String facId);

    /**
     * 设备分类统计信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONArray
     */
    JSONArray getDeviceStatByType(boolean isUseUserPermissions, String orgId, String nodeType, String search);

    /**
     * 根据类型查设备列表
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @param code                 设备类型编号
     * @return List
     */
    List<?> getDeviceInfoListByType(boolean isUseUserPermissions, String orgId, String nodeType, String code, String search);

    /**
     * 根据系统类型类型 获取设备统计分布
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONArray
     */
    JSONObject getDeviceStatBySystem(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取设备分类统计信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return HashMap
     */
    HashMap<String, String> getDeviceStatInfo(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取附近摄像机资源
     * @param longitude     经度
     * @param latitude      纬度
     * @param distanceKm    范围里程
     * @param wayIds        路段ID集
     * @return
     */
    Map<String, List<CameraResourceNearBO>> getCameraByPoint(Double longitude, Double latitude, Double distanceKm,String wayIds,String resourceIds,String excludeLocationTypes);

    /**
     * 获取附近设备资源
     * @param codes         设备类型集(多个英文逗号拼接)
     * @param longitude     经度
     * @param latitude      纬度
     * @param distanceKm    范围里程
     * @param wayIds        路段ID集
     * @return
     */
    Map<String, Object> getDeviceByPoint(String codes, Double longitude,
                                         Double latitude, Double distanceKm, String wayIds, String resourceIds, String excludeFacTypes,String excludeLocationTypes);

    /**
     * 2.4.1 数据设备过滤
     */
    PageUtil<DeviceVo> getDataDevice(DataDeviceBo dataDeviceBo);

    /**
     * 2.4.2 厂商设备过滤
     */
    PageUtil<DeviceVo> getManufacturerDevice(ManufacturerDeviceBo manufacturerDeviceBo);

    /**
     * 2.80.4.1 查询设备详情（根据设备编号查询）
     * @param deviceCode 设备编号
     */
    DeviceVo getDeviceByCode(String deviceCode);

    /**
     * 2.80.4.2 查询设备详情（根据设备编号查询）（网关专用）
     * @param deviceCode 设备编号
     */
    DeviceGatewayVo getDeviceGateway(String deviceCode);

    /**
     * 2.80.4.3 查询设备详情（根据设备id查询）
     * @param deviceId 设备id
     */
    DeviceVo getDeviceById(String deviceId);

    /**
     * 2.80.6 查询设备的外接设备
     * @param deviceId 设备编号
     */
    GatewayExternalVo getExternalDevice(String deviceId);

    /**
     * 车道设备列表
     * @param laneId 车道id
     */
    List<LaneDeviceVo> getLaneDeviceList(String laneId);

    /**
     * 设备分类统计及列表明细
     */
    Map<String,Object> deviceStatistics(DeviceStatisticsBo deviceStatisticsBo);

    List<Map<String, Object>> getCameraByCenterOffNo(CameraByCenterOffNoBo cameraByCenterOffNoBo);
}
