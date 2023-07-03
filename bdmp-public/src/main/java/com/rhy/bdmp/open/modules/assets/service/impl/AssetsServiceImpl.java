package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.CommonResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.config.CustomProperties;
import com.rhy.bdmp.open.modules.assets.dao.AssetsDao;
import com.rhy.bdmp.open.modules.assets.dao.FacilitiesDao;
import com.rhy.bdmp.open.modules.assets.dao.WayDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.*;
import com.rhy.bdmp.open.modules.assets.domain.vo.*;
import com.rhy.bdmp.open.modules.assets.enums.*;
import com.rhy.bdmp.open.modules.assets.feignclient.FeignClientYDJK;
import com.rhy.bdmp.open.modules.assets.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 公共资产服务
 *
 * @Author: yanggj
 * @Date: 2021/9/27 8:59
 * @Version: 1.0.0
 */
@Service
public class AssetsServiceImpl implements IAssetsService {
    public static final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    private IAssetsPermissionsTreeService permissionsTreeService;

    @Resource
    private IWayService wayService;

    @Resource
    private IFacilitiesService facilitiesService;

    @Resource
    private IDeviceService deviceService;

    @Resource
    private ICameraService cameraService;

    @Resource
    private AssetsDao assetsDao;

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    @Resource
    private IWayPlanService wayPlanService;

    @Resource
    private WayDao wayDao;

    @Resource
    private FacilitiesDao facilitiesDao;

    @Resource
    private IManufacturerService manufacturerService;

    @Resource
    private IDictService dictService;

    @Resource
    private IOrgService orgService;

    @Resource
    private CustomProperties customProperties;

    @Resource
    private FeignClientYDJK ydjkClient;


    @Override
    public Map<String, Object> getTotalMileage(Boolean isUseUserPermissions, String orgId, String nodeType) {
        // 参数校验
        checkParams(orgId, nodeType);
/*        if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
            throw new BadRequestException("不能统计设施里程数");
        }*/
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        // 获取高速路总里程
        return wayService.getTotalMileage(isUseUserPermissions, orgId, nodeType);
    }

    @Override
    public TollStationInfoBO getTollStationInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        // 获取收费站总况信息接口
        return facilitiesService.getTollStationStatInfo(isUseUserPermissions, orgId, nodeType);
    }

    @Override
    public TollStationDetailBO getTollStationDetail(String stationId) {
        if (StrUtil.isBlank(stationId)) {
            throw new BadRequestException("stationId: " + stationId + "不能为空");
        }
        TollStationDetailBO tollStationDetail = facilitiesService.getTollStationDetail(stationId);
        // 添加收费站图片访问地址
        if (null != tollStationDetail && StrUtil.isNotBlank(tollStationDetail.getFacImg())) {
            tollStationDetail.setFacImg(customProperties.getFileDownUrl() + tollStationDetail.getFacImg());
        }
        tollStationDetail.setEtcEntranceDriveWay(tollStationDetail.getEtcEntranceDriveWay() + "/" + tollStationDetail.getEtcEntranceDriveWay());
        tollStationDetail.setEtcExitDriveWay(tollStationDetail.getEtcExitDriveWay() + "/" + tollStationDetail.getEtcExitDriveWay());
        tollStationDetail.setMtcEntranceDriveWay(tollStationDetail.getMtcEntranceDriveWay() + "/" + tollStationDetail.getMtcEntranceDriveWay());
        tollStationDetail.setMtcExitDriveWay(tollStationDetail.getMtcExitDriveWay() + "/" + tollStationDetail.getMtcExitDriveWay());
        // 获取车道详情信息
        JSONObject jsonObject = JSONUtil.createObj(jsonConfig);
        List<FacLanVo> entranceList = new ArrayList<>();
        List<FacLanVo> exitListList = new ArrayList<>();
        List<FacilitiesTollStationLane> laneList = facilitiesDao.getLaneByTollStationId(stationId);
        for (FacilitiesTollStationLane lane : laneList) {
            FacLanVo facLanVo = new FacLanVo();
            facLanVo.setLaneId(lane.getLaneId());
            facLanVo.setLaneName(lane.getLaneName());
            facLanVo.setLaneNo(StrUtil.isBlank(lane.getLaneNo()) ? "0" : lane.getLaneNo());
            facLanVo.setLaneNoSort(StrUtil.isBlank(lane.getLaneNo()) ? 0 : Integer.parseInt(lane.getLaneNo()));
            facLanVo.setSort(null == lane.getSort() ? 0 : lane.getSort());
            facLanVo.setLaneIp(lane.getLaneIp());
            facLanVo.setLaneType(lane.getEtcMtc());
            facLanVo.setLaneInOut(lane.getInOut());
            if (1 == lane.getInOut()) {
                entranceList.add(facLanVo);
            } else if (2 == lane.getInOut()) {
                exitListList.add(facLanVo);
            }
        }
        entranceList = entranceList.stream().sorted(Comparator.comparing(FacLanVo::getSort)
                .thenComparing(FacLanVo::getLaneNoSort))
                .collect(Collectors.toList());

        exitListList = exitListList.stream().sorted(Comparator.comparing(FacLanVo::getSort)
                        .thenComparing(FacLanVo::getLaneNoSort))
                .collect(Collectors.toList());

        jsonObject.set("entranceList", entranceList);
        jsonObject.set("exitList", exitListList);
        jsonObject.set("direction", "");
        tollStationDetail.setLaneData(JSONUtil.toBean(jsonObject, Map.class));
        // 设置员工
        tollStationDetail.setEmployees(facilitiesDao.getEmployees(stationId));
        // 设置集控点电话
        tollStationDetail.setMonitorTelPhone(facilitiesDao.getControlPointTel(stationId));
        return tollStationDetail;
    }

    @Override
    public List<Object> getDeviceByTollStation(String stationId) {
        if (StrUtil.isBlank(stationId)) {
            throw new BadRequestException("stationId: " + stationId + "不能为空");
        }
        return deviceService.getDeviceByTollStation(stationId);
    }

    /**
     * 获取设备工作状态总况
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return /
     */
    @Override
    public HashMap<String, String> getDeviceStatInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {

        checkParams(orgId, nodeType);
        if (NodeTypeEnum.GROUP.getCode().equals(nodeType)) {
            throw new BadRequestException("nodeType不能为: " + nodeType);
        }
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        return deviceService.getDeviceStatInfo(isUseUserPermissions, orgId, nodeType);
    }

    /**
     * 获取当前登录用户所属机构管辖下的设备类型分布统计信息
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return /
     */
    @Override
    public JSONObject getDeviceTypeStatistics(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
//        if (NodeTypeEnum.GROUP.getCode().equals(nodeType)) {
//            throw new BadRequestException("nodeType不能为: " + nodeType);
//        }
        // nodeType转换
        nodeType = convertNodeType(nodeType);

        return deviceService.getDeviceStatBySystem(isUseUserPermissions, orgId, nodeType);
    }

    /**
     * 获取资源树
     *
     * @return /
     */
    @Override
    public List<Tree<String>> getComponeyWayGeo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        return permissionsTreeService.getAssetsOrgWayFacTree(isUseUserPermissions, orgId, nodeType);
    }

    @Override
    public BridgeClassifyStatBo getBridgeClassifyStat(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + ",节点类型不能为" + NodeTypeEnum.FAC.getDesc());
        }
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        // 获取桥梁统计信息
        return facilitiesService.getBridgeClassifyStat(isUseUserPermissions, orgId, nodeType);
    }

    @Override
    public JSONArray getFacStatByType(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType不能是设施
        if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + ",节点类型不能为" + NodeTypeEnum.FAC.getDesc());
        }
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        return facilitiesService.getFacStatByType(isUseUserPermissions, orgId, nodeType);
    }

