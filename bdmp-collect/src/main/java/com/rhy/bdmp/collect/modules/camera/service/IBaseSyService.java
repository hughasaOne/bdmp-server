package com.rhy.bdmp.collect.modules.camera.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.collect.modules.camera.domain.po.Sy;

import java.util.List;
import java.util.Set;

/**
 * @description 视频资源(云台) 服务接口
 * @author weicaifu
 * @date 2022-06-07 11:49
 * @version V1.0
 **/
public interface IBaseSyService extends IService<Sy> {

    /**
     * 视频资源(云台)列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Sy> list(QueryVO queryVO);

    /**
     * 视频资源(云台)列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Sy> page(QueryVO queryVO);

    /**
     * 查看视频资源(云台)(根据ID)
     * @param id
     * @return
     */
    Sy detail(String id);

    /**
     * 新增视频资源(云台)
     * @param sy
     * @return
     */
    int create(Sy sy);

    /**
     * 修改视频资源(云台)
     * @param sy
     * @return
     */
    int update(Sy sy);

    /**
     * 删除视频资源(云台)
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
