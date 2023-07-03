package com.rhy.bdmp.open.modules.pts.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.pts.domain.vo.UserPermissionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: yanggj
 * @Date: 2021/12/15 14:44
 * @Version: 1.0.0
 */
@Mapper
public interface PtsSysDao {
    Page<HashMap<String, Object>> queryPageOrg(IPage<HashMap<String, Object>> page,@Param("orgTypes") List<String> orgTypes);

    Page<HashMap<String, Object>> queryPageRole(IPage<HashMap<String, Object>> page, @Param("appId") String appId);

    List<HashMap<String, Object>> queryResourceByRoleId(@Param("roleId") String roleId,@Param("appId") String appId);

    List<String>  queryRoleByAppId(@Param("appId") String appId);

    Page<HashMap<String, Object>> queryPageUserByRole(Page<HashMap<String, Object>> page, @Param("roleIds") List<String> roleIds);

    List<HashMap<String, Object>> queryRoleByUserId(@Param("userId") String userId, @Param("appId")String appId);

    /**
     * 查询用户拥有的组织机构权限
     * @param userId 用户id
     */
    List<UserPermissionVo> getUserOrgPermission(@Param("userId") String userId,
                                                @Param("appId") String appId);
}