//    @Override
//    public List<?> getFacInfoListByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code) {
//        checkParams(orgId, nodeType);
//        // nodeType不能是设施
//        if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
//            throw new BadRequestException("nodeType:" + nodeType + ",节点类型不能为" + NodeTypeEnum.FAC.getDesc());
//        }
//        // nodeType转换
//        nodeType = convertNodeType(nodeType);
//        return facilitiesService.getFacInfoListByType(isUseUserPermissions, orgId, nodeType, code);
//    }

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
//    @Override
//    public Map<String, Object> getFacInfoByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search, String dictKeys) {
//        checkParams(orgId, nodeType);
//        // nodeType转换
//        nodeType = convertNodeType(nodeType);
//
//        FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
//        if (facilitiesTypeEnum == null) {
//            throw new BadRequestException("无效的设施类型编号: " + code);
//        }
//        List<String> facilitiesTypes = new ArrayList<>();
//        facilitiesTypes.add(code);
//        List<FacilitiesInfoBO> facilitiesInfoBOList;
//        List<DictType> dictTypeList;
//        List<Map<String, Object>> dictKeyNumList;
//        Map<Integer, Long> dictKeyNumMaps = new HashMap<>();
//        switch (facilitiesTypeEnum) {
//            case BRIDGE:
//                // 获取桥梁统计
//                dictKeyNumList = facilitiesService.listBridgeCount(isUseUserPermissions, orgId, nodeType, search);
//                for (Map<String, Object> map : dictKeyNumList){
//                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
//                }
//                dictTypeList = Arrays.stream(BridgeEnum.values()).map(item -> {
//                    DictType dictType = new DictType();
//                    dictType.setDictKey(item.getDictKey());
//                    dictType.setDickName(item.getDictName());
//                    dictType.setCheck(StrUtil.isBlank(dictKeys) ? item.getCheck(): -1 != ("," + dictKeys + ",").indexOf("," + item.getDictKey() + ","));
//                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
//                    return dictType;
//                }).collect(Collectors.toList());
//                // 获取桥梁列表
//                facilitiesInfoBOList = facilitiesService.listBridge(isUseUserPermissions, orgId, nodeType, search, dictKeys)
//                        .parallelStream().peek(facilitiesInfoBO -> facilitiesInfoBO.setType(code))
//                        .collect(Collectors.toList());
//
//                break;
//            case TOLL_STATION:
//                // 获取收费站统计
//                dictKeyNumList = facilitiesService.listTollStationCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
//                for (Map<String, Object> map : dictKeyNumList){
//                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
//                }
//                dictTypeList = Arrays.stream(TollStationEnum.values()).map(item -> {
//                    DictType dictType = new DictType();
//                    dictType.setDictKey(item.getDictKey());
//                    dictType.setDickName(item.getDictName());
//                    dictType.setCheck(StrUtil.isBlank(dictKeys) ? item.getCheck(): -1 != ("," + dictKeys + ",").indexOf("," + item.getDictKey() + ","));
//                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
//                    return dictType;
//                }).collect(Collectors.toList());
//                // 获取收费站列表
//                facilitiesInfoBOList = facilitiesService.listTollStation(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
//                        .parallelStream().peek(facilitiesInfoBO -> {
//                                    facilitiesInfoBO.setDictName(TollStationEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
//                                    facilitiesInfoBO.setType(code);
//                                }
//                        ).collect(Collectors.toList());
//                break;
//            case SERVICE_AREA:
//                // 获取服务区统计
//                dictKeyNumList = facilitiesService.listServiceAreaCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
//                for (Map<String, Object> map : dictKeyNumList){
//                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
//                }
//                dictTypeList = Arrays.stream(ServiceAreaEnum.values()).map(item -> {
//                    DictType dictType = new DictType();
//                    dictType.setDictKey(item.getDictKey());
//                    dictType.setDickName(item.getDictName());
//                    dictType.setCheck(StrUtil.isBlank(dictKeys) ? item.getCheck(): -1 != ("," + dictKeys + ",").indexOf("," + item.getDictKey() + ","));
//                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
//                    return dictType;
//                }).collect(Collectors.toList());
//                // 获取服务区列表
//                facilitiesInfoBOList = facilitiesService.listServiceArea(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
//                        .parallelStream().peek(facilitiesInfoBO -> {
//                                    facilitiesInfoBO.setDictName(ServiceAreaEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
//                                    facilitiesInfoBO.setType(code);
//                                }
//                        ).collect(Collectors.toList());
//                break;
//            case DOOR_FRAME:
//                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
//                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
//                // 获取门架统计
//                dictKeyNumList = facilitiesService.listDoorFrameCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
//                for (Map<String, Object> map : dictKeyNumList){
//                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
//                }
//                dictTypeList = Arrays.stream(DoorFrameEnum.values()).map(item -> {
//                    DictType dictType = new DictType();
//                    dictType.setDictKey(item.getDictKey());
//                    dictType.setDickName(item.getDictName());
//                    dictType.setCheck(StrUtil.isBlank(dictKeys) ? item.getCheck(): -1 != ("," + dictKeys + ",").indexOf("," + item.getDictKey() + ","));
//                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
//                    return dictType;
//                }).collect(Collectors.toList());
//                // 获取门架列表
//                facilitiesInfoBOList = facilitiesService.listDoorFrame(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
//                        .parallelStream().peek(facilitiesInfoBO -> {
//                                    facilitiesInfoBO.setDictName(DoorFrameEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
//                                    facilitiesInfoBO.setType(code);
//                                }
//                        ).collect(Collectors.toList());
//                break;
//            case TUNNEL:
//                // 获取隧道统计
//                dictKeyNumList = facilitiesService.listTunnelCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
//                for (Map<String, Object> map : dictKeyNumList){
//                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
//                }
//                dictTypeList = Arrays.stream(TunnelEnum.values()).map(item -> {
//                    DictType dictType = new DictType();
//                    dictType.setDictKey(item.getDictKey());
//                    dictType.setDickName(item.getDictName());
//                    dictType.setCheck(StrUtil.isBlank(dictKeys) ? item.getCheck(): -1 != ("," + dictKeys + ",").indexOf("," + item.getDictKey() + ","));
//                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
//                    return dictType;
//                }).collect(Collectors.toList());
//                // 获取隧道列表
//                facilitiesInfoBOList = facilitiesService.listTunnel(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
//                        .parallelStream().peek(facilitiesInfoBO -> {
//                                    facilitiesInfoBO.setType(code);
//                                }
//                        ).collect(Collectors.toList());
//                break;
//            default:
//                throw new BadRequestException("未知的设施类型");
//        }
//        Map<String, Object> result = new HashMap<>();
//        result.put("dictTypeList", dictTypeList);
//        result.put("list", facilitiesInfoBOList);
//        return result;
//    }

    /**
     * 根据设施类型获取设施（含具体设施字典分类统计）
     *
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型
     * @param code                 设施类型CODE
     * @param search               设施名称
     * @return
     */
    @Override
    public List<DictType> getFacDictTypeByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
        if (facilitiesTypeEnum == null) {
            throw new BadRequestException("无效的设施类型编号: " + code);
        }
        List<String> facilitiesTypes = new ArrayList<>();
        facilitiesTypes.add(code);
        List<DictType> dictTypeList;
        List<Map<String, Object>> dictKeyNumList;
        Map<Integer, Long> dictKeyNumMaps = new HashMap<>();
        switch (facilitiesTypeEnum) {
            case BRIDGE:
                // 获取桥梁统计
                dictKeyNumList = facilitiesService.listBridgeCount(isUseUserPermissions, orgId, nodeType, search);
                for (Map<String, Object> map : dictKeyNumList) {
                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
                }
                dictTypeList = Arrays.stream(BridgeEnum.values()).map(item -> {
                    DictType dictType = new DictType();
                    dictType.setDictKey(item.getDictKey());
                    dictType.setDickName(item.getDictName());
                    dictType.setCheck(true);
                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
                    return dictType;
                }).collect(Collectors.toList());
                break;
            case TOLL_STATION:
                // 获取收费站统计
                dictKeyNumList = facilitiesService.listTollStationCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
                for (Map<String, Object> map : dictKeyNumList) {
                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
                }
                dictTypeList = Arrays.stream(TollStationEnum.values()).map(item -> {
                    DictType dictType = new DictType();
                    dictType.setDictKey(item.getDictKey());
                    dictType.setDickName(item.getDictName());
                    dictType.setCheck(true);
                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
                    return dictType;
                }).collect(Collectors.toList());
                break;
            case SERVICE_AREA:
                // 获取服务区统计
                dictKeyNumList = facilitiesService.listServiceAreaCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
                for (Map<String, Object> map : dictKeyNumList) {
                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
                }
                dictTypeList = Arrays.stream(ServiceAreaEnum.values()).map(item -> {
                    DictType dictType = new DictType();
                    dictType.setDictKey(item.getDictKey());
                    dictType.setDickName(item.getDictName());
                    dictType.setCheck(true);
                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
                    return dictType;
                }).collect(Collectors.toList());
                break;
            case DOOR_FRAME:
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
                // 获取门架统计
                dictKeyNumList = facilitiesService.listDoorFrameCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
                for (Map<String, Object> map : dictKeyNumList) {
                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
                }
                dictTypeList = Arrays.stream(DoorFrameEnum.values()).map(item -> {
                    DictType dictType = new DictType();
                    dictType.setDictKey(item.getDictKey());
                    dictType.setDickName(item.getDictName());
                    dictType.setCheck(true);
                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
                    return dictType;
                }).collect(Collectors.toList());
                break;
            case TUNNEL:
                // 获取隧道统计
                dictKeyNumList = facilitiesService.listTunnelCount(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes);
                for (Map<String, Object> map : dictKeyNumList) {
                    dictKeyNumMaps.put(MapUtil.getInt(map, "dictKey"), MapUtil.getLong(map, "total"));
                }
                dictTypeList = Arrays.stream(TunnelEnum.values()).map(item -> {
                    DictType dictType = new DictType();
                    dictType.setDictKey(item.getDictKey());
                    dictType.setDickName(item.getDictName());
                    dictType.setCheck(true);
                    dictType.setNum(dictKeyNumMaps.getOrDefault(item.getDictKey(), 0L));
                    return dictType;
                }).collect(Collectors.toList());
                break;
            default:
                throw new BadRequestException("未知的设施类型");
        }
        return dictTypeList;
    }

    /**
     * 根据设施类型获取设施（含具体设施字典分类统计）
     *
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型
     * @param code                 设施类型CODE
     * @param search               设施名称
     * @param dictKeys             具体设施字典分类
     * @return
     */
    @Override
    public List<FacilitiesInfoBO> getFacInfoListByDictType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search, String dictKeys) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
        if (facilitiesTypeEnum == null) {
            throw new BadRequestException("无效的设施类型编号: " + code);
        }
        List<String> facilitiesTypes = new ArrayList<>();
        facilitiesTypes.add(code);
        List<FacilitiesInfoBO> facilitiesInfoBOList;
        switch (facilitiesTypeEnum) {
            case BRIDGE:
                // 获取桥梁列表
                facilitiesInfoBOList = facilitiesService.listBridge(isUseUserPermissions, orgId, nodeType, search, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> facilitiesInfoBO.setType(code))
                        .collect(Collectors.toList());

                break;
            case TOLL_STATION:
                // 获取收费站列表
                facilitiesInfoBOList = facilitiesService.listTollStation(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setDictName(TollStationEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            case SERVICE_AREA:
                // 获取服务区列表
                facilitiesInfoBOList = facilitiesService.listServiceArea(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setDictName(ServiceAreaEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            case DOOR_FRAME:
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
                // 获取门架列表
                facilitiesInfoBOList = facilitiesService.listDoorFrame(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setDictName(DoorFrameEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            case TUNNEL:
                // 获取隧道列表
                facilitiesInfoBOList = facilitiesService.listTunnel(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            default:
                throw new BadRequestException("未知的设施类型");
        }
        return facilitiesInfoBOList;
    }

    /**
     * 根据设施类型获取设施
     *
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型
     * @param code                 设施类型CODE
     * @param search               设施名称
     * @return
     */
    @Override
    public List<FacilitiesInfoBO> getFacInfoListByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);

        FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
        if (facilitiesTypeEnum == null) {
            throw new BadRequestException("无效的设施类型编号: " + code);
        }
        List<String> facilitiesTypes = new ArrayList<>();
        facilitiesTypes.add(code);
        List<FacilitiesInfoBO> facilitiesInfoBOList;
        String dictKeys = "";
        switch (facilitiesTypeEnum) {
            case BRIDGE:
                for (BridgeEnum bridgeEnum : BridgeEnum.values()) {
                    dictKeys += bridgeEnum.getDictKey() + ",";
                }
                if (StrUtil.isNotBlank(dictKeys)) {
                    dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                }
                // 获取桥梁列表
                facilitiesInfoBOList = facilitiesService.listBridge(isUseUserPermissions, orgId, nodeType, search, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> facilitiesInfoBO.setType(code))
                        .collect(Collectors.toList());

                break;
            case TOLL_STATION:
                for (TollStationEnum tollStationEnum : TollStationEnum.values()) {
                    dictKeys += tollStationEnum.getDictKey() + ",";
                }
                if (StrUtil.isNotBlank(dictKeys)) {
                    dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                }
                // 获取收费站列表
                facilitiesInfoBOList = facilitiesService.listTollStation(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setDictName(TollStationEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            case SERVICE_AREA:
                for (ServiceAreaEnum serviceAreaEnum : ServiceAreaEnum.values()) {
                    dictKeys += serviceAreaEnum.getDictKey() + ",";
                }
                if (StrUtil.isNotBlank(dictKeys)) {
                    dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                }
                // 获取服务区列表
                facilitiesInfoBOList = facilitiesService.listServiceArea(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setDictName(ServiceAreaEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            case DOOR_FRAME:
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
                for (DoorFrameEnum doorFrameEnum : DoorFrameEnum.values()) {
                    dictKeys += doorFrameEnum.getDictKey() + ",";
                }
                if (StrUtil.isNotBlank(dictKeys)) {
                    dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                }
                // 获取门架列表
                facilitiesInfoBOList = facilitiesService.listDoorFrame(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setDictName(DoorFrameEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            case TUNNEL:
                for (TunnelEnum tunnelEnum : TunnelEnum.values()) {
                    dictKeys += tunnelEnum.getDictKey() + ",";
                }
                if (StrUtil.isNotBlank(dictKeys)) {
                    dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                }
                // 获取隧道列表
                facilitiesInfoBOList = facilitiesService.listTunnel(isUseUserPermissions, orgId, nodeType, search, facilitiesTypes, dictKeys)
                        .parallelStream().peek(facilitiesInfoBO -> {
                                    facilitiesInfoBO.setType(code);
                                }
                        ).collect(Collectors.toList());
                break;
            default:
                throw new BadRequestException("未知的设施类型");
        }
        return facilitiesInfoBOList;
    }

    //  查询桥梁信息（过江大桥)
    @Override
    public List<FacilitiesInfoBO> listBridgeByCrossRiver(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listBridgeByCrossRiver(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public JSONArray getDeviceStatByType(Boolean isUseUserPermissions, String orgId, String nodeType, String search) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        return deviceService.getDeviceStatByType(isUseUserPermissions, orgId, nodeType, search);
    }

    @Override
    public List<?> getDeviceInfoListByType(Boolean isUseUserPermissions, String orgId, String nodeType, String code, String search) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        return deviceService.getDeviceInfoListByType(isUseUserPermissions, orgId, nodeType, code, search);
    }

    @Override
    public JSONObject getGantryInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        return facilitiesService.getGantryInfo(isUseUserPermissions, orgId, nodeType);
    }

    @Override
    public GantryDetailBO getGantryDetail(String deviceNumber) {
        return facilitiesService.getGantryDetail(deviceNumber);
    }

    @Override
    public GantryTree getGantryTree(String deviceNumber) {
        // 获取门架
        //Facilities facilities = facilitiesService.selectByIdAndType(deviceNumber, FacilitiesTypeEnum.DOOR_FRAME.getCode());
        GantryBO gantryBO = facilitiesService.getGantry(deviceNumber);

        // 获取设备
        List<Device> deviceList = deviceService.selectByFacId(gantryBO.getFacilitiesId());
        // 数据封装
        GantryTree gantryTree = new GantryTree();
        gantryTree.setId(gantryBO.getId());
        gantryTree.setName(gantryBO.getName());
        gantryTree.setOriginId(gantryBO.getOriginId());
        gantryTree.setStatus(String.valueOf(gantryBO.getStatus()));
        gantryTree.setLongitude(gantryBO.getLongitude());
        gantryTree.setLatitude(gantryBO.getLatitude());
        gantryTree.setIsMonitor(gantryBO.getIsMonitor());
        gantryTree.setMapUrl(gantryBO.getMapUrl());
        gantryTree.setOrgId(gantryBO.getOrgId());
        gantryTree.setOrgName(gantryBO.getOrgName());
        gantryTree.setWaysectionId(gantryBO.getWaysectionId());
        gantryTree.setWaysectionName(gantryBO.getWaysectionName());
        gantryTree.setCenterOffNo(gantryBO.getCenterOffNo());
        gantryTree.setBeginStakeNo(gantryBO.getBeginStakeNo());
        gantryTree.setEndStakeNo(gantryBO.getEndStakeNo());
        ArrayList<Object> child = new ArrayList<>(deviceList.size());
        deviceList.forEach(device -> {
            GantryTree deviceVO = new GantryTree();
            deviceVO.setId(device.getDeviceId());
            deviceVO.setName(device.getDeviceName());
            deviceVO.setOriginId(device.getDeviceIdOld());
            deviceVO.setStatus(device.getWorkStatus());
            deviceVO.setDeviceNo(device.getDeviceCode());
            deviceVO.setLatitude(device.getLongitude());
            deviceVO.setLongitude(device.getLatitude());
            child.add(deviceVO);
        });
        gantryTree.setChildren(child);
        return gantryTree;
    }


    @Override
    public HashMap<String, Object> getTunnelStatByType(Boolean isUseUserPermissions, String orgId, String nodeType) {
        // 校验参数
        checkParams(orgId, nodeType);
        // nodeType不能是设施
        if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + ",节点类型不能为" + NodeTypeEnum.FAC.getDesc());
        }
        nodeType = convertNodeType(nodeType);
        return facilitiesService.getTunnelStatByType(isUseUserPermissions, orgId, nodeType);
    }

    @Override
    public DeviceBO getDeviceDetail(String deviceId) {
        Map<String, String> device = deviceService.getDeviceDetail(deviceId);
        return BeanUtil.mapToBeanIgnoreCase(device, DeviceBO.class, true);
    }

    @Override
    public JSONObject getAllMsg(String nodeType) {
        // 判断 nodeType 是否合法
        if (StrUtil.isNotBlank(nodeType) && StrUtil.isNotBlank(nodeType) && !NodeTypeEnum.include(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + " 为非法参数,请检查");
        }
        boolean useUserPermissions = true;
        if (StrUtil.isBlank(nodeType)) {
            // 获取运营公司列表
            List<Org> assetsOrg = permissionsTreeService.getAssetsOrg(useUserPermissions);
            // 获取路段列表
            List<Waysection> assetsWay = permissionsTreeService.getAssetsWay(useUserPermissions);
            // 获取设施列表
            List<Facilities> assetsFac = permissionsTreeService.getAssetsFac(useUserPermissions);

            return JSONUtil.createObj(jsonConfig)
                    .putOnce("companyList", assetsOrg.parallelStream().map(org -> JSONUtil.createObj(jsonConfig)
                            .putOnce("id", org.getOrgId())
                            .putOnce("name", org.getOrgName())
                            .putOnce("code", org.getOrgCode()))
                            .collect(Collectors.toList()))
                    .putOnce("waySectionList", assetsWay.parallelStream().map(waysection -> JSONUtil.createObj(jsonConfig)
                            .putOnce("id", waysection.getWaysectionId())
                            .putOnce("companyId", waysection.getManageId())
                            .putOnce("name", waysection.getWaysectionName())
                            .putOnce("code", waysection.getWaysectionCode())
                            .putOnce("beginStakeNo", waysection.getBeginStakeNo())
                            .putOnce("endStakeNo", waysection.getEndStakeNo())
                    )
                            .collect(Collectors.toList()))
                    .putOnce("geographyList", assetsFac.parallelStream().map(fac -> JSONUtil.createObj(jsonConfig)
                            .putOnce("id", fac.getFacilitiesId())
                            .putOnce("waySectionId", fac.getWaysectionId())
                            .putOnce("name", fac.getFacilitiesName())
                            .putOnce("code", fac.getFacilitiesCode())
                            .putOnce("isMonitor", fac.getIsMonitor())
                            .putOnce("mapUrl", fac.getMapUrl())
                            .putOnce("status", fac.getStatus())
                            .putOnce("facilitiesType", fac.getFacilitiesType())
                            .putOnce("originId", fac.getFacilitiesIdOld())
                            .putOnce("centerOffNo", fac.getCenterOffNo())
                            .putOnce("beginStakeNo", fac.getBeginStakeNo())
                            .putOnce("endStakeNo", fac.getEndStakeNo())
                    )
                            .collect(Collectors.toList()));
        } else {
            if (NodeTypeEnum.ORG.getCode().equals(nodeType)) {
                // 获取运营公司列表
                List<Org> assetsOrg = permissionsTreeService.getAssetsOrg(useUserPermissions);
                return JSONUtil.createObj(jsonConfig)
                        .putOnce("companyList", assetsOrg.parallelStream().map(org -> JSONUtil.createObj(jsonConfig)
                                .putOnce("id", org.getOrgId())
                                .putOnce("name", org.getOrgName())
                                .putOnce("code", org.getOrgCode()))
                                .collect(Collectors.toList()));
            } else if (NodeTypeEnum.WAY.getCode().equals(nodeType)) {
                // 获取路段列表
                List<Waysection> assetsWay = permissionsTreeService.getAssetsWay(useUserPermissions);
                return JSONUtil.createObj(jsonConfig)
                        .putOnce("waySectionList", assetsWay.parallelStream().map(waysection -> JSONUtil.createObj(jsonConfig)
                                .putOnce("id", waysection.getWaysectionId())
                                .putOnce("companyId", waysection.getManageId())
                                .putOnce("name", waysection.getWaysectionName())
                                .putOnce("code", waysection.getWaysectionCode())
                                .putOnce("beginStakeNo", waysection.getBeginStakeNo())
                                .putOnce("endStakeNo", waysection.getEndStakeNo())
                        )
                                .collect(Collectors.toList()));
            } else if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
                // 获取设施列表
                List<Facilities> assetsFac = permissionsTreeService.getAssetsFac(useUserPermissions);
                return JSONUtil.createObj(jsonConfig)
                        .putOnce("geographyList", assetsFac.parallelStream().map(fac -> JSONUtil.createObj(jsonConfig)
                                .putOnce("id", fac.getFacilitiesId())
                                .putOnce("waySectionId", fac.getWaysectionId())
                                .putOnce("name", fac.getFacilitiesName())
                                .putOnce("code", fac.getFacilitiesCode())
                                .putOnce("isMonitor", fac.getIsMonitor())
                                .putOnce("mapUrl", fac.getMapUrl())
                                .putOnce("status", fac.getStatus())
                                .putOnce("facilitiesType", fac.getFacilitiesType())
                                .putOnce("originId", fac.getFacilitiesIdOld())
                                .putOnce("centerOffNo", fac.getCenterOffNo())
                                .putOnce("beginStakeNo", fac.getBeginStakeNo())
                                .putOnce("endStakeNo", fac.getEndStakeNo())
                        )
                                .collect(Collectors.toList()));
            } else {
                throw new BadRequestException("nodeType:" + nodeType + " 为非法参数,必须在[1,2,3]范围内");
            }
        }
    }

    @Override
    public JSONObject getRepairRateStatistics(Boolean isUseUserPermissions, String orgId, String nodeType) {
        return null;
    }

    @Override
    public List<?> queryWaySection(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType不能是设施
        if (NodeTypeEnum.FAC.getCode().equals(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + ",节点类型不能为" + NodeTypeEnum.FAC.getDesc());
        }
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        List<Waysection> assetsWay = permissionsTreeService.getAssetsWay(isUseUserPermissions, nodeType, orgId);

        return assetsWay.parallelStream().map(waySection -> JSONUtil.createObj(jsonConfig)
                .putOnce("id", waySection.getWaysectionId())
                .putOnce("name", waySection.getWaysectionName())
                .putOnce("code", waySection.getWaysectionCode())
                .putOnce("oriWaynetId", waySection.getOriWaynetId())
                .putOnce("companyId", waySection.getManageId())
                .putOnce("companyName", waySection.getOrgName())
                .putOnce("nodeId", waySection.getNodeId())
                .putOnce("districtIds", waySection.getDistrictIds())
                .putOnce("beginStakeNo", waySection.getBeginStakeNo())
                .putOnce("endStakeNo", waySection.getEndStakeNo())
                .putOnce("shortName", waySection.getShortName())
        )
                .collect(Collectors.toList());
    }

    @Override
    public JSONObject getCameraForBridge(String geographyinfoId) {
        if (StrUtil.isBlank(geographyinfoId)) {
            throw new BadRequestException("geographyinfoId: " + geographyinfoId + ", 不能为空");
        }

        // 获取桥梁信息
        FacilitiesBridge bridge = facilitiesService.selectBridgeById(geographyinfoId);

        // 获取桥梁下 视频资源
        CameraResourceGroupBO cameraResourceGroupBO = new CameraResourceGroupBO();
        // 当桥梁的设施id不空时,查询该设施的视频资源列表
        if (StrUtil.isNotBlank(bridge.getFacilitiesId())) {
            cameraResourceGroupBO = listCameraResource(true, bridge.getFacilitiesId(), NodeTypeEnum.FAC.getCode());
        }

        // 封装响应数据
        JSONObject jsonObject = JSONUtil.createObj(jsonConfig)
                .putOnce("pileNumber", bridge.getBridgeCenterStake())
                .putOnce("bridgeLevel", bridge.getTechnologyEvaluateName())
                .putOnce("bridgeStartStake", bridge.getBridgeStartStake())
                .putOnce("bridgeEndStake", bridge.getBridgeEndStake())
                .putOnce("acrossRadiusTypeName", bridge.getAcrossRadiusTypeName())
                .putOnce("adminRegionCode", bridge.getAdminRegionCode())
                .putOnce("adminRegionName", bridge.getAdminRegionName())
                .putOnce("companyName", bridge.getManageName())
                .putOnce("routesName", bridge.getRouteSName())
                .putOnce("bridgeName", bridge.getBridgeName())
                .putOnce("longitude", bridge.getLongitude())
                .putOnce("latitude", bridge.getLatitude())
                .putOnce("bridgeMeter", bridge.getBridgeMeter())
                .putOnce("isMonitor", bridge.getIsMonitor())
                .putOnce("mapUrl", bridge.getMapUrl())
                .putOnce("oriWaysectionNo", bridge.getOriWaysectionNo())
                .putOnce("bridgeSurfaceWidMeter", bridge.getBridgeSurfaceWideMeter())
                .putOnce("bridgeAllWideMeter", bridge.getBridgeAllWideMeter())
                .putOnce("cameraYtList", null)
                .putOnce("cameraTlList", null)
                .putOnce("cameraSyList", null);
        if (CollectionUtil.isNotEmpty(cameraResourceGroupBO.getCameraYtList())) {
            jsonObject.set("cameraYtList", cameraResourceGroupBO.getCameraYtList().parallelStream().map(camera -> JSONUtil.createObj(jsonConfig)
                    .putOnce("cameraId", camera.getCameraId())
                    .putOnce("cameraIp", camera.getCameraIp())
                    .putOnce("cameraName", camera.getCameraName())
                    .putOnce("longitude", camera.getLongitude())
                    .putOnce("latitude", camera.getLatitude())
                    .putOnce("dataSource", camera.getDataSource())
                    .putOnce("onlineStatus", camera.getOnlineStatus())
                    .putOpt("hasPTZ", camera.getHasPTZ())
            ).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(cameraResourceGroupBO.getCameraTlList())) {
            jsonObject.set("cameraTlList", cameraResourceGroupBO.getCameraTlList().parallelStream().map(camera -> JSONUtil.createObj(jsonConfig)
                    .putOnce("cameraId", camera.getCameraId())
                    .putOnce("cameraIp", camera.getCameraIp())
                    .putOnce("cameraName", camera.getCameraName())
                    .putOnce("longitude", camera.getLongitude())
                    .putOnce("latitude", camera.getLatitude())
                    .putOnce("dataSource", camera.getDataSource())
                    .putOnce("onlineStatus", camera.getOnlineStatus())
                    .putOnce("hasPTZ", camera.getHasPTZ())
            ).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(cameraResourceGroupBO.getCameraSyList())) {
            /*jsonObject.set("cameraSyList", cameraResourceGroupBO.getCameraSyList().parallelStream().map(camera -> JSONUtil.createObj(jsonConfig)
                    .putOnce("cameraId", camera.getCameraId())
                    .putOnce("cameraIp", camera.getCameraIp())
                    .putOnce("cameraName", camera.getCameraName())
                    .putOnce("longitude", camera.getLongitude())
                    .putOnce("latitude", camera.getLatitude())
                    .putOnce("dataSource", camera.getDataSource())
                    .putOnce("onlineStatus", camera.getOnlineStatus())
                    .putOnce("hasPTZ", camera.getHasPTZ())
            ).collect(Collectors.toList()));*/
            jsonObject.set("cameraSyList", Collections.emptyList());
        }
        return jsonObject;
    }

    @Override
    public CameraResourceGroupBO listCameraResource(Boolean isUseUserPermissions, String orgId, String nodeType) {
        CameraResourceGroupBO cameraResourceGroupBO = new CameraResourceGroupBO();
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        // 获取腾路视频资源列表
        List<CameraResourceTl> cameraResourceTlList = cameraService.listCameraResourceTl(isUseUserPermissions, orgId, nodeType);
        List<CameraResourceBO> cameraTlList = cameraResourceTlList.stream().map(cameraResourceTlBO -> {
            CameraResourceBO cameraResourceBO = new CameraResourceBO();
            BeanUtil.copyProperties(cameraResourceTlBO, cameraResourceBO);
            cameraResourceBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_TL.getCode());
            cameraResourceBO.setOnlineStatus(cameraResourceTlBO.getOnlineStatus());
            return cameraResourceBO;
        }).collect(Collectors.toList());

        // 收费站广场数据排前面
        List<CameraResourceBO> tollStationCameraListTl = new ArrayList<>();
        List<CameraResourceBO> cameraListTl = new ArrayList<>();
        for (CameraResourceBO cameraResourceBO : cameraTlList) {
            if ("11".equals(String.valueOf(cameraResourceBO.getLocationType()))) {
                tollStationCameraListTl.add(cameraResourceBO);
            } else {
                cameraListTl.add(cameraResourceBO);
            }
        }
        tollStationCameraListTl.addAll(cameraListTl);

        // 获取 yt 视频资源列表
        List<CameraResourceYt> cameraResourceYtList = cameraService.listCameraResourceYt(isUseUserPermissions, orgId, nodeType);
        List<CameraResourceBO> cameraYtList = cameraResourceYtList.stream().map(cameraResourceYt -> {
            CameraResourceBO cameraResourceBO = new CameraResourceBO();
            cameraResourceBO.setCameraId(cameraResourceYt.getId());
            cameraResourceBO.setCameraName(cameraResourceYt.getName());
            cameraResourceBO.setCameraIp("");
            cameraResourceBO.setLongitude(cameraResourceYt.getCoordX());
            cameraResourceBO.setLatitude(cameraResourceYt.getCoordY());
            cameraResourceBO.setWayName(cameraResourceYt.getWayName());
            cameraResourceBO.setInfoName(cameraResourceYt.getLocation());
            cameraResourceBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_YT.getCode());
            cameraResourceBO.setOnlineStatus(String.valueOf(cameraResourceYt.getStatus()));
            cameraResourceBO.setLocationType(cameraResourceYt.getLocationType());
            cameraResourceBO.setWayId(cameraResourceYt.getWayId());
            cameraResourceBO.setCompName(cameraResourceYt.getCompName());
            cameraResourceBO.setCompId(cameraResourceYt.getCompId());
            cameraResourceBO.setHasPTZ(cameraResourceYt.getHasPtz());
            cameraResourceBO.setCenterOffNo(cameraResourceYt.getCenterOffNo());
            return cameraResourceBO;
        }).collect(Collectors.toList());
        // 收费站广场数据排前面
        List<CameraResourceBO> tollStationCameraListYt = new ArrayList<>();
        List<CameraResourceBO> cameraListYt = new ArrayList<>();
        for (CameraResourceBO cameraResourceBO : cameraYtList) {
            if ("11".equals(String.valueOf(cameraResourceBO.getLocationType()))) {
                tollStationCameraListYt.add(cameraResourceBO);
            } else {
                cameraListYt.add(cameraResourceBO);
            }
        }
        tollStationCameraListYt.addAll(cameraListYt);

        // 获取上云视频资源列表
        List<CameraResourceSy> cameraResourceSyList = cameraService.listCameraResourceSy(isUseUserPermissions, orgId, nodeType);
        List<CameraResourceBO> cameraSyList = cameraResourceSyList.stream().map(cameraResourceSy -> {
            CameraResourceBO cameraResourceBO = new CameraResourceBO();
            cameraResourceBO.setCameraId(cameraResourceSy.getId());
            cameraResourceBO.setCameraName(cameraResourceSy.getName());
            cameraResourceBO.setCameraIp(cameraResourceSy.getIp());
            cameraResourceBO.setLongitude(cameraResourceSy.getCoordX());
            cameraResourceBO.setLatitude(cameraResourceSy.getCoordY());
            cameraResourceBO.setWayName(cameraResourceSy.getWayName());
            cameraResourceBO.setInfoName(cameraResourceSy.getLocation());
            cameraResourceBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_SY.getCode());
            cameraResourceBO.setOnlineStatus(String.valueOf(cameraResourceSy.getStatus()));
            cameraResourceBO.setLocationType(cameraResourceSy.getLocationType());
            cameraResourceBO.setWayId(cameraResourceSy.getWayId());
            cameraResourceBO.setCompName(cameraResourceSy.getCompName());
            cameraResourceBO.setCompId(cameraResourceSy.getCompId());
            cameraResourceBO.setHasPTZ(cameraResourceSy.getHasPtz());
            return cameraResourceBO;
        }).collect(Collectors.toList());
        List<CameraResourceBO> tollStationCameraListSy = new ArrayList<>();
        List<CameraResourceBO> cameraListSy = new ArrayList<>();
        for (CameraResourceBO cameraResourceBO : cameraSyList) {
            if ("11".equals(String.valueOf(cameraResourceBO.getLocationType()))) {
                tollStationCameraListSy.add(cameraResourceBO);
            } else {
                cameraListSy.add(cameraResourceBO);
            }
        }
        tollStationCameraListSy.addAll(cameraListSy);

        cameraResourceGroupBO.setCameraYtList(tollStationCameraListYt);
        cameraResourceGroupBO.setCameraTlList(tollStationCameraListTl);
//        cameraResourceGroupBO.setCameraSyList(tollStationCameraListSy);
        cameraResourceGroupBO.setCameraSyList(Collections.emptyList());
        return cameraResourceGroupBO;
    }

    @Override
    public Map<String, Object> listCameraId(Boolean isUseUserPermissions, String orgId, String nodeType) {
        Map<String, Object> resultMap = new HashMap<>();
        CameraResourceGroupBO cameraResourceGroupBO = new CameraResourceGroupBO();
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);
        // 获取腾路视频资源列表
        List<Map<String, Object>> cameraIdTlList = cameraService.listCameraIdTl(isUseUserPermissions, orgId, nodeType);
        resultMap.put("cameraTlList", cameraIdTlList);
        // 获取 yt 视频资源列表
        List<Map<String, Object>> cameraIdYtList = cameraService.listCameraIdYt(isUseUserPermissions, orgId, nodeType);
        resultMap.put("cameraYtList", cameraIdYtList);
        return resultMap;
    }

    @Override
    public List<CameraResourceYtBO> listCameraResourceYt(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);

        List<CameraResourceYt> cameraResourceYtList = cameraService.listCameraResourceYt(isUseUserPermissions, orgId, nodeType);
        if (cameraResourceYtList.isEmpty()) {
            return Collections.emptyList();
        }
        List<CameraResourceYtBO> cameraResourceYtBOList = cameraResourceYtList.stream().map(item -> {
            CameraResourceYtBO cameraResourceYtBO = BeanUtil.toBean(item, CameraResourceYtBO.class);
            cameraResourceYtBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_YT.getCode());
            cameraResourceYtBO.setOnlineStatus(String.valueOf(item.getStatus()));
            return cameraResourceYtBO;
        }).collect(Collectors.toList());

        // 收费站广场数据排前面
        List<CameraResourceYtBO> tollStationCameraListYt = new ArrayList<>();
        List<CameraResourceYtBO> cameraListYt = new ArrayList<>();
        for (CameraResourceYtBO cameraResourceYtBO : cameraResourceYtBOList) {
            if ("11".equals(String.valueOf(cameraResourceYtBO.getLocationType()))) {
                tollStationCameraListYt.add(cameraResourceYtBO);
            } else {
                cameraListYt.add(cameraResourceYtBO);
            }
        }
        tollStationCameraListYt.addAll(cameraListYt);
        return tollStationCameraListYt;
    }

    @Override
    public CameraResourceYtBO queryYtDetail(String id) {
        CameraResourceYt cameraResourceYt = cameraService.selectCameraYtById(id);
        if (cameraResourceYt == null) {
            return null;
        }
        CameraResourceYtBO cameraResourceYtBO = BeanUtil.toBean(cameraResourceYt, CameraResourceYtBO.class);
        cameraResourceYtBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_YT.getCode());
        return cameraResourceYtBO;

    }

    @Override
    public CameraResourceSyBO querySyDetail(String id) {
        CameraResourceSy cameraResourceSy = cameraService.selectCameraSyById(id);
        if (cameraResourceSy == null) {
            return null;
        }
        CameraResourceSyBO cameraResourceSyBO = BeanUtil.toBean(cameraResourceSy, CameraResourceSyBO.class);
        cameraResourceSyBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_SY.getCode());
        return cameraResourceSyBO;
    }

    @Override
    public List<CameraResourceTlBO> listCameraResourceTl(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = convertNodeType(nodeType);

        List<CameraResourceTl> cameraResourceTlList = cameraService.listCameraResourceTl(isUseUserPermissions, orgId, nodeType);
        if (cameraResourceTlList.isEmpty()) {
            return Collections.emptyList();
        }

        List<CameraResourceTlBO> cameraResourceTlBOList = cameraResourceTlList.stream().map(item -> {
            CameraResourceTlBO cameraResourceTlBO = BeanUtil.toBean(item, CameraResourceTlBO.class);
            cameraResourceTlBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_TL.getCode());
            cameraResourceTlBO.setOnlineStatus(item.getOnlineStatus());
            return cameraResourceTlBO;
        }).collect(Collectors.toList());

        // 收费站广场数据排前面
        List<CameraResourceTlBO> tollStationCameraListTl = new ArrayList<>();
        List<CameraResourceTlBO> cameraListTl = new ArrayList<>();
        for (CameraResourceTlBO cameraResourceTlBO : cameraResourceTlBOList) {
            if ("11".equals(String.valueOf(cameraResourceTlBO.getLocationType()))) {
                tollStationCameraListTl.add(cameraResourceTlBO);
            } else {
                cameraListTl.add(cameraResourceTlBO);
            }
        }
        tollStationCameraListTl.addAll(cameraListTl);
        return tollStationCameraListTl;
    }

    @Override
    public CameraResourceTlBO queryTlDetail(String id) {
        CameraResourceTl cameraResourceTl = cameraService.getCameraResourceTlById(id);
        if (cameraResourceTl == null) {
            return null;
        }
        CameraResourceTlBO cameraResourceTlBO = BeanUtil.toBean(cameraResourceTl, CameraResourceTlBO.class);
        cameraResourceTlBO.setDataSource(CameraDataSourceEnum.CAMERA_SOURCE_TL.getCode());
        return cameraResourceTlBO;
    }

    @Override
    public JSONObject queryServiceAreas(String geoId) {
        if (StrUtil.isBlank(geoId)) {
            throw new BadRequestException("设施id: " + geoId + ",不能为空");
        }
        return facilitiesService.queryServiceAreas(geoId);
    }

    @Override
    public JSONObject queryTunnelDetail(String geoId) {
        return facilitiesService.queryTunnelDetail(geoId);
    }

    @Override
    public WayPlanBO getPlanTotalMileage(Boolean isUseUserPermissions, String orgId, String nodeType) {
        // 参数校验
        checkParams(orgId, nodeType);
        // 判断是否集团 或者 运营公司
        if (StrUtil.isBlank(nodeType) || NodeTypeEnum.GROUP.getCode().equals(nodeType) || NodeTypeEnum.ORG.getCode().equals(nodeType)) {
            nodeType = convertNodeType(nodeType);
            WayPlanBO wayPlanBO = wayPlanService.queryWayPlan(isUseUserPermissions, orgId, nodeType);
            Map<String, Object> actualTotalMileage = wayService.getTotalMileage(isUseUserPermissions, orgId, nodeType);
            wayPlanBO.setActualTotalMileage(new BigDecimal(MapUtil.getStr(actualTotalMileage, "totalMileage")));
            return wayPlanBO;
        }
        return null;
    }

    @Override
    public List<WayPlanDetailBO> getPlanTotalMileageDetail(Boolean isUseUserPermissions, String orgId, String nodeType) {
        // 参数校验
        checkParams(orgId, nodeType);
        // 判断是否集团 或者 运营公司
        if (StrUtil.isBlank(nodeType) || NodeTypeEnum.GROUP.getCode().equals(nodeType) || NodeTypeEnum.ORG.getCode().equals(nodeType)) {
            nodeType = convertNodeType(nodeType);
            return wayPlanService.queryWayPlanDetail(isUseUserPermissions, orgId, nodeType);
        }
        return null;
    }

    /**
     * 获取桥隧比
     *
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId                节点ID
     * @param nodeType             节点类型(0:集团、1:运营公司、2:路段)
     * @param searchType           查询类型(1:运营公司、2:路段)
     * @return
     */
    @Override
    public JSONArray getBridgeTunnelRate(Boolean isUseUserPermissions, String orgId, String nodeType, String searchType) {
        JSONArray result = new JSONArray();
        checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);
        if (searchType.equals(NodeTypeEnum.ORG.getCode())) {
            if (!(nodeType.equals(NodeTypeEnum.GROUP.getName()) || nodeType.equals(NodeTypeEnum.ORG.getName()))) {
                throw new BadRequestException("nodeType应为[0:集团,1:运营公司]");
            }
            // 获取权限内的机构列表
            List<Org> orgList = assetsPermissionsTreeService.getAssetsOrg(isUseUserPermissions, nodeType, orgId);
            // 查询每个运营公司的桥梁里程,隧道里程和总里程
            for (Org org : orgList) {

                Map<String, Object> actualTotalMileage = wayService.getTotalMileage(isUseUserPermissions, org.getOrgId(), NodeTypeEnum.ORG.getName());
                Double totalMileage = MapUtil.getDouble(actualTotalMileage, "totalMileage");
                if (totalMileage == null || totalMileage.isNaN() || 0 == totalMileage) {
                    result.add(new JSONObject()
                            .set("orgName", org.getOrgName())
                            .set("bridgeTunnelRate", 0.0)
                    );
                    continue;
                }
                // 获取运营公司的桥基本信息 bridge_meter
                BridgeInfo bridgeInfo = facilitiesService.getBridgeInfo(isUseUserPermissions, org.getOrgId(), NodeTypeEnum.ORG.getName());
                // 获取运营公司隧道基本信息 tunnel_meter
                TunnelInfo tunnelInfo = facilitiesService.getTunnelInfo(isUseUserPermissions, org.getOrgId(), NodeTypeEnum.ORG.getName());
                // 获取运营公司总里程 mileage
                Double bridgeMileage = bridgeInfo.getBridgeMileage() == null ? 0.0 : bridgeInfo.getBridgeMileage();
                Double tunnelMileage = tunnelInfo.getTunnelMileage() == null ? 0.0 : tunnelInfo.getTunnelMileage();
                result.add(new JSONObject()
                        .set("orgName", org.getOrgName())
                        .set("bridgeTunnelRate", BigDecimal.valueOf((bridgeMileage + tunnelMileage) / totalMileage).setScale(4, RoundingMode.HALF_UP).doubleValue())
                        .set("detail", new JSONObject().set("bridgeKm", bridgeMileage)
                                .set("tunnelKm", tunnelMileage)
                                .set("orgKm", totalMileage))
                );
            }
        } else if (searchType.equals(NodeTypeEnum.WAY.getCode())) {
            if (!(nodeType.equals(NodeTypeEnum.GROUP.getName()) || nodeType.equals(NodeTypeEnum.ORG.getName()) || nodeType.equals(NodeTypeEnum.WAY.getName()))) {
                throw new BadRequestException("nodeType应为[0:集团,1:运营公司,2:路段]");
            }
            // 获取权限内的路段列表
            List<Waysection> waySectionList = assetsPermissionsTreeService.getAssetsWay(isUseUserPermissions, nodeType, orgId);
            List<Map<String, Object>> bridgeInfoMapList = facilitiesDao.getBridgeInfoByWay();
            Map<String, Double> bridgeInfoMap = new HashMap<>();
            for (Map<String, Object> map : bridgeInfoMapList) {
                if (StrUtil.isNotBlank(MapUtil.getStr(map, "waysectionId"))) {
                    bridgeInfoMap.put(MapUtil.getStr(map, "waysectionId"), MapUtil.getDouble(map, "bridgeMileage"));
                }
            }
            List<Map<String, Object>> tunnelInfoMapList = facilitiesDao.getTunnelInfoByWay();
            Map<String, Double> tunnelInfoMap = new HashMap<>();
            for (Map<String, Object> map : tunnelInfoMapList) {
                if (StrUtil.isNotBlank(MapUtil.getStr(map, "waysectionId"))) {
                    tunnelInfoMap.put(MapUtil.getStr(map, "waysectionId"), MapUtil.getDouble(map, "tunnelMileage"));
                }
            }
            // 查询每个路段的桥梁里程,隧道里程和总里程
            for (Waysection waysection : waySectionList) {
                Double totalMileage = (null == waysection.getMileage()) ? null : waysection.getMileage().doubleValue();
                if (totalMileage == null || totalMileage.isNaN() || 0 == totalMileage) {
                    result.add(new JSONObject()
                            .set("orgName", waysection.getWaysectionName())
                            .set("bridgeTunnelRate", 0.0)
                    );
                    continue;
                }
//                // 桥梁信息
//                BridgeInfo bridgeInfo = facilitiesService.getBridgeInfo(isUseUserPermissions, waysection.getWaysectionId(), NodeTypeEnum.WAY.getName());
//                // 隧道信息
//                TunnelInfo tunnelInfo = facilitiesService.getTunnelInfo(isUseUserPermissions, waysection.getWaysectionId(), NodeTypeEnum.WAY.getName());
//                Double bridgeMileage = bridgeInfo.getBridgeMileage() == null ? 0.0 : bridgeInfo.getBridgeMileage();
//                Double tunnelMileage = tunnelInfo.getTunnelMileage() == null ? 0.0 : tunnelInfo.getTunnelMileage();
                Double bridgeMileage = (null != MapUtil.getDouble(bridgeInfoMap, waysection.getWaysectionId())) ? MapUtil.getDouble(bridgeInfoMap, waysection.getWaysectionId()) : 0;
                Double tunnelMileage = (null != MapUtil.getDouble(tunnelInfoMap, waysection.getWaysectionId())) ? MapUtil.getDouble(tunnelInfoMap, waysection.getWaysectionId()) : 0;
                result.add(new JSONObject()
                        .set("orgName", waysection.getWaysectionName())
                        .set("bridgeTunnelRate", BigDecimal.valueOf((bridgeMileage + tunnelMileage) / totalMileage).setScale(4, RoundingMode.HALF_UP).doubleValue())
                        .set("detail", new JSONObject().set("bridgeKm", bridgeMileage)
                                .set("tunnelKm", tunnelMileage)
                                .set("orgKm", totalMileage))
                );
            }
        } else {
            throw new BadRequestException("searchType应为[1:运营公司,2:路段]");
        }
        result.sort(Comparator.comparing(obj -> ((JSONObject) obj).getBigDecimal("bridgeTunnelRate")).reversed());
        return result;
    }

    @Override
    public JSONObject getCompanyInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);
        if (!(nodeType.equals(NodeTypeEnum.GROUP.getName()) || nodeType.equals(NodeTypeEnum.ORG.getName()))) {
            throw new BadRequestException("nodeType应为 集团或运营公司");
        }
        // 获取运营公司总里程 mileage
        Map<String, Object> actualTotalMileage = wayService.getTotalMileage(isUseUserPermissions, orgId, nodeType);
        Double totalMileage = MapUtil.getDouble(actualTotalMileage, "totalMileage");

        // 获取运营公司的桥基本信息 bridge_meter
        BridgeInfo bridgeInfo = facilitiesService.getBridgeInfo(isUseUserPermissions, orgId, nodeType);
        // 获取运营公司隧道基本信息 tunnel_meter
        TunnelInfo tunnelInfo = facilitiesService.getTunnelInfo(isUseUserPermissions, orgId, nodeType);
        // 获取员工数和机构描述信息
        HashMap<String, Object> orgIntroduce = assetsDao.getOrgIntroduce(orgId);
        if (orgIntroduce == null) {
            orgIntroduce = new HashMap<>();
            orgIntroduce.put("description", "");
            orgIntroduce.put("employeeNum", 0);
        }
        Double bridgeMileage = bridgeInfo.getBridgeMileage() == null ? 0.0 : bridgeInfo.getBridgeMileage();
        Double tunnelMileage = tunnelInfo.getTunnelMileage() == null ? 0.0 : tunnelInfo.getTunnelMileage();
        double bridgeTunnelRate;
        if (totalMileage == null || totalMileage.isNaN()) {
            bridgeTunnelRate = 0D;
        } else {
            bridgeTunnelRate = BigDecimal.valueOf((bridgeMileage + tunnelMileage) / totalMileage).setScale(4, RoundingMode.HALF_UP).doubleValue();
        }
        return new JSONObject()
                .set("description", orgIntroduce.get("description"))
                .set("employeeNum", orgIntroduce.get("employeeNum"))
                .set("manageMileage", totalMileage)
                .set("bridgeTunnelRate", bridgeTunnelRate)
                .set("bridgeNum", bridgeInfo.getBridgeNum())
                .set("bridgeMileage", bridgeMileage)
                .set("tunnelNum", tunnelInfo.getTunnelNum())
                .set("tunnelMileage", tunnelMileage);
    }

    @Override
    public WaySectionInfo getWaysectionInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);
        if (!nodeType.equals(NodeTypeEnum.WAY.getName())) {
            throw new BadRequestException("节点类型必须为路段(2)");
        }
        // 路段简短信息
        WaySectionShort waySectionShort = wayService.getWaySectionShort(orgId);
        if (waySectionShort == null) {
            throw new BadRequestException("未查询到该路段信息,请核查");
        }
        Double manageMileage = waySectionShort.getManageMileage();
        if (manageMileage == null || manageMileage.isNaN()) {
            throw new BadRequestException("该路段里程为空,请核查");
        }
        // 桥梁信息
        BridgeInfo bridgeInfo = facilitiesService.getBridgeInfo(isUseUserPermissions, orgId, nodeType);
        // 隧道信息
        TunnelInfo tunnelInfo = facilitiesService.getTunnelInfo(isUseUserPermissions, orgId, nodeType);
        // 收费站
        TollStationInfoBO tollStationInfoBO = facilitiesService.getTollStationStatInfo(isUseUserPermissions, orgId, nodeType);
        // 服务区信息
        ServiceAreaInfo serviceAreaInfo = facilitiesService.getServiceAreaInfo(isUseUserPermissions, orgId, nodeType);

        WaySectionInfo waySectionInfo = new WaySectionInfo();
        BeanUtil.copyProperties(waySectionShort, waySectionInfo);
        BeanUtil.copyProperties(bridgeInfo, waySectionInfo);
        BeanUtil.copyProperties(tunnelInfo, waySectionInfo);
        //设置收费站信息
