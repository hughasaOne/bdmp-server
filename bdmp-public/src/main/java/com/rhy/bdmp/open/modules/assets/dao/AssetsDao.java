package com.rhy.bdmp.open.modules.assets.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rhy.bdmp.open.modules.assets.domain.bo.FacilitiesStatBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.MileageBo;
import com.rhy.bdmp.open.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.open.modules.assets.domain.po.Org;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description:
 * @Date: 2021/11/5 10:19
 * @Version: 1.0.0
 */
public interface AssetsDao {

    List<Org> getOrgList(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                         @Param("orgId") String orgId,
                         @Param("nodeType") String nodeType);

    /**
     * 获取最上级系统字典
     *
     * @return List
     */
    List<DictSystem> getSuperiorSystemDict();

    /**
     * 根据pid查找系统字典
     *
     * @param parentId pid
     * @return List
     */
    List<DictSystem> listByPid(@Param("parentId") String parentId);

    /**
     * 根据系统id列表查找子系统
     *
     * @param systemIds 系统id
     * @return List
     */
    List<DictSystem> listBySystemIds(@Param("systemIds") List<String> systemIds);

    /**
     * 获取org介绍
     *
     * @param orgId /
     * @return /
     */
    HashMap<String, Object> getOrgIntroduce(String orgId);


    /**
     * 获取机构设施统计信息(分页)
     *
     * @param page                 分页对象
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型(运营集团、运营公司、路段、设施)
     * @param name                 机构名称
     * @return
     */
    <E extends IPage<FacilitiesStatBo>> E getFacStatByOrg(IPage<FacilitiesStatBo> page,
                                                          @Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                          @Param("userId") String userId,
                                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                          @Param("orgId") String orgId,
                                                          @Param("nodeType") String nodeType,
                                                          @Param("name") String name);

    /**
     * 获取机构设施统计信息
     *
     * @param isUseUserPermissions 是否带权限
     * @param orgId                节点ID
     * @param nodeType             节点类型(运营集团、运营公司、路段、设施)
     * @param name                 路段名称
     * @return
     */
    List<FacilitiesStatBo> getFacStatByOrg(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                           @Param("userId") String userId,
                                           @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                           @Param("orgId") String orgId,
                                           @Param("nodeType") String nodeType,
                                           @Param("name") String name);

    /**
     * 获取运营公司里程列表
     *
     * @param isUseUserPermissions 是否带权限
     * @return
     */
    List<MileageBo> queryMileageListByOrg(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                          @Param("userId") String userId,
                                          @Param("dataPermissionsLevel") Integer dataPermissionsLevel);

    /**
     * 机构员工统计
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId               用户id
     * @param dataPermissionsLevel 用户权限级别(机构,路段,设施)
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     */
    List<Map<String, Object>> employeesStat(@Param("isUseUserPermissions") Boolean isUseUserPermissions,
                                                         @Param("userId") String userId,
                                                         @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                         @Param("orgId") String orgId,
                                                         @Param("nodeType") String nodeType);
}
