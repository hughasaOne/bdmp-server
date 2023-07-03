package com.rhy.bdmp.system.modules.assets.dao;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryDeviceBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceCategoryDao {
    List<Map<String, Object>> getCategoryItemNode();

    List<Map<String,Object>> getDeviceType(@Param("codes") JSONArray codes);

    List<Map<String,Object>> getDeviceDict(@Param("codes") JSONArray codes);

    Page<Map<String,Object>> getDevice(Page<Map<String,Object>> page,
                                             @Param("codes") JSONArray codes,
                                             @Param("deviceBo") CategoryDeviceBo deviceBo,
                                             @Param("categoryType") Integer categoryType);

    List<Map<String, Object>> getDeviceByCodes(@Param("codes") JSONArray codes);
}