//        BeanUtil.copyProperties(tollStationInfoBO, waySectionInfo);
        waySectionInfo.setChargeStationNum(tollStationInfoBO.getTotal());
        waySectionInfo.setChargeStationNormalPassNum(tollStationInfoBO.getNormalNumber());
        waySectionInfo.setChargeStationTemporaryCloseNum(tollStationInfoBO.getCloseNumber());
        BeanUtil.copyProperties(serviceAreaInfo, waySectionInfo);

        // 起止桩号
        waySectionInfo.setPileNumber(waySectionShort.getBeginStakeNo() + " ~ " + waySectionShort.getEndStakeNo());
        waySectionInfo.setBeginStakeNo(waySectionShort.getBeginStakeNo());
        waySectionInfo.setEndStakeNo(waySectionShort.getEndStakeNo());

        Double bridgeMileage = bridgeInfo.getBridgeMileage() == null ? 0.0 : bridgeInfo.getBridgeMileage();
        Double tunnelMileage = tunnelInfo.getTunnelMileage() == null ? 0.0 : tunnelInfo.getTunnelMileage();
        // 桥隧比
        waySectionInfo.setBridgeTunnelRate(BigDecimal.valueOf((bridgeMileage + tunnelMileage) / manageMileage).setScale(4, RoundingMode.HALF_UP).doubleValue());
        return waySectionInfo;
    }

    /**
     * 设施数量统计
     *
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型（0：运营集团、1：运营公司、2：路段、3：设施）
     * @param name                 名称（公司、路段）
     * @param byType               数据类型（公司、路段）
     * @param pageSize             分页大小（为0表示不分页）
     * @param pageNumber           当前页
     * @return
     */
    public Object getFacStat(Boolean isUseUserPermissions, String orgId, String nodeType, String name, Integer byType, Integer pageSize, Integer pageNumber) {
        checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);
        if (null == byType || 0 == byType) {
            throw new BadRequestException("[byType]参数不能为空 ");
        }
        if (null == pageSize) {
            throw new BadRequestException("[pageSize]参数不能为空 ");
        }
        if (0 < pageSize && (null == pageNumber || 0 >= pageNumber)) {
            throw new BadRequestException("[pageNumber]参数不能为空,且必须大于0");
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        if (1 == byType) {
            if (0 == pageSize) {
                List<FacilitiesStatBo> facilitiesStatBoList = assetsDao.getFacStatByOrg(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, name);
                return facilitiesStatBoList;
            } else {
                Page<FacilitiesStatBo> page = new Page<>();
                page.setCurrent(pageNumber);
                page.setSize(pageSize);
                assetsDao.getFacStatByOrg(page, isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, name);
                return new PageUtil<FacilitiesStatBo>(page);
            }
        } else if (2 == byType) {
            if (0 == pageSize) {
                List<FacilitiesStatBo> facilitiesStatBoList = wayDao.getFacStatByWay(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, name);
                return facilitiesStatBoList;
            } else {
                Page<FacilitiesStatBo> page = new Page<>();
                page.setCurrent(pageNumber);
                page.setSize(pageSize);
                wayDao.getFacStatByWay(page, isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, name);
                return new PageUtil<FacilitiesStatBo>(page);
            }
        }
        return null;
    }

    /**
     * 获取里程列表（运营公司、路段）
     *
     * @param isUseUserPermissions 是否带权限
     * @param nodeType             节点类型(运营公司：1，路段：2)
     * @param orgId                当节点类型为nodeType=2时,允许再传一个orgId(运营公司ID)
     * @return
     */
    public List<MileageBo> queryMileageList(Boolean isUseUserPermissions, Integer nodeType, String orgId) {
        if (1 != nodeType && 2 != nodeType) {
            throw new BadRequestException("[nodeType]参数非法 ");
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        // 获取运营公司里程列表
        if (1 == nodeType) {
            return assetsDao.queryMileageListByOrg(isUseUserPermissions, userId, dataPermissionsLevel);
            // 获取路段里程列表
        } else {
            return wayDao.queryMileageListByWay(isUseUserPermissions, userId, dataPermissionsLevel, orgId);
        }
    }

    /**
     * 设施分组统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param code                 设施类型
     */
    public List<Map<String, Object>> facilitiesGroupByNodeType(Boolean isUseUserPermissions, String orgId, String nodeType, String code) {
        List<Map<String, Object>> mapList;
        checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);
        if (StrUtil.isBlank(orgId)) {
            throw new BadRequestException("[orgId]参数不能为空 ");
        }
        if (StrUtil.isBlank(nodeType)) {
            throw new BadRequestException("[nodeType]参数不能为空 ");
        }
        FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
        if (facilitiesTypeEnum == null) {
            throw new BadRequestException("无效的设施类型编号: " + code);
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        List<String> facilitiesTypes = new ArrayList<>();
        facilitiesTypes.add(code);
        switch (facilitiesTypeEnum) {
            case BRIDGE:
                // 获取桥梁数
                mapList = facilitiesService.bridgeGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
                break;
            case TOLL_STATION:
                // 获取收费数
                mapList = facilitiesService.facilitiesGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
                break;
            case SERVICE_AREA:
                // 获取服务区数
                mapList = facilitiesService.facilitiesGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
                break;
            case DOOR_FRAME:
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
                facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
                // 获取门架数
                mapList = facilitiesService.facilitiesGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
                break;
            case TUNNEL:
                // 获取隧道数
                mapList = facilitiesService.tunnelGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
                break;
            default:
                throw new BadRequestException("未知的设施类型");
        }
        if (null != mapList && 1 == mapList.size()) {
            if (StrUtil.isBlank(MapUtil.getStr(mapList.get(0), "id"))) {
                return null;
            }
        }
        return mapList;
    }

    /**
     * 机构员工统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     */
    public Map<String, Object> employeesStat(Boolean isUseUserPermissions, String orgId, String nodeType) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);
        if (StrUtil.isBlank(orgId)) {
            throw new BadRequestException("[orgId]参数不能为空 ");
        }
        if (StrUtil.isBlank(nodeType)) {
            throw new BadRequestException("[nodeType]参数不能为空 ");
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        List<Map<String, Object>> resultMapList = assetsDao.employeesStat(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
        if (null != resultMapList && 0 < resultMapList.size()) {
            for (Map<String, Object> map : resultMapList) {
                if (orgId.equals(MapUtil.getStr(map, "id"))) {
                    resultMap = map;
                } else {
                    mapList.add(map);
                }
            }
            resultMap.put("list", mapList);
            return resultMap;
        } else {
            return null;
        }
    }

    /**
     * 2.7.2.1 组织-数据资产树（集团-运营公司）
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param isAsync              是否异步（是否向下查找）
     */
    @Override
    public List<Tree<String>> getGroupOrgTree(Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync) {
        // 参数校验
        this.checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        return assetsPermissionsTreeService.getGroupOrgTree(isUseUserPermissions, orgId, nodeType, isAsync);
    }

    // 2.7.2.2 组织路段-数据资产树（集团-运营公司-路段）
    @Override
    public List<Tree<String>> getGroupOrgWayTree(Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync) {
        // 参数校验
        this.checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        return assetsPermissionsTreeService.getGroupOrgWayTree(isUseUserPermissions, orgId, nodeType, isAsync);
    }

    // 2.7.2.3 设施一级
    @Override
    public List<Tree<String>> getFac(DataFacBo dataFacBo) {
        // 参数校验
        String nodeType = dataFacBo.getNodeType();
        this.checkParams(dataFacBo.getOrgId(), nodeType);
        if (!"2".equals(nodeType)) {
            throw new BadRequestException("当前节点类型 " + nodeType + " 不正确，需要的为 " + 2);
        }
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        dataFacBo.setNodeType(nodeType);
        return assetsPermissionsTreeService.getFac(dataFacBo);
    }

    // 2.7.2.4 设施二级
    @Override
    public List<Tree<String>> getSubFac(DataFacBo dataFacBo) {
        // 参数校验
        String nodeType = dataFacBo.getNodeType();
        this.checkParams(dataFacBo.getOrgId(), nodeType);
        if (!"3".equals(nodeType)) {
            throw new BadRequestException("当前节点类型 " + nodeType + " 不正确，需要的为 " + 3);
        }
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        dataFacBo.setNodeType(nodeType);
        return assetsPermissionsTreeService.getSubFac(dataFacBo);
    }

    /**
     * 2.7.3.1  厂商资产树（集团-公司）
     *
     * @param orgId    节点编号
     * @param nodeType 节点类型(集团、运营公司、路段)
     */
    @Override
    public List<Tree<String>> getGroupOrgManufacturerTree(String orgId, String nodeType, Boolean isAsync) {
        // 参数校验
        this.checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        return manufacturerService.getGroupOrgManufacturerTree(orgId, nodeType, isAsync);
    }

    @Override
    public List<Tree<String>> getGroupOrgWayManufacturerTree(String orgId, String nodeType, Boolean isAsync) {
        // 参数校验
        this.checkParams(orgId, nodeType);
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        return manufacturerService.getGroupOrgWayManufacturerTree(orgId, nodeType, isAsync);
    }

    /**
     * 2.7.3.3  厂商资产树（设施一级）
     */
    @Override
    public List<Tree<String>> getFacManufacturer(ManufacturerFacBo manufacturerFacBo) {
        String nodeType = manufacturerFacBo.getNodeType();
        // 参数校验
        this.checkParams(manufacturerFacBo.getOrgId(), nodeType);
        if (!"2".equals(nodeType)) {
            throw new BadRequestException("当前节点类型 " + nodeType + " 不正确，需要的为 " + 2);
        }
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        manufacturerFacBo.setNodeType(nodeType);
        return manufacturerService.getFacManufacturer(manufacturerFacBo);
    }

    /**
     * 2.7.3.4  厂商资产树（设施二级）
     */
    @Override
    public List<Tree<String>> getSubFacManufacturerTree(ManufacturerFacBo manufacturerFacBo) {
        String nodeType = manufacturerFacBo.getNodeType();
        // 参数校验
        this.checkParams(manufacturerFacBo.getOrgId(), nodeType);
        if (!"3".equals(nodeType)) {
            throw new BadRequestException("当前节点类型 " + nodeType + " 不正确，需要的为 " + 3);
        }
        // nodeType转换
        nodeType = this.convertNodeType(nodeType);
        manufacturerFacBo.setNodeType(nodeType);
        return manufacturerService.getSubFacManufacturerTree(manufacturerFacBo);
    }

    /**
     * 2.30设备过滤
     */
    @Override
    public PageUtil<DeviceVo> getDataDevice(DataDeviceBo dataDeviceBo) {
        String nodeType = dataDeviceBo.getNodeType();
        if ("4".equals(nodeType)) {
            nodeType = "subFac";
        } else {
            // 参数校验
            this.checkParams(dataDeviceBo.getOrgId(), nodeType);
            // nodeType转换
            nodeType = this.convertNodeType(nodeType);
        }
        dataDeviceBo.setNodeType(nodeType);
        return deviceService.getDataDevice(dataDeviceBo);
    }

    /**
     * 2.30 厂商设备过滤
     */
    @Override
    public PageUtil<DeviceVo> getManufacturerDevice(ManufacturerDeviceBo manufacturerDeviceBo) {
        String nodeType = manufacturerDeviceBo.getNodeType();
        if ("4".equals(nodeType)) {
            nodeType = "subFac";
        } else {
            // 参数校验
            this.checkParams(manufacturerDeviceBo.getOrgId(), nodeType);
            // nodeType转换
            nodeType = this.convertNodeType(nodeType);
        }
        manufacturerDeviceBo.setNodeType(nodeType);
        return deviceService.getManufacturerDevice(manufacturerDeviceBo);
    }


    /**
     * 2.80.1 查询字典code(根据字典目录code查询)
     *
     * @param dictDirCodes 字典目录code集合
     */
    @Override
    public List<Map<String, String>> getCodeList(Set<String> dictDirCodes, String dictName) {
        return dictService.getCodeList(dictDirCodes, dictName);
    }

    /**
     * 查询组织列表
     */
    @Override
    public Object getOrgList(OrgListBo orgListBo) {
        return orgService.getOrgList(orgListBo);
    }

    /**
     * 获取组织详情
     *
     * @param orgId 组织id
     */
    @Override
    public OrgVo getOrgInfoById(String orgId) {
        return orgService.getOrgInfoById(orgId);
    }

    /**
     * 根据机构id查询机构子部门
     *
     * @param orgId 组织id
     * @return
     */
    @Override
    public List<OrgTreeVo> getOrgSonById(String orgId) {
        OrgVo infoById = orgService.getOrgInfoById(orgId);
        List<OrgTreeVo> list = new ArrayList<>();
        OrgTreeVo vo = new OrgTreeVo();
        BeanUtil.copyProperties(infoById, vo);
        list.add(vo);
        return recursionOrg(list);
    }

    private List<OrgTreeVo> recursionOrg(List<OrgTreeVo> list) {
        list.forEach(vo -> {
                String orgId = vo.getOrgId();
            List<OrgTreeVo> recursionOrgVo = orgService.getOrgInfoByParentId(orgId);
            if (CollUtil.isNotEmpty(recursionOrgVo)) {
                vo.setChildren(recursionOrgVo);
                recursionOrg(recursionOrgVo);
            }
        });
        return list;
    }

    /**
     * 2.80.4.1 查询设备详情（根据设备编号查询）
     *
     * @param deviceCode 设备编号
     */
    @Override
    public DeviceVo getDeviceByCode(String deviceCode) {
        return deviceService.getDeviceByCode(deviceCode);
    }

    /**
     * 2.80.4.2 查询设备详情（根据设备编号查询）（网关专用）
     *
     * @param deviceCode 设备编号
     */
    @Override
    public DeviceGatewayVo getDeviceGateway(String deviceCode) {
        return deviceService.getDeviceGateway(deviceCode);
    }

    /**
     * 2.80.4.3 查询设备详情（根据设备id查询）
     *
     * @param deviceId 设备id
     */
    @Override
    public DeviceVo getDeviceById(String deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

    /**
     * 2.80.6 查询设备的外接设备
     *
     * @param deviceId 设备编号
     */
    @Override
    public GatewayExternalVo getExternalDevice(String deviceId) {
        return deviceService.getExternalDevice(deviceId);
    }

    /**
     * 2.80.7 查询设施列表
     */
    @Override
    public Object getFacList(FacListBo facListBo) {
        String nodeType = facListBo.getNodeType();
        String orgId = facListBo.getOrgId();

        this.checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);

        facListBo.setNodeType(nodeType);
        return facilitiesService.getFacList(facListBo);
    }

    /**
     * 2.80.8 查询设施详情
     *
     * @param facId 设施id
     */
    @Override
    public FacilitiesVo getFacById(String facId) {
        return facilitiesService.getFacById(facId);
    }

    /**
     * 2.80.9 查询路段列表
     */
    @Override
    public Page<Waysection> getWayList(WayListBo wayListBo) {
        String nodeType = wayListBo.getNodeType();
        String orgId = wayListBo.getOrgId();

        if (StrUtil.isNotBlank(nodeType)) {
            if (Integer.parseInt(nodeType) > 2) {
                throw new BadRequestException("您当前权限不足");
            }
        }

        this.checkParams(orgId, nodeType);
        nodeType = convertNodeType(nodeType);

        wayListBo.setNodeType(nodeType);
        return wayService.getWayList(wayListBo);
    }

    /**
     * 2.80.10 查询路段详情
     *
     * @param wayId 路段id
     */
    @Override
    public Waysection getWayById(String wayId) {
        return wayService.getWayById(wayId);
    }

    /**
     * 2.80.11 设备字典分类查询
     *
     * @param searchType 查询类型（1:设备总类，2:设备类型）
     * @param typeId     设备总类/设备类型的id
     * @param name       设备字典类型的名称
     */
    @Override
    public List<Map<String, String>> getDeviceDict(Integer searchType, String typeId, String name) {
        if (null == searchType != StrUtil.isBlank(typeId)) {
            throw new BadRequestException("searchType 和 typeId 必须同时为空或同时非空");
        }
        return dictService.getDeviceDict(searchType, typeId, name);
    }


    /**
     * 车道设备列表
     *
     * @param laneId 车道id
     */
    @Override
    public List<LaneDeviceVo> getLaneDeviceList(String laneId) {
        return deviceService.getLaneDeviceList(laneId);
    }

    private void checkParams(String orgId, String nodeType) {
        if ((StrUtil.isBlank(orgId) != StrUtil.isBlank(nodeType))) {
            throw new BadRequestException("orgId 和 nodeType 必须同时为空或同时非空");
        }
        // 判断 nodeType 是否合法
        if (StrUtil.isNotBlank(nodeType) && !NodeTypeEnum.include(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + " 为非法参数,请检查");
        }
    }

    /**
     * 设施分类统计及列表明细
     */
    @Override
    public Map<String, Object> facStatistics(FacStatisticsBo facStatisticsBo) {
        String nodeType = "";
        if (null != facStatisticsBo.getNodeType()) {
            checkParams(facStatisticsBo.getOrgId(), facStatisticsBo.getNodeType().toString());
            nodeType = this.convertNodeType(facStatisticsBo.getNodeType().toString());
        }
        List<FacilitiesInfoBO> temp = new ArrayList<>();
        this.specificTypes(facStatisticsBo, temp);
        return facilitiesService.facStatistics(facStatisticsBo, nodeType, temp);
    }


    /**
     * 获取特定的设施
     */
    private void specificTypes(FacStatisticsBo facStatisticsBo, List<FacilitiesInfoBO> temp) {
        Iterator<String> iterator = facStatisticsBo.getFacTypes().iterator();
        while (iterator.hasNext()) {
            String code = iterator.next();
            List<FacilitiesInfoBO> facilitiesInfoBOList = new ArrayList<>();
            FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
            List<String> facilitiesTypes = new ArrayList<>();
            facilitiesTypes.add(code);
            String dictKeys = "";
            if (null != facilitiesTypeEnum) {
                switch (facilitiesTypeEnum) {
                    case BRIDGE:
                        for (BridgeEnum bridgeEnum : BridgeEnum.values()) {
                            dictKeys += bridgeEnum.getDictKey() + ",";
                        }
                        if (StrUtil.isNotBlank(dictKeys)) {
                            dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                        }
                        // 获取桥梁列表
                        facilitiesInfoBOList = facilitiesService.listBridge1(facStatisticsBo, dictKeys)
                                .parallelStream().peek(facilitiesInfoBO -> facilitiesInfoBO.setType(code))
                                .collect(Collectors.toList());
                        iterator.remove();
                        break;
                    case TOLL_STATION:
                        for (TollStationEnum tollStationEnum : TollStationEnum.values()) {
                            dictKeys += tollStationEnum.getDictKey() + ",";
                        }
                        if (StrUtil.isNotBlank(dictKeys)) {
                            dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                        }
                        // 获取收费站列表
                        facilitiesInfoBOList = facilitiesService.listTollStation1(facStatisticsBo, facilitiesTypes, dictKeys)
                                .parallelStream().peek(facilitiesInfoBO -> {
                                            facilitiesInfoBO.setDictName(TollStationEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                            facilitiesInfoBO.setType(code);
                                        }
                                ).collect(Collectors.toList());
                        iterator.remove();
                        break;
                    case SERVICE_AREA:
                        for (ServiceAreaEnum serviceAreaEnum : ServiceAreaEnum.values()) {
                            dictKeys += serviceAreaEnum.getDictKey() + ",";
                        }
                        if (StrUtil.isNotBlank(dictKeys)) {
                            dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                        }
                        // 获取服务区列表
                        facilitiesInfoBOList = facilitiesService.listServiceArea1(facStatisticsBo, facilitiesTypes, dictKeys)
                                .parallelStream().peek(facilitiesInfoBO -> {
                                            facilitiesInfoBO.setDictName(ServiceAreaEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                            facilitiesInfoBO.setType(code);
                                        }
                                ).collect(Collectors.toList());
                        iterator.remove();
                        break;
                    case DOOR_FRAME:
                        facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
                        facilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
                        for (DoorFrameEnum doorFrameEnum : DoorFrameEnum.values()) {
                            dictKeys += doorFrameEnum.getDictKey() + ",";
                        }
                        if (StrUtil.isNotBlank(dictKeys)) {
                            dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                        }
                        // 获取门架列表
                        facilitiesInfoBOList = facilitiesService.listDoorFrame1(facStatisticsBo, facilitiesTypes, dictKeys)
                                .parallelStream().peek(facilitiesInfoBO -> {
                                            facilitiesInfoBO.setDictName(DoorFrameEnum.convertDictKeyToName(facilitiesInfoBO.getDictKey()));
                                            facilitiesInfoBO.setType(code);
                                        }
                                ).collect(Collectors.toList());
                        iterator.remove();
                        break;
                    case TUNNEL:
                        for (TunnelEnum tunnelEnum : TunnelEnum.values()) {
                            dictKeys += tunnelEnum.getDictKey() + ",";
                        }
                        if (StrUtil.isNotBlank(dictKeys)) {
                            dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
                        }
                        // 获取隧道列表
                        facilitiesInfoBOList = facilitiesService.listTunnel1(facStatisticsBo, facilitiesTypes, dictKeys)
                                .parallelStream().peek(facilitiesInfoBO -> {
                                            facilitiesInfoBO.setType(code);
                                        }
                                ).collect(Collectors.toList());
                        iterator.remove();
                        break;
                    default:
                        break;
                }
            }
            temp.addAll(facilitiesInfoBOList);
        }
    }

    /**
     * 设备分类统计及列表明细
     */
    @Override
    public Map<String, Object> deviceStatistics(DeviceStatisticsBo deviceStatisticsBo) {
        if (null != deviceStatisticsBo.getNodeType()) {
            checkParams(deviceStatisticsBo.getOrgId(), deviceStatisticsBo.getNodeType().toString());
        }
        return deviceService.deviceStatistics(deviceStatisticsBo);
    }

    private String convertNodeType(String nodeType) {
        return NodeTypeEnum.getName(nodeType);
    }

    private int customMap2LaneType(Map<String, Object> map) {
        return (null == MapUtil.getInt(map, "laneNo") ? 0 : MapUtil.getInt(map, "laneNo"));
    }

    @Override
    public Object getUrlByFacId(GetUrlByFacIdBo getUrlByFacIdBo) {
        CameraResourceGroupBO cameraResourceGroupBO = this.listCameraResource(true, getUrlByFacIdBo.getFacId(), "3");
        Map<String, Object> params = new HashMap<>();
        HashSet<String> ids = new HashSet<>();
        switch (getUrlByFacIdBo.getDataSource()) {
            case "1":
                List<CameraResourceBO> cameraYtList = cameraResourceGroupBO.getCameraYtList();
                cameraYtList.forEach(yt -> {
                    ids.add(yt.getCameraId());
                });
                break;
            case "2":
                List<CameraResourceBO> cameraTlList = cameraResourceGroupBO.getCameraTlList();
                cameraTlList.forEach(tl -> {
                    ids.add(tl.getCameraId());
                });
                break;
            case "3":
                List<CameraResourceBO> cameraSyList = cameraResourceGroupBO.getCameraSyList();
                cameraSyList.forEach(sy -> {
                    ids.add(sy.getCameraId());
                });
                break;
            default:
                break;
        }

        if (CollUtil.isEmpty(ids)) {
            return null;
        }

        params.put("ids", ids);
        params.put("dataSource", getUrlByFacIdBo.getDataSource());
        params.put("netType", getUrlByFacIdBo.getNetType());
        params.put("rateType", getUrlByFacIdBo.getRateType());

        CommonResult commonResult = ydjkClient.getUrlByFacId(params);
        return commonResult.get("data");
    }

    @Override
    public Object getOrgByWay(String wayId,String orgTypes) {
        return permissionsTreeService.getOrgByWay(wayId,orgTypes);
    }
}
