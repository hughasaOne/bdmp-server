package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.WaysectionPlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 计划里程 服务接口
 * @author duke
 * @date 2021-11-12 10:58
 * @version V1.0
 **/
public interface IBaseWaysectionPlanService extends IService<WaysectionPlan> {

    /**
     * 计划里程列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<WaysectionPlan> list(QueryVO queryVO);

    /**
     * 计划里程列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<WaysectionPlan> page(QueryVO queryVO);

    /**
     * 查看计划里程(根据ID)
     * @param id
     * @return
     */
    WaysectionPlan detail(String id);

    /**
     * 新增计划里程
     * @param waysectionPlan
     * @return
     */
    int create(WaysectionPlan waysectionPlan);

    /**
     * 修改计划里程
     * @param waysectionPlan
     * @return
     */
    int update(WaysectionPlan waysectionPlan);

    /**
     * 删除计划里程
     * @param ids
     * @return
     */
    int delete(Set<String> ids);


}
