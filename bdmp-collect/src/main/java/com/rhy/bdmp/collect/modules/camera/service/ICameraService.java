package com.rhy.bdmp.collect.modules.camera.service;

import com.rhy.bdmp.collect.modules.camera.domain.bo.Camera;
import com.rhy.bdmp.collect.modules.camera.domain.vo.CameraResponse;

import java.util.List;

/**
 * @description 采集视频资源数据
 * @author jiangzhimin
 * @date 2021-08-02 17:19
 */
public interface ICameraService {

    /**
     * 同步视频资源(云台)
     * @return
     */
    boolean syncCameraByYt();

    /**
     * 同步视频资源(腾路)
     * @return
     */
    boolean syncCameraByTl();

    boolean syncCameraBySY();

    /**
     * 获取yt视频树
     * @param isAll 是否查询全部
     * @param resId 节点id
     * @author weicaifu
     */
    List<Camera> cameraTree(boolean isAll, String resId);

    /**
     * 获取tl视频列表
     * @param pageSize 页大小
     * @param page 当前页
     * @param type 类型
     * @author weicaifu
     */
    CameraResponse tlVideo(String pageSize, String page, String type);
}
