package com.rhy.bdmp.system.modules.sys.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceQueryVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceVo;

import java.util.List;
import java.util.Set;

public interface ResourceService {
    /**
    * @Description: 删除资源
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    void delete(Set<String> resourceIds);

    /**
    * @Description: 查询应用资源树节点
    * @Author: dongyu
    * @Date: 2021/4/15
     * @param resourceQueryVo
     */
    List<NodeVo> findAppResourceTree(ResourceQueryVo resourceQueryVo);

    /**
    * @Description: 新增资源
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int create(Resource resource);

    /**
    * @Description: 修改资源
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    int update(Resource resource);

    /**
     * 资源列表查询
     * @param queryVO 查询条件
     * @return
     */
    Object list(QueryVO queryVO);

    /**
     * 查看资源(根据ID)
     * @param resourceId
     * @return
     */
    ResourceVo detail(String resourceId);

    /**
    * @Description: 查询当前用户拥有的资源权限
    * @Author: dongyu
    * @Date: 2021/4/27
     * @param isUseUserPermissions
     * @param appId
     */
    List<NodeVo> findResourcesByCurrentUser(Boolean isUseUserPermissions, String appId);

    /**
    * @Description: 查询当前用户拥有的目录、菜单、按钮资源权限
    * @Author: dongyu
    * @Date: 2021/4/27
     * @param isUseUserPermissions
     * @param appId
     */
    List<NodeVo> findResourcesByCurrentUser2(Boolean isUseUserPermissions, String appId);

    List<Tree<String>> getUserMenuTree(Boolean isUseUserPermissions, String appId);

    /**
    * @Description: 查询资源子节点（根据应用ID、资源ID）
    * @Author: dongyu
    * @Date: 2021/5/7
     * @return
    */
    List<ResourceVo> findResourceChildren(String appId, String parentId, String includeId, QueryVO queryVO);

    /**
     * 根据父节点获取资源列表
     * @param queryVO 查询条件
     * @return
     */
    List<ResourceVo> listByParentId(QueryVO queryVO);
}
