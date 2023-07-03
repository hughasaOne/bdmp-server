package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DictSystemDao {
    List<Map<String,Object>> getDictSystem(@Param("dictBO") DictBO dictBO);

    Page<Map<String,Object>> getDictSystem(Page page, @Param("dictBO") DictBO dictBO);
}
