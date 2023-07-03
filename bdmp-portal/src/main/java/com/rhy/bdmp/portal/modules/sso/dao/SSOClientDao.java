package com.rhy.bdmp.portal.modules.sso.dao;

import com.rhy.bdmp.portal.modules.sso.domain.AppUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author: lipeng
 * @Date: 2021/1/27
 * @description: 单点登录相关查询接口
 * @version: 1.0
 */
@Mapper
public interface SSOClientDao {
    /**
     * 根据用户ID查询用户映射
     * @param userId
     * @return
     */
    public AppUserVO queryUserMappingByUserId(@Param("userId") String userId, @Param("appId") String appId);

    /**
     * 根据用户ID、应用ID查询数据行数
     * @param userId
     * @param appId
     * @return
     */
    public int checkUserPermissions(@Param("userId") String userId, @Param("appId") String appId);
}
