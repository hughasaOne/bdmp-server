package com.rhy.bdmp.collect.modules.jdxt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bcp.common.datasource.DynamicRoutingDataSource;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.base.modules.assets.domain.po.*;
import com.rhy.bdmp.base.modules.assets.service.*;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.rhy.bdmp.collect.modules.jdxt.service.IJdxtService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author jiangzhimin
 * @description 采集机电系统数据
 * @date 2021-08-02 17:19
 */
@Service
public class JdxtServiceImpl implements IJdxtService {

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Resource
    private IBaseOrgService baseOrgService;

    @Resource
    private IBaseWaysectionService baseWaysectionService;

    @Resource
    private IBaseFacilitiesService baseFacilitiesService;

    @Resource
    private IBaseDeviceService baseDeviceService;

    @Resource
    private IBaseCameraResourceTlService baseCameraResourceTlService;

    @Resource
    private IBaseCameraResourceYtService baseCameraResourceYtService;

    @Resource
    private IBaseDictService baseDictService;

    @Resource
    private IBaseDictBrandService baseDictBrandService;

    @Resource
    private IBaseDictDeviceService baseDictDeviceService;

    @Resource
    private IBaseDictSystemService baseDictSystemService;

    @Resource
    private IBaseRouteService baseRouteService;

    @Resource
    private IBaseFacilitiesTollStationService baseFacilitiesTollStationService;

    @Resource
    private IBaseFacilitiesServiceAreaService baseFacilitiesServiceAreaService;

    @Resource
    private IBaseFacilitiesBridgeService baseFacilitiesBridgeService;

    @Resource
    private IBaseFacilitiesTunnelService baseFacilitiesTunnelService;

    @Resource
    private IBaseFacilitiesGantryService baseFacilitiesGantryService;

    @Resource
    private IBaseFacilitiesTollStationLaneService baseFacilitiesTollStationLaneService;

    @Resource
    private SqlHelperService sqlHelperService;


