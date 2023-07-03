package com.rhy.bdmp.quartz.modules.common.dao;

import com.rhy.bdmp.quartz.modules.common.to.UserAppPermissions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface CommonDao {
    Set<String> getUserPermissions(@Param("userId") String userId);

    UserAppPermissions getCurentUser(@Param("userId") String userId);
}
