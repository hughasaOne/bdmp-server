package com.rhy.bdmp.open.modules.system.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bdmp.open.modules.system.domain.vo.MicroUserVo;
import com.rhy.bdmp.open.modules.system.domain.vo.UserPassVo;

import java.util.List;

/**
 * 系统服务
 *
 * @author weicaifu
 */
public interface SystemService {
    List<Tree<String>> getUserMenuTree(Boolean isUseUserPermissions, String appId);

    List<Tree<String>> getOrgUserTree();

    /**
     * 查询(Micro)用户信息,根据用户ID
     *
     * @return
     */
    MicroUserVo getMicroUserInfo();

    /**
     * 查询当前用户拥有的目录、菜单、按钮资源权限
     *
     * @param isUseUserPermissions 权限
     * @param appId                应用id
     * @return
     */
    List<NodeVo> findResourcesByCurrentUser2(Boolean isUseUserPermissions, String appId);

    /**
     * 修改密码
     * @param userPassVo
     * @return
     * @throws Exception
     */
    int updatePass(UserPassVo userPassVo) throws Exception;
}
