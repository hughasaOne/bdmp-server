package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    /**
    * @Description: 删除角色资源表（根据角色Id）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    void deleteRoleResourceByRoleIds(@Param("roleIds") Set<String> roleIds);

    /**
     * @Description: 删除用户资源表（根据角色Id）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void deleteUserRoleByRoleIds(@Param("roleIds") Set<String> roleIds);

    /**
     * @Description: 更新用户角色权限（根据角色ID更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertUserRoleByRoleId(@Param("roleId") String roleId, @Param("userIds") Set<String> userIds, @Param("createBy") String createBy, @Param("createTime") Date createTime);

    /**
     * @Description: 更新角色资源权限（根据角色ID更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertRoleResourceByRoleId(@Param("roleId") String roleId, @Param("resourceIds") Set<String> resourceIds, @Param("createBy") String createBy, @Param("createTime") Date createTime);

    /**
     * @Description: 查询用户角色权限（根据角色ID，返回用户ID）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findUserIdsByRoleId(@Param("roleId") String roleId);

    /**
     * @Description: 查询角色资源权限（根据角色ID，返回资源ID）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findResourceIdsByRoleIds(@Param("roleIds") List<String> roleIds);

    /**
    * @Description: 根据角色ID查询role_resource表，返回资源ID(只返回子节点)
    * @Author: dongyu
    * @Date: 2021/4/27
    */
    List<String> findResourceIdsByRoleIdsWithoutParentId(@Param("roleIds") List<String> roleIds);

    /**
     * @Description: 根据角色ID、应用ID查询用户角色权限表(返回用户ID集合)
     * @Author: dongyu
     * @Date: 2021/4/28
     */
    List<String> findUserIdsByRoleIdAndAppId(@Param("roleId") String roleId, @Param("appId") String appId);

    /**
    * @Description: 删除用户角色表（根据角色Id、应用Id）
    * @Author: dongyu
    * @Date: 2021/4/28
    */
    int deleteUserRoleByRoleIdAndAppId(@Param("roleId") String roleId,@Param("appId") String appId);

    /**
     * @Description: 根据角色ID和应用ID查询role_resource表，返回资源ID(只返回子节点)
     * @Author: dongyu
     * @Date: 2021/4/28
     */
    List<String> findResourceIdsByRoleIdAndAppIdWithoutParentId(@Param("roleId") String roleId, @Param("appId") String appId);

    /**
     * @Description: 删除角色资源表（根据角色ID、应用ID）
     * @Author: dongyu
     * @Date: 2021/4/28
     */
    void deleteRoleResourceByRoleIdAndAppId(@Param("roleId") String roleId, @Param("appId") String appId);

}
