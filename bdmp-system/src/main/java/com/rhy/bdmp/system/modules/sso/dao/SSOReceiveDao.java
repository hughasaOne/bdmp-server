package com.rhy.bdmp.system.modules.sso.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author: lipeng
 * @Date: 2021/5/12
 * @description:
 * @version: 1.0
 */
@Mapper
public interface SSOReceiveDao {

    /**
     * 查询系统参数中基础数据管理平台的首页地址
     * @return
     */
    public String queryHoneUrl();
}
