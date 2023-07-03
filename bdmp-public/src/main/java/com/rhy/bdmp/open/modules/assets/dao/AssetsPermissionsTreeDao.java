package com.rhy.bdmp.open.modules.assets.dao;

import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.Org;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description
 * @date 2021-04-19 14:02
 **/
@Mapper
public interface AssetsPermissionsTreeDao {

    /**
     * 获取台账资源-集团
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return List
     */
    List<Org> getAssetsGroup(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                             @Param("userId") String userId,
                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                             @Param("isAsync") Boolean isAsync);

    /**
     * 获取台账资源-集团
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Org> getAssetsGroup(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                             @Param("userId") String userId,
                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                             @Param("orgId") String orgId,
                             @Param("nodeType") String nodeType,
                             @Param("isAsync") Boolean isAsync);

    /**
     * 获取台账资源-运营公司
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return List
     */
    List<Org> getAssetsOrg(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                           @Param("userId") String userId,
                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                           @Param("isAsync") Boolean isAsync);

    /**
     * 获取台账资源-运营公司
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Org> getAssetsOrg(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                           @Param("userId") String userId,
                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                           @Param("orgId") String orgId,
                           @Param("nodeType") String nodeType,
                           @Param("isAsync") Boolean isAsync);

    /**
     * 获取台账资源-路段
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return List
     */
    List<Waysection> getAssetsWay(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("isAsync") Boolean isAsync);

    /**
     * 获取台账资源-路段
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Waysection> getAssetsWay(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType,
                                  @Param("isAsync") Boolean isAsync);

    /**
     * 获取台账资源-设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return List
     */
    List<Facilities> getAssetsFac(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("isAsync") Boolean isAsync,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 获取台账资源-设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @param facilitiesTypes      设施类型
     * @return List
     */
    List<Facilities> getAssetsFac(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType,
                                  @Param("isAsync") Boolean isAsync,
                                  @Param("facilitiesTypes") List<String> facilitiesTypes);

    /**
     * 获取台账资源-设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Waysection> getFacPermissions(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType);

    /**
     * 收费站
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Facilities> getTollStation(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType,
                                  @Param("isAsync") Boolean isAsync,
                                  @Param("facType") String facType);

    /**
     * 隧道
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Facilities> getTunnel(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                    @Param("userId") String userId,
                                    @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                    @Param("orgId") String orgId,
                                    @Param("nodeType") String nodeType,
                                    @Param("isAsync") Boolean isAsync,
                                    @Param("facType") String facType);

    /**
     * 门架
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Facilities> getGantry(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                               @Param("userId") String userId,
                               @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                               @Param("orgId") String orgId,
                               @Param("nodeType") String nodeType,
                               @Param("isAsync") Boolean isAsync,
                               @Param("facilitiesTypes") List<String> facilitiesTypes);

    /**
     * 服务区
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Facilities> getServiceArea(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                               @Param("userId") String userId,
                               @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                               @Param("orgId") String orgId,
                               @Param("nodeType") String nodeType,
                               @Param("isAsync") Boolean isAsync,
                               @Param("facType") String facType);

    /**
     * 获取台账资源-子设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return List
     */
    List<Facilities> getAssetsFacChildren(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                          @Param("userId") String userId,
                                          @Param("isAsync") Boolean isAsync,
                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 获取台账资源-子设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Facilities> getAssetsFacChildren(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                          @Param("userId") String userId,
                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                          @Param("orgId") String orgId,
                                          @Param("nodeType") String nodeType,
                                          @Param("isAsync") Boolean isAsync,
                                          @Param("facilitiesTypes") List<String> facilitiesTypes);

    /**
     * 获取台账资源-设施-桥梁
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Facilities> getAssetsFacBridge(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                  @Param("userId") String userId,
                                  @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                  @Param("orgId") String orgId,
                                  @Param("nodeType") String nodeType,
                                  @Param("facilitiesType") String facilitiesType);


    /**
     * 根据userId获取用户详情
     *
     * @param userId 用户id
     * @return User
     */
    User getUserById(String userId);

    List<Org> getGroupOrgMonitorByWay(@Param("wayId") String wayId,@Param("orgTypesArray") String[] orgTypesArray);

    List<String> getFacIds(@Param("wayId") String wayId);

    List<String> getUsers(@Param("companyId") String companyId, @Param("facIds") List<String> facIds);

    List<Org> getControlPointList(@Param("users") List<String> users);

}