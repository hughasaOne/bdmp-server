package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.CameraResourceTl;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 视频资源(腾路) 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseCameraResourceTlService extends IService<CameraResourceTl> {

    /**
     * 视频资源(腾路)列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<CameraResourceTl> list(QueryVO queryVO);

    /**
     * 视频资源(腾路)列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<CameraResourceTl> page(QueryVO queryVO);

    /**
     * 查看视频资源(腾路)(根据ID)
     * @param cameraId
     * @return
     */
    CameraResourceTl detail(String cameraId);

    /**
     * 新增视频资源(腾路)
     * @param cameraResourceTl
     * @return
     */
    int create(CameraResourceTl cameraResourceTl);

    /**
     * 修改视频资源(腾路)
     * @param cameraResourceTl
     * @return
     */
    int update(CameraResourceTl cameraResourceTl);

    /**
     * 删除视频资源(腾路)
     * @param cameraIds
     * @return
     */
    int delete(Set<String> cameraIds);


}
