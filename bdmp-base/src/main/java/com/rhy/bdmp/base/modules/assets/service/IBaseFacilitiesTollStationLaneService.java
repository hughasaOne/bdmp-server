package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStationLane;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author jiangzhimin
 * @date 2021-12-02 10:06
 * @version V1.0
 **/
public interface IBaseFacilitiesTollStationLaneService extends IService<FacilitiesTollStationLane> {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<FacilitiesTollStationLane> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<FacilitiesTollStationLane> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     * @param id
     * @return
     */
    FacilitiesTollStationLane detail(String id);

    /**
     * 新增
     * @param facilitiesTollStationLane
     * @return
     */
    int create(FacilitiesTollStationLane facilitiesTollStationLane);

    /**
     * 修改
     * @param facilitiesTollStationLane
     * @return
     */
    int update(FacilitiesTollStationLane facilitiesTollStationLane);

    /**
     * 删除
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
