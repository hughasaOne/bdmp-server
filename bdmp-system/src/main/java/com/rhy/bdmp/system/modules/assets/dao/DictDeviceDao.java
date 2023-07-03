package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DictDeviceDao {
    List<Map<String, Object>> getChildren(@Param("parentId") String parentId);

    Map<String,Object> selectById(@Param("deviceDictId") String deviceDictId);

    List<Map<String,Object>> getDictDevice(@Param("dictBO") DictBO dictBO);

    Page<Map<String,Object>> getDictDevice(Page page,@Param("dictBO") DictBO dictBO);

    String getDictDeviceId(@Param("deviceTypeId") String deviceTypeId);

    String getMaxDeviceDictId(@Param("deviceTypeId") String deviceTypeId);
}
