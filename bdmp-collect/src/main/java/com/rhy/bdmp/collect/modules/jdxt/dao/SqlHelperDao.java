package com.rhy.bdmp.collect.modules.jdxt.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SqlHelperDao extends Mapper {

    @Select({"<script>${sql}</script>"})
    List<Map<String, Object>> searchSql(@Param("sql") String var1);

    @Select({"<script>${sql}</script>"})
    <E extends IPage<Map<String, Object>>> E searchPageSql(IPage<Map<String, Object>> var1, @Param("sql") String var2);

    @Insert({"<script><foreach item='sql' index='index' collection='sqlList' separator=';' open='' close=';'><trim>${sql}</trim></foreach></script>"})
    int exeSqlListForBatch(@Param("sqlList") List<String> var1);
}
