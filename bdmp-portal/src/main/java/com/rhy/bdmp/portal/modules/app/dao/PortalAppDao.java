package com.rhy.bdmp.portal.modules.app.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.App;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortalAppDao {

    /**
     * @Description: 根据用户查找应用访问权限
     * @Author: dongyu
     * @Date: 2021/5/10
     */
    List<App> findAppByUser(@Param("isUseUserPermissions") Boolean isUseUserPermissions, @Param("userId") String userId);

}
