package com.rhy.bdmp.system.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bdmp.system.modules.assets.domain.po.User;

import java.util.List;

public interface IAssetsPermissionsTreeService {
    /**
     * 获取当前登录用户
     * @return User
     */
    User getCurrentUser();

    /**
     * 获取台账资源组织树
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return
     */
    List<NodeVo> getAssetsOrgTree(Boolean isUseUserPermissions);

    /**
     * 获取台账资源组织路段树
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return
     */
    List<NodeVo> getAssetsOrgWayTree(Boolean isUseUserPermissions);

    /**
     * 获取台账资源组织路段设施树
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param isShowChildren 是否获取子设施
     * @return
     */
    List<NodeVo> getAssetsOrgWayFacTree(Boolean isUseUserPermissions, Boolean isShowChildren);
    List<Tree<String>> getAssetsOrgWay2Tree(Boolean isUseUserPermissions);
    Object asyncTree(String search, Boolean isUseUserPermissions);
    List<Tree<String>> getAssetsOrgWayFacTree(Boolean isUseUserPermissions);


    List<Tree<String>> getFacByWayId(Boolean isUseUserPermissions, String waysectionId);

    List<Tree<String>> getChildFacilities(Boolean isUseUserPermissions, String facilitiesId);

    Object getUserOrgTree(Boolean isUseUserPermissions);
}
