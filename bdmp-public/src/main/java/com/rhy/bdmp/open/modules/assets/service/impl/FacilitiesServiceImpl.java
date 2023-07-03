package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ovit.cloud.common.utils.UserInfoUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.dao.FacilitiesDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.open.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.open.modules.assets.domain.vo.FacilitiesVo;
import com.rhy.bdmp.open.modules.assets.enums.*;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.open.modules.assets.service.IAssetsService;
import com.rhy.bdmp.open.modules.assets.service.IFacilitiesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: yanggj
 * @Description: 设施业务处理类(收费站, 门架, 桥梁, 服务区...)
 * 处理 收费站,门架桥梁等业务
 * @Date: 2021/9/28 8:53
 * @Version: 1.0.0
 */
@Service
public class FacilitiesServiceImpl implements IFacilitiesService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    private FacilitiesDao facilitiesDao;

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    @Resource
    private IAssetsService assetsService;

    @Override
    public TollStationInfoBO getTollStationStatInfo(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        HashMap<String, String> result = facilitiesDao.getTollStationStatInfo(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, FacilitiesTypeEnum.TOLL_STATION.getCode());
        return BeanUtil.mapToBeanIgnoreCase(result, TollStationInfoBO.class, true);
    }

    @Override
    public TollStationDetailBO getTollStationDetail(String stationId) {
        return facilitiesDao.getTollStationDetail(stationId);
    }

    @Override
    public BridgeClassifyStatBo getBridgeClassifyStat(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId= UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        List<BridgeStatBo> list = facilitiesDao.getBridgeClassifyStat(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, FacilitiesTypeEnum.BRIDGE.getCode());
        BridgeClassifyStatBo bridgeClassifyStatBo = new BridgeClassifyStatBo();
        int total = 0;
        BigDecimal totalLength = BigDecimal.ZERO;
        // sql中处理 num 和 length 为空的问题
        for (BridgeStatBo bridgeStatBo : list) {
            total += Integer.parseInt(bridgeStatBo.getNum());
            totalLength = totalLength.add(new BigDecimal(bridgeStatBo.getTotalLength()));
        }
        bridgeClassifyStatBo.setTotal(String.valueOf(total));
        bridgeClassifyStatBo.setTotalLength(totalLength.toPlainString());
        bridgeClassifyStatBo.setStatistics(list);
        return bridgeClassifyStatBo;

    }

    /**
     * 按设施类型分类统计
     *
     * @param isUseUserPermissions /
     * @param orgId                /
     * @param nodeType             /
     * @return /
     */
    @Override
    public JSONArray getFacStatByType(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        // 获取所有设施类型
        List<String> facilitiesTypes = Lists.newArrayList(
                FacilitiesTypeEnum.TOLL_STATION.getCode(),
                FacilitiesTypeEnum.TUNNEL.getCode(),
                FacilitiesTypeEnum.DOOR_FRAME.getCode(),
                FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode(),
                FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode(),
                FacilitiesTypeEnum.SERVICE_AREA.getCode());
        List<FacStatByTypeBO> list = facilitiesDao.getFacStatByType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
        //查询桥梁的统计信息
        List<FacStatByTypeBO> bridgeList = facilitiesDao.getFacBridgeStatByType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
        //合并到一起
        list.addAll(bridgeList);
        JSONArray array = new JSONArray();
        if (CollectionUtil.isEmpty(list)) {
            return array;
        }
        Map<String, List<FacStatByTypeBO>> collect = list.parallelStream().collect(Collectors.groupingBy(FacStatByTypeBO::getCode));
        collect.forEach((k, v) -> array.add(JSONUtil.createObj(jsonConfig)
                .putOnce("code", k)
                .putOnce("name", FacilitiesTypeEnum.getFacTypeName(k))
                .putOnce("total", v.stream().mapToInt(FacStatByTypeBO::getTotal).sum())
                .putOnce("list", v.stream().map(item -> JSONUtil.createObj(jsonConfig)
                                .putOnce("waysectionId", item.getWaysectionId())
                                .putOnce("waysectionNo", item.getWaysectionNo())
                                .putOnce("waysectionName", item.getWaysectionName())
                                .putOnce("oriWaysectionNo", item.getOriWaysectionNo())
                                .putOnce("total", item.getTotal())
                        ).collect(Collectors.toList())
                )
        ));
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            jsonObject.set("order", FacilitiesTypeEnum.getInstance(array.getJSONObject(i).getStr("code")).getOrder());
        }
        array.sort(Comparator.comparing(obj -> ((JSONObject) obj).getBigDecimal("order")));
        return array;
    }

