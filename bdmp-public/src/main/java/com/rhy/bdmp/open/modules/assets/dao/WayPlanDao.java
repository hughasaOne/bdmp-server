package com.rhy.bdmp.open.modules.assets.dao;

import com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanBO;
import com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanDetailBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2021/11/12.
 *
 * @author duke
 */
public interface WayPlanDao {

    /**
     * 高速-获取预计总里程
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @return com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanBO
     */
    WayPlanBO getPlanMileage(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                             @Param("userId") String userId,
                             @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                             @Param("orgId") String orgId,
                             @Param("nodeType") String nodeType);

    /**
     * 获取预计里程详情
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @return java.util.List<com.rhy.bdmp.open.modules.assets.domain.bo.WayPlanDetailBO>
     */
    List<WayPlanDetailBO> getPlanMileageDetail(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                               @Param("userId") String userId,
                                               @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                               @Param("orgId") String orgId,
                                               @Param("nodeType") String nodeType);
}
