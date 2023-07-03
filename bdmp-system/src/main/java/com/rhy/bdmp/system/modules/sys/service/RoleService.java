package com.rhy.bdmp.system.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.system.modules.sys.domain.vo.RoleVo;

import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 角色列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);

    /**
     * 查看角色(根据ID)
     * @param roleId
     * @return
     */
    RoleVo detail(String roleId);

    /**
    * @Description: 删除角色
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    void delete(Set<String> roleIds);

    /**
     * @Description: 更新用户角色权限（根据角色ID更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void updateUseRoleByRoleId(String roleId, String appId, Set<String> userIds);

    /**
     * @Description: 更新角色资源权限（根据角色ID更新）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void updateRoleResourceByRoleIdAndAppId(String roleId, String appId, Set<String> resourceIds);

    /**
    * @Description: 查询用户角色权限（根据角色ID）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<String> findUserIdsByRoleIdAndAppId(String roleId, String appId);

    /**
    * @Description: 查询角色资源权限（根据角色ID）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<String> findResourceIdsByRoleIdAndAppId(String roleId, String appId);

    /**
    * @Description: 查询应用角色树
    * @Author: dongyu
    * @Date: 2021/4/15
     * @param appIds
    */
    List<NodeVo> findAppRoleTree(Set<String> appIds);

    /**
    * @Description: 新增角色
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int create(Role role);

    /**
    * @Description: 修改角色
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int update(Role role);
}
