package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.rhy.bdmp.open.modules.assets.dao.DictDao;
import com.rhy.bdmp.open.modules.assets.service.IDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 字典服务
 * @author weicaifu
 */
@Service
public class DictServiceImpl implements IDictService {

    @Resource
    private DictDao dictDao;

    /**
     * 2.80.1 查询字典code(根据字典目录code查询)
     * @param dictDirCodes 字典目录code集合
     */
    @Override
    public List<Map<String, String>> getCodeList(Set<String> dictDirCodes,String dictName) {
        if (CollUtil.isNotEmpty(dictDirCodes)){
            return dictDao.getCodeList(dictDirCodes,dictName);
        }else{
            return null;
        }
    }

    /**
     * 2.80.11 设备字典分类查询
     * @param searchType 查询类型（1:设备总类，2:设备类型）
     * @param typeId 设备总类/设备类型的id
     * @param name 设备字典类型的名称
     */
    @Override
    public List<Map<String, String>> getDeviceDict(Integer searchType, String typeId, String name) {
        return dictDao.getDeviceDict(searchType,typeId,name);
    }
}
