package com.rhy.bdmp.quartz.modules.sysbusiness.dao;

import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    /**
     * 根据用户id查询用户信息
     * @param userId
     * @return
     */
    @DataSource("basedata")
    User selectById(String userId);
}
