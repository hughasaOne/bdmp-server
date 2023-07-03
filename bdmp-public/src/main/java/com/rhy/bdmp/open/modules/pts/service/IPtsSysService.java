package com.rhy.bdmp.open.modules.pts.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.pts.domain.vo.UserPermissionVo;

import java.util.List;

/**
 * @Author: yanggj
 * @Date: 2021/12/15 17:39
 * @Version: 1.0.0
 */
public interface IPtsSysService {

    Page<?> queryPageOrg(Integer currentPage, Integer size,String orgType);

    Page<?> queryPageRole(Integer currentPage, Integer size, String appId);

    Page<?> queryPageUser(Integer currentPage, Integer size, String appId);

    /**
     * 查询用户拥有的组织机构权限
     * @param userId 用户id
     */
    List<UserPermissionVo> getUserOrgPermission(String userId, String appId);
}