//    @Override
//    public List<?> getFacInfoListByType(boolean isUseUserPermissions, String orgId, String nodeType, String code) {
//        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
//        String userId = userPermissions.getUserId();
//        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
//        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
//
//
//        FacilitiesTypeEnum facilitiesTypeEnum = FacilitiesTypeEnum.getInstance(code);
//        if (facilitiesTypeEnum == null) {
//            throw new BadRequestException("无效的设施类型编号: " + code);
//        }
//
//
//
//
//        List<Facilities> list = new ArrayList<>();
//        if (FacilitiesTypeEnum.BRIDGE.getCode().equals(code)) {
//            list = facilitiesDao.getFacInfoBridgeListByType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
//        } else {
//            List<String> facTypes = new ArrayList<>();
//            facTypes.add(code);
//            // 针对门架
//            if (code.equals(FacilitiesTypeEnum.DOOR_FRAME.getCode())) {
//                facTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
//                facTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
//            }
//            list = facilitiesDao.getFacInfoListByType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facTypes);
//        }
//        return list.stream().map(fac -> JSONUtil.createObj(jsonConfig)
//                        .putOnce("infoNo", fac.getFacilitiesCode())
//                        .putOnce("status", fac.getStatus())
//                        .putOnce("isMonitor", fac.getIsMonitor())
//                        .putOnce("mapUrl", fac.getMapUrl())
//                        .putOnce("geographyInfoId", fac.getFacilitiesId())
//                        .putOnce("latitude", fac.getLatitude())
//                        .putOnce("longitude", fac.getLongitude())
//                        .putOnce("infoName", fac.getFacilitiesName())
//                        .putOnce("originId", fac.getFacilitiesIdOld())
//                        .putOnce("infoType", fac.getFacilitiesType()))
//                .collect(Collectors.toList());
//    }

    @Override
    public JSONObject getGantryInfo(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        String code = FacilitiesTypeEnum.DOOR_FRAME.getCode();
        List<String> facTypes = new ArrayList<>();
        facTypes.add(code);
        facTypes.add("32330711");
        facTypes.add("32330712");
        // 获取门架列表
        List<Facilities> list = facilitiesDao.getFacInfoListByType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facTypes);
        // 获取省界门架数量
        long provinceGantrys = list.parallelStream().filter(fac -> fac.getIsProvinceDoorFrame() != null && fac.getIsProvinceDoorFrame() == 1).count();
        return JSONUtil.createObj(jsonConfig).putOnce("totalGantrys", list.size()).putOnce("provinceGantrys", provinceGantrys);
    }

    @Override
    public GantryDetailBO getGantryDetail(String deviceNumber) {
        if (StrUtil.isBlank(deviceNumber)) {
            throw new BadRequestException("deviceNumber 不能为空");
        }
        return facilitiesDao.getGantryDetail(deviceNumber);
    }

    @Override
    public Facilities selectByIdAndType(String facilitiesId, String facilitiesType) {
        Facilities facilities = facilitiesDao.selectByIdAndType(facilitiesId, facilitiesType);
        if (facilities == null) {
            throw new BadRequestException("facId: " + facilitiesId + ", 该类型[" + facilitiesType + "]设施不存在!");
        }
        return facilities;
    }

    @Override
    public HashMap<String, Object> getTunnelStatByType(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        //String userId = userPermissions.getUserId();
        Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        List<TunnelStatInfo> list = facilitiesDao.getTunnelStatByType(isUseUserPermissions, String.valueOf(userId), dataPermissionsLevel, orgId, nodeType);
        HashMap<String, Object> result = new HashMap<>(8);
        double totalLength = list.parallelStream().filter(item -> item.getLength() != null).mapToDouble(TunnelStatInfo::getLength).sum();
        int total = list.parallelStream().filter(item -> item.getTotal() != null).mapToInt(TunnelStatInfo::getTotal).sum();
        for (TunnelStatInfo tunnelStatInfo : list) {
            if (tunnelStatInfo.getLength() != null) {
                double percentage = NumberUtil.div(tunnelStatInfo.getLength() * 100, totalLength, 2);
                tunnelStatInfo.setPercentage(percentage);
            }
        }
        result.put("total", String.valueOf(total));
        result.put("totalLength", String.valueOf(totalLength));
        result.put("statistics", list.stream().filter(item -> item.getName() != null).collect(Collectors.toList()));
        return result;
    }

    @Override
    public FacilitiesBridge selectBridgeById(String facId) {
        FacilitiesBridge bridge = facilitiesDao.selectBridgeById(facId);
        if (bridge == null) {
            throw new BadRequestException("bridgeId: " + facId + ",桥梁不存在");
        }
        return bridge;
    }

    @Override
    public JSONObject queryServiceAreas(String geoId) {
        // 获取设施信息
        HashMap<String, String> resultMap = facilitiesDao.getFacByIdAndType(geoId, FacilitiesTypeEnum.SERVICE_AREA.getCode());
        if (resultMap == null) {
            throw new BadRequestException("geoId: " + geoId + ",服务区不存在! 请检查参数是否正确");
        }
        // 获取服务区信息
        List<Map<String, Object>> list = facilitiesDao.selectServiceAreaByGeoId(geoId);

        // 响应数据封装
        JSONObject result = JSONUtil.createObj(jsonConfig);
        result.putAll(resultMap);
        result.set("serviceAreaList", list);
        return result;
    }

    @Override
    public JSONObject queryTunnelDetail(String geoId) {
        // 获取设施信息
        HashMap<String, String> resultMap = facilitiesDao.getFacByIdAndType(geoId, FacilitiesTypeEnum.TUNNEL.getCode());
        if (resultMap == null) {
            throw new BadRequestException("geoId: " + geoId + ",隧道信息不存在! 请检查参数是否正确");
        }
        // 获取隧道上下行门架id
        Map<String, String> tunnelGantryMap = facilitiesDao.getTunnelGantry(geoId);
        // 获取隧道信息
        List<FacilitiesTunnel> list = facilitiesDao.selectTunnelByGeoId(geoId);
        // 响应数据封装
        JSONObject result = JSONUtil.createObj(jsonConfig);
        result.putAll(resultMap);
        if (null != tunnelGantryMap && !tunnelGantryMap.isEmpty()) {
            result.putAll(tunnelGantryMap);
        } else {
            result.putOnce("upGantryOriginId", "");
            result.putOnce("downGantryOriginId", "");
        }
        result.putOnce("tunnelDetails", list.isEmpty() ? Collections.emptyList() : list.parallelStream().map(tunnel -> JSONUtil.createObj(jsonConfig)
                .putOnce("tunnelName", tunnel.getTunnelName())
                .putOnce("routesName", tunnel.getRouteSName())
                .putOnce("type", tunnel.getTunnelLongTypeName())
                .putOnce("tunnelMeter", tunnel.getTunnelMeter())
                .putOnce("tunnelCleanHeight", tunnel.getTunnelCleanHeight())
                .putOnce("pavementWide", tunnel.getPavementWide())
                .putOnce("tunnelAllWide", tunnel.getTunnelAllWide())
                .putOnce("tunnelCleanWide", tunnel.getTunnelCleanWide())
                .putOnce("latitude", tunnel.getLatitude())
                .putOnce("longitude", tunnel.getLongitude())
                .putOnce("tunnelCenterStake", tunnel.getTunnelCenterStake())
                .putOnce("tunnelEntranceStake", tunnel.getTunnelEntranceStake())
                .putOnce("planProjectBridgeStake", tunnel.getPlanProjectBridgeStake()))
                .collect(Collectors.toList()));
        return result;
    }

    @Override
    public GantryBO getGantry(String deviceNumber) {
        GantryBO gantry = facilitiesDao.getGantry(deviceNumber);
        if (gantry == null) {
            throw new BadRequestException("deviceNumber: " + deviceNumber + ",门架不存在");
        }
        return gantry;
    }

    @Override
    public BridgeInfo getBridgeInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.getBridgeInfo(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public TunnelInfo getTunnelInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.getTunnelInfo(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public ServiceAreaInfo getServiceAreaInfo(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.getServiceAreaInfo(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    public List<Map<String, Object>> listBridgeCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listBridgeCount(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search);
    }

    @Override
    public List<FacilitiesInfoBO> listBridge(Boolean isUseUserPermissions, String orgId, String nodeType, String search, String dictKeys) {
        BridgeEnum[] bridgeEnums = BridgeEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (BridgeEnum bridgeEnum : bridgeEnums) {
                if (bridgeEnum.getCheck()) {
                    dictKeys += bridgeEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (BridgeEnum bridgeEnum : bridgeEnums) {
                    if (dictKey.equals(String.valueOf(bridgeEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("桥梁字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
       // Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listBridge(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, dictKeys);
    }

    @Override
    public List<FacilitiesInfoBO> listBridge1(FacStatisticsBo facStatisticsBo, String dictKeys) {
        BridgeEnum[] bridgeEnums = BridgeEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (BridgeEnum bridgeEnum : bridgeEnums) {
                if (bridgeEnum.getCheck()) {
                    dictKeys += bridgeEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (BridgeEnum bridgeEnum : bridgeEnums) {
                    if (dictKey.equals(String.valueOf(bridgeEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("桥梁字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(facStatisticsBo.getIsUseUserPermissions());
        return facilitiesDao.listBridge1(facStatisticsBo, userPermissions, dictKeys);
    }

    //  查询桥梁信息（过江大桥
    @Override
    public List<FacilitiesInfoBO> listBridgeByCrossRiver(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listBridgeByCrossRiver(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    public List<Map<String, Object>> listTollStationCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listTollStationCount(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes);
    }

    @Override
    public List<FacilitiesInfoBO> listTollStation(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys) {
        TollStationEnum[] tollStationEnums = TollStationEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (TollStationEnum tollStationEnum : tollStationEnums) {
                if (tollStationEnum.getCheck()) {
                    dictKeys += tollStationEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (TollStationEnum tollStationEnum : tollStationEnums) {
                    if (dictKey.equals(String.valueOf(tollStationEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("收费站字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listTollStation(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes, dictKeys);
    }

    @Override
    public List<FacilitiesInfoBO> listTollStation1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys) {
        TollStationEnum[] tollStationEnums = TollStationEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (TollStationEnum tollStationEnum : tollStationEnums) {
                if (tollStationEnum.getCheck()) {
                    dictKeys += tollStationEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (TollStationEnum tollStationEnum : tollStationEnums) {
                    if (dictKey.equals(String.valueOf(tollStationEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("收费站字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(facStatisticsBo.getIsUseUserPermissions());
        return facilitiesDao.listTollStation1(facStatisticsBo, userPermissions, facilitiesTypes, dictKeys);
    }

    public List<Map<String, Object>> listServiceAreaCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listServiceAreaCount(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes);
    }

    @Override
    public List<FacilitiesInfoBO> listServiceArea(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys) {
        ServiceAreaEnum[] serviceAreaEnums = ServiceAreaEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (ServiceAreaEnum serviceAreaEnum : serviceAreaEnums) {
                if (serviceAreaEnum.getCheck()) {
                    dictKeys += serviceAreaEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (ServiceAreaEnum serviceAreaEnum : serviceAreaEnums) {
                    if (dictKey.equals(String.valueOf(serviceAreaEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("服务区字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listServiceArea(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes, dictKeys);
    }

    @Override
    public List<FacilitiesInfoBO> listServiceArea1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys) {
        ServiceAreaEnum[] serviceAreaEnums = ServiceAreaEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (ServiceAreaEnum serviceAreaEnum : serviceAreaEnums) {
                if (serviceAreaEnum.getCheck()) {
                    dictKeys += serviceAreaEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (ServiceAreaEnum serviceAreaEnum : serviceAreaEnums) {
                    if (dictKey.equals(String.valueOf(serviceAreaEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("服务区字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(facStatisticsBo.getIsUseUserPermissions());
        return facilitiesDao.listServiceArea1(facStatisticsBo, userPermissions, facilitiesTypes, dictKeys);
    }

    public List<Map<String, Object>> listDoorFrameCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listDoorFrameCount(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes);
    }

    @Override
    public List<FacilitiesInfoBO> listDoorFrame(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys) {
        DoorFrameEnum[] doorFrameEnums = DoorFrameEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (DoorFrameEnum doorFrameEnum : doorFrameEnums) {
                if (doorFrameEnum.getCheck()) {
                    dictKeys += doorFrameEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (DoorFrameEnum doorFrameEnum : doorFrameEnums) {
                    if (dictKey.equals(String.valueOf(doorFrameEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("门架字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listDoorFrame(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes, dictKeys);
    }

    @Override
    public List<FacilitiesInfoBO> listDoorFrame1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys) {
        DoorFrameEnum[] doorFrameEnums = DoorFrameEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (DoorFrameEnum doorFrameEnum : doorFrameEnums) {
                if (doorFrameEnum.getCheck()) {
                    dictKeys += doorFrameEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (DoorFrameEnum doorFrameEnum : doorFrameEnums) {
                    if (dictKey.equals(String.valueOf(doorFrameEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("门架字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(facStatisticsBo.getIsUseUserPermissions());
        return facilitiesDao.listDoorFrame1(facStatisticsBo, userPermissions, facilitiesTypes, dictKeys);
    }

    public List<Map<String, Object>> listTunnelCount(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listTunnelCount(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes);
    }

    @Override
    public List<FacilitiesInfoBO> listTunnel(Boolean isUseUserPermissions, String orgId, String nodeType, String search, List<String> facilitiesTypes, String dictKeys) {
        TunnelEnum[] tunnelEnums = TunnelEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (TunnelEnum tunnelEnum : tunnelEnums) {
                if (tunnelEnum.getCheck()) {
                    dictKeys += tunnelEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (TunnelEnum tunnelEnum : tunnelEnums) {
                    if (dictKey.equals(String.valueOf(tunnelEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("隧道字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return facilitiesDao.listTunnel(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, search, facilitiesTypes, dictKeys);
    }

    @Override
    public List<FacilitiesInfoBO> listTunnel1(FacStatisticsBo facStatisticsBo, List<String> facilitiesTypes, String dictKeys) {
        TunnelEnum[] tunnelEnums = TunnelEnum.values();
        if (StrUtil.isBlank(dictKeys)) {
            dictKeys = "";
            for (TunnelEnum tunnelEnum : tunnelEnums) {
                if (tunnelEnum.getCheck()) {
                    dictKeys += tunnelEnum.getDictKey() + ",";
                }
            }
            if (0 < dictKeys.length()) {
                dictKeys = dictKeys.substring(0, dictKeys.length() - 1);
            }
        } else {
            String[] dictKeyArray = dictKeys.split(",");
            for (String dictKey : dictKeyArray) {
                boolean isExistDictKey = false;
                for (TunnelEnum tunnelEnum : tunnelEnums) {
                    if (dictKey.equals(String.valueOf(tunnelEnum.getDictKey()))) {
                        isExistDictKey = true;
                        break;
                    }
                }
                if (!isExistDictKey) {
                    throw new BadRequestException("隧道字典分类: " + dictKey + ",不存在");
                }
            }
        }
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(facStatisticsBo.getIsUseUserPermissions());
        return facilitiesDao.listTunnel1(facStatisticsBo, userPermissions, facilitiesTypes, dictKeys);
    }

    /**
     * 获取附近的可视对讲资源
     *
     * @param longitude  经度
     * @param latitude   纬度
     * @param distanceKm 范围里程
     * @param wayIds     路段ID集
     * @return
     */
    @Override
    public List<Map<String, String>> getContactByPoint(Double longitude, Double latitude, Double distanceKm, String wayIds) {
        Map<String, List<FacResourceNearBO>> facByPoint = getFacByPoint("32330200", longitude, latitude, distanceKm, wayIds, "");
        List<FacResourceNearBO> tollStation = facByPoint.get("tollStation");
        if (CollUtil.isEmpty(tollStation)){
            return null;
        }
        /*List<Map<String, String>> list = new ArrayList<>();

        List<Map<String, String>> resList = new LinkedList<>();

        List<Map<String, String>> handList = new LinkedList<>();
        List<Map<String, String>> shoutaiList = new LinkedList<>();
        List<Map<String, String>> inList = new LinkedList<>();
        List<Map<String, String>> outList = new LinkedList<>();
        List<Map<String, String>> phoneList = new LinkedList<>();
        List<Map<String, String>> seatList = new LinkedList<>();
        List<Map<String, String>> otherList = new LinkedList<>();
        if (CollUtil.isNotEmpty(tollStation)) {
            list = facilitiesDao.getContactByPoint(tollStation);

            for (Map<String, String> detail : list) {
                String name = MapUtil.getStr(detail, "name");
                if (name.contains("手持")){
                    handList.add(detail);
                }
                else if (name.contains("手台")){
                    shoutaiList.add(detail);
                }
                else if (name.contains("入口")){
                    inList.add(detail);
                }
                else if (name.contains("出口")){
                    outList.add(detail);
                }
                else if (name.contains("调度") && name.contains("话机")){
                    phoneList.add(detail);
                }
                else if (name.contains("坐席")){
                    seatList.add(detail);
                }
                else {
                    otherList.add(detail);
                }
            }
        }
        resList.addAll(handList);
        resList.addAll(shoutaiList);
        resList.addAll(inList);
        resList.addAll(outList);
        resList.addAll(phoneList);
        resList.addAll(seatList);
        resList.addAll(otherList);*/
        return facilitiesDao.getContactByPoint(tollStation);
    }

    /**
     * 获取附近设备资源
     *
     * @param codes      设施类型集(多个英文逗号拼接)
     * @param longitude  经度
     * @param latitude   纬度
     * @param distanceKm 范围里程
     * @param wayIds     路段ID集
     * @return
     */
    @Override
    public Map<String, List<FacResourceNearBO>> getFacByPoint(String codes, Double longitude, Double latitude,
                                                              Double distanceKm, String wayIds, String bridgeLevel) {
        Map<String, List<FacResourceNearBO>> result = new HashMap<>();
        if (StrUtil.isBlank(codes)) {
            throw new BadRequestException("codes:" + codes + ",设施类型不能为空");
        } else {
            String facTypeCodes = ",";
            for (FacilitiesTypeEnum facilitiesTypeEnum : FacilitiesTypeEnum.values()) {
                facTypeCodes += facilitiesTypeEnum.getCode() + ",";
            }
            if (!",".equals(facTypeCodes)) {
                String[] codeArray = codes.split(",");
                for (String code : codeArray) {
                    if (-1 == facTypeCodes.indexOf("," + code + ",")) {
                        throw new BadRequestException("codes:" + code + ",不在设施类型查找范围内");
                    }
                }
            }
        }
        if (null == longitude || 0 == longitude) {
            throw new BadRequestException("longitude:" + longitude + ",经度不能为空");
        }
        if (null == latitude || 0 == latitude) {
            throw new BadRequestException("latitude:" + latitude + ",纬度不能为空");
        }
        if (null == distanceKm || 0 == distanceKm) {
            throw new BadRequestException("distanceKm:" + distanceKm + ",附近范围里程不能为空");
        }
        //  有桥梁先查桥梁
        if (-1 != codes.indexOf(FacilitiesTypeEnum.BRIDGE.getCode())) {
            List<FacResourceNearBO> facResourceNearBOList = facilitiesDao.getBridgeByPoint(longitude, latitude, distanceKm, wayIds, bridgeLevel);
            result.put(FacilitiesTypeEnum.BRIDGE.getName(), facResourceNearBOList);
        }
        // 不是桥梁或不仅仅只有桥梁
        if (!codes.equals(FacilitiesTypeEnum.BRIDGE.getCode())) {
            String[] searchCodes = codes.split(",");
            searchCodes = ArrayUtil.removeEle(searchCodes, FacilitiesTypeEnum.BRIDGE.getCode());
            if (-1 != codes.indexOf(FacilitiesTypeEnum.DOOR_FRAME.getCode())) {
                searchCodes = ArrayUtil.append(searchCodes, FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode(), FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
            }
            codes = ArrayUtil.join(searchCodes, ",");
            List<FacResourceNearBO> deviceResourceNearBOList = facilitiesDao.getFacByPoint(codes, longitude, latitude, distanceKm, wayIds);
            for (FacResourceNearBO facResourceNearBO : deviceResourceNearBOList) {
                List<FacilitiesInfoBO> facilitiesInfoBOList = assetsService.getFacInfoListByDictType(false, facResourceNearBO.getFacilitiesId(), "3", facResourceNearBO.getCode(), "", "");
                if (null != facilitiesInfoBOList && 0 < facilitiesInfoBOList.size()) {
                    facResourceNearBO.setDictKey(String.valueOf(null == facilitiesInfoBOList.get(0).getDictKey() ? "" : facilitiesInfoBOList.get(0).getDictKey()));
                    facResourceNearBO.setDictName(facilitiesInfoBOList.get(0).getDictName());
                    facResourceNearBO.setPileNumber(facilitiesInfoBOList.get(0).getCenterOffNo());
                    facResourceNearBO.setBeginStakeNo(facilitiesInfoBOList.get(0).getBeginStakeNo());
                    facResourceNearBO.setEndStakeNo(facilitiesInfoBOList.get(0).getEndStakeNo());
                }
            }

            Map<String, List<FacResourceNearBO>> groupByDataSourceType = deviceResourceNearBOList.stream().collect(Collectors.groupingBy(FacResourceNearBO::getCode));
            for (FacilitiesTypeEnum facilitiesTypeEnum : FacilitiesTypeEnum.values()) {
                if (groupByDataSourceType.containsKey(facilitiesTypeEnum.getCode())) {
                    result.put(facilitiesTypeEnum.getName(), groupByDataSourceType.get(facilitiesTypeEnum.getCode()));
                    groupByDataSourceType.remove(facilitiesTypeEnum.getCode());
                }
            }
        }
        return result;
    }

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
    public List<Map<String, Object>> facilitiesGroupByNodeType(Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel, String orgId, String nodeType, List<String> facilitiesTypes) {
        return facilitiesDao.facilitiesGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
    }


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
    public List<Map<String, Object>> tunnelGroupByNodeType(Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel, String orgId, String nodeType, List<String> facilitiesTypes) {
        List<Map<String, Object>> facMapList = new ArrayList<>();
        List<Map<String, Object>> tunnelMapList = facilitiesDao.tunnelGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
        Set<String> groupSet = new HashSet<>();
        for (Map<String, Object> map : tunnelMapList) {
            groupSet.add(MapUtil.getStr(map, "id"));
        }
        for (String id : groupSet) {
            if (StrUtil.isNotBlank(id)) {
                Map<String, Object> groupMap = new HashMap<>();
                List<Map<String, Object>> list = new ArrayList<>();
                groupMap.put("id", id);
                for (Map<String, Object> map : tunnelMapList) {
                    if (id.equals(MapUtil.getStr(map, "id"))) {
                        Map<String, Object> childMap = new HashMap<>();
                        childMap.put("dictKey", MapUtil.getStr(map, "dictKey"));
                        childMap.put("dictName", MapUtil.getStr(map, "dictName"));
                        childMap.put("num", MapUtil.getStr(map, "num"));
                        list.add(childMap);
                        groupMap.put("name", MapUtil.getStr(map, "name"));
                        groupMap.put("shortName", MapUtil.getStr(map, "shortName"));
                        groupMap.put("num", (null == MapUtil.getInt(groupMap, "num") ? 0 : MapUtil.getInt(groupMap, "num")) + MapUtil.getInt(map, "num"));
                    }
                    groupSet.add(MapUtil.getStr(map, "id"));
                }
                groupMap.put("list", list);
                facMapList.add(groupMap);
            }
        }
        Collections.sort(facMapList, Comparator.comparing(this::customMap).reversed());
        return facMapList;
    }

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
    public List<Map<String, Object>> bridgeGroupByNodeType(Boolean isUseUserPermissions, String userId, Integer dataPermissionsLevel, String orgId, String nodeType, List<String> facilitiesTypes) {
        List<Map<String, Object>> facMapList = new ArrayList<>();
        List<Map<String, Object>> bridgeMapList = facilitiesDao.bridgeGroupByNodeType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
        Set<String> groupSet = new HashSet<>();
        for (Map<String, Object> map : bridgeMapList) {
            groupSet.add(MapUtil.getStr(map, "id"));
        }
        for (String id : groupSet) {
            if (StrUtil.isNotBlank(id)) {
                Map<String, Object> groupMap = new HashMap<>();
                List<Map<String, Object>> list = new ArrayList<>();
                groupMap.put("id", id);
                for (Map<String, Object> map : bridgeMapList) {
                    if (id.equals(MapUtil.getStr(map, "id"))) {
                        Map<String, Object> childMap = new HashMap<>();
                        childMap.put("dictKey", MapUtil.getStr(map, "dictKey"));
                        childMap.put("dictName", MapUtil.getStr(map, "dictName"));
                        childMap.put("num", MapUtil.getStr(map, "num"));
                        list.add(childMap);
                        groupMap.put("name", MapUtil.getStr(map, "name"));
                        groupMap.put("shortName", MapUtil.getStr(map, "shortName"));
                        groupMap.put("num", (null == MapUtil.getInt(groupMap, "num") ? 0 : MapUtil.getInt(groupMap, "num")) + MapUtil.getInt(map, "num"));
                    }
                    groupSet.add(MapUtil.getStr(map, "id"));
                }
                groupMap.put("list", list);
                facMapList.add(groupMap);
            }
        }
        Collections.sort(facMapList, Comparator.comparing(this::customMap).reversed());
        return facMapList;
    }

    /**
     * 2.80.7 查询设施列表
     */
    @Override
    public Object getFacList(FacListBo facListBo) {
        // 请求参数
        String orgId = facListBo.getOrgId();
        String nodeType = facListBo.getNodeType();
        Set<String> facilitiesTypes = facListBo.getFacilitiesTypes();
        Integer currentPage = facListBo.getCurrentPage();
        Integer pageSize = facListBo.getPageSize();
        Boolean isUseUserPermissions = facListBo.getIsUseUserPermissions();

        // 用户信息
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Page<Facilities> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);
        page.setOptimizeCountSql(false);
        if (0 == pageSize) {
            List<FacilitiesVo> facList = facilitiesDao.getFacList(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
            return sortFacList(facList);
        } else {
            Page<FacilitiesVo> facList = facilitiesDao.getFacList(page, isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, facilitiesTypes);
            facList.setRecords(sortFacList(facList.getRecords()));
            return new PageUtil<FacilitiesVo>(facList);
        }
    }

    private List<FacilitiesVo> sortFacList(List<FacilitiesVo> facList) {
        facList.sort((fac1, fac2) -> {
            Float num1 = getNumberText(fac1.getCenterOffNo());
            Float num2 = getNumberText(fac2.getCenterOffNo());
            num1 = num1 == null ? Float.MAX_VALUE : num1;
            num2 = num2 == null ? Float.MAX_VALUE : num2;
            return num1.compareTo(num2);
        });
        facList.forEach(facilitiesInfoBO -> {
            String offNo = facilitiesInfoBO.getCenterOffNo();
            String name = facilitiesInfoBO.getFacilitiesName();
            if (StrUtil.isNotBlank(offNo)) {
                facilitiesInfoBO.setFacNickName(name + offNo);
            }
        });
        return facList;
    }

    /**
     * 2.80.8 查询设施详情
     *
     * @param facId 设施id
     */
    @Override
    public FacilitiesVo getFacById(String facId) {
        return facilitiesDao.getFacById(facId);
    }

    /**
     * 设施分类统计及列表明细
     */
    @Override
    public Map<String, Object> facStatistics(FacStatisticsBo facStatisticsBo, String nodeType, List<FacilitiesInfoBO> temp) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(facStatisticsBo.getIsUseUserPermissions());

        // 获取设施
        List<Map<String, Object>> facList = new ArrayList<>();
        if (CollUtil.isNotEmpty(facStatisticsBo.getFacTypes())) {
            facList = facilitiesDao.facStatistics(facStatisticsBo, userPermissions);
        }

        if (CollUtil.isEmpty(facList) && CollUtil.isEmpty(temp)) {
            return null;
        }

        for (Map<String, Object> map : facList) {
            temp.add(BeanUtil.mapToBean(map, FacilitiesInfoBO.class, true));
        }

        Map<String, List<FacilitiesInfoBO>> tempMap = temp.stream().collect(Collectors.groupingBy(FacilitiesInfoBO::getType));

        // 构建返回的数据
        Map<String, Object> resMap = new HashMap<>();
        // 按设施类型分
        tempMap.forEach((key, value) -> {
            Map<String, Object> facTempMap = new HashMap<>();
            // 排序
            value.sort((fac1, fac2) -> {
                Float num1 = getNumberText(fac1.getCenterOffNo());
                Float num2 = getNumberText(fac2.getCenterOffNo());
                num1 = num1 == null ? Float.MAX_VALUE : num1;
                num2 = num2 == null ? Float.MAX_VALUE : num2;
                return num1.compareTo(num2);
            });
            // 拼接name+桩号
            value.forEach(facilitiesInfoBO -> {
                String offNo = facilitiesInfoBO.getCenterOffNo();
                if (StrUtil.isNotBlank(offNo)) {
                    facilitiesInfoBO.setFacNickName(facilitiesInfoBO.getName() + offNo);
                }
            });
            facTempMap.put("name", value.get(0).getTypeName());
            facTempMap.put("total", value.size());
            facTempMap.put("data", value);
            resMap.put(key, facTempMap);
        });
        return resMap;
    }

    /**
     * 转换桩号为浮点数 取前两段
     *
     * @param str
     * @return
     */
    private static Float getNumberText(String str) {
        try {
            return Float.valueOf(str);
        } catch (Exception ignored) {
            try {
                char[] chs = str.toCharArray();
                StringBuffer number = new StringBuffer();
                // 默认剔除两位字符 例如K45+23
                int num = 2;
                // 如果第一个字符是数字 则剔除一位字符 例如 45+23
                if (chs[0] >= 48 && chs[0] <= 57) {
                    --num;
                }
                // 如果第二个字符不是数字 则需剔除三位字符 例如 ZK45+23
                if (!(chs[1] >= 48 && chs[1] <= 57)) {
                    num = 3;
                }
                //取0-9;这个范围的数字!
                for (int i = 0; i < chs.length; i++) {
                    if (num >= 0) {
                        if (chs[i] >= 48 && chs[i] <= 57) {
                            number.append(chs[i]);
                        } else {
                            --num;
                            // 默认剔除最后一个字符则拼接小数点
                            if (num == 0) {
                                number.append(".");
                            }
                        }
                    }
                }
                return Float.valueOf(number.toString());
            } catch (Exception e) {
                return null;
            }
        }
    }

    private int customMap(Map<String, Object> map) {
        return (null == MapUtil.getInt(map, "num") ? 0 : MapUtil.getInt(map, "num"));
    }

}
