package com.rhy.bdmp.open.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import com.jtkj.cloud.common.vo.UserNewVo;
import com.rhy.bdmp.open.modules.assets.domain.bo.DataFacBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.Org;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;

import java.util.List;

/**
 * 权限处理
 *
 * @author yanggj
 */
public interface IAssetsPermissionsTreeService {

    /**
     * 获取机构树
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param orgId                节点id
     * @param nodeType             节点类型
     * @return List
     */
    List<Tree<String>> getAssetsOrgWayFacTree(Boolean isUseUserPermissions, String orgId, String nodeType);

    /**
     * 2.7.2.1 组织-数据资产树（集团-运营公司）
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param isAsync              是否异步（是否向下查找）
     */
    List<Tree<String>> getGroupOrgTree(Boolean isUseUserPermissions, String orgId, String nodeType,Boolean isAsync);

    // 2.7.2.2 组织路段-数据资产树（集团-运营公司-路段）
    List<Tree<String>> getGroupOrgWayTree(Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync);

    // 2.7.2.3 设施一级
    List<Tree<String>> getFac(DataFacBo dataFacBo);

    // 2.7.2.4 设施二级
    List<Tree<String>> getSubFac(DataFacBo dataFacBo);

    /**
     * 获取org
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return List
     */
    List<Org> getAssetsOrg(Boolean isUseUserPermissions);

    /**
     * 获取org
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param nodeType             节点类型
     * @param orgId                节点id
     * @return List
     */
    List<Org> getAssetsOrg(Boolean isUseUserPermissions, String nodeType, String orgId);

    /**
     * 获取路段
     *
     * @param isUseUserPermissions /
     * @return List
     */
    List<Waysection> getAssetsWay(Boolean isUseUserPermissions);

    /**
     * 获取路段
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param nodeType             节点类型
     * @param orgId                节点id
     * @return List
     */
    List<Waysection> getAssetsWay(Boolean isUseUserPermissions, String nodeType, String orgId);

    /**
     * 获取设施
     *
     * @param isUseUserPermissions /
     * @return List
     */
    List<Facilities> getAssetsFac(Boolean isUseUserPermissions);

    /**
     * 获取设施(收费站、门架、桥梁、隧道、服务区)
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param nodeType             节点类型
     * @param orgId                节点id
     * @return List
     */
    List<Facilities> getAssetsFac(Boolean isUseUserPermissions, String nodeType, String orgId);

    /**
     * 获取设施
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param nodeType             节点类型
     * @param orgId                节点id
     * @param facilitiesTypes      设施类型ID集合
     * @return List
     */
    List<Facilities> getAssetsFac(Boolean isUseUserPermissions, String nodeType, String orgId, List<String> facilitiesTypes);

    /**
     * 获取当前登录用户,用户不存在时抛出异常
     *
     * @return User
     */
    User getCurrentUser();
    UserNewVo getCurrentUserNew();

    /**
     * 获取用户权限
     *
     * @param isUseUserPermissions /
     * @return /
     */
    UserPermissions getUserPermissions(Boolean isUseUserPermissions);

    Object getOrgByWay(String wayId,String orgTypes);
}
