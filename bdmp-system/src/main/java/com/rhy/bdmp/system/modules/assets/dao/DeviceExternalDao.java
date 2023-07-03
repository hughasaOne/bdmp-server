package com.rhy.bdmp.system.modules.assets.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface DeviceExternalDao {
    List<Map<String, Object>> list(@Param("deviceId") String deviceId);

    void delete(@Param("deviceId") String deviceId, @Param("externalIds") Set<String> externalIds);
}
