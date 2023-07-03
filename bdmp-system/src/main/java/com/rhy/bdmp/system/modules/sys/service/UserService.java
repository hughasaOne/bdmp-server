package com.rhy.bdmp.system.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.system.modules.sys.domain.vo.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    /**
     * 查询用户拥有的组织机构权限
     * @param userId 用户id
     */
    List<UserPermissionVo> getUserOrgPermission(String userId,String appId);

    /**
     * 用户列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);

    /**
     * 查看用户(根据ID)
     * @param userId
     * @return
     */
    UserVo detail(String userId);

    /**
    * @Description: 删除用户
    * @Author: dongyu
    * @Date: 2021/4/14
    */
    void delete(Set<String> userIds);

    /**
     * @Description: 更新用户应用权限（根据userId更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void updateUserAppByUserId(String userId, Set<String> appIds);

    /**
     * @Description: 更新用户角色权限（根据userId）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void updateUseRoleByUserId(Map<String, Object> userRoleQueryMap);

    /**
    * @Description: 查询用户应用权限（根据用户ID,返回应用id集合）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<String> findAppIdsByUserId(String userId);

    /**
    * @Description: 查询用户角色权限（根据用户ID和应用ID,返回角色ID集合）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<String> findRoleIdsByUserIdAndAppId(String userId, String appId);

    /**
    * @Description: 查询用户组织关系
    * @Author: dongyu
    * @Date: 2021/4/15
     * @param appId
     */
    List<NodeVo> findUserOrgTree(String appId);

    /**
    * @Description: 新增用户
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int create(User user);

    /**
    * @Description: 修改用户
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int update(User user);

    /**
     * @Description: 修改用户配置
     * @Author: jaingzhimin
     * @Date: 2022/1/25
     */
    int updateUserConfig(Object userConfig);

    /**
    * @Description: 查询用户的应用数据权限（根据用户ID,返回应用ID集合）
    * @Author: dongyu
    * @Date: 2021/4/22
    */
    List<String> findAppPermissionIdsByUserId(String userId,String appId);

    /**
    * @Description: 查询用户的台账数据权限（根据用户ID,返回台账对应权限ID集合）
    * @Author: dongyu
    * @Date: 2021/4/22
    */
    Map<String, Object> findAssetsPermissionIdsByUserId(String userId,String appId);

    /**
     * @Description: 更新用户的应用数据权限（根据用户ID、应用ID）
     * @Author: dongyu
     * @Date: 2021/4/22
     * @param sysUserDataQueryVo
     */
    void updateAppPermission(SysUserDataQueryVo sysUserDataQueryVo);

    /**
     * @Description: 更新用户的台账数据权限（根据用户ID、应用ID、权限级别）
     * @Author: dongyu
     * @Date: 2021/4/22
     */
    void updateAssetsPermission(AssetsUserDataQueryVo assetsUserDataQueryVo);


    /**
     * 更新用户的组织机构权限
     */
    void updateUserOrgPermission(SysUserDataQueryVo sysUserDataQueryVo);

    /**
     * 修改密码
     * @param userPassVo
     * @return
     */
    int updatePass(UserPassVo userPassVo) throws Exception;

    /**
     * @Description: 重置密码
     * @Author: dongyu
     * @Date: 2021/4/27
     */
    int resetPassword(String userId, String newPass) throws Exception;

    /**
     * @Description: 查询用户角色权限（根据用户ID,返回角色ID集合）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findRoleIdsByUserId(String userId);

    /**
    * @Description: 查询当前用户的应用权限
    * @Author: dongyu
    * @Date: 2021/5/10
    */
    List<App> findCurrentUserApp(Boolean isUseUserPermissions);
}
