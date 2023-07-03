package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DictBrandDao {
    List<Map<String,Object>> selectMaps(@Param("parentId") String parentId);

    Map<String,Object> selectById(@Param("brandId") String brandId);

    List<Map<String,Object>> getDictBrand(@Param("dictBO") DictBO dictBO);

    Page<Map<String,Object>> getDictBrand(Page page,@Param("dictBO") DictBO dictBO);
}
