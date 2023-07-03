package com.rhy.bdmp.open.modules.pts.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @Author: yanggj
 * @Date: 2021/12/15 14:44
 * @Version: 1.0.0
 */
@Mapper
public interface PtsAssetsDao {
    Page<HashMap<String, Object>> queryPageWaySection(Page<HashMap<String, Object>> page);
    Page<HashMap<String, Object>> queryPageServiceArea(Page<HashMap<String, Object>> page,@Param("facType") String facType);
    Page<HashMap<String, Object>> queryPageTollStation(Page<HashMap<String, Object>> page, @Param("facType") String facType);
}
