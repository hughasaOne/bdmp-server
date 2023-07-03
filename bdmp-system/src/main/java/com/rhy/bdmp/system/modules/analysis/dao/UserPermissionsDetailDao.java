package com.rhy.bdmp.system.modules.analysis.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.rhy.bdmp.system.modules.analysis.domain.vo.OrgUserVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.UserPermissionsVO;
import com.rhy.bdmp.system.modules.sys.domain.vo.OrgVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 查询用户权限明细对应持久层
 *
 * @author PSQ
 */
@Mapper
public interface UserPermissionsDetailDao {
    /**
     * 根据用户Id去查询App权限
     *
     * @param userId  用户id
     * @return
     */
    List<String> getCompanyUserIdAppData(@Param("userId") String userId);

    /**
     * 根据公司查询用户
     *
     * @param orgId orgId
     * @return 公司直属用户
     */
    List<UserVo> getOrgUser(String orgId);

    /**
     * 根据公司查询子部门用户
     *
     * @param orgId orgId
     * @return 返回公司下属机构, 机构下属机构用户
     */
    List<UserVo> getCompanyUserRecursion(String orgId);

    /**
     * 查询用户台账数据权限
     *
     * @param userId   用户Id
     * @param dataType 台账数据类型
     * @return
     */
    List<UserPermissionsVO> getUserDataPermissions(@Param("userId") String userId,
                                                   @Param("dataType") String dataType);

    /**
     * 根据用户id查询出用户菜单权限
     *
     * @param userId 用户id
     * @return
     */
    List<UserPermissionsVO> getUserMenu(@Param("userId") String userId);

    /**
     * 根据用户id查询应用访问权限
     *
     * @param userId 用户id
     * @return
     */
    List<UserPermissionsVO> getUserIdApp(@Param("userId") String userId);

    /**
     * 通过用户id,部门id查询部门权限
     *
     * @param orgId 部门id
     * @return
     */
    List<OrgVo>  getUserIdOrg(@Param("orgId") String orgId);

    /**
     * 查询用户相关信息
     *
     * @param userId 用户id
     * @return
     */
    UserPermissionsVO getUser(String userId);

    /**
     * 查询org信息
     *
     * @param valueId 本方法中为orgId或parentId
     * @return
     */
    OrgVo getOrgById(String valueId);

    /**
     * 查询菜单信息
     *
     * @param parentId 菜单id或parentId
     * @return
     */
    Resource getResourceById(String parentId);

    /**
     * 删除用户的角色
     *
     * @param userId 用户id
     */
    void deleteUserRole(String userId);

    /**
     * 同步用户的角色菜单权限
     *
     * @param permissionsId 被复制权限的用户id
     * @param userId        被同步的用户id
     * @param createBy      创建人,当前登录人id
     */
    void setUserRolePermissions(@Param("permissionsId") String permissionsId,
                                @Param("userId") String userId,
                                @Param("createBy") String createBy
    );

    /**
     * 同步用户的应用访问权限
     *
     * @param permissionsId 被复制权限的用户id
     * @param userId        被同步的用户id
     * @param createBy      创建人,当前登录人id
     */
    void setUserAppPermissions(@Param("permissionsId") String permissionsId,
                               @Param("userId") String userId,
                               @Param("createBy") String createBy
    );

    /**
     * 删除用户的app访问权限
     *
     * @param userId 用户id
     */
    void deleteUserApp(String userId);

    /**
     * 添加用户t_bdmp_sys_user_data表权限数据
     *
     * @param permissionsId 被复制权限的用户id
     * @param userId        被同步的用户id
     * @param createBy      创建人,当前登录人id
     * @param dataType      数据类型
     */
    void setUserDataPermissions(@Param("permissionsId") String permissionsId,
                                @Param("userId") String userId,
                                @Param("createBy") String createBy,
                                @Param("dataType") String dataType);

    /**
     * 删除用户t_bdmp_sys_user_data表权限数据
     *
     * @param userId   用户id
     * @param dataType 数据类型
     */
    void deleteUserAppData(@Param("userId") String userId, @Param("dataType") String dataType);

    /**
     * 查询公司
     *
     * @return
     */
    Set<OrgUserVO> selectOrgAll();

    /**
     * 查询组织机构
     *
     * @param orgId 公司id
     * @return
     */
    Set<OrgUserVO> selectOrg1(String orgId);

    /**
     * 查询组织机构下的用户
     *
     * @param orgIds 公司id
     * @return
     */
    Set<OrgUserVO> selectOrgUser(@Param("orgIds") Set<String> orgIds);

    /**
     * 查询组织机构
     *
     * @return 返回组织机构 用于表格
     */
    List<OrgUserVO> selectOrg();

    /**
     * @author weicaifu
     * 获取组织及用户
     */
    List<OrgUserVO> getNodeList();
}
