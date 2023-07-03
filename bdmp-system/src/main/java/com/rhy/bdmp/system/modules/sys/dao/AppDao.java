package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface AppDao extends BaseMapper<App> {

    /**
     * @Description: 删除用户应用表（根据应用id）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void deleteUserAppByAppId(@Param("appIds") Set<String> appIds);

    /**
     * @Description: 删除用户映射表（根据应用id）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void deleteUserMappingByAppId(@Param("appIds") Set<String> appIds);

    /**
     * @Description: 根据应用Id更新用户应用表
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertUserAppByAppId(@Param("appId") String appId, @Param("userIds") Set<String> userIds, @Param("createBy") String createBy, @Param("createTime") Date createTime);


    /**
     * @Description: 查询用户应用权限（根据应用ID,返回用户ID集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findUserIdsByAppId(@Param("appId") String appId);

}