    /**
     * 同步运营公司
     *
     * @return
     */
    @Override
    public boolean syncOperatingCompany() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("HOI_Company").set("CompType", new String[]{"3000101", "3000102"}));
            List<Org> orgList = baseOrgService.list(new QueryWrapper<Org>().eq("id_source", "jdxt").in("org_type", new String[]{"000100", "000200", "000300"}));
            List<Org> operationOrgList = new ArrayList<Org>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (Org org : orgList) {
                    if (entity.getStr("Company_Id").equals(org.getOrgId())) {
                        isExist = true;
                        // 存在则更新
                        updateOrgData(org, entity);
                        org.setUpdateTime(currentDateTime);
                        operationOrgList.add(org);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    Org org = new Org();
                    updateOrgData(org, entity);
                    org.setOrgId(entity.getStr("Company_Id"));
                    org.setCreateTime(currentDateTime);
                    org.setUpdateTime(currentDateTime);
                    org.setIdSource("jdxt");

                    operationOrgList.add(org);
                }
            }
            if (0 < operationOrgList.size()) {
                result = baseOrgService.saveOrUpdateBatch(operationOrgList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }


    /**
     * 根据参数同步运营公司
     *
     * @param companyIds 运营公司Id
     * @return bool
     */
    @Override
    public boolean syncOperatingCompanyArgs(Set<String> companyIds) {
        if (CollUtil.isEmpty(companyIds)){
            return true;
        }
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("HOI_Company")
                    .set("CompType", new String[]{"3000101", "3000102"})
                    .set("Company_Id", companyIds));
            List<Org> orgList = baseOrgService.list(new QueryWrapper<Org>().eq("id_source", "jdxt").
                    eq("org_type", "000300").in("org_id", companyIds));
            List<Org> operationOrgList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (Org org : orgList) {
                    if (entity.getStr("Company_Id").equals(org.getOrgId())) {
                        isExist = true;
                        // 存在则更新
                        updateOrgData(org, entity);
                        org.setUpdateTime(currentDateTime);
                        operationOrgList.add(org);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    Org org = new Org();
                    updateOrgData(org, entity);
                    org.setOrgId(entity.getStr("Company_Id"));
                    org.setCreateTime(currentDateTime);
                    org.setUpdateTime(currentDateTime);
                    org.setIdSource("jdxt");
                    operationOrgList.add(org);
                }
            }
            if (0 < operationOrgList.size()) {
                result = baseOrgService.saveOrUpdateBatch(operationOrgList);
                // 同步设施
                result = syncWaySectionArgs(companyIds, null);
            }
            // 同步运营路段
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 根据参数同步运营路段
     * @param orgIds    运营公司Id集合
     * @param waysectionIds 运营路段Id集合
     * @return
     */
    @Override
    public boolean syncWaySectionArgs(Set<String> orgIds, Set<String> waysectionIds) {
        if (CollUtil.isEmpty(orgIds) && CollUtil.isEmpty(waysectionIds)) {
            return true;
        }
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Waysection> operationWaysectionList = new ArrayList<Waysection>();
            Date currentDateTime = DateUtil.date();
            // 传递运营公司
            if (CollUtil.isNotEmpty(orgIds)) {
                for (String orgId : orgIds) {
                    List<Entity> entityList = db.query(" * from HOI_WaySection where OPCOMP_Id=? and WaySection_Name not like ?", orgId, "%监控中心%");
                    List<Waysection> waysectionList = baseWaysectionService.list(new QueryWrapper<Waysection>().eq("waysection_id_source", "jdxt").eq("manage_id", orgId));
                    for (Entity entity : entityList) {
                        boolean isExist = false;
                        for (Waysection waysection : waysectionList) {
                            if (entity.getStr("WaySection_Id").equals(waysection.getWaysectionId())) {
                                isExist = true;
                                // 存在则更新
                                updateWaysectionData(waysection, entity);
                                waysection.setUpdateTime(currentDateTime);
                                operationWaysectionList.add(waysection);
                                break;
                            }
                        }
                        if (!isExist) {
                            // 不存在则新增
                            Waysection waysection = new Waysection();
                            updateWaysectionData(waysection, entity);
                            waysection.setWaysectionId(entity.getStr("WaySection_Id"));
                            waysection.setCreateTime(currentDateTime);
                            waysection.setUpdateTime(currentDateTime);
                            waysection.setWaysectionIdSource("jdxt");
                            operationWaysectionList.add(waysection);
                        }
                    }
                }
            }
            // 直接传递路段
            if (CollUtil.isNotEmpty(waysectionIds)) {
                for (String waysectionId : waysectionIds) {
                    List<Entity> entityList = db.query("select * from HOI_WaySection where WaySection_Id=? and WaySection_Name not like ?", waysectionId, "%监控中心%");
                    List<Waysection> waysectionList = baseWaysectionService.list(new QueryWrapper<Waysection>()
                            .eq("waysection_id_source", "jdxt").eq("waysection_id", waysectionId));
                    for (Entity entity : entityList) {
                        boolean isExist = false;
                        for (Waysection waysection : waysectionList) {
                            if (entity.getStr("WaySection_Id").equals(waysection.getWaysectionId())) {
                                isExist = true;
                                // 存在则更新
                                updateWaysectionData(waysection, entity);
                                waysection.setUpdateTime(currentDateTime);
                                operationWaysectionList.add(waysection);
                                break;
                            }
                        }
                        if (!isExist) {
                            // 不存在则新增
                            Waysection waysection = new Waysection();
                            updateWaysectionData(waysection, entity);
                            waysection.setWaysectionId(entity.getStr("WaySection_Id"));
                            waysection.setCreateTime(currentDateTime);
                            waysection.setUpdateTime(currentDateTime);
                            waysection.setWaysectionIdSource("jdxt");
                            operationWaysectionList.add(waysection);
                        }
                    }
                }
            }
            if (0 < operationWaysectionList.size()) {
                result = baseWaysectionService.saveOrUpdateBatch(operationWaysectionList);
                // 同步该路段下的设施
                result = syncFacilitiesArgs(operationWaysectionList.stream().map(Waysection::getWaysectionId).collect(Collectors.toSet()), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 根据参数同步设施
     * @param waysectionIds 运营路段Id集合
     * @param facilitiesIds 设施id
     * @return
     */
    @Override
    public boolean syncFacilitiesArgs(Set<String> waysectionIds, Set<String> facilitiesIds) {
        if (CollUtil.isEmpty(waysectionIds) && CollUtil.isEmpty(facilitiesIds)) {
            return true;
        }
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Facilities> operationFacilitiesList = new ArrayList<Facilities>();
            Date currentDateTime = DateUtil.date();
            // 路段下的设施
            if (CollUtil.isNotEmpty(waysectionIds)){
                for (String waysectionId : waysectionIds) {
                    List<Entity> entityList = db.findAll(Entity.create().setTableName("HOI_GeographyInfo").set("WaySection_Id", waysectionId));
                    List<Facilities> facilitiesList = baseFacilitiesService
                            .list(new QueryWrapper<Facilities>()
                                    .eq("facilities_id_source", "jdxt")
                                    .eq("waysection_id", waysectionId));
                    for (Entity entity : entityList) {
                        boolean isExist = false;
                        for (Facilities facilities : facilitiesList) {
                            if (entity.getStr("GeographyInfo_Id").equals(facilities.getFacilitiesId())) {
                                isExist = true;
                                // 存在则更新
                                updateFacilitiesData(facilities, entity);
                                facilities.setUpdateTime(currentDateTime);
                                operationFacilitiesList.add(facilities);
                                break;
                            }
                        }
                        if (!isExist) {
                            // 不存在则新增
                            Facilities facilities = new Facilities();
                            updateFacilitiesData(facilities, entity);
                            facilities.setFacilitiesId(entity.getStr("GeographyInfo_Id"));
                            facilities.setCreateTime(currentDateTime);
                            facilities.setUpdateTime(currentDateTime);
                            facilities.setFacilitiesIdSource("jdxt");
                            operationFacilitiesList.add(facilities);
                        }
                    }
                }
            }
            // 直接传递设施
            if (CollUtil.isNotEmpty(facilitiesIds)){
                for (String facilitiesId : facilitiesIds) {
                    List<Entity> entityList = db.findAll(Entity.create().setTableName("HOI_GeographyInfo").set("GeographyInfo_Id", facilitiesId));
                    List<Facilities> facilitiesList = baseFacilitiesService
                            .list(new QueryWrapper<Facilities>()
                                    .eq("facilities_id_source", "jdxt")
                                    .eq("facilities_id", facilitiesId));
                    for (Entity entity : entityList) {
                        boolean isExist = false;
                        for (Facilities facilities : facilitiesList) {
                            if (entity.getStr("GeographyInfo_Id").equals(facilities.getFacilitiesId())) {
                                isExist = true;
                                // 存在则更新
                                updateFacilitiesData(facilities, entity);
                                facilities.setUpdateTime(currentDateTime);
                                operationFacilitiesList.add(facilities);
                                break;
                            }
                        }
                        if (!isExist) {
                            // 不存在则新增
                            Facilities facilities = new Facilities();
                            updateFacilitiesData(facilities, entity);
                            facilities.setFacilitiesId(entity.getStr("GeographyInfo_Id"));
                            facilities.setCreateTime(currentDateTime);
                            facilities.setUpdateTime(currentDateTime);
                            facilities.setFacilitiesIdSource("jdxt");
                            operationFacilitiesList.add(facilities);
                        }
                    }
                }
            }
            if (0 < operationFacilitiesList.size()) {
                result = baseFacilitiesService.saveOrUpdateBatch(operationFacilitiesList);
                // 同步设施下设备
                result = syncDeviceArgs(operationFacilitiesList.stream().map(Facilities::getFacilitiesId).collect(Collectors.toSet()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 根据参数同步设备
     *
     * @param facilitiesIds 设施Id集合
     * @return
     */
    private boolean syncDeviceArgs(Set<String> facilitiesIds) {
        if (CollUtil.isEmpty(facilitiesIds)){
            return true;
        }
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Facilities> facilitiesList = baseFacilitiesService.list(new QueryWrapper<Facilities>()
                    .eq("facilities_id_source", "jdxt")
                    .in(CollUtil.isNotEmpty(facilitiesIds),"facilities_id", facilitiesIds));
            List<Device> operationDeviceList = new ArrayList<Device>();
            Date currentDateTime = DateUtil.date();
            for (Facilities facilities : facilitiesList) {
                List<Entity> entityList = db.findAll(Entity.create().setTableName("HOP_DeviceRecord").set("GeographyInfo_Id", facilities.getFacilitiesId()));
                List<Device> deviceList = baseDeviceService.list(new QueryWrapper<Device>().eq("device_id_source", "jdxt").eq("facilities_id", facilities.getFacilitiesId()));
                for (Entity entity : entityList) {
                    boolean isExist = false;
                    for (Device device : deviceList) {
                        if (entity.getStr("DeviceRecord_Id").equals(device.getDeviceId())) {
                            isExist = true;
                            // 存在则更新
                            updateDeviceData(device, entity);
                            device.setUpdateTime(currentDateTime);
                            operationDeviceList.add(device);
                            break;
                        }
                    }
                    if (!isExist) {
                        // 不存在则新增
                        Device device = new Device();
                        updateDeviceData(device, entity);
                        device.setDeviceId(entity.getStr("DeviceRecord_Id"));
                        device.setCreateTime(currentDateTime);
                        device.setUpdateTime(currentDateTime);
                        device.setDeviceIdSource("jdxt");
                        operationDeviceList.add(device);
                    }
                }
            }
            if (0 < operationDeviceList.size()) {
                result = baseDeviceService.saveOrUpdateBatch(operationDeviceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }


    /**
     * 同步运营路段
     *
     * @return
     */
    @Override
    public boolean syncWaySection() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Org> orgList = baseOrgService.list(new QueryWrapper<Org>().eq("id_source", "jdxt").eq("org_type", "000300"));
            List<Waysection> operationWaysectionList = new ArrayList<Waysection>();
            Date currentDateTime = DateUtil.date();
            for (Org org : orgList) {
//                List<Entity> entityList =  db.findAll(Entity.create().setTableName("HOI_WaySection").set("OPCOMP_Id", org.getOrgId()));
                List<Entity> entityList = db.query("select * from HOI_WaySection where OPCOMP_Id=? and WaySection_Name not like ?", org.getOrgId(), "%监控中心%");
                List<Waysection> waysectionList = baseWaysectionService.list(new QueryWrapper<Waysection>().eq("waysection_id_source", "jdxt").eq("manage_id", org.getOrgId()));
                for (Entity entity : entityList) {
                    boolean isExist = false;
                    for (Waysection waysection : waysectionList) {
                        if (entity.getStr("WaySection_Id").equals(waysection.getWaysectionId())) {
                            isExist = true;
                            // 存在则更新
                            updateWaysectionData(waysection, entity);
                            waysection.setUpdateTime(currentDateTime);
                            operationWaysectionList.add(waysection);
                            break;
                        }
                    }
                    if (!isExist) {
                        // 不存在则新增
                        Waysection waysection = new Waysection();
                        updateWaysectionData(waysection, entity);
                        waysection.setWaysectionId(entity.getStr("WaySection_Id"));
                        waysection.setCreateTime(currentDateTime);
                        waysection.setUpdateTime(currentDateTime);
                        waysection.setWaysectionIdSource("jdxt");
                        operationWaysectionList.add(waysection);
                    }
                }
            }
            if (0 < operationWaysectionList.size()) {
                result = baseWaysectionService.saveOrUpdateBatch(operationWaysectionList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步设施
     *
     * @return
     */
    @Override
    public boolean syncFacilities() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
//            Db localDB = DbUtil.use((DataSource)dynamicRoutingDataSource.getDataSourceMap().get("default"));
            List<Waysection> waysectionList = baseWaysectionService.list(new QueryWrapper<Waysection>().eq("waysection_id_source", "jdxt"));
            List<Facilities> operationFacilitiesList = new ArrayList<Facilities>();
            Date currentDateTime = DateUtil.date();
            for (Waysection waysection : waysectionList) {
                List<Entity> entityList = db.findAll(Entity.create().setTableName("HOI_GeographyInfo").set("WaySection_Id", waysection.getWaysectionId()));
                List<Facilities> facilitiesList = baseFacilitiesService.list(new QueryWrapper<Facilities>().eq("facilities_id_source", "jdxt").eq("waysection_id", waysection.getWaysectionId()));
                for (Entity entity : entityList) {
                    boolean isExist = false;
                    for (Facilities facilities : facilitiesList) {
                        if (entity.getStr("GeographyInfo_Id").equals(facilities.getFacilitiesId())) {
                            isExist = true;
                            // 存在则更新
                            updateFacilitiesData(facilities, entity);
                            facilities.setUpdateTime(currentDateTime);
                            operationFacilitiesList.add(facilities);
                            break;
                        }
                    }
                    if (!isExist) {
                        // 不存在则新增
                        Facilities facilities = new Facilities();
                        updateFacilitiesData(facilities, entity);
                        facilities.setFacilitiesId(entity.getStr("GeographyInfo_Id"));
                        facilities.setCreateTime(currentDateTime);
                        facilities.setUpdateTime(currentDateTime);
                        facilities.setFacilitiesIdSource("jdxt");
                        operationFacilitiesList.add(facilities);
                    }
                }
            }
            if (0 < operationFacilitiesList.size()) {
                result = baseFacilitiesService.saveOrUpdateBatch(operationFacilitiesList);
                // 更新parent_id为本系统ID(保留自身系统id情况下开放)
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步设备
     *
     * @return
     */
    @Override
    public boolean syncDevice() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Facilities> facilitiesList = baseFacilitiesService.list(new QueryWrapper<Facilities>().eq("facilities_id_source", "jdxt"));
            List<Device> operationDeviceList = new ArrayList<Device>();
            Date currentDateTime = DateUtil.date();
            for (Facilities facilities : facilitiesList) {
                List<Entity> entityList = db.findAll(Entity.create().setTableName("HOP_DeviceRecord").set("GeographyInfo_Id", facilities.getFacilitiesId()));
                List<Device> deviceList = baseDeviceService.list(new QueryWrapper<Device>().eq("device_id_source", "jdxt").eq("facilities_id", facilities.getFacilitiesId()));
                for (Entity entity : entityList) {
                    boolean isExist = false;
                    for (Device device : deviceList) {
                        if (entity.getStr("DeviceRecord_Id").equals(device.getDeviceId())) {
                            isExist = true;
                            // 存在则更新
                            updateDeviceData(device, entity);
                            device.setUpdateTime(currentDateTime);
                            operationDeviceList.add(device);
                            break;
                        }
                    }
                    if (!isExist) {
                        // 不存在则新增
                        Device device = new Device();
                        updateDeviceData(device, entity);
                        device.setDeviceId(entity.getStr("DeviceRecord_Id"));
                        device.setCreateTime(currentDateTime);
                        device.setUpdateTime(currentDateTime);
                        device.setDeviceIdSource("jdxt");
                        operationDeviceList.add(device);
                    }
                }
            }
            if (0 < operationDeviceList.size()) {
                result = baseDeviceService.saveOrUpdateBatch(operationDeviceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步视频资源（腾路）
     *
     * @return
     */
    @Override
    public boolean syncVideoResourceTl() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_assets_camera_resource_tl"));
            List<CameraResourceTl> cameraResourceTlList = baseCameraResourceTlService.list();
            List<CameraResourceTl> operationCameraResourceTlList = new ArrayList<CameraResourceTl>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (CameraResourceTl cameraResourceTl : cameraResourceTlList) {
                    if (entity.getStr("camera_id").equals(cameraResourceTl.getCameraId())) {
                        isExist = true;
                        updateCameraResourceTlData(cameraResourceTl, entity);
                        cameraResourceTl.setUpdateTime(currentDateTime);
                        operationCameraResourceTlList.add(cameraResourceTl);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    CameraResourceTl cameraResourceTl = new CameraResourceTl();
                    updateCameraResourceTlData(cameraResourceTl, entity);
                    cameraResourceTl.setCameraId(entity.getStr("camera_id"));
                    cameraResourceTl.setCreateTime(currentDateTime);
                    cameraResourceTl.setUpdateTime(currentDateTime);

                    operationCameraResourceTlList.add(cameraResourceTl);
                }

            }
            if (0 < operationCameraResourceTlList.size()) {
                result = baseCameraResourceTlService.saveOrUpdateBatch(operationCameraResourceTlList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步视频资源（云台）
     *
     * @return
     */
    @Override
    public boolean syncVideoResourceYt() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_assets_camera_resource_yt"));
            List<CameraResourceYt> cameraResourceYtList = baseCameraResourceYtService.list();
            List<CameraResourceYt> operationCameraResourceYtList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (CameraResourceYt cameraResourceYt : cameraResourceYtList) {
                    if (entity.getStr("id").equals(cameraResourceYt.getId())) {
                        isExist = true;
                        updateCameraResourceYtData(cameraResourceYt, entity);
                        cameraResourceYt.setUpdateTime(currentDateTime);
                        operationCameraResourceYtList.add(cameraResourceYt);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    CameraResourceYt cameraResourceYt = new CameraResourceYt();
                    updateCameraResourceYtData(cameraResourceYt, entity);
                    cameraResourceYt.setId(entity.getStr("id"));
                    cameraResourceYt.setCreateTime(currentDateTime);
                    cameraResourceYt.setUpdateTime(currentDateTime);
                    operationCameraResourceYtList.add(cameraResourceYt);
                }

            }
            if (0 < operationCameraResourceYtList.size()) {
                result = baseCameraResourceYtService.saveOrUpdateBatch(operationCameraResourceYtList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    // 扩展相关：路段、收费站、服务区、桥梁、隧道、门架

    /**
     * 同步字典
     *
     * @return
     */
    @Override
    public boolean syncDict() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            // 一、同步字典目录
            List<Entity> entityList = db.findAll(Entity.create().setTableName("OPF_Sys_Dict").set("System_Id", "FD08D656-19D8-4CE5-AE7E-8A76F1F976A8"));
            List<Dict> dictList = baseDictService.list(new QueryWrapper<Dict>().eq("id_source", "jdxt").eq("node_type", "1"));
            List<Dict> operationDictList = new ArrayList<Dict>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (Dict dict : dictList) {
                    if (entity.getStr("Id").equals(dict.getDictId())) {
                        isExist = true;
                        // 存在则更新
                        dict.setCode(entity.getStr("Id"));
                        dict.setName(entity.getStr("Name"));
                        dict.setUpdateTime(currentDateTime);
                        operationDictList.add(dict);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    Dict dict = new Dict();
                    dict.setDictId(entity.getStr("Id"));
                    dict.setCode(entity.getStr("Id"));
                    dict.setName(entity.getStr("Name"));
                    dict.setNodeType(1);
                    dict.setDatastatusid(1);
                    dict.setCreateTime(currentDateTime);
                    dict.setUpdateTime(currentDateTime);
                    dict.setIdSource("jdxt");
                    dict.setIdOld(entity.getStr("Id"));
                    operationDictList.add(dict);
                }
            }
            if (0 < operationDictList.size()) {
                result = baseDictService.saveOrUpdateBatch(operationDictList);
            }
            // 如果少数据，则认为删除（本地字典目录datastatusid = 0）
            List<Dict> delDictList = new ArrayList<>();
            for (Dict dict : dictList) {
                boolean isExist = false;
                for (Entity entity : entityList) {
                    if (dict.getDictId().equals(entity.getStr("Id"))) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    dict.setDatastatusid(0);
                    delDictList.add(dict);
                }
            }
            if (0 < delDictList.size()) {
                baseDictService.updateBatchById(delDictList);
            }
            // 二、同步字典
            List<Dict> dicts = baseDictService.list(new QueryWrapper<Dict>().eq("id_source", "jdxt").eq("node_type", "1"));
            List<Dict> operationDictItemList = new ArrayList<>();
            for (Dict dict : dicts) {
                List<Entity> itemList = db.query("select * from OPF_Sys_Dict_Item where Dict_ID in (select ID from OPF_Sys_Dict where System_Id = 'FD08D656-19D8-4CE5-AE7E-8A76F1F976A8') and Dict_ID = ?", dict.getDictId());
                List<Dict> dictItems = baseDictService.list(new QueryWrapper<Dict>().eq("id_source", "jdxt").eq("parent_id", dict.getDictId()));
                for (Entity entity : itemList) {
                    boolean isExist = false;
                    for (Dict dictItem : dictItems) {
                        if (entity.getStr("Id").equals(dictItem.getDictId())) {
                            isExist = true;
                            // 存在则更新
                            dictItem.setCode(entity.getStr("Id"));
                            dictItem.setName(entity.getStr("Name"));
                            dictItem.setParentId(dict.getDictId());
                            dictItem.setUpdateTime(currentDateTime);
                            operationDictItemList.add(dictItem);
                            break;
                        }
                    }
                    if (!isExist) {
                        // 不存在则新增
                        Dict dictItem = new Dict();
                        dictItem.setDictId(entity.getStr("Id"));
                        dictItem.setCode(entity.getStr("Id"));
                        dictItem.setName(entity.getStr("Name"));
                        dictItem.setNodeType(2);
                        dictItem.setLevel(1);
                        dictItem.setDatastatusid(1);
                        dictItem.setParentId(dict.getDictId());
                        dictItem.setCreateTime(currentDateTime);
                        dictItem.setUpdateTime(currentDateTime);
                        dictItem.setIdSource("jdxt");
                        dictItem.setIdOld(entity.getStr("Id"));
                        operationDictItemList.add(dictItem);
                    }
                }
                // 如果少数据，则认为删除（本地字典目录datastatusid = 0）
                List<Dict> delDictItemList = new ArrayList<Dict>();
                for (Dict dictItem : dictItems) {
                    boolean isExist = false;
                    for (Entity entity : itemList) {
                        if (dictItem.getIdOld().equals(entity.getStr("Id"))) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        dictItem.setDatastatusid(0);
                        delDictItemList.add(dictItem);
                    }
                }
                if (0 < delDictItemList.size()) {
                    baseDictService.updateBatchById(delDictItemList);
                }
            }
            if (0 < operationDictItemList.size()) {
                result = baseDictService.saveOrUpdateBatch(operationDictItemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步字典（品牌）
     *
     * @return
     */
    @Override
    public boolean syncDictBrand() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("HOP_BrandInfo"));
            List<DictBrand> dictBrandList = baseDictBrandService.list(new QueryWrapper<DictBrand>().eq("id_source", "jdxt"));
            List<DictBrand> operationDictBrandList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (DictBrand dictBrand : dictBrandList) {
                    if (entity.getStr("BrandInfo_Id").equals(dictBrand.getBrandId())) {
                        isExist = true;
                        updateDictBrandData(dictBrand, entity);
                        dictBrand.setUpdateTime(currentDateTime);
                        operationDictBrandList.add(dictBrand);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    DictBrand dictBrand = new DictBrand();
                    updateDictBrandData(dictBrand, entity);
                    dictBrand.setBrandId(entity.getStr("BrandInfo_Id"));
                    dictBrand.setIdSource("jdxt");
                    dictBrand.setCreateTime(currentDateTime);
                    dictBrand.setUpdateTime(currentDateTime);
                    operationDictBrandList.add(dictBrand);
                }
            }
            if (0 < operationDictBrandList.size()) {
                result = baseDictBrandService.saveOrUpdateBatch(operationDictBrandList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步字典（设备）
     *
     * @return
     */
    @Override
    public boolean syncDictDevice() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("HOP_DeviceDict"));
            List<DictDevice> dictDevicedList = baseDictDeviceService.list(new QueryWrapper<DictDevice>().eq("id_source", "jdxt"));
            List<DictDevice> operationDictDeviceList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (DictDevice dictDevice : dictDevicedList) {
                    if (entity.getStr("DeviceDict_Id").equals(dictDevice.getDeviceDictId())) {
                        isExist = true;
                        updateDictDeviceData(dictDevice, entity);
                        dictDevice.setUpdateTime(currentDateTime);
                        operationDictDeviceList.add(dictDevice);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    DictDevice dictDevice = new DictDevice();
                    updateDictDeviceData(dictDevice, entity);
                    dictDevice.setDeviceDictId(entity.getStr("DeviceDict_Id"));
                    dictDevice.setIdSource("jdxt");
                    dictDevice.setCreateTime(currentDateTime);
                    dictDevice.setUpdateTime(currentDateTime);
                    operationDictDeviceList.add(dictDevice);
                }

            }
            if (0 < operationDictDeviceList.size()) {
                result = baseDictDeviceService.saveOrUpdateBatch(operationDictDeviceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步字典（系统）
     *
     * @return
     */
    @Override
    public boolean syncDictSystem() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("HOP_MachineSystem").set("Company_Id", "DCBDF020-AF3D-4B05-AF39-7501FDD37774"));
            List<DictSystem> dictSystemList = baseDictSystemService.list(new QueryWrapper<DictSystem>().eq("id_source", "jdxt"));
            List<DictSystem> operationDictSystemList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (DictSystem dictSystem : dictSystemList) {
                    if (entity.getStr("System_Id").equals(dictSystem.getSystemId())) {
                        isExist = true;
                        updateDictSystemData(dictSystem, entity);
                        dictSystem.setUpdateTime(currentDateTime);
                        operationDictSystemList.add(dictSystem);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    DictSystem dictSystem = new DictSystem();
                    updateDictSystemData(dictSystem, entity);
                    dictSystem.setSystemId(entity.getStr("System_Id"));
                    dictSystem.setIdSource("jdxt");
                    dictSystem.setCreateTime(currentDateTime);
                    dictSystem.setUpdateTime(currentDateTime);
                    operationDictSystemList.add(dictSystem);
                }
            }
            if (0 < operationDictSystemList.size()) {
                result = baseDictSystemService.saveOrUpdateBatch(operationDictSystemList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    // 扩展相关：路段、收费站、服务区、桥梁、隧道、门架

    /**
     * 同步路段
     *
     * @return
     */
    @Override
    public boolean syncRoute() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_assets_route"));
            List<Route> routeList = baseRouteService.list(new QueryWrapper<Route>().eq("id_source", "jdxt"));
            List<Route> operationRouteList = new ArrayList<>();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (Route route : routeList) {
                    if (entity.getStr("route_id").equals(route.getRouteId())) {
                        isExist = true;
                        updateRouteData(route, entity);
                        operationRouteList.add(route);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    Route route = new Route();
                    updateRouteData(route, entity);
                    route.setRouteId(entity.getStr("route_id"));
                    route.setIdSource("jdxt");
                    operationRouteList.add(route);
                }
            }
            if (0 < operationRouteList.size()) {
                result = baseRouteService.saveOrUpdateBatch(operationRouteList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步收费站
     *
     * @return
     */
    @Override
    public boolean syncTollStation() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_assets_facilities_toll_station"));
            List<FacilitiesTollStation> tollStationList = baseFacilitiesTollStationService.list(new QueryWrapper<FacilitiesTollStation>().eq("id_source", "jdxt"));
            List<FacilitiesTollStation> operationTollStationList = new ArrayList<>();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (FacilitiesTollStation tollStation : tollStationList) {
                    if (entity.getStr("toll_station_id").equals(tollStation.getTollStationId())) {
                        isExist = true;
                        updateTollStationData(tollStation, entity);
                        operationTollStationList.add(tollStation);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    FacilitiesTollStation tollStation = new FacilitiesTollStation();
                    updateTollStationData(tollStation, entity);
                    tollStation.setTollStationId(entity.getStr("toll_station_id"));
                    tollStation.setIdSource("jdxt");
                    operationTollStationList.add(tollStation);
                }
            }
            if (0 < operationTollStationList.size()) {
                result = baseFacilitiesTollStationService.saveOrUpdateBatch(operationTollStationList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步服务区
     *
     * @return
     */
    @Override
    public boolean syncServiceArea() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_service_area"));
            List<FacilitiesServiceArea> serviceAreaList = baseFacilitiesServiceAreaService.list(new QueryWrapper<FacilitiesServiceArea>().eq("id_source", "jdxt"));
            List<FacilitiesServiceArea> operationServiceAreaList = new ArrayList<>();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (FacilitiesServiceArea serviceArea : serviceAreaList) {
                    if (entity.getStr("id").equals(serviceArea.getServiceAreaId())) {
                        isExist = true;
                        updateServiceAreaData(serviceArea, entity);
                        operationServiceAreaList.add(serviceArea);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    FacilitiesServiceArea serviceArea = new FacilitiesServiceArea();
                    updateServiceAreaData(serviceArea, entity);
                    serviceArea.setServiceAreaId(entity.getStr("id"));
                    serviceArea.setIdSource("jdxt");
                    operationServiceAreaList.add(serviceArea);
                }
            }
            if (0 < operationServiceAreaList.size()) {
                result = baseFacilitiesServiceAreaService.saveOrUpdateBatch(operationServiceAreaList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步桥梁
     *
     * @return
     */
    @Override
    public boolean syncBridge() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_assets_facilities_bridge"));
            List<FacilitiesBridge> bridgeList = baseFacilitiesBridgeService.list(new QueryWrapper<FacilitiesBridge>().eq("id_source", "jdxt"));
            List<FacilitiesBridge> operationBridgeList = new ArrayList<>();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (FacilitiesBridge bridge : bridgeList) {
                    if (entity.getStr("bridge_id").equals(bridge.getBridgeId())) {
                        isExist = true;
                        updateBridgeData(bridge, entity);
                        operationBridgeList.add(bridge);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    FacilitiesBridge bridge = new FacilitiesBridge();
                    updateBridgeData(bridge, entity);
                    bridge.setBridgeId(entity.getStr("bridge_id"));
                    bridge.setIdSource("jdxt");
                    operationBridgeList.add(bridge);
                }
            }
            if (0 < operationBridgeList.size()) {
                result = baseFacilitiesBridgeService.saveOrUpdateBatch(operationBridgeList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步隧道
     *
     * @return
     */
    @Override
    public boolean syncTunnel() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_assets_facilities_tunnel"));
            List<FacilitiesTunnel> tunnelList = baseFacilitiesTunnelService.list(new QueryWrapper<FacilitiesTunnel>().eq("id_source", "jdxt"));
            List<FacilitiesTunnel> operationTunnelList = new ArrayList<>();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (FacilitiesTunnel tunnel : tunnelList) {
                    if (entity.getStr("tunnel_id").equals(tunnel.getTunnelId())) {
                        isExist = true;
                        updateTunnelData(tunnel, entity);
                        operationTunnelList.add(tunnel);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    FacilitiesTunnel tunnel = new FacilitiesTunnel();
                    updateTunnelData(tunnel, entity);
                    tunnel.setTunnelId(entity.getStr("tunnel_id"));
                    tunnel.setIdSource("jdxt");
                    operationTunnelList.add(tunnel);
                }
            }
            if (0 < operationTunnelList.size()) {
                result = baseFacilitiesTunnelService.saveOrUpdateBatch(operationTunnelList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步门架
     *
     * @return
     */
    @Override
    public boolean syncGantry() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_gantryinfo"));
            List<FacilitiesGantry> tunnelList = baseFacilitiesGantryService.list(new QueryWrapper<FacilitiesGantry>().eq("id_source", "jdxt"));
            List<FacilitiesGantry> operationGantryList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (FacilitiesGantry gantry : tunnelList) {
                    if (entity.getStr("gantryid").equals(gantry.getGantryId())) {
                        isExist = true;
                        updateGantryData(gantry, entity);
                        operationGantryList.add(gantry);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    FacilitiesGantry gantry = new FacilitiesGantry();
                    updateGantryData(gantry, entity);
                    gantry.setGantryId(entity.getStr("gantryid"));
                    gantry.setIdSource("jdxt");
                    operationGantryList.add(gantry);
                }
            }
            if (0 < operationGantryList.size()) {
                result = baseFacilitiesGantryService.saveOrUpdateBatch(operationGantryList);
            }
            // 更新门架设施ID
            List<String> sqlList = new ArrayList<>();
            String sql = "update t_bdmp_assets_facilities_gantry gantry left join t_bdmp_assets_facilities fac on gantry.gantry_id=fac.facilities_id_old set gantry.facilities_id=fac.facilities_id";
            sqlList.add(sql);
            if (0 < sqlList.size()) {
                sqlHelperService.exeSqlListLocalDB(sqlList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步收费站车道
     *
     * @return
     */
    @Override
    public boolean syncTollStationLane() {
        boolean result = true;
        try {
            Db db = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("jdxt"));
            List<Entity> entityList = db.findAll(Entity.create().setTableName("t_bdmp_station_lane"));
            List<FacilitiesTollStationLane> tollStationLaneList = baseFacilitiesTollStationLaneService.list(new QueryWrapper<FacilitiesTollStationLane>().eq("id_source", "jdxt"));
            List<FacilitiesTollStationLane> operationTollStationLaneList = new ArrayList<>();
            Date currentDateTime = DateUtil.date();
            for (Entity entity : entityList) {
                boolean isExist = false;
                for (FacilitiesTollStationLane tollStationLane : tollStationLaneList) {
                    if (entity.getStr("laneid").equals(tollStationLane.getLaneId())) {
                        isExist = true;
                        updateTollStationLaneData(tollStationLane, entity);
                        tollStationLane.setUpdateTime(currentDateTime);
                        operationTollStationLaneList.add(tollStationLane);
                        break;
                    }
                }
                if (!isExist) {
                    // 不存在则新增
                    FacilitiesTollStationLane tollStationLane = new FacilitiesTollStationLane();
                    updateTollStationLaneData(tollStationLane, entity);
                    tollStationLane.setIdSource("jdxt");
                    tollStationLane.setCreateTime(currentDateTime);
                    tollStationLane.setUpdateTime(currentDateTime);
                    operationTollStationLaneList.add(tollStationLane);
                }
            }
            if (0 < operationTollStationLaneList.size()) {
                result = baseFacilitiesTollStationLaneService.saveOrUpdateBatch(operationTollStationLaneList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }


    private Org updateOrgData(Org org, Entity entity) {
        org.setOrgCode(entity.getStr("Comp_Code"));
        org.setOrgName(entity.getStr("Comp_Name"));
        org.setOrgShortName(entity.getStr("Comp_Short_Name"));
        org.setParentId(entity.getStr("Parent_Id"));
        org.setDatastatusid(entity.getInt("Status"));
        if ("3000102".equals(entity.getStr("CompType"))) {
            org.setOrgType("000300");
        } else if ("3000101".equals(entity.getStr("CompType"))) {
            if ("N000001".equals(entity.getStr("Comp_Code"))) {
                org.setOrgType("000100");
            } else if ("N000002".equals(entity.getStr("Comp_Code"))) {
                org.setOrgType("000200");
            }
        }
        org.setIdOld(entity.getStr("Company_Id"));
        org.setNodeId(entity.getStr("nodeId"));
        return org;
    }

    private Waysection updateWaysectionData(Waysection waysection, Entity entity) {
        waysection.setWaysectionName(entity.getStr("WaySection_Name"));
        waysection.setWaysectionSName(entity.getStr("WaySection_SName"));
        waysection.setWaysectionCode(entity.getStr("Code"));
        waysection.setAreaNo(entity.getStr("AreaNo"));
        waysection.setWaynetId(entity.getStr("WaySection_No"));
        waysection.setOriWaynetId(entity.getStr("ORI_WaySection_No"));
        waysection.setMileage(entity.getBigDecimal("Maintenance_Mileage"));
        waysection.setManageId(entity.getStr("OPCOMP_Id"));
        waysection.setTollRegion(entity.getStr("Toll_Region"));
        waysection.setBarrio(entity.getStr("Barrio"));
        waysection.setManageType(entity.getStr("Manage_Type"));
        waysection.setRoadTeamInfo(entity.getStr("RoadTeam_Info"));
        waysection.setRescueTeamInfo(entity.getStr("RescueTeam_Info"));
        waysection.setIfWholeMonitor(entity.getInt("If_Whole_Monitor"));
        waysection.setIfCharge(entity.getInt("If_Charge"));
        waysection.setIsCollection(entity.getInt("IsCollection"));
        waysection.setIsSendDataBody(entity.getInt("IsSendDataBody"));
        waysection.setBeginStationId(entity.getStr("Begin_Station_Id"));
        waysection.setCentreStationId(entity.getStr("Centre_Station_Id"));
        waysection.setEndStationId(entity.getStr("End_Station_Id"));
        if (StrUtil.isNotBlank(entity.getStr("Begin_StakeNo")) && 0 != entity.getInt("Begin_StakeNo")) {
            waysection.setBeginStakeNo(entity.getStr("Begin_StakeNo"));
        } else {
            if (StrUtil.isNotBlank(entity.getStr("Begin_StakeNoK")) && 0 != entity.getInt("Begin_StakeNoK")) {
                if (StrUtil.isNotBlank(entity.getStr("Begin_StakeNoM")) && 0 != entity.getInt("Begin_StakeNoM")) {
                    waysection.setBeginStakeNo("k" + entity.getInt("Begin_StakeNoK") + "+" + entity.getInt("Begin_StakeNoM"));
                } else {
                    waysection.setBeginStakeNo("k" + entity.getInt("Begin_StakeNoK"));
                }
            }
        }
        waysection.setBeginStakeNoK(entity.getInt("Begin_StakeNoK"));
        waysection.setBeginStakeNoM(entity.getInt("Begin_StakeNoM"));
        if (StrUtil.isNotBlank(entity.getStr("End_StakeNo")) && 0 != entity.getInt("End_StakeNo")) {
            waysection.setEndStakeNo(entity.getStr("End_StakeNo"));
        } else {
            if (StrUtil.isNotBlank(entity.getStr("End_StakeNoK")) && 0 != entity.getInt("End_StakeNoK")) {
                if (StrUtil.isNotBlank(entity.getStr("End_StakeNoM")) && 0 != entity.getInt("End_StakeNoM")) {
                    waysection.setEndStakeNo("k" + entity.getInt("End_StakeNoK") + "+" + entity.getInt("End_StakeNoM"));
                } else {
                    waysection.setEndStakeNo("k" + entity.getInt("End_StakeNoK"));
                }
            }
        }
        waysection.setEndStakeNoK(entity.getInt("End_StakeNoK"));
        waysection.setEndStakeNoM(entity.getInt("End_StakeNoM"));
        waysection.setDatastatusid(entity.getInt("Status"));
        waysection.setWaysectionIdOld(entity.getStr("WaySection_Id"));
        waysection.setNodeId(entity.getStr("nodeId"));
        return waysection;
    }

    private Facilities updateFacilitiesData(Facilities facilities, Entity entity) {
        facilities.setFacilitiesName(entity.getStr("InfoName"));
        facilities.setFacilitiesCode(entity.getStr("InfoNo"));
        facilities.setFacilitiesType(entity.getStr("InfoType"));
        facilities.setWaysectionId(entity.getStr("WaySection_Id"));
        facilities.setParentId(entity.getStr("Parent_Id"));
        facilities.setLocation(entity.getStr("Location"));
        facilities.setLongitude(entity.getStr("Longitude"));
        facilities.setLatitude(entity.getStr("Latitude"));
        facilities.setBeginStakeNo(entity.getStr("Begin_StakeNo"));
        facilities.setBeginStakeNoDes(entity.getStr("Begin_StakeNoDes"));
        facilities.setBeginStakeNoK(entity.getInt("Begin_StakeNoK"));
        facilities.setBeginStakeNoM(entity.getInt("Begin_StakeNoM"));
        if (StrUtil.isBlank(entity.getStr("Center_StakeNo"))){
            if (StrUtil.isNotBlank(entity.getStr("Center_OffNoDes"))){
                if (null != entity.getInt("Center_OffNoK") && null != entity.getInt("Center_OffNoM")){
                    String centerOffNum = entity.getStr("Center_OffNoDes")+entity.getInt("Center_OffNoK")+ "+" + entity.getInt("Center_OffNoM");
                    facilities.setCenterOffNo(centerOffNum);
                }
            }
            else {
                if (null != entity.getInt("Center_OffNoK") && null != entity.getInt("Center_OffNoM")){
                    String centerStakeNo = "K"+entity.getInt("Center_OffNoK")+ "+" + entity.getInt("Center_OffNoM");
                    facilities.setCenterOffNo(centerStakeNo);
                }
            }
        }
        else {
            facilities.setCenterOffNo(entity.getStr("Center_StakeNo"));
        }
        facilities.setCenterOffNoDes(entity.getStr("Center_OffNoDes"));
        facilities.setCenterOffNoK(entity.getInt("Center_OffNoK"));
        facilities.setCenterOffNoM(entity.getInt("Center_OffNoM"));
        facilities.setEndStakeNo(entity.getStr("End_StakeNo"));
        facilities.setEndStakeNoDes(entity.getStr("End_StakeNoDes"));
        facilities.setEndStakeNoK(entity.getInt("End_StakeNoK"));
        facilities.setEndStakeNoM(entity.getInt("End_StakeNoM"));
        facilities.setLCenterOffsetValue(entity.getBigDecimal("RCenter_OffSetValue"));
        facilities.setRCenterOffsetValue(entity.getBigDecimal("RCenter_OffSetValue"));
        facilities.setStatus(entity.getInt("Status"));
        facilities.setCompanyId(entity.getStr("Company_Id"));
        facilities.setFacilitiesIdOld(entity.getStr("origin_id"));
        facilities.setIsMonitor(entity.getStr("IsMonitor"));
        facilities.setMapUrl(entity.getStr("MapUrl"));
        facilities.setIsOtnSite(entity.getStr("IsOTNSite"));
        facilities.setNationalSiteType(entity.getStr("NationalSiteType"));
        facilities.setProvincialSiteType(entity.getStr("ProvincialSiteType"));
        facilities.setSiteCategory(entity.getStr("SiteCategory"));
        facilities.setComputerRoomForm(entity.getStr("ComputerRoomForm"));
        facilities.setOtnRemark(entity.getStr("OTN_Remark"));
        facilities.setBaiduCode(entity.getStr("baidu_code"));
        return facilities;
    }

    private Device updateDeviceData(Device device, Entity entity) {
        String regex = "([A-Z]|[a-z]|[0-9]|-){0,}";
        device.setDeviceName(entity.getStr("DeviceName"));
        device.setDeviceAlias(entity.getStr("DeviceName"));
        device.setDeviceDictId(entity.getStr("Dict_Id"));
        device.setSortId(entity.getStr("Sort_Id"));
        device.setDeviceCode(entity.getStr("DeviceNo"));
        if (StrUtil.isNotBlank(entity.getStr("DeviceName"))) {
            if (-1 != entity.getStr("DeviceName").indexOf("摄像机") || -1 != entity.getStr("DeviceName").indexOf("车牌识别")) {
                device.setDeviceType("130100");
            } else if (-1 != entity.getStr("DeviceName").indexOf("情报板")) {
                device.setDeviceType("130200");
            } else if (-1 != entity.getStr("DeviceName").indexOf("车辆检测") || -1 != entity.getStr("DeviceName").indexOf("车检器")) {
                device.setDeviceType("130300");
            } else if (-1 != entity.getStr("DeviceName").indexOf("感温")) {
                device.setDeviceType("130400");
            } else if (-1 != entity.getStr("DeviceName").indexOf("脚踏报警")) {
                device.setDeviceType("130500");
            } else if (-1 != entity.getStr("DeviceName").indexOf("紧急电话")) {
                device.setDeviceType("130600");
            } else if (-1 != entity.getStr("DeviceName").indexOf("智能机箱")) {
                device.setDeviceType("130700");
            } else if (-1 != entity.getStr("DeviceName").indexOf("工控机")) {
                device.setDeviceType("130800");
            } else if (-1 != entity.getStr("DeviceName").indexOf("广播") || -1 != entity.getStr("DeviceName").indexOf("定向音")) {
                device.setDeviceType("130900");
            } else if (-1 != entity.getStr("DeviceName").indexOf("称重") || -1 != entity.getStr("DeviceName").indexOf("计重")
                    || -1 != entity.getStr("DeviceName").indexOf("称台")) {
                device.setDeviceType("131000");
            } else if (-1 != entity.getStr("DeviceName").indexOf("气象")) {
                device.setDeviceType("131100");
            } else if (-1 != entity.getStr("DeviceName").indexOf("基站")) {
                device.setDeviceType("131200");
            } else if (-1 != entity.getStr("DeviceName").indexOf("雷达") || -1 != entity.getStr("DeviceName").indexOf("雷视一体机")) {
                device.setDeviceType("131300");
            } else if (-1 != entity.getStr("DeviceName").indexOf("光栅阵列")) {
                device.setDeviceType("131400");
            } else if (-1 != entity.getStr("DeviceName").indexOf("路灯")) {
                device.setDeviceType("131500");
            } else if (-1 != entity.getStr("DeviceName").indexOf("工作站")) {
                device.setDeviceType("131800");
            } else if (-1 != entity.getStr("DeviceName").indexOf("服务器")) {
                device.setDeviceType("131900");
            } else if (-1 != entity.getStr("DeviceName").indexOf("指示器")) {
                device.setDeviceType("132000");
            } else if (-1 != entity.getStr("DeviceName").indexOf("V2X-RSU")) {
                device.setDeviceType("132100");
            }
            else if (-1 != entity.getStr("DeviceName").indexOf("天线")) {
                device.setDeviceType("132200");
            }
        }
        device.setLocation(entity.getStr("Location"));
        device.setDirection(entity.getStr("Direction"));
        // 转换设备所属系统类型（养护存多个字段、本系统只保留最后一级字段）
        if (StrUtil.isNotBlank(entity.getStr("SubSubSystem_Id"))) {
            if (entity.getStr("SubSubSystem_Id").matches(regex)) {
                device.setSystemId(entity.getStr("SubSubSystem_Id"));
            } else {
                device.setSystemId(entity.getStr("SubSubSystemName"));
            }
        } else if (StrUtil.isNotBlank(entity.getStr("SubSystem_Id"))) {
            device.setSystemId(entity.getStr("SubSystem_Id"));
        } else if (StrUtil.isNotBlank(entity.getStr("System_Id"))) {
            device.setSystemId(entity.getStr("System_Id"));
        }
        device.setFacilitiesId(entity.getStr("GeographyInfo_Id"));
        device.setWaysectionId(entity.getStr("WaySection_Id"));
        device.setIp(entity.getStr("IP"));
        device.setLongitude(entity.getStr("Longitude"));
        device.setLatitude(entity.getStr("Latitude"));
        device.setBeginStakeNo(entity.getStr("Begin_StakeNo"));
        device.setBeginStakeNoDes(entity.getStr("Begin_StakeNoDes"));
        device.setBeginStakeNoK(entity.getInt("Begin_StakeNoK"));
        device.setBeginStakeNoM(entity.getInt("Begin_StakeNoM"));
        device.setEndStakeNo(entity.getStr("End_StakeNo"));
        device.setEndStakeNoDes(entity.getStr("End_StakeNoDes"));
        device.setEndStakeNoK(entity.getInt("End_StakeNoK"));
        device.setEndStakeNoM(entity.getInt("End_StakeNoM"));
        if (StrUtil.isBlank(entity.getStr("Center_OffNo"))){
            if (StrUtil.isNotBlank(entity.getStr("Center_OffNoDes"))){
                if (null != entity.getInt("Center_OffNoK") && null != entity.getInt("Center_OffNoM")){
                    String centerOffNum = entity.getStr("Center_OffNoDes")+entity.getInt("Center_OffNoK")+ "+" + entity.getInt("Center_OffNoM");
                    device.setCenterOffNo(centerOffNum);
                }
            }
            else {
                if (null != entity.getInt("Center_OffNoK") && null != entity.getInt("Center_OffNoM")){
                    String centerOffNum = "K"+entity.getInt("Center_OffNoK")+ "+" + entity.getInt("Center_OffNoM");
                    device.setCenterOffNo(centerOffNum);
                }
            }
        }
        else {
            device.setCenterOffNo(entity.getStr("Center_OffNo"));
        }

        device.setCenterOffNoDes(entity.getStr("Center_OffNoDes"));
        device.setCenterOffNoK(entity.getInt("Center_OffNoK"));
        device.setCenterOffNoM(entity.getInt("Center_OffNoM"));
        device.setLCenterOffsetValue(entity.getBigDecimal("LCenter_OffSetValue"));
        device.setRCenterOffsetValue(entity.getBigDecimal("RCenter_OffSetValue"));
        device.setQrCodeUrl(entity.getStr("QRcodeUrl"));
        device.setDeviceModel(entity.getStr("SpecModel"));
        if (StrUtil.isNotBlank(entity.getStr("SeriaNumber")) ) {
            device.setSeriaNumber(entity.getStr("SeriaNumber"));
        }

        device.setSpecifications(entity.getStr("Specifications"));
        device.setVariety(entity.getStr("Variety"));
        device.setWeight(entity.getBigDecimal("Weight"));
        device.setUnit(entity.getStr("Unit"));
        device.setBrandId(entity.getStr("BrandInfo_Id"));
        device.setMaterial(entity.getStr("Material"));
        device.setGpd(entity.getStr("gpd"));
        device.setOpticalCable(entity.getStr("OpticalCable"));
        device.setLayoutModel(entity.getStr("LayoutModel"));
        device.setServiceYear(entity.getStr("ServiceYear"));
        device.setPropertyRightUnitId(entity.getStr("PropertyRightUnit_Id"));
        device.setManageUnitId(entity.getStr("ManageUnit_Id"));
        device.setManageDepartmentId(entity.getStr("ManageDepartment_Id"));
        device.setZrDepartmentId(entity.getStr("ZRDepartmentId"));
        device.setUseDepartmentId(entity.getStr("UseDepartment_Id"));
        device.setZrUserName(entity.getStr("ZRName"));
        device.setUseUserName(entity.getStr("UseName"));
        device.setAssetsNo(entity.getStr("AssetsNo"));
        device.setAssetsSource(entity.getStr("AssetsSource"));
        device.setMaintainNo(entity.getStr("MaintainNo"));
        device.setManageStatus(entity.getStr("ManageStatus"));
        device.setOldManageStatus(entity.getStr("OldManageStatus"));
        device.setWorkStatus(entity.getStr("WorkStatus"));
        device.setOldWorkStatus(entity.getStr("OldWorkStatus"));
        if (StrUtil.isNotBlank(entity.getStr("RecordDate"))) {
            device.setRecordDate(DateUtil.parse(entity.getStr("RecordDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StrUtil.isNotBlank(entity.getStr("StartUseDate"))) {
            device.setStartUseDate(DateUtil.parse(entity.getStr("StartUseDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StrUtil.isNotBlank(entity.getStr("InstallDate"))) {
            device.setInstallDate(DateUtil.parse(entity.getStr("InstallDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        device.setStep(entity.getInt("Step"));
        device.setUseYear(entity.getStr("UseYear"));
        device.setSupplierId(entity.getStr("Supplier_Id"));
        device.setSupplier(entity.getStr("Supplier"));
        device.setManufacturerId(entity.getStr("Manufacturer_Id"));
        device.setManufacturer(entity.getStr("Manufacturer"));
        device.setMadeCountry(entity.getStr("MadeCountry"));
        device.setFactoryNumber(entity.getStr("FactoryNumber"));
        device.setFactoryNo(entity.getStr("FactoryNo"));
        if (StrUtil.isNotBlank(entity.getStr("FactoryDate"))) {
            device.setFactoryDate(DateUtil.parse(entity.getStr("FactoryDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StrUtil.isNotBlank(entity.getStr("EffectiveStartDate"))) {
            device.setEffectiveStartDate(DateUtil.parse(entity.getStr("EffectiveStartDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        if (StrUtil.isNotBlank(entity.getStr("EffectiveEndDate"))) {
            device.setEffectiveEndDate(DateUtil.parse(entity.getStr("EffectiveEndDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        device.setDrawingNumber(entity.getStr("DrawingNumber"));
        device.setOperatingSystem(entity.getStr("OperatingSystem"));
        device.setDatabasess(entity.getStr("Database"));
        device.setIsNewObj(entity.getInt("IsNewObj"));
        device.setPurchaseDeptName(entity.getStr("PurchaseDept"));
        device.setPurchaseUserName(entity.getStr("PurchasePeople"));
        if (StrUtil.isNotBlank(entity.getStr("BuyDate"))) {
            device.setBuyDate(DateUtil.parse(entity.getStr("BuyDate"), "yyyy-MM-dd HH:mm:ss"));
        }
        device.setBuyPrice(entity.getBigDecimal("BuyPrice"));
        device.setCheckDate(entity.getDate("CheckDate"));
        device.setInvoiceNo(entity.getStr("InvoiceNo"));
        device.setSupplierContact(entity.getStr("SupplierContact"));
        device.setSupplierContactNo(entity.getStr("SupplierContactNo"));
        device.setCheckSituation(entity.getStr("CheckSituation"));
        device.setExistSpare(entity.getInt("ExistSpare"));
        device.setContractNo(entity.getStr("ContractNo"));
        device.setDatastatusid(entity.getInt("Status"));
        device.setInGatewayAddr(entity.getStr("gateway"));
        device.setSubnetMask(entity.getStr("netmask"));
        device.setPort(entity.getStr("port"));
        device.setDeviceIdOld(entity.getStr("OldEquipmentCode"));
        device.setDisplayRatio(entity.getStr("Resolution"));
        String color = entity.getStr("Color");
        Integer colorCode = null;
        if (StrUtil.isNotBlank(color)){
            if ("双基色".equals(color)){
                colorCode = 1;
            }
            if ("全彩".equals(color)){
                colorCode = 2;
            }
        }
        device.setDisplayColor(colorCode);
        if (StrUtil.isNotBlank(entity.getStr("laneNum"))){
            int laneNum = Integer.parseInt(entity.getStr("laneNum"));
            device.setLaneNum(laneNum);
        }
        else {
            device.setLaneNum(null);
        }
        device.setRemark(entity.getStr("Remark"));
        return device;
    }

    private CameraResourceTl updateCameraResourceTlData(CameraResourceTl cameraResourceTl, Entity entity) {
        cameraResourceTl.setCameraName(entity.getStr("camera_name"));
        cameraResourceTl.setDeviceType(entity.getInt("device_type"));
        cameraResourceTl.setCameraIp(entity.getStr("camera_ip"));
        cameraResourceTl.setLongitude(entity.getStr("longitude"));
        cameraResourceTl.setLatitude(entity.getStr("latitude"));
        cameraResourceTl.setLongitude84(entity.getStr("longitude_84"));
        cameraResourceTl.setLatitude84(entity.getStr("latitude_84"));
        cameraResourceTl.setSort(entity.getLong("sort"));
        cameraResourceTl.setDatastatusid(entity.getInt("datastatusid"));
        cameraResourceTl.setCreateBy(entity.getStr("create_by"));
        cameraResourceTl.setUpdateBy(entity.getStr("update_by"));
        cameraResourceTl.setWayId(entity.getStr("way_id"));
        cameraResourceTl.setWayName(entity.getStr("way_name"));
        cameraResourceTl.setGeographyinfoId(entity.getStr("geographyinfo_id"));
        cameraResourceTl.setLocation(entity.getStr("location"));
        cameraResourceTl.setInfoName(entity.getStr("info_name"));
        cameraResourceTl.setOnlineStatus(entity.getStr("online_status"));
        cameraResourceTl.setUuid(entity.getStr("uuid"));
        return cameraResourceTl;
    }

    private CameraResourceYt updateCameraResourceYtData(CameraResourceYt cameraResourceYt, Entity entity) {
        cameraResourceYt.setName(entity.getStr("name"));
        cameraResourceYt.setParentId(entity.getStr("parent_id"));
        cameraResourceYt.setType(entity.getInt("type"));
        cameraResourceYt.setCarSum(entity.getInt("car_sum"));
        cameraResourceYt.setCameraLevel(entity.getStr("camera_level"));
        cameraResourceYt.setStatus(entity.getInt("status"));
        cameraResourceYt.setHasPtz(entity.getInt("has_ptz"));
        cameraResourceYt.setCoordX(entity.getStr("coord_x"));
        cameraResourceYt.setCoordY(entity.getStr("coord_y"));
        cameraResourceYt.setWayId(entity.getStr("way_id"));
        cameraResourceYt.setWayName(entity.getStr("way_name"));
        cameraResourceYt.setGeographyinfoId(entity.getStr("geographyinfo_id"));
        cameraResourceYt.setLocation(entity.getStr("location"));
        cameraResourceYt.setSort(entity.getLong("sort"));
        cameraResourceYt.setDatastatusid(entity.getInt("datastatusid"));
        cameraResourceYt.setCreateBy(entity.getStr("create_by"));
        cameraResourceYt.setUpdateBy(entity.getStr("update_by"));
        return cameraResourceYt;
    }

    private DictBrand updateDictBrandData(DictBrand dictBrand, Entity entity) {
        dictBrand.setBrandNo(entity.getStr("Brand_No"));
        dictBrand.setBrandName(entity.getStr("Brand_Name"));
        dictBrand.setDatastatusid(entity.getInt("Status"));
        dictBrand.setCreateBy(entity.getStr("Creator"));
        dictBrand.setUpdateBy(entity.getStr("Modifier"));
        dictBrand.setIdOld(entity.getStr("BrandInfo_Id"));
        return dictBrand;
    }

    private DictDevice updateDictDeviceData(DictDevice dictDevice, Entity entity) {
        dictDevice.setSortId(entity.getStr("Sort_Id"));
        dictDevice.setPDeviceDictId(entity.getStr("PDeviceDict_Id"));
        dictDevice.setCode(entity.getStr("Code"));
        dictDevice.setName(entity.getStr("Name"));
        dictDevice.setAliasName(entity.getStr("Alias"));
        dictDevice.setVariety(entity.getStr("Variety"));
        dictDevice.setUnit(entity.getStr("Unit"));
        dictDevice.setSummary(entity.getStr("Summary"));
        dictDevice.setDatastatusid(entity.getInt("Status"));
        dictDevice.setCreateBy(entity.getStr("Creator"));
        dictDevice.setUpdateBy(entity.getStr("Modifier"));
        dictDevice.setIdOld(entity.getStr("DeviceDict_Id"));
        return dictDevice;
    }

    private DictSystem updateDictSystemData(DictSystem dictSystem, Entity entity) {
        dictSystem.setSystemNo(entity.getStr("SystemNo"));
        dictSystem.setSystemSerial(entity.getStr("Serial"));
        dictSystem.setSystemName(entity.getStr("SystemName"));
        dictSystem.setSystemType(entity.getStr("SystemType"));
        dictSystem.setParentId(entity.getStr("PSystem_Id"));
        dictSystem.setDatastatusid(entity.getInt("Status"));
        dictSystem.setCreateBy(entity.getStr("Creator"));
        dictSystem.setUpdateBy(entity.getStr("Modifier"));
        dictSystem.setIdOld(entity.getStr("System_Id"));
        return dictSystem;
    }

    private Route updateRouteData(Route route, Entity entity) {
        route.setManageCode(entity.getStr("manage_code"));
        route.setManageName(entity.getStr("manage_name"));
        route.setRouteCode(entity.getStr("route_code"));
        route.setBarrioCode(entity.getStr("barrio_code"));
        route.setBarrioName(entity.getStr("barrio_name"));
        route.setRouteSName(entity.getStr("route_s_name"));
        route.setFillUnit(entity.getStr("fill_unit"));
        route.setRouteName(entity.getStr("route_name"));
        route.setRouteNatureCode(entity.getStr("route_nature_code"));
        route.setRouteNatureName(entity.getStr("route_nature_name"));
        route.setMaintenanceUnit(entity.getStr("maintenance_unit"));
        route.setMaintenanceNatureCode(entity.getStr("maintenance_nature_code"));
        route.setMaintenanceNatureName(entity.getStr("maintenance_nature_name"));
        route.setAcceptanceDate(entity.getStr("acceptance_date"));
        route.setToDate(entity.getStr("to_date"));
        route.setRouteUseCode(entity.getStr("route_use_code"));
        route.setRouteUseName(entity.getStr("route_use_name"));
        route.setRouteAdminCode(entity.getStr("route_admin_code"));
        route.setRemark(entity.getStr("remark"));
        route.setStartRouteStake(entity.getStr("start_route_stake"));
        route.setStartRouteName(entity.getStr("start_route_name"));
        route.setStartIsBoundaryPointCode(entity.getStr("start_is_boundary_point_code"));
        route.setStartIsBoundaryPointName(entity.getStr("start_is_boundary_point_name"));
        route.setStartBoundaryPointTypeCode(entity.getStr("start_boundary_point_type_code"));
        route.setStartBoundaryPointTypeName(entity.getStr("start_boundary_point_type_name"));
        route.setStartLongitude(entity.getStr("start_longitude"));
        route.setStartLatitude(entity.getStr("start_latitude"));
        route.setEndRouteStake(entity.getStr("end_route_stake"));
        route.setEndRouteName(entity.getStr("end_route_name"));
        route.setEndIsBoundaryPointCode(entity.getStr("end_is_boundary_point_code"));
        route.setEndIsBoundaryPointName(entity.getStr("end_is_boundary_point_name"));
        route.setEndBoundaryPointTypeCode(entity.getStr("end_boundary_point_type_code"));
        route.setEndBoundaryPointTypeName(entity.getStr("end_boundary_point_type_name"));
        route.setEndLongitude(entity.getStr("end_longitude"));
        route.setEndLatitude(entity.getStr("end_latitude"));
        route.setBreakChainStake(entity.getStr("break_chain_stake"));
        route.setBreakChainDistance(entity.getStr("break_chain_distance"));
        route.setOriginalRouteCode(entity.getStr("original_route_code"));
        route.setRouteAdjustCode(entity.getStr("route_adjust_code"));
        route.setRouteAdjustName(entity.getStr("route_adjust_name"));
        route.setOriginalRouteSName(entity.getStr("original_route_s_name"));
        route.setOriginalStartStake(entity.getStr("original_start_stake"));
        route.setOriginalEndStake(entity.getStr("original_end_stake"));
        route.setOperatingWaysectionId(entity.getStr("way_id"));
        route.setMaintenanceMileage(entity.getBigDecimal("maintenance_mileage"));
        route.setIdOld(entity.getStr("route_id"));
        return route;
    }

    private FacilitiesTollStation updateTollStationData(FacilitiesTollStation tollStation, Entity entity) {
        tollStation.setManageCode(entity.getStr("manage_code"));
        tollStation.setManageName(entity.getStr("manage_name"));
        tollStation.setRouteCode(entity.getStr("route_code"));
        tollStation.setTollStationNo(entity.getStr("toll_station_no"));
        tollStation.setRouteSName(entity.getStr("route_s_name"));
        tollStation.setTollStationName(entity.getStr("toll_station_name"));
        tollStation.setTollStationStake(entity.getStr("toll_station_stake"));
        tollStation.setRoadStartStake(entity.getStr("road_start_stake"));
        tollStation.setRoadEndStake(entity.getStr("road_end_stake"));
        tollStation.setRoadStartPlace(entity.getStr("road_start_place"));
        tollStation.setRoadEndPlace(entity.getStr("road_end_place"));
        tollStation.setAdminRegionCode(entity.getStr("admin_region_code"));
        tollStation.setAdminRegionName(entity.getStr("admin_region_name"));
        tollStation.setTollDirectionCode(entity.getStr("toll_direction_code"));
        tollStation.setTollDirectionName(entity.getStr("toll_direction_name"));
        tollStation.setTollNatureCode(entity.getStr("toll_nature_code"));
        tollStation.setTollNatureName(entity.getStr("toll_nature_name"));
        tollStation.setTollStationTypeCode(entity.getStr("toll_station_type_code"));
        tollStation.setTollStationTypeName(entity.getStr("toll_station_type_name"));
        tollStation.setTollStationPlaceCode(entity.getStr("toll_station_place_code"));
        tollStation.setTollStationPlaceName(entity.getStr("toll_station_place_name"));
        tollStation.setTollMileage(entity.getStr("toll_mileage"));
        tollStation.setBridgeTunnelMeter(entity.getStr("bridge_tunnel_meter"));
        tollStation.setEntranceDrivewayNum(entity.getStr("entrance_driveway_num"));
        tollStation.setEntranceDrivewayEtcNum(entity.getStr("entrance_driveway_etc_num"));
        tollStation.setExitDrivewayNum(entity.getStr("exit_driveway_num"));
        tollStation.setExitDrivewayEtcNum(entity.getStr("exit_driveway_etc_num"));
        tollStation.setApprovalTollStartDate(entity.getStr("approval_toll_start_date"));
        tollStation.setApprovalTollYearLimit(entity.getStr("approval_toll_year_limit"));
        tollStation.setSupervisePhone(entity.getStr("supervise_phone"));
        tollStation.setPropertyRightUnitName(entity.getStr("property_right_unit_name"));
        tollStation.setPropertyRightUnitCode(entity.getStr("property_right_unit_code"));
        tollStation.setBuildDate(entity.getStr("build_date"));
        tollStation.setLongitude(entity.getStr("longitude"));
        tollStation.setLatitude(entity.getStr("latitude"));
        tollStation.setRemark(entity.getStr("remark"));
        tollStation.setIdCode(entity.getStr("id_code"));
        tollStation.setOperatingWaysectionId(entity.getStr("way_id"));
        tollStation.setFacilitiesId(entity.getStr("geographyinfo_id"));
        tollStation.setLocationDesc(entity.getStr("location_desc"));
        tollStation.setStationHexId(entity.getStr("stationHexID"));
        tollStation.setStationProvinceId(entity.getStr("stationProvinceid"));
        tollStation.setStationCountryId(entity.getStr("stationCountryid"));
        tollStation.setIdOld(entity.getStr("toll_station_id"));
        return tollStation;
    }

    private FacilitiesServiceArea updateServiceAreaData(FacilitiesServiceArea serviceArea, Entity entity) {
        serviceArea.setProvince(entity.getStr("province"));
        serviceArea.setCompanyId(entity.getStr("company_id"));
        serviceArea.setServerId(entity.getStr("server_id"));
        serviceArea.setBelongTo(entity.getStr("belong_to"));
        serviceArea.setHighspeedName(entity.getStr("highspeed_name"));
        serviceArea.setHighspeedNo(entity.getStr("highspeed_no"));
        serviceArea.setRoadType(entity.getStr("road_type"));
        serviceArea.setRoadPart(entity.getStr("road_part"));
        serviceArea.setRoadNo(entity.getStr("road_no"));
        serviceArea.setDoneBuiltDate(entity.getStr("done_built_date"));
        serviceArea.setOpenDate(entity.getStr("open_date"));
        serviceArea.setType(entity.getStr("type"));
        serviceArea.setMobile(entity.getStr("mobile"));
        serviceArea.setManagementModel(entity.getStr("management_model"));
        serviceArea.setDirection(entity.getStr("direction"));
        serviceArea.setDirectionName(entity.getStr("direction_name"));
        serviceArea.setIntroduction(entity.getStr("introduction"));
        serviceArea.setLongitude(entity.getStr("longitude"));
        serviceArea.setLatitude(entity.getStr("latitude"));
        serviceArea.setOwnerManagerCount(entity.getStr("owner_manager_count"));
        serviceArea.setManagementManagerCount(entity.getStr("management_manager_count"));
        serviceArea.setManagementServerCount(entity.getStr("management_server_count"));
        serviceArea.setInfoRunCount(entity.getStr("info_run_count"));
        serviceArea.setDailyWorkerCount(entity.getStr("daily_worker_count"));
        serviceArea.setAreaCovered(entity.getStr("area_covered"));
        serviceArea.setBuildingCovered(entity.getStr("building_covered"));
        serviceArea.setEatingCovered(entity.getStr("eating_covered"));
        serviceArea.setBussinessCovered(entity.getStr("bussiness_covered"));
        serviceArea.setParkCovered(entity.getStr("park_covered"));
        serviceArea.setOilCovered(entity.getStr("oil_covered"));
        serviceArea.setChargeCovered(entity.getStr("charge_covered"));
        serviceArea.setPlantCovered(entity.getStr("plant_covered"));
        serviceArea.setDailyMaleToiletLast(entity.getStr("daily_male_toilet_last"));
        serviceArea.setDailyFemaleToiletLast(entity.getStr("daily_female_toilet_last"));
        serviceArea.setDailyGoodCarFlowLast(entity.getStr("daily_good_car_flow_last"));
        serviceArea.setDailyCustomerCarFlowLast(entity.getStr("daily_customer_car_flow_last"));
        serviceArea.setDailyCustomerFlowLast(entity.getStr("daily_customer_flow_last"));
        serviceArea.setDailyDriveInLast(entity.getStr("daily_drive_in_last"));
        serviceArea.setDailyMaleToiletThis(entity.getStr("daily_male_toilet_this"));
        serviceArea.setDailyFemaleToiletThis(entity.getStr("daily_female_toilet_this"));
        serviceArea.setDailyGoodCarFlowThis(entity.getStr("daily_good_car_flow_this"));
        serviceArea.setDailyCustomerCarFlowThis(entity.getStr("daily_customer_car_flow_this"));
        serviceArea.setDailyCustomerFlowThis(entity.getStr("daily_customer_flow_this"));
        serviceArea.setDailyDriveInThis(entity.getStr("daily_drive_in_this"));
        serviceArea.setBigGoodParkCount(entity.getStr("big_good_park_count"));
        serviceArea.setNormalGoodParkCount(entity.getStr("normal_good_park_count"));
        serviceArea.setBigCustomerParkCount(entity.getStr("big_customer_park_count"));
        serviceArea.setSmallCustomerParkCount(entity.getStr("small_customer_park_count"));
        serviceArea.setDangerousParkCount(entity.getStr("dangerous_park_count"));
        serviceArea.setAnimalParkCount(entity.getStr("animal_park_count"));
        serviceArea.setFemaleParkCount(entity.getStr("female_park_count"));
        serviceArea.setEasyParkCount(entity.getStr("easy_park_count"));
        serviceArea.setIsSetCarstopStation(entity.getStr("is_set_carstop_station"));
        serviceArea.setGasCompany(entity.getStr("gas_company"));
        serviceArea.setGasGunCount(entity.getStr("gas_gun_count"));
        serviceArea.setOilGunCount(entity.getStr("oil_gun_count"));
        serviceArea.setChargeCompany(entity.getStr("charge_company"));
        serviceArea.setChargeCount(entity.getStr("charge_count"));
        serviceArea.setMalePeeCount(entity.getStr("male_pee_count"));
        serviceArea.setMaleSquatPooCount(entity.getStr("male_squat_poo_count"));
        serviceArea.setMaleSitPooCount(entity.getStr("male_sit_poo_count"));
        serviceArea.setFemaleSquatPooCount(entity.getStr("female_squat_poo_count"));
        serviceArea.setFemaleSitPooCount(entity.getStr("female_sit_poo_count"));
        serviceArea.setThirdToiletCount(entity.getStr("third_toilet_count"));
        serviceArea.setThirdToiletPooCount(entity.getStr("third_toilet_poo_count"));
        serviceArea.setEasyToiletCount(entity.getStr("easy_toilet_count"));
        serviceArea.setEasyToiletPooCount(entity.getStr("easy_toilet_poo_count"));
        serviceArea.setIsUseTogether(entity.getStr("is_use_together"));
        serviceArea.setHasRestaurant(entity.getStr("has_restaurant"));
        serviceArea.setRestaurantName(entity.getStr("restaurant_name"));
        serviceArea.setRestaurantType(entity.getStr("restaurant_type"));
        serviceArea.setHasBussiness(entity.getStr("has_bussiness"));
        serviceArea.setBussinessName(entity.getStr("bussiness_name"));
        serviceArea.setHasGuestRoom(entity.getStr("has_guest_room"));
        serviceArea.setBedCount(entity.getStr("bed_count"));
        serviceArea.setSingleRoomCount(entity.getStr("single_room_count"));
        serviceArea.setDoubleRoomCount(entity.getStr("double_room_count"));
        serviceArea.setHasShower(entity.getStr("has_shower"));
        serviceArea.setShowerCount(entity.getStr("shower_count"));
        serviceArea.setMaleShowerCount(entity.getStr("male_shower_count"));
        serviceArea.setFemaleShowerCount(entity.getStr("female_shower_count"));
        serviceArea.setIsDistinguish(entity.getStr("is_distinguish"));
        serviceArea.setHasCarRepair(entity.getStr("has_car_repair"));
        serviceArea.setCarRepairMobile(entity.getStr("car_repair_mobile"));
        serviceArea.setBookOnline(entity.getStr("book_online"));
        serviceArea.setPayWay(entity.getStr("pay_way"));
        serviceArea.setHasMomBabyRoom(entity.getStr("has_mom_baby_room"));
        serviceArea.setMomBabyRoomCount(entity.getStr("mom_baby_room_count"));
        serviceArea.setHasMomBabyRoomToilet(entity.getStr("has_mom_baby_room_toilet"));
        serviceArea.setMomBabyRoomToiletCount(entity.getStr("mom_baby_room_toilet_count"));
        serviceArea.setMomBabyRoomCovered(entity.getStr("mom_baby_room_covered"));
        serviceArea.setHasMomBabyRoomRest(entity.getStr("has_mom_baby_room_rest"));
        serviceArea.setHasPassageway(entity.getStr("has_passageway"));
        serviceArea.setPassagewayCount(entity.getStr("passageway_count"));
        serviceArea.setIsAllCovered(entity.getStr("is_all_covered"));
        serviceArea.setHasEmergencyHelp(entity.getStr("has_emergency_help"));
        serviceArea.setHasMedicalServer(entity.getStr("has_medical_server"));
        serviceArea.setHasMedicalRoom(entity.getStr("has_medical_room"));
        serviceArea.setHasServiceWatch(entity.getStr("has_service_watch"));
        serviceArea.setWatchMobile(entity.getStr("watch_mobile"));
        serviceArea.setHasManagementSystem(entity.getStr("has_management_system"));
        serviceArea.setHasCustomerFlowSystem(entity.getStr("has_customer_flow_system"));
        serviceArea.setHasCheckSystem(entity.getStr("has_check_system"));
        serviceArea.setHasInfoSend(entity.getStr("has_info_send"));
        serviceArea.setIsWifiCovered(entity.getStr("is_wifi_covered"));
        serviceArea.setCompanyName(entity.getStr("company_name"));
        serviceArea.setServerName(entity.getStr("server_name"));
        serviceArea.setHasCleanHelp(entity.getStr("has_clean_help"));
        serviceArea.setHasHelpCall(entity.getStr("has_help_call"));
        serviceArea.setHasHelpAir(entity.getStr("has_help_air"));
        serviceArea.setDriverHomeEquipment(entity.getStr("driver_home_equipment"));
        serviceArea.setFarmProductSell(entity.getStr("farm_product_sell"));
        serviceArea.setWaysectionId(entity.getStr("waysection_id"));
        serviceArea.setFacilitiesId(entity.getStr("geographyInfo_id"));
        serviceArea.setIdOld(entity.getStr("id"));
        return serviceArea;
    }

    private FacilitiesBridge updateBridgeData(FacilitiesBridge bridge, Entity entity) {
        bridge.setManageCode(entity.getStr("manage_code"));
        bridge.setManageName(entity.getStr("manage_name"));
        bridge.setRouteCode(entity.getStr("route_code"));
        bridge.setBridgeNo(entity.getStr("bridge_no"));
        bridge.setRouteSName(entity.getStr("route_s_name"));
        bridge.setAdminRegionCode(entity.getStr("admin_region_code"));
        bridge.setAdminRegionName(entity.getStr("admin_region_name"));
        bridge.setBridgeCenterStake(entity.getStr("bridge_center_stake"));
        bridge.setBridgeName(entity.getStr("bridge_name"));
        bridge.setBridgePlace(entity.getStr("bridge_place"));
        bridge.setTollNatureCode(entity.getStr("toll_nature_code"));
        bridge.setTollNatureName(entity.getStr("toll_nature_name"));
        bridge.setBridgeStartStake(entity.getStr("bridge_start_stake"));
        bridge.setBridgeEndStake(entity.getStr("bridge_end_stake"));
        bridge.setMaintenanceIndustryCode(entity.getStr("maintenance_industry_code"));
        bridge.setMaintenanceName(entity.getStr("maintenance_industry_name"));
        bridge.setAcrossPlaceTypeCode(entity.getStr("across_place_type_code"));
        bridge.setAcrossPlaceTypeName(entity.getStr("across_place_type_name"));
        bridge.setAcrossPlaceName(entity.getStr("across_place_name"));
        bridge.setUseTypeCode(entity.getStr("use_type_code"));
        bridge.setUseTypeName(entity.getStr("use_type_name"));
        bridge.setUseYearTypeCode(entity.getStr("use_year_type_code"));
        bridge.setUseYearTypeName(entity.getStr("use_year_type_name"));
        bridge.setAcrossRadiusTypeCode(entity.getStr("across_radius_type_code"));
        bridge.setAcrossRadiusTypeName(entity.getStr("across_radius_type_name"));
        bridge.setTechnologyEvaluateCode(entity.getStr("technology_evaluate_code"));
        bridge.setTechnologyEvaluateName(entity.getStr("technology_evaluate_name"));
        bridge.setTechnologyEvaluateUnit(entity.getStr("technology_evaluate_unit"));
        bridge.setEvaluateDate(entity.getStr("evaluate_date"));
        bridge.setLatelyCheckDate(entity.getStr("lately_check_date"));
        bridge.setMainDiseaseCode(entity.getStr("main_disease_code"));
        bridge.setMainDiseaseName(entity.getStr("main_disease_name"));
        bridge.setMainDiseaseDesc(entity.getStr("main_disease_desc"));
        bridge.setIsRepeatCode(entity.getStr("is_repeat_code"));
        bridge.setIsRepeatName(entity.getStr("is_repeat_name"));
        bridge.setIsNativeYearUnsafeBridgeCode(entity.getStr("is_native_year_unsafe_bridge_code"));
        bridge.setIsNativeYearUnsafeBridgeName(entity.getStr("is_native_year_unsafe_bridge_name"));
        bridge.setIsAmCode(entity.getStr("is_am_code"));
        bridge.setIsAmName(entity.getStr("is_am_name"));
        bridge.setIsIronInterchangeCode(entity.getStr("is_iron_interchange_code"));
        bridge.setIsIronInterchangeName(entity.getStr("is_iron_interchange_name"));
        bridge.setInterchangeTypeCode(entity.getStr("interchange_type_code"));
        bridge.setInterchangeTypeName(entity.getStr("interchange_type_name"));
        bridge.setInterchangeAcrossRoute(entity.getStr("interchange_across_route"));
        bridge.setInterchangeUnicomRoute(entity.getStr("interchange_unicom_route"));
        bridge.setLongitude(entity.getStr("longitude"));
        bridge.setLatitude(entity.getStr("latitude"));
        bridge.setBridgeStartLongitude(entity.getStr("bridge_start_longitude"));
        bridge.setBridgeStartLatitude(entity.getStr("bridge_start_latitude"));
        bridge.setBridgeEndLongitude(entity.getStr("bridge_end_longitude"));
        bridge.setBridgeEndLatitude(entity.getStr("bridge_end_latitude"));
        bridge.setBridgePlaceCode(entity.getStr("bridge_place_code"));
        bridge.setBridgePlaceName(entity.getStr("bridge_place_name"));
        bridge.setIsNarrowBridgeWideRoadCode(entity.getStr("is_narrow_bridge_wide_road_code"));
        bridge.setIsNarrowBridgeWideRoadName(entity.getStr("is_narrow_bridge_wide_road_name"));
        bridge.setIsLongBridgeDirCode(entity.getStr("is_long_bridge_dir_code"));
        bridge.setIsLongBridgeDirName(entity.getStr("is_long_bridge_dir_name"));
        bridge.setBridgeMeter(entity.getStr("bridge_meter"));
        bridge.setAcrossRadiusMeter(entity.getStr("across_radius_meter"));
        bridge.setAHoleMaxAcrossRadiusMeter(entity.getStr("a_hole_max_across_radius_meter"));
        bridge.setBridgeAcrossRadiusCombination(entity.getStr("bridge_across_radius_combination"));
        bridge.setBridgeHoleNum(entity.getStr("bridge_hole_num"));
        bridge.setMainBridgeHoleNum(entity.getStr("main_bridge_hole_num"));
        bridge.setMainBridgeMainAcrossMeter(entity.getStr("main_bridge_main_across_meter"));
        bridge.setMainBridgeEdgeAcrossMeter(entity.getStr("main_bridge_edge_across_meter"));
        bridge.setBeforeQupteBridgeMeter(entity.getStr("before_qupte_bridge_meter"));
        bridge.setAfterQupteBridgeMeter(entity.getStr("after_qupte_bridge_meter"));
        bridge.setBridgeAllWideMeter(entity.getStr("bridge_all_wide_meter"));
        bridge.setBridgeSurfaceWideMeter(entity.getStr("bridge_surface_wide_meter"));
        bridge.setPavementWideMeter(entity.getStr("pavement_wide_meter"));
        bridge.setDrivewayWideMeter(entity.getStr("driveway_wide_meter"));
        bridge.setBridgeHightMeter(entity.getStr("bridge_hight_meter"));
        bridge.setBridgeSurfaceHightMeter(entity.getStr("bridge_surface_hight_meter"));
        bridge.setBridgeBelowHightMeter(entity.getStr("bridge_below_hight_meter"));
        bridge.setRampSquareMeters(entity.getStr("ramp_square_meters"));
        bridge.setRampKm(entity.getStr("ramp_km"));
        bridge.setBridgeUpperStructureTypeCode(entity.getStr("bridge_upper_structure_type_code"));
        bridge.setBridgeUpperStructureTypeName(entity.getStr("bridge_upper_structure_type_name"));
        bridge.setBridgeBelowStructureTypeCode(entity.getStr("bridge_below_structure_type_code"));
        bridge.setBridgeBelowStructureTypeName(entity.getStr("bridge_below_structure_type_name"));
        bridge.setBridgeBaseStructureTypeCode(entity.getStr("bridge_base_structure_type_code"));
        bridge.setBridgeBaseStructureTypeName(entity.getStr("bridge_base_structure_type_name"));
        bridge.setBridgeQuoteStructureTypeCode(entity.getStr("bridge_quote_structure_type_code"));
        bridge.setBridgeQuoteStructureTypeName(entity.getStr("bridge_quote_structure_type_name"));
        bridge.setBridgeBearingTypeCode(entity.getStr("bridge_bearing_type_code"));
        bridge.setBridgeBearingTypeName(entity.getStr("bridge_bearing_type_name"));
        bridge.setBridgeAbutmentTypeCode(entity.getStr("bridge_abutment_type_code"));
        bridge.setBridgeAbutmentTypeName(entity.getStr("bridge_abutment_type_name"));
        bridge.setBridgePierTypeCode(entity.getStr("bridge_pier_type_code"));
        bridge.setBridgePierTypeName(entity.getStr("bridge_pier_type_name"));
        bridge.setBridgePierCaTypeCode(entity.getStr("bridge_pier_ca_type_code"));
        bridge.setBridgePierCaTypeName(entity.getStr("bridge_pier_ca_type_name"));
        bridge.setBridgeScalingTypeCode(entity.getStr("bridge_scaling_type_code"));
        bridge.setBridgeScalingTypeName(entity.getStr("bridge_scaling_type_name"));
        bridge.setBridgeArchAcrossRate(entity.getStr("bridge_arch_across_rate"));
        bridge.setBridgeSurfaceSlope(entity.getStr("bridge_surface_slope"));
        bridge.setBridgeSurfaceLineCode(entity.getStr("bridge_surface_line_code"));
        bridge.setBridgeSurfaceLineName(entity.getStr("bridge_surface_line_name"));
        bridge.setCurveSlopeCode(entity.getStr("curve_slope_code"));
        bridge.setCurveSlopeName(entity.getStr("curve_slope_name"));
        bridge.setBridgePositionTerrainCode(entity.getStr("bridge_position_terrain_code"));
        bridge.setBridgePositionTerrainName(entity.getStr("bridge_position_terrain_name"));
        bridge.setBridgeSurfaceDecorateCode(entity.getStr("bridge_surface_decorate_code"));
        bridge.setBridgeSurfaceDecorateName(entity.getStr("bridge_surface_decorate_name"));
        bridge.setBridgeSurfaceUpperMaterialCode(entity.getStr("bridge_surface_upper_material_code"));
        bridge.setBridgeSurfaceUpperMaterialName(entity.getStr("bridge_surface_upper_material_name"));
        bridge.setBridgeSurfaceBelowMaterialType(entity.getStr("bridge_surface_below_material_type"));
        bridge.setMainBridgeCrossCode(entity.getStr("main_bridge_cross_code"));
        bridge.setMainBridgeCrossName(entity.getStr("main_bridge_cross_name"));
        bridge.setQuoteBridgeCorssCode(entity.getStr("quote_bridge_corss_code"));
        bridge.setQuoteBridgeCorssName(entity.getStr("quote_bridge_corss_name"));
        bridge.setDesignLoadLevelCode(entity.getStr("design_load_level_code"));
        bridge.setDesignLoadLevelName(entity.getStr("design_load_level_name"));
        bridge.setCheckLoadCode(entity.getStr("check_load_code"));
        bridge.setCheckLoadName(entity.getStr("check_load_name"));
        bridge.setSeismicLevelCode(entity.getStr("seismic_level_code"));
        bridge.setSeismicLevelName(entity.getStr("seismic_level_name"));
        bridge.setRiverSlopeCdoe(entity.getStr("river_slope_cdoe"));
        bridge.setRiverSlopeName(entity.getStr("river_slope_name"));
        bridge.setRiverLowHeightMeter(entity.getStr("river_low_height_meter"));
        bridge.setDesignFloodFrequency(entity.getStr("design_flood_frequency"));
        bridge.setDesignWaterLevel(entity.getStr("design_water_level"));
        bridge.setNormalWaterLevel(entity.getStr("normal_water_level"));
        bridge.setNormalFloodLevel(entity.getStr("normal_flood_level"));
        bridge.setHistoryFloodLevel(entity.getStr("history_flood_level"));
        bridge.setRiverbedChange(entity.getStr("riverbed_change"));
        bridge.setSailingLevelCode(entity.getStr("sailing_level_code"));
        bridge.setSailingLevelName(entity.getStr("sailing_level_name"));
        bridge.setMaintenanceName(entity.getStr("maintenance_name"));
        bridge.setMaintenanceNatureCode(entity.getStr("maintenance_nature_code"));
        bridge.setMaintenanceNatureName(entity.getStr("maintenance_nature_name"));
        bridge.setDesignUnitName(entity.getStr("design_unit_name"));
        bridge.setBuildUnitName(entity.getStr("build_unit_name"));
        bridge.setImplementationUnitName(entity.getStr("implementation_unit_name"));
        bridge.setSupervisionUnitName(entity.getStr("supervision_unit_name"));
        bridge.setRegulatoryUnitName(entity.getStr("regulatory_unit_name"));
        bridge.setProjectNo(entity.getStr("project_no"));
        bridge.setTotalPrice(entity.getStr("total_price"));
        bridge.setBuildYear(entity.getStr("build_year"));
        bridge.setStartsDate(entity.getStr("starts_date"));
        bridge.setCompleteDate(entity.getStr("complete_date"));
        bridge.setToDate(entity.getStr("to_date"));
        bridge.setChangeDate(entity.getStr("change_date"));
        bridge.setChangeReasonCode(entity.getStr("change_reason_code"));
        bridge.setChangeReasonName(entity.getStr("change_reason_name"));
        bridge.setChangeReasonDesc(entity.getStr("change_reason_desc"));
        bridge.setRemark(entity.getStr("remark"));
        bridge.setLatelyRemouldYear(entity.getStr("lately_remould_year"));
        bridge.setLatelyRemouldCompleteDate(entity.getStr("lately_remould_complete_date"));
        bridge.setLatelyRemouldPartCode(entity.getStr("lately_remould_part_code"));
        bridge.setLatelyRemouldPartName(entity.getStr("lately_remould_part_name"));
        bridge.setLatelyRemouldImplementationName(entity.getStr("lately_remould_implementation_name"));
        bridge.setLatelyProjectNatureCode(entity.getStr("lately_project_nature_code"));
        bridge.setLatelyProjectNatureName(entity.getStr("lately_project_nature_name"));
        bridge.setIsSubsidiesProjectCode(entity.getStr("is_subsidies_project_code"));
        bridge.setIsSubsidiesProjectName(entity.getStr("is_subsidies_project_name"));
        bridge.setHasTrafficControlCode(entity.getStr("has_traffic_control_code"));
        bridge.setHasTrafficControlName(entity.getStr("has_traffic_control_name"));
        bridge.setPlanProjectId(entity.getStr("plan_project_id"));
        bridge.setPlanProjectRouteCode(entity.getStr("plan_project_route_code"));
        bridge.setPlanProjectRouteName(entity.getStr("plan_project_route_name"));
        bridge.setPlanProjectBridgeStake(entity.getStr("plan_project_bridge_stake"));
        bridge.setOriginalManageCode(entity.getStr("original_manage_code"));
        bridge.setOriginalManageName(entity.getStr("original_manage_name"));
        bridge.setOriginalRouteCode(entity.getStr("original_route_code"));
        bridge.setOriginalOperationSortCode(entity.getStr("original_operation_sort_code"));
        bridge.setOriginalOperationSortName(entity.getStr("original_operation_sort_name"));
        bridge.setTranslationValue(entity.getStr("translation_value"));
        bridge.setHighwayAdjustCode(entity.getStr("highway_adjust_code"));
        bridge.setHighwayAdjustName(entity.getStr("highway_adjust_name"));
        bridge.setIsReverseStakeCode(entity.getStr("is_reverse_stake_code"));
        bridge.setIsReverseStakeName(entity.getStr("is_reverse_stake_name"));
        bridge.setReverseRouteMileage(entity.getStr("reverse_route_mileage"));
        bridge.setIsDelCode(entity.getStr("is_del_code"));
        bridge.setIsDelName(entity.getStr("is_del_name"));
        bridge.setUniqueId(entity.getStr("unique_id"));
        bridge.setIdCode(entity.getStr("id_code"));
        bridge.setHighwayAdjustAdminCode(entity.getStr("highway_adjust_admin_code"));
        bridge.setHighwayAdjustAdminName(entity.getStr("highway_adjust_admin_name"));
        bridge.setOperatingWaysectionId(entity.getStr("way_id"));
        bridge.setFacilitiesId(entity.getStr("geographyinfo_id"));
        bridge.setBridgeType(entity.getStr("bridge_type"));
        bridge.setIdOld(entity.getStr("bridge_id"));
        return bridge;
    }

    private FacilitiesTunnel updateTunnelData(FacilitiesTunnel tunnel, Entity entity) {
        tunnel.setManageCode(entity.getStr("manage_code"));
        tunnel.setManageName(entity.getStr("manage_name"));
        tunnel.setRouteCode(entity.getStr("route_code"));
        tunnel.setTunnelNo(entity.getStr("tunnel_no"));
        tunnel.setRouteSName(entity.getStr("route_s_name"));
        tunnel.setTunnelCenterStake(entity.getStr("tunnel_center_stake"));
        tunnel.setAdminRegionCode(entity.getStr("admin_region_code"));
        tunnel.setAdminRegionName(entity.getStr("admin_region_name"));
        tunnel.setTunnelName(entity.getStr("tunnel_name"));
        tunnel.setTunnelPalce(entity.getStr("tunnel_palce"));
        tunnel.setTunnelEntranceStake(entity.getStr("tunnel_entrance_stake"));
        tunnel.setTunnelLongTypeCode(entity.getStr("tunnel_long_type_code"));
        tunnel.setTunnelLongTypeName(entity.getStr("tunnel_long_type_name"));
        tunnel.setTunnelMeter(entity.getStr("tunnel_meter"));
        tunnel.setTunnelAllWide(entity.getStr("tunnel_all_wide"));
        tunnel.setTunnelCleanWide(entity.getStr("tunnel_clean_wide"));
        tunnel.setTunnelCleanHeight(entity.getStr("tunnel_clean_height"));
        tunnel.setPavementWide(entity.getStr("pavement_wide"));
        tunnel.setRoadSurfaceLayerTypeCode(entity.getStr("road_surface_layer_type_code"));
        tunnel.setRoadSurfaceLayerTypeName(entity.getStr("road_surface_layer_type_name"));
        tunnel.setLongitude(entity.getStr("longitude"));
        tunnel.setLatitude(entity.getStr("latitude"));
        tunnel.setTunnelMaintenanceLevelCode(entity.getStr("tunnel_maintenance_level_code"));
        tunnel.setTunnelMaintenanceLevelName(entity.getStr("tunnel_maintenance_level_name"));
        tunnel.setIsLongTunnelDirectoryCode(entity.getStr("is_long_tunnel_directory_code"));
        tunnel.setIsLongTunnelDirectoryName(entity.getStr("is_long_tunnel_directory_name"));
        tunnel.setIdCode(entity.getStr("id_code"));
        tunnel.setRemark(entity.getStr("remark"));
        tunnel.setHoleShapeCode(entity.getStr("hole_shape_code"));
        tunnel.setHoleShapeName(entity.getStr("hole_shape_name"));
        tunnel.setSurfaceShapeCode(entity.getStr("surface_shape_code"));
        tunnel.setSurfaceShapeName(entity.getStr("surface_shape_name"));
        tunnel.setMaterialCode(entity.getStr("material_code"));
        tunnel.setMaterialName(entity.getStr("material_name"));
        tunnel.setTunnelDrainageTypeCode(entity.getStr("tunnel_drainage_type_code"));
        tunnel.setTunnelDrainageTypeName(entity.getStr("tunnel_drainage_type_name"));
        tunnel.setSecureChannelNum(entity.getStr("secure_channel_num"));
        tunnel.setTunnelLightingCode(entity.getStr("tunnel_lighting_code"));
        tunnel.setTunnelLightingName(entity.getStr("tunnel_lighting_name"));
        tunnel.setTunnelVentilateCode(entity.getStr("tunnel_ventilate_code"));
        tunnel.setTunnelVentilateName(entity.getStr("tunnel_ventilate_name"));
        tunnel.setTunnelFireCode(entity.getStr("tunnel_fire_code"));
        tunnel.setTunnelFireName(entity.getStr("tunnel_fire_name"));
        tunnel.setTunnelElectronicCode(entity.getStr("tunnel_electronic_code"));
        tunnel.setTunnelElectronicName(entity.getStr("tunnel_electronic_name"));
        tunnel.setIsWaterBelowCode(entity.getStr("is_water_below_code"));
        tunnel.setIsWaterBelowName(entity.getStr("is_water_below_name"));
        tunnel.setBuildUnitName(entity.getStr("build_unit_name"));
        tunnel.setDesignUnitName(entity.getStr("design_unit_name"));
        tunnel.setImplementationUnitName(entity.getStr("implementation_unit_name"));
        tunnel.setSupervisionUnitName(entity.getStr("supervision_unit_name"));
        tunnel.setMaintenanceName(entity.getStr("maintenance_name"));
        tunnel.setMaintenanceNatureCode(entity.getStr("maintenance_nature_code"));
        tunnel.setMaintenanceNatureName(entity.getStr("maintenance_nature_name"));
        tunnel.setIndustryCode(entity.getStr("industry_code"));
        tunnel.setIndustryName(entity.getStr("industry_name"));
        tunnel.setRegulatoryUnitName(entity.getStr("regulatory_unit_name"));
        tunnel.setStartsDate(entity.getStr("starts_date"));
        tunnel.setCompleteDate(entity.getStr("complete_date"));
        tunnel.setToDate(entity.getStr("to_date"));
        tunnel.setChangeDate(entity.getStr("change_date"));
        tunnel.setChangeReasonCode(entity.getStr("change_reason_code"));
        tunnel.setChangeReasonName(entity.getStr("change_reason_name"));
        tunnel.setChangeReasonDesc(entity.getStr("change_reason_desc"));
        tunnel.setBuildProjectNo(entity.getStr("build_project_no"));
        tunnel.setBuildTotalPrice(entity.getStr("build_total_price"));
        tunnel.setBuildYear(entity.getStr("build_year"));
        tunnel.setRemouldYear(entity.getStr("remould_year"));
        tunnel.setTechnologyEvaluateUnit(entity.getStr("technology_evaluate_unit"));
        tunnel.setEvaluateDate(entity.getStr("evaluate_date"));
        tunnel.setRemouldCompleteDate(entity.getStr("remould_complete_date"));
        tunnel.setTunnelRemouldPartCode(entity.getStr("tunnel_remould_part_code"));
        tunnel.setTunnelRemouldPartName(entity.getStr("tunnel_remould_part_name"));
        tunnel.setProjectNatureCode(entity.getStr("project_nature_code"));
        tunnel.setProjectNatureName(entity.getStr("project_nature_name"));
        tunnel.setTunnelMainDiseaseCode(entity.getStr("tunnel_main_disease_code"));
        tunnel.setTunnelMainDiseaseName(entity.getStr("tunnel_main_disease_name"));
        tunnel.setTunnelMainDiseaseDesc(entity.getStr("tunnel_main_disease_desc"));
        tunnel.setHighwayAdjustAdminCode(entity.getStr("highway_adjust_admin_code"));
        tunnel.setHighwayAdjustAdminName(entity.getStr("highway_adjust_admin_name"));
        tunnel.setAllEvaluateLevelCode(entity.getStr("all_evaluate_level_code"));
        tunnel.setAllEvaluateLevelName(entity.getStr("all_evaluate_level_name"));
        tunnel.setAllEvaluateDate(entity.getStr("all_evaluate_date"));
        tunnel.setAllEvaluateUnit(entity.getStr("all_evaluate_unit"));
        tunnel.setCivilEvaluateLevelCode(entity.getStr("civil_evaluate_level_code"));
        tunnel.setCivilEvaluateLevelName(entity.getStr("civil_evaluate_level_name"));
        tunnel.setCivilEvaluateDate(entity.getStr("civil_evaluate_date"));
        tunnel.setCivilEvaluateUnit(entity.getStr("civil_evaluate_unit"));
        tunnel.setMechanicalEvaluateLevelCode(entity.getStr("mechanical_evaluate_level_code"));
        tunnel.setMechanicalEvaluateLevelName(entity.getStr("mechanical_evaluate_level_name"));
        tunnel.setMechanicalEvaluateDate(entity.getStr("mechanical_evaluate_date"));
        tunnel.setMechanicalEvaluateUnit(entity.getStr("mechanical_evaluate_unit"));
        tunnel.setOtherEvaluateLevelCode(entity.getStr("other_evaluate_level_code"));
        tunnel.setOtherEvaluateLevelName(entity.getStr("other_evaluate_level_name"));
        tunnel.setOtherEvaluateDate(entity.getStr("other_evaluate_date"));
        tunnel.setOtherEvaluateUnit(entity.getStr("other_evaluate_unit"));
        tunnel.setPlanProjectId(entity.getStr("plan_project_id"));
        tunnel.setPlanProjectRouteCode(entity.getStr("plan_project_route_code"));
        tunnel.setPlanProjectRouteName(entity.getStr("plan_project_route_name"));
        tunnel.setPlanProjectBridgeStake(entity.getStr("plan_project_bridge_stake"));
        tunnel.setOriginalManageCode(entity.getStr("original_manage_code"));
        tunnel.setOriginalManageName(entity.getStr("original_manage_name"));
        tunnel.setOriginalRouteCode(entity.getStr("original_route_code"));
        tunnel.setOperationSortCode(entity.getStr("operation_sort_code"));
        tunnel.setOperationSortName(entity.getStr("operation_sort_name"));
        tunnel.setTranslationValue(entity.getStr("translation_value"));
        tunnel.setHighwayAdjustCode(entity.getStr("highway_adjust_code"));
        tunnel.setHighwayAdjustName(entity.getStr("highway_adjust_name"));
        tunnel.setIsReverseStakeCode(entity.getStr("is_reverse_stake_code"));
        tunnel.setIsReverseStakeName(entity.getStr("is_reverse_stake_name"));
        tunnel.setReverseRouteMileage(entity.getStr("reverse_route_mileage"));
        tunnel.setIsDelCode(entity.getStr("is_del_code"));
        tunnel.setIsDelName(entity.getStr("is_del_name"));
        tunnel.setUniqueId(entity.getStr("unique_id"));
        tunnel.setOperatingWaysectionId(entity.getStr("way_id"));
        tunnel.setFacilitiesId(entity.getStr("geographyinfo_id"));
        tunnel.setIdOld(entity.getStr("tunnel_id"));
        tunnel.setLatitude84(entity.getStr("latitude_84"));
        tunnel.setLongitude84(entity.getStr("longitude_84"));
        return tunnel;
    }

    private FacilitiesGantry updateGantryData(FacilitiesGantry gantry, Entity entity) {
        gantry.setFeeUnit(entity.getStr("feeunit"));
        gantry.setRoadSection(entity.getStr("roadsection"));
        gantry.setProvince(entity.getInt("province"));
        gantry.setInOut(entity.getStr("inout"));
        gantry.setGantryType(entity.getStr("gantrytype"));
        gantry.setGantryOrder(entity.getStr("gantryorder"));
        gantry.setDerection(entity.getStr("derection"));
        gantry.setSection(entity.getStr("section"));
        gantry.setRealGantryId(entity.getStr("realgantryid"));
        gantry.setHex(entity.getStr("hex"));
        gantry.setRsuName(entity.getStr("rsuname"));
        gantry.setVlpName(entity.getStr("vlpname"));
        gantry.setIpcIp1(entity.getStr("ipcip1"));
        gantry.setIpcIp2(entity.getStr("ipcip2"));
        gantry.setIpcVIp(entity.getStr("ipcvip"));
        gantry.setServerIp1(entity.getStr("serverip1"));
        gantry.setServerIp2(entity.getStr("serverip2"));
        gantry.setServerVIp(entity.getStr("servervip"));
        gantry.setRsuIp(entity.getStr("rsuip"));
        gantry.setNtpIp(entity.getStr("ntpip"));
        gantry.setPsamIp(entity.getStr("psamip"));
        gantry.setOrganId(entity.getStr("organid"));
        gantry.setStation(entity.getStr("station"));
        gantry.setCenter(entity.getStr("center"));
        gantry.setCompany(entity.getStr("company"));
        gantry.setUserMark(entity.getInt("usermark"));
        gantry.setCenterId(entity.getStr("centerid"));
        gantry.setVirtualGantryFlag(entity.getInt("virtualgantryflag"));
        gantry.setIpcCount(entity.getInt("ipccount"));
        gantry.setServerCount(entity.getInt("servercount"));
        gantry.setIsProvince(entity.getInt("isprovince"));
        gantry.setNeiProvinces(entity.getStr("NeiProvinces"));
        gantry.setLocDirection(entity.getStr("LocDirection"));
        gantry.setIdOld(entity.getStr("gantryid"));
        return gantry;
    }

    private FacilitiesTollStationLane updateTollStationLaneData(FacilitiesTollStationLane tollStationLane, Entity entity) {
        tollStationLane.setLaneId(entity.getStr("laneid"));
        tollStationLane.setEtcMtc(entity.getInt("etc_mtc"));
        tollStationLane.setInOut(entity.getInt("in_out"));
        tollStationLane.setTollStationNo(entity.getStr("station_no"));
        tollStationLane.setTollStationName(entity.getStr("station_name"));
        tollStationLane.setWayNo(entity.getStr("way_no"));
        tollStationLane.setWayName(entity.getStr("way"));
        tollStationLane.setTollStationId(entity.getStr("stationid"));
        tollStationLane.setIdOld(entity.getStr("laneid"));
        return tollStationLane;
    }

}
