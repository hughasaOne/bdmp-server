package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 角色 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseRoleService extends IService<Role> {

    /**
     * 角色列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Role> list(QueryVO queryVO);

    /**
     * 角色列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Role> page(QueryVO queryVO);

    /**
     * 查看角色(根据ID)
     * @param roleId
     * @return
     */
    Role detail(String roleId);

    /**
     * 新增角色
     * @param role
     * @return
     */
    int create(Role role);

    /**
     * 修改角色
     * @param role
     * @return
     */
    int update(Role role);

    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    int delete(Set<String> roleIds);


}
