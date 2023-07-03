package com.rhy.bdmp.open.modules.assets.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字典服务
 * @author weicaifu
 */
public interface IDictService {
    List<Map<String, String>> getCodeList(Set<String> dictDirCodes,String dictName);

    List<Map<String, String>> getDeviceDict(Integer searchType, String typeId, String name);
}
