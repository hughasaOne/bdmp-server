package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.open.modules.assets.domain.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yanggj
 * @Date: 2021/9/27 8:59
 * @Version: 1.0.0
 */
public interface IAssetsService {
    /**
     * 获取高速总里程接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return String
     */
    Map<String, Object> getTotalMileage(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取收费站总况信息接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return TollStationInfoBO
     */
    TollStationInfoBO getTollStationInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 查询收费站详情
     *
     * @param stationId 收费站id
     * @return TollStationDetailBO
     */
    TollStationDetailBO getTollStationDetail(String stationId);

    /**
     * 查询收费站下设备列表
     *
     * @param stationId 收费站id
     * @return List
     */
    List<Object> getDeviceByTollStation(String stationId);

    /**
     * 获取设备总况信息接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return HashMap
     */
    HashMap<String, String> getDeviceStatInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取设备类型分布统计接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONArray
     */
    JSONObject getDeviceTypeStatistics(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 按 运营集团-运营公司-路段-地理位置 获取  机构  目录树
     * 获取当前登录用户 有权限  运营集团-运营公司-路段-地理位置 获取目录树
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Tree<String>> getComponeyWayGeo(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 桥梁统计信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return BridgeClassifyStatBo
     */
    BridgeClassifyStatBo getBridgeClassifyStat(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 设施类型分类
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONArray
     */
    JSONArray getFacStatByType(Boolean isUseUserPermissions, String orgId, String nodeType);

//    /**
//     * 设施类型分类-简要列表信息
//     *
//     * @param isUseUserPermissions 是否带用户权限
//     * @param orgId                节点id
//     * @param nodeType             节点类型
//     * @param code                 设施类型编号
//     * @return List
//     */
//    List<?> getFacInfoListByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code);

//    /**
//     * 根据设施类型获取设施（含具体设施字典分类统计）
//     * @param isUseUserPermissions  是否带权限
//     * @param orgId                 节点ID
//     * @param nodeType              节点类型
//     * @param code                  设施类型CODE
//     * @param search                设施名称
//     * @param dictKeys              具体设施字典分类
//     * @return
//     */
//    Map<String, Object> getFacInfoByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search, String dictKeys);



    /**
     * 获取路产设施细分项统计
     * @param isUseUserPermissions  是否带权限
     * @param orgId                 节点ID
     * @param nodeType              节点类型
     * @param code                  设施类型CODE
     * @param search                设施名称
     * @return
     */
    List<DictType> getFacDictTypeByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search);

    /**
     * 获取路产设施细分项列表
     * @param isUseUserPermissions  是否带权限
     * @param orgId                 节点ID
     * @param nodeType              节点类型
     * @param code                  设施类型CODE
     * @param search                设施名称
     * @param dictKeys              具体设施字典分类
     * @return
     */
    List<FacilitiesInfoBO> getFacInfoListByDictType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search, String dictKeys);

    /**
     * 根据设施类型获取设施
     * @param isUseUserPermissions  是否带权限
     * @param orgId                 节点ID
     * @param nodeType              节点类型
     * @param code                  设施类型CODE
     * @param search                设施名称
     * @return
    */
    List<FacilitiesInfoBO> getFacInfoListByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search);

    /**
     *  查询桥梁信息（过江大桥)
     * @param isUseUserPermissions  是否带权限
     * @param orgId                 节点ID
     * @param nodeType              节点类型
     * @return
     */
    List<FacilitiesInfoBO> listBridgeByCrossRiver(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 设备类型分类
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @param search               关键字模糊查询（设备名或桩号）
     * @return JSONArray
     */
    JSONArray getDeviceStatByType(Boolean isUseUserPermissions, String orgId, String nodeType, String search);

    /**
     * 设备类型分类-简要列表信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @param code                 设备类型编号
     * @param search               关键字模糊查询（设备名或桩号）
     * @return List
     */
    List<?> getDeviceInfoListByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search);

    /**
     * 获取门架总况信息接口
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONObject
     */
    JSONObject getGantryInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 门架基本信息
     *
     * @param deviceNumber 门架编号
     * @return HashMap
     */
    GantryDetailBO getGantryDetail(String deviceNumber);

    /**
     * 门架设备树
     *
     * @param deviceNumber 门架编号
     * @return GantryTree
     */
    GantryTree getGantryTree(String deviceNumber);

    /**
     * 获取隧道总况信息展示
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return HashMap
     */
    HashMap<String, Object> getTunnelStatByType(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取设备基本信息
     *
     * @param deviceId 设备id
     * @return DeviceBO
     */
    DeviceBO getDeviceDetail(String deviceId);

    /**
     * 获取当前用户下的运营公司、路段信息、地理位置信息
     * @param nodeType  节点类型(运营公司:1/路段:2/设施:3)
     * @return JSONObject
     */
    JSONObject getAllMsg(String nodeType);

    /**
     * 设备及时维修率
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return JSONObject
     */
    JSONObject getRepairRateStatistics(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取路段权限信息
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<?> queryWaySection(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取桥梁上视频资源
     *
     * @param bridgeId 桥梁id
     * @return JSONObject
     */
    JSONObject getCameraForBridge(String bridgeId);

    /**
     * 获取视频资源列表
     *
     * @param isUseUserPermissions /
     * @param orgId                /
     * @param nodeType             /
     * @return JSONArray
     */
    CameraResourceGroupBO listCameraResource(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取视频资源ID列表
     *
     * @param isUseUserPermissions /
     * @param orgId                /
     * @param nodeType             /
     * @return JSONArray
     */
    Map<String, Object> listCameraId(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取视频资源信息（科技）
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List<CameraResourceYt></CameraResourceYt>
     */
    List<CameraResourceYtBO> listCameraResourceYt(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取视频资源详情（科技）
     *
     * @param id 视频资源id
     * @return CameraResourceTl
     */
    CameraResourceYtBO queryYtDetail(String id);

    CameraResourceSyBO querySyDetail(String id);

    /**
     * 获取视频资源列表（腾路）
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<CameraResourceTlBO> listCameraResourceTl(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取视频资源详情（腾路-地图展示）
     *
     * @param id 视频资源id
     * @return CameraResourceTl
     */
    CameraResourceTlBO queryTlDetail(String id);

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

    /**
     * 高速-获取预计总里程
     * @param isUseUserPermissions
     * @param orgId
     * @param nodeType
     * @return
     */
    WayPlanBO getPlanTotalMileage(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取预计里程详情
     * @return
     */
    List<WayPlanDetailBO> getPlanTotalMileageDetail(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取桥隧比
     * @param isUseUserPermissions  是否带用户权限
     * @param orgId                 节点ID
     * @param nodeType              节点类型(0:集团、1:运营公司、2:路段)
     * @param searchType            查询类型(1:运营公司、2:路段)
     * @return
     */
    JSONArray getBridgeTunnelRate(Boolean isUseUserPermissions, String orgId, String nodeType, String searchType);

    JSONObject getCompanyInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    WaySectionInfo getWaysectionInfo(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 设施数量统计
     * @param isUseUserPermissions  是否带权限
     * @param orgId                 节点ID
     * @param nodeType              节点类型（0：运营集团、1：运营公司、2：路段、3：设施）
     * @param name                  名称（公司、路段）
     * @param byType                数据类型（公司、路段）
     * @param pageSize              分页大小（为0表示不分页）
     * @param pageNumber            当前页
     * @return
     */
    Object getFacStat(Boolean isUseUserPermissions, String orgId, String nodeType, String name, Integer byType, Integer pageSize, Integer pageNumber);

    /**
     * 获取里程列表（运营公司、路段）
     * @param isUseUserPermissions  是否带权限
     * @param nodeType  节点类型(运营公司：1，路段：2)
     * @param orgId  当节点类型为nodeType=2时,允许再传一个orgId(运营公司ID)
     * @return
     */
    List<MileageBo> queryMileageList(Boolean isUseUserPermissions, Integer nodeType, String orgId);

    /**
     * 设施分组统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param code                 设施类型
     */
    List<Map<String, Object>> facilitiesGroupByNodeType(Boolean isUseUserPermissions, String orgId, String nodeType, String code);

    /**
     * 机构员工统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     */
    Map<String, Object> employeesStat(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 2.7.2.1 组织-数据资产树（集团-运营公司）
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param isAsync              是否异步（是否向下查找）
     */
    List<Tree<String>> getGroupOrgTree(Boolean isUseUserPermissions, String orgId, String nodeType,Boolean isAsync);

    //

    /**
     * 2.7.2.2 组织路段-数据资产树（集团-运营公司-路段）
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param isAsync              是否异步（是否向下查找）
     */
    List<Tree<String>> getGroupOrgWayTree(Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync);

    /**
     * 2.7.2.3 设施一级
     */
    List<Tree<String>> getFac(DataFacBo dataFacBo);

    /**
     * 2.7.2.4 设施二级
     */
    List<Tree<String>> getSubFac(DataFacBo dataFacBo);

    /**
     * 2.7.3.1  厂商资产树（集团-公司）
     * @param orgId        节点编号
     * @param nodeType    节点类型(集团、运营公司、路段)
     * @param isAsync 是否异步
     */
    List<Tree<String>> getGroupOrgManufacturerTree(String orgId, String nodeType,Boolean isAsync);

    /**
     * 2.7.3.2  厂商资产树（集团-公司-路段）
     * @param orgId        节点编号
     * @param nodeType    节点类型(集团、运营公司、路段)
     * @param isAsync 是否异步
     */
    List<Tree<String>> getGroupOrgWayManufacturerTree(String orgId, String nodeType, Boolean isAsync);

    /**
     * 2.7.3.3  厂商资产树（设施一级）
     */
    List<Tree<String>> getFacManufacturer(ManufacturerFacBo manufacturerFacBo);

    /**
     * 2.7.3.4  厂商资产树（设施二级）
     */
    List<Tree<String>> getSubFacManufacturerTree(ManufacturerFacBo manufacturerFacBo);

    /**
     * 2.4.1 数据设备过滤
     */
    PageUtil<DeviceVo> getDataDevice(DataDeviceBo dataDeviceBo);

    /**
     * 2.4.2 厂商设备过滤
     */
    PageUtil<DeviceVo> getManufacturerDevice(ManufacturerDeviceBo manufacturerDeviceBo);

    /**
     * 2.80.1 查询字典code(根据字典目录code查询)
     * @param dictDirCodes 字典目录code集合
     */
    List<Map<String, String>> getCodeList(Set<String> dictDirCodes,String dictName);

    /**
     * 2.80.2 查询机构列表(根据机构类型查询)
     */
    Object getOrgList(OrgListBo orgListBo);

    /**
     * 2.80.3 查询机构详情（根据orgId）
     * @param orgId 组织id
     */
    OrgVo getOrgInfoById(String orgId);

    /**
     * 根据机构id查询机构子部门 树形结构
     * @param orgId 组织id
     * @return
     */
    List<OrgTreeVo> getOrgSonById(String orgId);

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
     * 2.80.7 查询设施列表
     */
    Object getFacList(FacListBo facListBo);

    /**
     * 2.80.8 查询设施详情
     * @param facId 设施id
     */
    FacilitiesVo getFacById(String facId);

    /**
     * 2.80.9 查询路段列表
     */
    Page<Waysection> getWayList(WayListBo wayListBo);

    /**
     * 2.80.10 查询路段详情
     * @param wayId 路段id
     */
    Waysection getWayById(String wayId);

    /**
     * 2.80.11 设备字典分类查询
     * @param searchType 查询类型（1:设备总类，2:设备类型）
     * @param typeId 设备总类/设备类型的id
     * @param name 设备字典类型的名称
     */
    List<Map<String,String>> getDeviceDict(Integer searchType, String typeId, String name);

    /**
     * 车道设备列表
     * @param laneId 车道id
     */
    List<LaneDeviceVo> getLaneDeviceList(String laneId);

    /**
     * 设施分类统计及列表明细
     */
    Map<String,Object> facStatistics(FacStatisticsBo facStatisticsBo);

    /**
     * 设备分类统计及列表明细
     */
    Map<String,Object> deviceStatistics(DeviceStatisticsBo deviceStatisticsBo);

    Object getUrlByFacId(GetUrlByFacIdBo getUrlByFacIdBo);



    Object getOrgByWay(String wayId,String orgTypes);
}
