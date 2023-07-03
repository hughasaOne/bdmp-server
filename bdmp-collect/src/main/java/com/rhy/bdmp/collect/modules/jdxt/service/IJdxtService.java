package com.rhy.bdmp.collect.modules.jdxt.service;

import java.util.Set;

/**
 * @description 采集机电系统数据
 * @author jiangzhimin
 * @date 2021-08-02 17:19
 */
public interface IJdxtService {

    /**
     * 同步运营公司
     * @return
     */
    boolean syncOperatingCompany();

    /**
     * 根据参数同步运营公司
     * @param companyIds 运营公司Id
     * @return
     */
    boolean syncOperatingCompanyArgs(Set<String> companyIds);

    /**
     * 根据参数同步运营路段
     * @param orgIds    运营公司Id集合
     * @param waysectionIds 运营路段Id集合
     * @return
     */
    boolean syncWaySectionArgs(Set<String> orgIds, Set<String> waysectionIds);

    /**
     * 根据参数同步设施
     * @param waysectionIds 运营路段Id集合
     * @param facilitiesIds 设施id
     * @return
     */
    boolean syncFacilitiesArgs(Set<String> waysectionIds,Set<String> facilitiesIds);

    /**
     * 同步运营路段
     * @return
     */
    boolean syncWaySection();

    /**
     * 同步设施
     * @return
     */
    boolean syncFacilities();

    /**
     * 同步设备
     * @return
     */
    boolean syncDevice();

    /**
     * 同步视频资源（腾路）
     * @return
     */
    boolean syncVideoResourceTl();

    /**
     * 同步视频资源（云台）
     * @return
     */
    boolean syncVideoResourceYt();

    // 字典相关：字典、品牌、设备、系统
    /**
     * 同步字典
     * @return
     */
    boolean syncDict();

    /**
     * 同步字典（品牌）
     * @return
     */
    boolean syncDictBrand();

    /**
     * 同步字典（设备）
     * @return
     */
    boolean syncDictDevice();

    /**
     * 同步字典（系统）
     * @return
     */
    boolean syncDictSystem();

    // 扩展相关：路段、收费站、服务区、桥梁、隧道、门架
    /**
     * 同步路段
     * @return
     */
    boolean syncRoute();

    /**
     * 同步收费站
     * @return
     */
    boolean syncTollStation();

    /**
     * 同步服务区
     * @return
     */
    boolean syncServiceArea();

    /**
     * 同步桥梁
     * @return
     */
    boolean syncBridge();

    /**
     * 同步隧道
     * @return
     */
    boolean syncTunnel();

    /**
     * 同步门架
     * @return
     */
    boolean syncGantry();

    /**
     * 同步收费站车道
     * @return
     */
    boolean syncTollStationLane();

}
