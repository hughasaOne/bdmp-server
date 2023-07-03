package com.rhy.bdmp.portal.modules.waitdo.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.App;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description
 * @date 2021-01-27 10:01
 **/
@Mapper
public interface WaitDoDao {

    /**
     * 查询UserApp(根据用户ID)
     * @param userId 用户ID
     * @param appIds 应用ID
     */
    List<App> findUserAppByUserId(@Param("userId") String userId, @Param("appIds") Set<String> appIds);

}