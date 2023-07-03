package com.rhy.bdmp.open.modules.system.dao;

import com.rhy.bdmp.open.modules.system.domain.vo.MicroUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @description
* @author jiangzhimin
* @date 2021-07-15 09:22
* @version V1.0
**/
@Mapper
public interface MicroUserDao {


    /**
     * 查询(Micro)用户信息,根据用户ID
     * @param userId
     * @return
     */
    MicroUserVo getMicroUserInfo(@Param("userId") String userId);
    MicroUserVo getMicroOrgInfoByOrgId(@Param("orgId") String orgId);

}