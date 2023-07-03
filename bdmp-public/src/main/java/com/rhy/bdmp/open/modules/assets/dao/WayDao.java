package com.rhy.bdmp.open.modules.assets.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jtkj.cloud.db.tenant.anno.IgnorePemission;
import com.jtkj.cloud.db.tenant.anno.IgnoreTenant;
import com.rhy.bdmp.open.modules.assets.domain.bo.FacilitiesStatBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.MileageBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.WaySectionShort;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description:
 * @Date: 2021/9/28 9:03
 * @Version: 1.0.0
 */
@IgnoreTenant
@IgnorePemission
public interface WayDao {

    /**
     * 获取总里程数
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型
     * @return String
     */
    Map<String, Object> getTotalMileage(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                        @Param("userId") String userId,
                                        @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                        @Param("orgId") String orgId,
                                        @Param("nodeType") String nodeType);

    WaySectionShort getWaySectionShort(@Param("waysectionId") String orgId);

    /**
     * 获取路段设施统计信息(分页)
     *
     * @param page                 分页对象
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型(运营集团、运营公司、路段、设施)
     * @param name                 路段名称
     * @return
     */
    <E extends IPage<FacilitiesStatBo>> E getFacStatByWay(IPage<FacilitiesStatBo> page,
                                                          @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                          @Param("userId") String userId,
                                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                          @Param("orgId") String orgId,
                                                          @Param("nodeType") String nodeType,
                                                          @Param("name") String name);

    /**
     * 获取路段设施统计信息
     *
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型(运营集团、运营公司、路段、设施)
     * @param name                 路段名称
     * @return
     */
    List<FacilitiesStatBo> getFacStatByWay(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                           @Param("userId") String userId,
                                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                           @Param("orgId") String orgId,
                                           @Param("nodeType") String nodeType,
                                           @Param("name") String name);

    /**
     * 获取路段里程列表
     *
     * @param isUseUserPermissions  是否带权限
     * @param orgId 运营公司ID
     * @return
     */
    List<MileageBo> queryMileageListByWay(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                          @Param("userId") String userId,
                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                          @Param("orgId") String orgId);

    /**
     * 2.80.9 查询路段列表
     * @param isUseUserPermissions 是否带用户数据权限
     * @param userId 用户ID
     * @param dataPermissionsLevel 权限级别
     * @param orgId 节点ID(集团、运营公司、路段)
     * @param nodeType 节点类型
     */
    Page<Waysection> getWayList(Page<Waysection> page,
                                @Param("isUseUserPermissions")Boolean isUseUserPermissions,
                                @Param("userId")String userId,
                                @Param("dataPermissionsLevel")Integer dataPermissionsLevel,
                                @Param("orgId")String orgId,
                                @Param("nodeType")String nodeType);

    /**
     * 2.80.10 查询路段详情
     * @param wayId 路段id
     */
    Waysection getWayById(@Param("wayId") String wayId);
}
