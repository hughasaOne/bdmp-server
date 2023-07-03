package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import com.rhy.bdmp.system.modules.sys.domain.vo.UserMappingVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMappingDao extends BaseMapper<UserMapping> {
    /**
    * @Description: 查询用户映射(根据用户ID、应用ID)
    * @Author: dongyu
    * @Date: 2021/5/13
    */
    UserMappingVo findUserMappingByUserIdAndAppId(@Param("userId") String userId, @Param("appId") String appId);

    /**
    * @Description: 查询用户映射(根据用户ID、应用下用户ID)
    * @Author: dongyu
    * @Date: 2021/5/13
    */
    UserMappingVo findUserMappingByAppIdAndAppUserId(@Param("appId") String appId, @Param("appUserId") String appUserId);


    /**
     * 基础数据下的用户的映射用户不重复
     * @author weicaifu
     */
    boolean checkAppSubUserRepeat(@Param("userMapping") UserMapping userMapping);

    /**
     * 基础数据下的用户映射同一个app下的用户不超过一个
     * @author weicaifu
     */
    boolean checkAppSubuserMultiple(@Param("userMapping") UserMapping userMapping);
}
