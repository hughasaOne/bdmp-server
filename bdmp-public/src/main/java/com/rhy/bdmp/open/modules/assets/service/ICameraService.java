package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceSy;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceTl;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceYt;
import com.rhy.bdmp.open.modules.assets.domain.vo.RequestCameraDirVo;

import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 摄像头业务
 * @Date: 2021/9/28 8:53
 * @Version: 1.0.0
 */
public interface ICameraService {

    /**
     * 通过地理位置id获取科技视频资源列表
     *
     * @param orgId 设施id
     * @return List
     */
    List<CameraResourceYt> listCameraResourceYt(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 通过地理位置id获取科技视频资源列表
     *
     * @param orgId 设施id
     * @return List
     */
    List<Map<String, Object>> listCameraIdYt(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 通过地理位置id获取科技视频资源列表
     *
     * @param orgId 设施id
     * @return List
     */
    List<CameraResourceSy> listCameraResourceSy(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 通过id获取视频资源详情(科技)
     *
     * @param id 视频资源id
     * @return CameraResourceYt
     */
    CameraResourceYt selectCameraYtById(String id);

    CameraResourceSy selectCameraSyById(String id);

    /**
     * 通过地理位置id获取腾路视频资源列表
     *
     * @param isUseUserPermissions
     * @param nodeType             设施id
     * @param orgId
     * @return List
     */
    List<CameraResourceTl> listCameraResourceTl(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 通过地理位置id获取腾路视频资源列表
     *
     * @param isUseUserPermissions
     * @param nodeType             设施id
     * @param orgId
     * @return List
     */
    List<Map<String, Object>> listCameraIdTl(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 通过视频资源id获取腾路视频资源详情
     *
     * @param id 视频资源id
     * @return CameraResourceTl
     */
    CameraResourceTl getCameraResourceTlById(String id);

    /**
     * @description 云台视频资源目录
     * @param requestCameraDirVo
     * @return
     */

    List<Tree<String>> getCameraDirYt(RequestCameraDirVo requestCameraDirVo);

}
