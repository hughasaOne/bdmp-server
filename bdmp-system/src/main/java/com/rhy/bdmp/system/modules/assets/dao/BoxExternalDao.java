package com.rhy.bdmp.system.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 终端箱外接设备数据持久化层
 * @author 魏财富
 */
@Mapper
public interface BoxExternalDao {

    /**
     * 获取可用的外设
     * @param facId 终端箱的设施Id
     */
    List<Map<String,String>> getEnablePeripherals(@Param("facId") String facId);

    /**
     * 获取外设信息
     * @param deviceId 终端箱Id
     */
    List<Map<String, String>> getExternalInfo(@Param("deviceId") String deviceId,@Param("sn") String sn);

    /**
     * @param boxId 终端箱Id
     * @param portNum 端口号
     */
    Map<String,String> getPortInfoByPortAndId(@Param("boxId") String boxId,@Param("portNum") String portNum);
}
