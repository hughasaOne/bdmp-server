package com.rhy.bdmp.auth.service;


import com.rhy.bdmp.auth.domain.vo.UserVo;
import com.rhy.bdmp.base.modules.sys.domain.po.User;

import java.util.List;

/**
 * User对象对应服务层
 * @author PSQ
 */
public interface UserService {

    /**
     * 查看用户(根据username)
     *
     * @param username 用户名
     * @return user对象
     */
    User getUserByUsername(String username);

    /**
     * 查询用户角色权限（根据用户ID,返回角色ID集合）
     *
     * @param userId 用户id
     * @return 权限集合
     */
    List<String> findRoleIdsByUserId(String userId);

    void setUserInfo(UserVo userVo,String orgId);

}
