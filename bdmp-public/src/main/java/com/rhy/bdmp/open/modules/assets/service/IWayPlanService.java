package com.rhy.bdmp.open.modules.assets.service;

import com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanBO;
import com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanDetailBO;

import java.util.List;

/**
 * Created on 2021/11/12.
 * 计划里程
 * @author duke
 */
public interface IWayPlanService {

    /**
     * 高速-获取预计总里程
     * @param isUseUserPermissions 是否带用户权限
     * @param orgId 节点id
     * @param nodeType 节点类型
     * @return com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanBO
     */
    WayPlanBO queryWayPlan(boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 获取预计里程详情
     * @return
     */
    List<WayPlanDetailBO> queryWayPlanDetail(Boolean isUseUserPermissions, String orgId, String nodeType);
}
