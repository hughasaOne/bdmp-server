package com.rhy.bdmp.auth.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author PSQ
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    /**
     * 查询用户角色权限（根据用户ID,返回角色ID集合）
     * @param userId 用户id
     * @return 返回权限集合
     */
    List<String> findRoleIdsByUserId(@Param("userId") String userId);
}
