package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ovit.cloud.common.utils.UserInfoUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.assets.dao.AssetsDao;
import com.rhy.bdmp.open.modules.assets.dao.DeviceDao;
import com.rhy.bdmp.open.modules.assets.dao.FacilitiesDao;
import com.rhy.bdmp.open.modules.assets.dao.ManufacturerDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.*;
import com.rhy.bdmp.open.modules.assets.domain.po.Device;
import com.rhy.bdmp.open.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.open.modules.assets.domain.to.CameraListTo;
import com.rhy.bdmp.open.modules.assets.domain.vo.DeviceGatewayVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.GatewayExternalVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.LaneDeviceVo;
import com.rhy.bdmp.open.modules.assets.enums.DeviceTypeEnum;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.open.modules.assets.service.IDeviceService;
import com.rhy.bdmp.open.modules.system.dao.MicroUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: yanggj
 * @Description: 设备业务处理类(终端箱, 情报板, IP电话...)
 * @Date: 2021/9/28 8:57
 * @Version: 1.0.0
 */
@Service
public class DeviceServiceImpl implements IDeviceService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    private DeviceDao deviceDao;
    @Resource
    private AssetsDao assetsDao;
    @Resource
    private ManufacturerDao manufacturerDao;
    @Resource
    private MicroUserDao microUserDao;
    @Resource
    private FacilitiesDao facDao;

    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    @Override
    public List<Device> selectByFacId(String facilitiesId) {
        return deviceDao.selectByFacId(facilitiesId);
    }

    @Override
    public Map<String, String> getDeviceDetail(String deviceId) {
        Map<String, String> device = deviceDao.getDeviceDetail(deviceId);
        if (device == null) {
            throw new BadRequestException("deviceId:" + deviceId + ",设备不存在");
        }
        return device;
    }

    @Override
    public List<Device> selectCameraByFacId(String facId) {
        return deviceDao.selectCameraByFacId(facId);
    }

    @Override
    public JSONArray getDeviceByTollStation(String stationId) {
        List<TollStationDeviceBO> deviceList = deviceDao.getDeviceByTollStation(stationId);
        JSONArray array = new JSONArray();
        if (deviceList.isEmpty()) {
            return array;
        }
        Map<TollStationDeviceBO, List<TollStationDeviceBO>> collect = deviceList.parallelStream().collect(Collectors.groupingBy(item -> {
            TollStationDeviceBO deviceBO = new TollStationDeviceBO();
            deviceBO.setSystemId(item.getSystemId());
            deviceBO.setSystemName(item.getSystemName());
            return deviceBO;
        }));
        collect.forEach((k, v) -> array.add(JSONUtil.createObj(jsonConfig)
                .putOnce("systemId", k.getSystemId())
                .putOnce("systemName", k.getSystemName())
                .putOnce("children", v)
        ));
        return array;
    }

