package com.rhy.bdmp.collect.modules.jdxt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.collect.modules.jdxt.dao.SqlHelperDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* @description
* @author jiangzhimin
* @date 2021-08-02 17:16
* @version V1.0
**/
@Service
public class SqlHelperService {

    @Resource
    private SqlHelperDao sqlHelperDao;

    public SqlHelperService() {
    }

    @DataSource("default")
    public List<Map<String, Object>> searchSqlLocalDB(String sql) {
        return sqlHelperDao.searchSql(sql);
    }

    @DataSource("default")
    public Page<Map<String, Object>> searchPageSqlLocalDB(int currentPage, int limit, String sql) {
        return (Page)sqlHelperDao.searchPageSql(new Page((long)currentPage, (long)limit), sql);
    }

    @DataSource("default")
    public int exeSqlListLocalDB(List<String> sqlList) {
        return sqlHelperDao.exeSqlListForBatch(sqlList);
    }

    @DataSource("jdxt")
    public List<Map<String, Object>> searchSqlJdxtDB(String sql) {
        return sqlHelperDao.searchSql(sql);
    }

    @DataSource("jdxt")
    public Page<Map<String, Object>> searchPageSqlJdxtDB(int currentPage, int limit, String sql) {
        return (Page)sqlHelperDao.searchPageSql(new Page((long)currentPage, (long)limit), sql);
    }

    @DataSource("jdxt")
    public int exeSqlListJdxtDB(List<String> sqlList) {
        return sqlHelperDao.exeSqlListForBatch(sqlList);
    }
}