package com.rhy.bdmp.system.modules.sys.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Mapper
public interface ResourceDao extends BaseMapper<Resource> {

    /**
    * @Description: 查找资源下的子资源（包含当前节点）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    List<ResourceVo> findResouceChildren(@Param("resourceId") String resourceId);

    /**
    * @Description: 删除角色资源权限表（根据资源Id）
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    void deleteRoleResourceByResourceId(@Param("resourceIds") Set<String> resourceIds);



    /**
     * @Description: 更新角色资源权限（根据资源Id）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    void insertRoleResourceByResourceId(@Param("resourceId") String resourceId, @Param("roleIds") Set<String> roleIds, @Param("createBy") String createBy, @Param("createTime") Date createTime);

    /**
     * @Description: 查询角色资源权限（根据资源ID，返回角色ID）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findRoleIdsByResourceId(@Param("resourceId") String resourceId);

    /**
     * @Description: 查询角色资源权限（根据资源ID、应用ID）
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    List<String> findRoleIdsByResourceIdAndAppId(@Param("resourceId") String resourceId, @Param("appId") String appId);

    /**
    * @Description: 删除角色资源表（根据资源Id、应用Id）
    * @Author: dongyu
    * @Date: 2021/4/28
    */
    void deleteRoleResourceByResourceIdAndAppId(@Param("resourceId") String resourceId, @Param("appId") String appId);

    /**
    * @Description: 查询当前用户拥有的目录和菜单资源
    * @Author: dongyu
    * @Date: 2021/4/29
    */
    List<ResourceVo> selectResourceByTypeAndRole(@Param("userId") String userId, @Param("appId") String appId);

    /**
    * @Description: 查询当前用户拥有的目录和菜单、按钮资源
    * @Author: dongyu
    * @Date: 2021/4/29
    */
    List<ResourceVo> selectResourceByTypeAndRole2(@Param("userId") String userId, @Param("appId") String appId);

    /**
     * 获取用户菜单
     * @param appId 应用id
     */
    List<ResourceVo> getUserMenu(String appId);

    /**
     * @Description: 查询资源子节点（根据应用ID、资源ID）
     * @Author: dongyu
     * @Date: 2021/5/7
     */
    List<ResourceVo> findResourceChildren(@Param("appId") String appId, @Param("parentId") String parentId, @Param("includeId") String includeId, @Param("ew") QueryWrapper<ResourceVo> queryWrapper);

    /**
     * 根据父节点获取资源列表
     * @param queryWrapper 查询条件
     * @return
     */
    List<ResourceVo> listByParentId(@Param("ew") QueryWrapper<Resource> queryWrapper);
}
