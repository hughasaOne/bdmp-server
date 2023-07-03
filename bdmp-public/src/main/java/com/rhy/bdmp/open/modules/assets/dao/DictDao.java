package com.rhy.bdmp.open.modules.assets.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字典服务
 * @author weicaifu
 */
@Mapper
public interface DictDao {
    List<Map<String, String>> getCodeList(@Param("dictDirCodes") Set<String> dictDirCodes,@Param("dictName") String dictName);

    /**
     * 2.80.11 设备字典分类查询
     * @param searchType 查询类型（1:设备总类，2:设备类型）
     * @param typeId 设备总类/设备类型的id
     * @param name 设备字典类型的名称
     */
    List<Map<String, String>> getDeviceDict(@Param("searchType") Integer searchType,
                                            @Param("typeId") String typeId,
                                            @Param("name") String name);
}
