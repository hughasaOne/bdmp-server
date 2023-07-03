package com.rhy.bdmp.system.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacShortVO;
import com.rhy.bdmp.system.modules.assets.domain.vo.OrgShortVO;
import com.rhy.bdmp.system.modules.assets.domain.vo.WayShortVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @return
     */
    List<OrgShortVO> getAssetsGroup(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                    @Param("userId") String userId,
                                    @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 获取台账资源-组织
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return
     */
    List<OrgShortVO> getAssetsOrg(@Param("isUseUserPermissions") Boolean isUseUserPermissions, @Param("userId") String userId, @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 获取台账资源-路段
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return
     */
    List<WayShortVO> getAssetsWay(@Param("isUseUserPermissions") Boolean isUseUserPermissions, @Param("userId") String userId, @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 获取台账资源-设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return
     */
    List<FacShortVO> getAssetsFac(@Param("isUseUserPermissions") Boolean isUseUserPermissions, @Param("userId") String userId, @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 获取台账资源-子设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @return
     */
    List<Facilities> getAssetsFacChildren(@Param("isUseUserPermissions") Boolean isUseUserPermissions, @Param("userId") String userId, @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    List<Map<String,Object>> asyncTree(@Param("search") String search);

    /*
     * 根据用户id获取用户对象
     * */
    User getUserById(String userId);

    /**
     * 根据路段id 获取设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param waysectionId         路段id
     * @return FacShortVO
     */
    List<FacShortVO> getFacByWayId(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                   @Param("userId") String userId,
                                   @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                   @Param("waysectionId") String waysectionId);

    /**
     * 根据设施id 获取子设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户ID
     * @param dataPermissionsLevel 数据权限级别
     * @param facilitiesId         设施id
     * @return FacShortVO
     */
    List<FacShortVO> getChildFacilities(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                        @Param("userId") String userId,
                                        @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                        @Param("facilitiesId") String facilitiesId);

    /**
     * 获取用户组织机构权限树
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param userId               用户Id
     * @param dataPermissionsLevel 数据权限级别
     * @return
     */
    List<Org> getOrgList(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                         @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                         @Param("userId") String userId);

    Set<String> getPermissionsLevel1(@Param("userId") String userId);

    Set<String> getPermissionsLevel2(@Param("userId") String userId);

    Set<String> getPermissionsLevel3(@Param("userId") String userId);
}