package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.system.modules.sys.domain.vo.UserPermissionVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper
public interface UserDao extends BaseMapper<User> {
    /**
     * 查询用户拥有的组织机构权限
     * @param userId 用户id
     */
    List<UserPermissionVo> getUserOrgPermission(@Param("userId") String userId,
                                                @Param("appId") String appId);

    /**
     * @Description: 删除用户应用表 关联数据（根据用户id删除）
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    void deleteUserAppByUserId(@Param("userIds") Set<String> userIds);

    /**
     * @Description: 删除 用户数据权限表 关联数据（根据用户id删除）
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    void deleteUserDataByUserIdAndDataType(@Param("userIds") Set<String> userIds,
                                           @Param("dataType") Integer dataType,
                                           @Param("appId") String appId);

    /**
    * @Description: 删除 用户角色关系表 关联数据（根据用户id删除）
    * @Author: dongyu
    * @Date: 2021/4/14
    */
    void deleteUserRoleByUserId(@Param("userIds")Set<String> userIds);

    /**
    * @Description: 删除用户映射（根据用户id）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    void deleteUserMappingByUserId(@Param("userIds")Set<String> userIds);

    /**
     * @Description: 根据用户Id更新用户应用表
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertUserAppByUserId(@Param("userId") String userId, @Param("appIds") Set<String> appIds, @Param("currentUser") String currentUser, @Param("currentDateTime") Date currentDateTime);

    /**
     * @Description: 更新用户角色权限（根据userId更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertUserRoleByUserId(@Param("userId") String userId, @Param("roleIds") List<String> roleIds, @Param("createBy") String createBy, @Param("createTime") Date createTime);

    /**
     * @Description: 更新用户数据权限（根据用户id）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertUserDataByUserId(@Param("userId") String userId,
                                @Param("dataType") Integer dataType,
                                @Param("permissonIds") Set<String> permissonIds,
                                @Param("createBy") String createBy,
                                @Param("createTime") Date createTime,
                                @Param("appId") String appId);

    /**
     * @Description: 查询用户应用权限（根据用户ID,返回应用id集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findAppIdsByUserId(@Param("userId") String userId);

    /**
     * @Description: 查询用户角色权限（根据用户ID,返回角色ID集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findRoleIdsByUserId(@Param("userId") String userId);

    /**
     * @Description: 查询用户数据权限（根据用户ID,返回应用ID、dataType集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<Map<String, Object>> findUserDataByUserId(@Param("userId") String userId);

    /**
    * @Description: 查找组织及其子组织下的所有用户
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<UserVo> findUserListByOrgId(@Param("orgId") String orgId);

    /**
     * @Description: 查询用户数据权限（根据用户Id、权限类别、权限等级，返回权限ID集合）
     * @Author: dongyu
     * @Date: 2021/4/22
     */
    List<String> selectPermissionIds(@Param("userId") String userId, @Param("dataType") Integer dataType);

    /**
     * 查询用户拥有的应用权限
     * @param userId 用户id
     * @param appId 应用id
     */
    List<String> getUserPermission(@Param("userId") String userId,
                                      @Param("appId") String appId,
                                      @Param("dataType") Integer dataType);

    /**
    * @Description: 根据用户ID和应用ID查找角色Ids(返回角色Ids)
    * @Author: dongyu
    * @Date: 2021/4/28
    */
    List<String> findRoleIdsByUserIdAndAppId(@Param("userId") String userId, @Param("appId") String appId);

    /**
    * @Description: 删除用户角色权限（根据用户ID、应用ID）
    * @Author: dongyu
    * @Date: 2021/4/28
    */
    int deleteUserRoleByUserIdAndAppId(@Param("userId") String userId, @Param("appId") String appId);

    /**
    * @Description: 根据用户查找应用访问权限
    * @Author: dongyu
    * @Date: 2021/5/10
    */
    List<App> findAppByUser(@Param("isUseUserPermissions") Boolean isUseUserPermissions, @Param("userId") String userId);

}