//    @Override
//    public JSONArray getDeviceStatByType(boolean isUseUserPermissions, String orgId, String nodeType) {
//        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
//        String userId = userPermissions.getUserId();
//        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
//        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
//
//        List<DeviceStatByTypeBO> list = deviceDao.getDeviceStatByType(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
//        JSONArray array = new JSONArray();
//        if (list.isEmpty()) {
//            return array;
//        }
//        Map<DeviceStatByTypeBO, List<DeviceStatByTypeBO>> collect = list.parallelStream().collect(Collectors.groupingBy(item -> new DeviceStatByTypeBO(item.getName(), item.getCode())));
//        collect.forEach((k, v) -> array.add(JSONUtil.createObj(jsonConfig)
//                .putOnce("code", k.getCode())
//                .putOnce("name", k.getName())
//                .putOnce("total", v.stream().mapToInt(DeviceStatByTypeBO::getTotal).sum())
//                .putOnce("list", v.stream().map(item -> JSONUtil.createObj(jsonConfig)
//                                .putOnce("waysectionId", item.getWaysectionId())
//                                .putOnce("waysectionNo", item.getWaysectionNo())
//                                .putOnce("waysectionName", item.getWaysectionName())
//                                .putOnce("oriWaysectionNo", item.getOriWaysectionNo())
//                                .putOnce("total", item.getTotal())
//                        ).collect(Collectors.toList())
//                )
//        ));
//        return array;
//    }

    @Override
    public JSONArray getDeviceStatByType(boolean isUseUserPermissions, String orgId, String nodeType, String search) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(String.valueOf(userId))){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        List<DeviceStatByTypeBO> list = deviceDao.getDeviceStatByType(isUseUserPermissions,userId, dataPermissionsLevel, orgId, nodeType, search,userType);
        JSONArray array = new JSONArray();
        if (list.isEmpty()) {
            return array;
        }
        for (DeviceStatByTypeBO deviceStatByTypeBO : list){
            JSONObject jsonObject = JSONUtil.createObj();
            jsonObject.set("code", deviceStatByTypeBO.getCode());
            jsonObject.set("name", deviceStatByTypeBO.getName());
            jsonObject.set("total", deviceStatByTypeBO.getTotal());
            array.add(jsonObject);
        }
        return array;
    }

    @Override
    public List<?> getDeviceInfoListByType(boolean isUseUserPermissions, String orgId, String nodeType, String code, String search) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(String.valueOf(userId))){
            userType = microUserDao.getMicroUserInfo(String.valueOf(userId)).getUserType();
        }
        List<Device> list = deviceDao.getDeviceInfoListByType(isUseUserPermissions, userId, userType, dataPermissionsLevel, orgId, nodeType, code, search);
        List<JSONObject> jsonArray = list.stream().map(device -> JSONUtil.createObj(jsonConfig)
                .putOnce("id", device.getDeviceId())
                .putOnce("deviceNo", device.getDeviceCode())
                .putOnce("latitude", device.getLatitude())
                .putOnce("longitude", device.getLongitude())
                .putOnce("originId", device.getDeviceIdOld())
                .putOnce("ip", device.getIp())
                .putOnce("name", device.getDeviceName())
                .putOnce("workStatus", device.getWorkStatus())
                .putOnce("dataSource", device.getDataSource())
                .putOnce("centerOffNoDes", device.getCenterOffNoDes())
                .putOnce("locationType", device.getLocationType())
                .putOnce("facilitiesId", device.getFacilitiesId())
                .putOnce("facilitiesName", device.getFacilitiesName())
                .putOnce("waysectionId", device.getWaysectionId())
                .putOnce("waysectionName", device.getWaysectionName())
                .putOnce("orgId", device.getOrgId())
                .putOnce("orgName", device.getOrgName())
                .putOnce("shortName", device.getShortName())
                .putOnce("systemId", device.getSystemId())
                .putOnce("systemName", device.getSystemName())
                .putOnce("location", device.getLocation())
                .putOnce("deviceType", device.getDeviceType())
                .putOnce("remark", device.getRemark())
                .putOnce("direction", device.getDirection())
                .putOnce("deviceTypeName", device.getDeviceTypeName())
                .putOnce("deviceDictName", device.getDeviceDictName())
                .putOnce("deviceDictId", device.getDeviceDictId())
                )
                .collect(Collectors.toList());
        return jsonArray;

    }

    @Override
    public JSONObject getDeviceStatBySystem(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId = UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();


        JSONObject result = new JSONObject();
        // 获得所有一级系统id
        List<DictSystem> dictList = assetsDao.getSuperiorSystemDict();
        int totalDeviceNum = 0;//设备总数
        int normalDeviceNum = 0;//正常设备数
        int abnormalDeviceNum = 0;//异常设备数
        List<DeviceInfoBO> deviceInfoList = new ArrayList<>();
        for (DictSystem dictSystem : dictList) {
            // 根据pid查询出所有的子系统列表
            List<String> systemIds = new ArrayList<>();
            // systemId 非空时,才去查询系统字典表
            systemIds.add(dictSystem.getSystemId());
            List<DictSystem> childSystemIds = assetsDao.listByPid(dictSystem.getSystemId());
            while (!childSystemIds.isEmpty()) {
                List<String> tempIds = childSystemIds.stream().map(DictSystem::getSystemId).collect(Collectors.toList());
                systemIds.addAll(tempIds);
                childSystemIds = assetsDao.listBySystemIds(tempIds);
            }
            // 拿到该系统的所有子系统id集合去设备表统计设备数量
            List<DeviceStatisticsInfo> statistics = deviceDao.getDeviceCountBySystem(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, systemIds);
            //封装结果集
            DeviceInfoBO deviceInfoBO = new DeviceInfoBO();
            deviceInfoBO.setType(dictSystem.getSystemId());
            deviceInfoBO.setName(dictSystem.getSystemName());
            if (CollectionUtil.isNotEmpty(statistics)) {
                for (DeviceStatisticsInfo e : statistics) {
                    //总设备数
                    totalDeviceNum = totalDeviceNum + e.getTotal();
                    if ("0".equals(e.getStatus())) {
                        //正常设备
                        normalDeviceNum = normalDeviceNum + e.getTotal();
                        deviceInfoBO.setNormalDeviceNum(e.getTotal());
                    } else {
                        //异常设备
                        abnormalDeviceNum = abnormalDeviceNum + e.getTotal();
                        deviceInfoBO.setAbnormalDeviceNum(e.getTotal());
                    }
                }
            }
            deviceInfoList.add(deviceInfoBO);
        }
        result.putOnce("totalDeviceNum", totalDeviceNum);
        result.putOnce("normalDeviceNum", normalDeviceNum);
        result.putOnce("abnormalDeviceNum", abnormalDeviceNum);
        result.putOnce("list", deviceInfoList);
        return result;
    }

    @Override
    public HashMap<String, String> getDeviceStatInfo(boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId = UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        // 获取设备总况信息
        return deviceDao.getDeviceStatInfo(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
    }

    /**
     * 获取附近摄像机资源
     *
     * @param longitude  经度
     * @param latitude   纬度
     * @param distanceKm 范围里程
     * @param wayIds     路段ID集
     * @return
     */
    @Override
    public Map<String, List<CameraResourceNearBO>> getCameraByPoint(Double longitude, Double latitude, Double distanceKm, String wayIds,String resourceIds,String excludeLocationTypes) {
        if (null == longitude || 0 == longitude) {
            throw new BadRequestException("longitude:" + longitude + ",经度不能为空");
        }
        if (null == latitude || 0 == latitude) {
            throw new BadRequestException("latitude:" + latitude + ",纬度不能为空");
        }
        if (null == distanceKm || 0 == distanceKm) {
            throw new BadRequestException("distanceKm:" + distanceKm + ",附近范围里程不能为空");
        }
        Integer userType = null;
        String userId = WebUtils.getUserId();
        if (StrUtil.isNotBlank(userId)){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        String[] excludeLocationTypeArray = null;
        if (StrUtil.isNotBlank(excludeLocationTypes)){
            excludeLocationTypeArray = excludeLocationTypes.split(",");
        }
        List<CameraResourceNearBO> cameraResourceNearBOList = deviceDao.getCameraByPoint(longitude, latitude, distanceKm, wayIds, userType, resourceIds,excludeLocationTypeArray);
        Map<String, List<CameraResourceNearBO>> groupByDataSourceType = cameraResourceNearBOList.stream().collect(Collectors.groupingBy(CameraResourceNearBO::getDataSource));
        if (groupByDataSourceType.containsKey("1")) {
            List<CameraResourceNearBO> tempYT = groupByDataSourceType.get("1");
            // 保证resourceIds查询的数据在第一个
            List<CameraResourceNearBO> tempList = new LinkedList<>();
            if (StrUtil.isNotBlank(resourceIds)){
                List<String> resourceIdList = ListUtil.toList(resourceIds.split(","));
                Iterator<CameraResourceNearBO> iterator = tempYT.iterator();
                while (iterator.hasNext()){
                    CameraResourceNearBO cameraResourceNearBO = iterator.next();
                    for (String id : resourceIdList) {
                        if (cameraResourceNearBO.getCameraId().equals(id)){
                            tempList.add(cameraResourceNearBO);
                            iterator.remove();
                        }
                    }
                }
            }
            tempList.addAll(tempYT);
            groupByDataSourceType.put("cameraYtList", tempList);
            groupByDataSourceType.remove("1");
        }
        if (groupByDataSourceType.containsKey("2")) {
            List<CameraResourceNearBO> tempTL = groupByDataSourceType.get("2");

            // 保证resourceIds查询的数据在第一个
            List<CameraResourceNearBO> tempList = new LinkedList<>();
            if (StrUtil.isNotBlank(resourceIds)){
                List<String> resourceIdList = ListUtil.toList(resourceIds.split(","));
                Iterator<CameraResourceNearBO> iterator = tempTL.iterator();
                while (iterator.hasNext()){
                    CameraResourceNearBO cameraResourceNearBO = iterator.next();
                    for (String id : resourceIdList) {
                        if (StrUtil.isNotBlank(cameraResourceNearBO.getUuid())){
                            if (cameraResourceNearBO.getUuid().equals(id)){
                                tempList.add(cameraResourceNearBO);
                                iterator.remove();
                            }
                        }
                    }
                }
            }
            tempList.addAll(tempTL);
            groupByDataSourceType.put("cameraTlList", tempList);
            groupByDataSourceType.remove("2");
        }
        if (groupByDataSourceType.containsKey("3")) {
            List<CameraResourceNearBO> tempSY = groupByDataSourceType.get("3");
            // 保证resourceIds查询的数据在第一个
            List<CameraResourceNearBO> tempList = new LinkedList<>();
            if (StrUtil.isNotBlank(resourceIds)){
                List<String> resourceIdList = ListUtil.toList(resourceIds.split(","));
                Iterator<CameraResourceNearBO> iterator = tempSY.iterator();
                while (iterator.hasNext()){
                    CameraResourceNearBO cameraResourceNearBO = iterator.next();
                    for (String id : resourceIdList) {
                        if (cameraResourceNearBO.getCameraId().equals(id)){
                            tempList.add(cameraResourceNearBO);
                            iterator.remove();
                        }
                    }
                }
            }
            tempList.addAll(tempSY);
//            groupByDataSourceType.put("cameraSyList", tempList);
            groupByDataSourceType.put("cameraSyList", Collections.emptyList());
            groupByDataSourceType.remove("3");
        }
        return groupByDataSourceType;
    }

    /**
     * 获取附近设备资源
     * @param codes         设备类型集(多个英文逗号拼接)
     * @param longitude     经度
     * @param latitude      纬度
     * @param distanceKm    范围里程
     * @param wayIds        路段ID集
     * @return
     */
    @Override
    public Map<String, Object> getDeviceByPoint(String codes, Double longitude, Double latitude,
                                                Double distanceKm, String wayIds,String resourceIds,
                                                String excludeFacTypes,String excludeLocationTypes) {
        Map<String, Object> result = new HashMap<>();
        if (StrUtil.isBlank(codes)){
            throw new BadRequestException("codes:" + codes + ",设备类型不能为空");
        } else {
            String deviceTypeCodes = ",";
            for (DeviceTypeEnum deviceTypeEnum : DeviceTypeEnum.values()){
                deviceTypeCodes += deviceTypeEnum.getCode() + ",";
            }
            if (!",".equals(deviceTypeCodes)){
                String[] codeArray = codes.split(",");
                for (String code : codeArray){
                    if (-1 == deviceTypeCodes.indexOf("," + code + ",")){
                        throw new BadRequestException("codes:" + code + ",不在设备类型查找范围内");
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
        //  有摄像机先查摄像机
        if (-1 != codes.indexOf(DeviceTypeEnum.SXJ.getCode())) {
            Integer userType = null;
            String userId = WebUtils.getUserId();
            if (StrUtil.isNotBlank(userId)){
                userType = microUserDao.getMicroUserInfo(userId).getUserType();
            }
            String[] excludeLocationTypeArray = null;
            if (StrUtil.isNotBlank(excludeLocationTypes)){
                excludeLocationTypeArray = excludeLocationTypes.split(",");
            }
            List<CameraResourceNearBO> cameraResourceNearBOList = deviceDao.getCameraByPoint(longitude, latitude, distanceKm, wayIds, userType, resourceIds,excludeLocationTypeArray);
            List<CameraResourceNearBO> cameraTempList = new LinkedList<>();
            if (StrUtil.isNotBlank(excludeFacTypes)){
                List<String> facCodes = Arrays.asList(excludeFacTypes.split(","));
                List<String> gantryList = facDao.getFacIdByCode(facCodes);
                for (CameraResourceNearBO cameraResourceNearBO : cameraResourceNearBOList) {
                    boolean exclude = gantryList.stream().anyMatch(facId -> facId.equals(cameraResourceNearBO.getFacilitiesId()));
                    if (!exclude){
                        cameraTempList.add(cameraResourceNearBO);
                    }
                }
                cameraResourceNearBOList = cameraTempList;
            }
            Map<String, List<CameraResourceNearBO>> groupByDataSourceType = cameraResourceNearBOList.stream().collect(Collectors.groupingBy(CameraResourceNearBO::getDataSource));
            if (groupByDataSourceType.containsKey("1")) {
                // 保证resourceIds查询的数据在第一个
                List<CameraResourceNearBO> tempYT = groupByDataSourceType.get("1");
                List<CameraResourceNearBO> tempList = new LinkedList<>();
                if (StrUtil.isNotBlank(resourceIds)){
                    List<String> resourceIdList = ListUtil.toList(resourceIds.split(","));
                    Iterator<CameraResourceNearBO> iterator = tempYT.iterator();
                    while (iterator.hasNext()){
                        CameraResourceNearBO cameraResourceNearBO = iterator.next();
                        for (String id : resourceIdList) {
                            if (cameraResourceNearBO.getCameraId().equals(id)){
                                tempList.add(cameraResourceNearBO);
                                iterator.remove();
                            }
                        }
                    }
                }
                tempList.addAll(tempYT);
                groupByDataSourceType.put("cameraYtList", tempList);
                groupByDataSourceType.remove("1");
            }
            if (groupByDataSourceType.containsKey("2")) {
                List<CameraResourceNearBO> tempTL = groupByDataSourceType.get("2");

                // 保证resourceIds查询的数据在第一个
                List<CameraResourceNearBO> tempList = new LinkedList<>();
                if (StrUtil.isNotBlank(resourceIds)){
                    List<String> resourceIdList = ListUtil.toList(resourceIds.split(","));
                    Iterator<CameraResourceNearBO> iterator = tempTL.iterator();
                    while (iterator.hasNext()){
                        CameraResourceNearBO cameraResourceNearBO = iterator.next();
                        for (String id : resourceIdList) {
                            if (StrUtil.isNotBlank(cameraResourceNearBO.getUuid())){
                                if (cameraResourceNearBO.getUuid().equals(id)){
                                    tempList.add(cameraResourceNearBO);
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
                tempList.addAll(tempTL);
                groupByDataSourceType.put("cameraTlList", tempList);
                groupByDataSourceType.remove("2");
            }
            if (groupByDataSourceType.containsKey("3")) {
                List<CameraResourceNearBO> tempSY = groupByDataSourceType.get("3");
                // 保证resourceIds查询的数据在第一个
                List<CameraResourceNearBO> tempList = new LinkedList<>();
                if (StrUtil.isNotBlank(resourceIds)){
                    List<String> resourceIdList = ListUtil.toList(resourceIds.split(","));
                    Iterator<CameraResourceNearBO> iterator = tempSY.iterator();
                    while (iterator.hasNext()){
                        CameraResourceNearBO cameraResourceNearBO = iterator.next();
                        for (String id : resourceIdList) {
                            if (cameraResourceNearBO.getCameraId().equals(id)){
                                tempList.add(cameraResourceNearBO);
                                iterator.remove();
                            }
                        }
                    }
                }
                tempList.addAll(tempSY);
                groupByDataSourceType.put("cameraSyList", tempList);
                groupByDataSourceType.remove("3");
            }
            result.put(DeviceTypeEnum.SXJ.getKeyword(), groupByDataSourceType);
        }
        // 不是摄像机或不仅仅只有摄像机
        if (!codes.equals(DeviceTypeEnum.SXJ.getCode())){
            String[] searchCodes = codes.split(",");
            searchCodes = ArrayUtil.removeEle(searchCodes, DeviceTypeEnum.SXJ.getCode());
            codes = ArrayUtil.join(searchCodes, ",");
            List<DeviceResourceNearBO> deviceResourceNearBOList = deviceDao.getDeviceByPoint(codes, longitude, latitude, distanceKm, wayIds);

            List<DeviceResourceNearBO> tempList = new LinkedList<>();
            List<DeviceResourceNearBO> tempDeviceList = new LinkedList<>();
            if (StrUtil.isNotBlank(resourceIds)){
                List<String> idList = ListUtil.toList(resourceIds.split(","));
                for (String id : idList) {
                    if (CollUtil.isNotEmpty(deviceResourceNearBOList)){
                        Iterator<DeviceResourceNearBO> iterator = deviceResourceNearBOList.iterator();
                        while (iterator.hasNext()){
                            DeviceResourceNearBO nearBO = iterator.next();
                            if (nearBO.getDeviceRecordId().equals(id)){
                                tempList.add(nearBO);
                                iterator.remove();
                            }
                        }
                    }
                }
            }
            tempList.addAll(deviceResourceNearBOList);

            // 排除指定的设施类型
            if (StrUtil.isNotBlank(excludeFacTypes)){
                List<String> facCodes = Arrays.asList(excludeFacTypes.split(","));
                List<String> facList = facDao.getFacIdByCode(facCodes);
                for (DeviceResourceNearBO nearBO : tempList) {
                    boolean exclude = facList.stream().anyMatch(facCode -> facCode.equals(nearBO.getFacilitiesId()));
                    if (!exclude){
                        tempDeviceList.add(nearBO);
                    }
                }
                tempList = tempDeviceList;
            }

            Map<String, List<DeviceResourceNearBO>> groupByDataSourceType = tempList.stream().map(deviceResourceNearBO -> {
                deviceResourceNearBO.setCodeName(DeviceTypeEnum.getName(deviceResourceNearBO.getCode()));
                return deviceResourceNearBO;
            }).collect(Collectors.groupingBy(DeviceResourceNearBO::getCode));
            for (DeviceTypeEnum deviceTypeEnum : DeviceTypeEnum.values()){
                if (groupByDataSourceType.containsKey(deviceTypeEnum.getCode())){
                    result.put(deviceTypeEnum.getKeyword(), groupByDataSourceType.get(deviceTypeEnum.getCode()));
                    groupByDataSourceType.remove(deviceTypeEnum.getCode());
                }
            }
        }
        return result;
    }

    /**
     * 2.30 数据设备过滤
     */
    @Override
    public PageUtil<DeviceVo> getDataDevice(DataDeviceBo dataDeviceBo) {
        // 请求参数
        Boolean isUseUserPermissions = dataDeviceBo.getIsUseUserPermissions();
        String nodeType = dataDeviceBo.getNodeType();
        String orgId = dataDeviceBo.getOrgId();
        List<String> deviceTypes = dataDeviceBo.getDeviceTypes();
        Integer currentPage = dataDeviceBo.getCurrentPage();
        Integer pageSize = dataDeviceBo.getPageSize();
        String deviceDictId = dataDeviceBo.getDeviceDictId();
        String deviceName = dataDeviceBo.getDeviceName();

        // 用户信息
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Page<DeviceVo> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        page.setOptimizeCountSql(true);
        Page<DeviceVo> pageDevice = deviceDao.getDevice(page, isUseUserPermissions, userId, dataPermissionsLevel,
                nodeType, orgId, deviceTypes,deviceDictId,deviceName);
        return new PageUtil<DeviceVo>(pageDevice);
    }

    /**
     * 厂商设备过滤
     */
    @Override
    public PageUtil<DeviceVo> getManufacturerDevice(ManufacturerDeviceBo manufacturerDeviceBo) {
        // 请求参数
        String nodeType = manufacturerDeviceBo.getNodeType();
        String orgId = manufacturerDeviceBo.getOrgId();
        List<String> deviceTypes = manufacturerDeviceBo.getDeviceTypes();
        Integer currentPage = manufacturerDeviceBo.getCurrentPage();
        Integer pageSize = manufacturerDeviceBo.getPageSize();
        String deviceDictId = manufacturerDeviceBo.getDeviceDictId();
        String deviceName = manufacturerDeviceBo.getDeviceName();

        // 用户厂商
        String userManufacturer = manufacturerDao.getUserManufacturer(WebUtils.getUserId());
        Page<DeviceVo> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(currentPage);
        // 关闭分页自动count(*)
        page.setSearchCount(false);
        List<?> pageManufacturerDevice = deviceDao.getManufacturerDevice(userManufacturer, nodeType,
                orgId, deviceTypes, deviceDictId, deviceName,
                pageSize * currentPage - pageSize, pageSize * currentPage);
        // SQL查询结果
        List records = (List<DeviceVo>) pageManufacturerDevice.get(0);
        // 数据总数count
        Long total = ((List<Long>) pageManufacturerDevice.get(1)).get(0);
        page.setTotal(total);
        page.setRecords(records);
        return new PageUtil<DeviceVo>(page);
    }

    /**
     * 2.80.4.1 查询设备详情（根据设备编号查询）
     * @param deviceCode 设备编号
     */
    @Override
    public DeviceVo getDeviceByCode(String deviceCode) {
        return deviceDao.getDeviceByCode(deviceCode);
    }

    /**
     * 查询设备详情（根据设备编号查询）（网关专用）
     * 查询设备的所属网关
     * @param deviceCode 设备编号
     */
    @Override
    public DeviceGatewayVo getDeviceGateway(String deviceCode) {
        DeviceVo device = deviceDao.getDeviceByCode(deviceCode);
        DeviceGatewayVo deviceGatewayVo = new DeviceGatewayVo();
        BeanUtil.copyProperties(device,deviceGatewayVo);

        DeviceVo gateway = deviceDao.getDeviceGateway(deviceGatewayVo.getDeviceCode());
        deviceGatewayVo.setGateway(gateway);
        return deviceGatewayVo;
    }

    /**
     * 2.80.4.3 查询设备详情（根据设备id查询）
     * @param deviceId 设备id
     */
    @Override
    public DeviceVo getDeviceById(String deviceId) {
        return deviceDao.getDeviceById(deviceId);
    }

    /**
     * 2.80.6 查询设备的外接设备
     * @param deviceId 设备编号
     */
    @Override
    public GatewayExternalVo getExternalDevice(String deviceId) {
        // 查询设备
        DeviceVo temp = deviceDao.getDeviceByDeviceId(deviceId);
        GatewayExternalVo gatewayExternalVo = new GatewayExternalVo();
        BeanUtil.copyProperties(temp,gatewayExternalVo);
        // 设置外接设备
        gatewayExternalVo.setExternalList(deviceDao.getExternalDevice(deviceId));
        return gatewayExternalVo;
    }

    /**
     * 车道设备列表
     * @param laneId 车道id
     */
    @Override
    public List<LaneDeviceVo> getLaneDeviceList(String laneId) {
        return deviceDao.getLaneDeviceList(laneId);
    }

    /**
     * 设备分类统计及列表明细
     */
    @Override
    public Map<String,Object> deviceStatistics(DeviceStatisticsBo deviceStatisticsBo) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(deviceStatisticsBo.getIsUseUserPermissions());

        Set<String> deviceTypes = deviceStatisticsBo.getDeviceTypes();
        List<Map<String, Object>> cameraList = null;
        if (deviceTypes.contains("130100")){
            deviceTypes.remove("130100");
            Integer userType = null;
            String userId = WebUtils.getUserId();
            if (StrUtil.isNotBlank(userId)){
                userType = microUserDao.getMicroUserInfo(userId).getUserType();
            }
            cameraList = deviceDao.cameraStatistics(deviceStatisticsBo,userPermissions,userType);
        }

        // 获取设备
        List<Map<String, Object>> deviceList = new ArrayList<>();
        if (CollUtil.isNotEmpty(deviceTypes)){
            deviceList = deviceDao.deviceStatistics(deviceStatisticsBo, userPermissions);
        }

        if (CollUtil.isEmpty(deviceList) && CollUtil.isEmpty(cameraList)){
            return null;
        }

        if (CollUtil.isNotEmpty(cameraList)){
            deviceList.addAll(cameraList);
        }

        List<DeviceBO> tempList = new ArrayList<>();
        for (Map<String, Object> device : deviceList) {
            tempList.add(BeanUtil.mapToBean(device,DeviceBO.class,true));
        }

        Map<String, List<DeviceBO>> deviceMap = tempList.stream().collect(Collectors.groupingBy(DeviceBO::getDeviceType));

        // 构建返回的数据
        Map<String,Object> resMap = new HashMap<>();
        BigDecimal deviceMileage;
        if (CollUtil.isNotEmpty(deviceMap)){
            // 按设备类型分
            for (Map.Entry<String, List<DeviceBO>> entry : deviceMap.entrySet()) {
                String deviceType = entry.getValue().get(0).getDeviceType();
                Map<String,Object> tempMap = new HashMap<>();
                switch (deviceType){
                    case "131400":
                        // 统计设备里程
                        deviceMileage = new BigDecimal("0");
                        for (DeviceBO device : entry.getValue()) {
                            deviceMileage = deviceMileage.add(device.getDeviceMileage());
                        }
                        tempMap.put("total",deviceMileage);
                        tempMap.put("data",entry.getValue());
                        tempMap.put("name",entry.getValue().get(0).getTypeName());
                        resMap.put(deviceType,tempMap);
                        break;
                    case "130100":
                        // 摄像头分tl、yt
                        Map<String, List<DeviceBO>> cameraMap = entry.getValue().stream().collect(Collectors.groupingBy(DeviceBO::getDataSource));
                        tempMap.put("name",entry.getValue().get(0).getTypeName());
                        Map<String,Object> dataSource = new HashMap<>();
                        for (Map.Entry<String, List<DeviceBO>> cameraEntry : cameraMap.entrySet()) {
                            Map<String,Object> cameraTemp = new HashMap<>();
                            cameraTemp.put("total",cameraEntry.getValue().size());
                            cameraTemp.put("data",cameraEntry.getValue());
                            dataSource.put(cameraEntry.getKey(),cameraTemp);
                            tempMap.put("data",dataSource);
                        }
                        resMap.put(deviceType,tempMap);
                        break;
                    default:
                        tempMap.put("total",entry.getValue().size());
                        tempMap.put("data",entry.getValue());
                        tempMap.put("name",entry.getValue().get(0).getTypeName());
                        resMap.put(deviceType,tempMap);
                        break;
                }
            }
        }
        return resMap;
    }

    @Override
    public List<Map<String, Object>> getCameraByCenterOffNo(CameraByCenterOffNoBo cameraByCenterOffNoBo) {
        String centerOffNo = cameraByCenterOffNoBo.getCenterOffNo();
        String direction = cameraByCenterOffNoBo.getDirection();
        Integer searchRule = cameraByCenterOffNoBo.getSearchRule();
        boolean allowable = centerOffNo.matches("[a-zA-z]*[0-9]*\\+[0-9]*");
        if (!allowable){
            throw new BadRequestException("桩号：" + centerOffNo + "输入不合法,正例：ZK123+45");
        }
        centerOffNo = centerOffNo.replaceAll("[a-zA-Z]", "").replace("+",".");
        double eventCenterOffNo = Double.parseDouble(centerOffNo.replaceAll("[a-zA-Z]", "").replace("+","."));

        // 上行: 桩号递增，下行: 桩号递减
        List<CameraListTo> resCamera = new ArrayList<CameraListTo>();
        switch (searchRule){
            case 1:
                // 单向
                // 方向顺向最近摄像头
                if (StrUtil.isBlank(direction)){
                    throw new BadRequestException("方向不为空");
                }
                List<CameraListTo> cameraUnidirectionalList = deviceDao.getUnidirectionalCameraList(cameraByCenterOffNoBo);
                resCamera.add(this.getCameraByDirection(cameraUnidirectionalList, direction, eventCenterOffNo));
                break;
            case 2:
                // 上行、下行最近摄像头
                List<CameraListTo> bilaterallyCameraList = deviceDao.getBilaterallyCameraList(cameraByCenterOffNoBo);
                resCamera.add(this.getCameraByDirection(bilaterallyCameraList, "上行", eventCenterOffNo));
                resCamera.add(this.getCameraByDirection(bilaterallyCameraList, "下行", eventCenterOffNo));
                break;
            case 3:
                // 桩号最近摄像头
                bilaterallyCameraList = deviceDao.getBilaterallyCameraList(cameraByCenterOffNoBo);
                resCamera.add(this.getCameraByDirection(bilaterallyCameraList, "上行", eventCenterOffNo));
                resCamera.add(this.getCameraByDirection(bilaterallyCameraList, "下行", eventCenterOffNo));
                resCamera.removeIf(Objects::isNull);
                if (CollUtil.isEmpty(resCamera)){
                    return Collections.emptyList();
                }
                CameraListTo cameraListTo = resCamera.stream().map(cameraTo -> {
                    double centerOffNoD = Double.parseDouble(cameraTo.getCenterOffNo());
                    double cameraTo1 = Math.abs(centerOffNoD - eventCenterOffNo);
                    cameraTo.setCenterOffNo(String.valueOf(cameraTo1));
                    return cameraTo;
                }).min(Comparator.comparing(cameraTo -> Double.parseDouble(cameraTo.getCenterOffNo()))).orElse(null);
                resCamera.clear();
                resCamera.add(cameraListTo);
                break;
            default:
                break;
        }
        if (CollUtil.isEmpty(resCamera)){
            return Collections.emptyList();
        }
        resCamera.removeIf(Objects::isNull);
        return resCamera.stream().map(cameraTo -> {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("id",cameraTo.getId());
            tempMap.put("name",cameraTo.getName());
            tempMap.put("direction",cameraTo.getDirection());
            return tempMap;
        }).collect(Collectors.toList());
    }

    private CameraListTo getCameraByDirection(List<CameraListTo> cameraList,String direction,double eventCenterOffNo){
        CameraListTo camera = null;
        cameraList = cameraList.parallelStream().map(cameraTo -> {
            cameraTo.setCenterOffNo(cameraTo.getCenterOffNo().replaceAll("[a-zA-Z]", "").replace("+", "."));
            return cameraTo;
        }).collect(Collectors.toList());
        switch (direction){
            case "上行":
                // 在小于等于传递过来的桩号里取最大桩号的摄像头
                camera = cameraList.parallelStream().filter(cameraTo -> {
                    double cameraCenterOffNoD = Double.parseDouble(cameraTo.getCenterOffNo());
                    return eventCenterOffNo >= cameraCenterOffNoD && StrUtil.equals("上行", cameraTo.getDirection());
                }).max(Comparator.comparing(cameraListTo -> Double.parseDouble(cameraListTo.getCenterOffNo()))).orElse(null);
                break;
            case "下行":
                // 在大于等于传递过来的桩号里取最小桩号的摄像头
                camera = cameraList.parallelStream().filter(cameraTo -> {
                    double cameraCenterOffNoD = Double.parseDouble(cameraTo.getCenterOffNo());
                    return eventCenterOffNo <= cameraCenterOffNoD && StrUtil.equals("下行", cameraTo.getDirection());
                }).min(Comparator.comparing(cameraListTo -> Double.parseDouble(cameraListTo.getCenterOffNo()))).orElse(null);
                break;
            default:
                break;
        }
        return camera;
    }
}
