package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.CameraResourceYt;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 视频资源(云台) 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseCameraResourceYtService extends IService<CameraResourceYt> {

    /**
     * 视频资源(云台)列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<CameraResourceYt> list(QueryVO queryVO);

    /**
     * 视频资源(云台)列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<CameraResourceYt> page(QueryVO queryVO);

    /**
     * 查看视频资源(云台)(根据ID)
     * @param id
     * @return
     */
    CameraResourceYt detail(String id);

    /**
     * 新增视频资源(云台)
     * @param cameraResourceYt
     * @return
     */
    int create(CameraResourceYt cameraResourceYt);

    /**
     * 修改视频资源(云台)
     * @param cameraResourceYt
     * @return
     */
    int update(CameraResourceYt cameraResourceYt);

    /**
     * 删除视频资源(云台)
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
