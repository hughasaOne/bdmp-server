package com.rhy.bdmp.open.modules.assets.dao;

import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceSy;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceTl;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceYt;
import com.rhy.bdmp.open.modules.assets.domain.vo.CameraDirVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description:
 * @Date: 2021/9/28 8:58
 * @Version: 1.0.0
 */
@Mapper
public interface CameraDao {


    /**
     * 获取yt视频资源列表
     *
     * @param isUseUserPermissions /
     * @param userId               /
     * @param userType        根据用户类型是否为“演示用户”，决定是否只显示在线视频资源，以及不为收费亭内的视频资源
     * @param dataPermissionsLevel /
     * @param orgId                /
     * @param nodeType             /
     * @return List
     */
    List<CameraResourceYt> listCameraResourceYt(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                @Param("userId") String userId,
                                                @Param("userType") Integer userType,
                                                @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                @Param("orgId") String orgId,
                                                @Param("nodeType") String nodeType);

    /**
     * 获取yt视频资源列表
     *
     * @param isUseUserPermissions /
     * @param userId               /
     * @param userType        根据用户类型是否为“演示用户”，决定是否只显示在线视频资源，以及不为收费亭内的视频资源
     * @param dataPermissionsLevel /
     * @param orgId                /
     * @param nodeType             /
     * @return List
     */
    List<Map<String, Object>> listCameraIdYt(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                             @Param("userId") String userId,
                                             @Param("userType") Integer userType,
                                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                             @Param("orgId") String orgId,
                                             @Param("nodeType") String nodeType);

    /**
     * 获取yt视频资源列表
     *
     * @param isUseUserPermissions /
     * @param userId               /
     * @param userType        根据用户类型是否为“演示用户”，决定是否只显示在线视频资源，以及不为收费亭内的视频资源
     * @param dataPermissionsLevel /
     * @param orgId                /
     * @param nodeType             /
     * @return List
     */
    List<CameraResourceSy> listCameraResourceSy(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                @Param("userId") String userId,
                                                @Param("userType") Integer userType,
                                                @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                @Param("orgId") String orgId,
                                                @Param("nodeType") String nodeType);

    /**
     * 获取yt视频资源详情
     *
     * @param id 视频资源id
     * @return CameraResourceYt
     */
    CameraResourceYt selectCameraYtById(@Param("id") String id);

    CameraResourceSy selectCameraSyById(@Param("id") String id);

    /**
     * 获取腾路视频资源列表
     *
     * @param isUseUserPermissions /
     * @param userId               /
     * @param userType        根据用户类型是否为“演示用户”，决定是否只显示在线视频资源，以及不为收费亭内的视频资源
     * @param dataPermissionsLevel /
     * @param orgId                /
     * @param nodeType             /
     * @return List
     */
    List<CameraResourceTl> listCameraResourceTl(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                @Param("userId") String userId,
                                                @Param("userType") Integer userType,
                                                @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                @Param("orgId") String orgId,
                                                @Param("nodeType") String nodeType);

    /**
     * 获取腾路视频资源列表
     *
     * @param isUseUserPermissions /
     * @param userId               /
     * @param userType        根据用户类型是否为“演示用户”，决定是否只显示在线视频资源，以及不为收费亭内的视频资源
     * @param dataPermissionsLevel /
     * @param orgId                /
     * @param nodeType             /
     * @return List
     */
    List<Map<String, Object>> listCameraIdTl(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                             @Param("userId") String userId,
                                             @Param("userType") Integer userType,
                                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                             @Param("orgId") String orgId,
                                             @Param("nodeType") String nodeType);

    /**
     * 根据id查询腾路视频资源
     *
     * @param id id
     * @return CameraResourceTl
     */
    CameraResourceTl getCameraResourceTlById(@Param("id") String id);


    /**
     * 根据运营公司ID获取运营集团
     * @param parentId
     * @return
     */
    CameraDirVo getOrgGroup(@Param("parentId") String parentId);

    /**
     * 获取运营公司
     *
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别
     * @param isUseUserPermissions 是否使用权限
     */
    List<CameraDirVo> getOrgList(@Param("userId") String userId,
                                 @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                 @Param("isUseUserPermissions") Boolean isUseUserPermissions);



    /**
     * 根据节点查询资源
     *
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别
     * @param isUseUserPermissions 是否使用权限
     * @param nodeId               节点id
     * @param nodeType             节点类型
     */
    List<CameraDirVo> getCameraResource(@Param("userId") String userId,
                                        @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                        @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                        @Param("nodeId") String nodeId,
                                        @Param("nodeType") Integer nodeType);

}
