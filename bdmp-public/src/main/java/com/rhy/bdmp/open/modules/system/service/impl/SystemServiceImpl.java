package com.rhy.bdmp.open.modules.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jtkj.cloud.common.utils.BaseDataUtil;
import com.jtkj.cloud.common.utils.UserInfoUtil;
import com.jtkj.cloud.common.vo.UserNewVo;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.exception.EntityNotFoundException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.open.modules.system.dao.MicroUserDao;
import com.rhy.bdmp.open.modules.system.dao.ResourceDao;
import com.rhy.bdmp.open.modules.system.dao.SystemDao;
import com.rhy.bdmp.open.modules.system.dao.UserDao;
import com.rhy.bdmp.open.modules.system.domain.vo.*;
import com.rhy.bdmp.open.modules.system.service.SystemService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统服务
 * @author weicaifu
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private IAssetsPermissionsTreeService permissionsTreeService;
    @Resource
    private SystemDao systemDao;
    @Resource
    private MicroUserDao microUserDao;
    @Resource
    private UserDao userDao;
    @Resource
    private ResourceDao resourceDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Tree<String>> getUserMenuTree(Boolean isUseUserPermissions, String appId) {
        // 获取当前用户信息
        //User user = permissionsTreeService.getCurrentUser();
        UserNewVo user=permissionsTreeService.getCurrentUserNew();
        //判断用户是否为admin
        if (null != user.getIsAdmin() && user.getIsAdmin() == 1) {
            isUseUserPermissions = false;
        }

        //isUseUserPermissions等于true时按权限查找,isUseUserPermissions等于false查所有
        List<ResourceVo> resourceVoList;
        if (isUseUserPermissions) {
            //查询当前用户拥有的目录和菜单资源
            resourceVoList = systemDao.getUserMenuByPermissions(String.valueOf(user.getUserId()), appId);
        } else {
            //不按权限过滤根据应用查所有目录、菜单
            resourceVoList = systemDao.getUserMenu(appId);
        }
        //去重
        List<ResourceVo> distinctResult;
        distinctResult = resourceVoList.stream().distinct().collect(Collectors.toList());

        // 构建树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("sort");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("componentName");

        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        Map<String, Object> nodeMap;
        Map<String, Object> metaMap;
        for (ResourceVo resourceVo : distinctResult) {
            long sort = null == resourceVo.getSort() ? 0l : resourceVo.getSort();
            nodeMap = new HashMap<String,Object>();
            nodeMap.put("name",resourceVo.getComponentName());
            nodeMap.put("nickName",resourceVo.getComponentName());
            if (3 == resourceVo.getResourceType()){
                nodeMap.put("permission",resourceVo.getPermission());
                nodeList.add(new TreeNode<>(resourceVo.getResourceId(), resourceVo.getParentId(), resourceVo.getResourceTitle(), sort)
                        .setExtra(nodeMap));
            }
            else{
                metaMap = new HashMap<String,Object>();
                nodeMap.put("path",resourceVo.getPath());
                nodeMap.put("componentPath",resourceVo.getComponentPath());
                nodeMap.put("resourceType",resourceVo.getResourceType());

                metaMap.put("title",resourceVo.getResourceTitle());
                metaMap.put("nickName",resourceVo.getComponentName());
                metaMap.put("resourceType",resourceVo.getResourceType());
                metaMap.put("icon",resourceVo.getIcon());
                metaMap.put("cache",resourceVo.getCache());
                metaMap.put("hidden",resourceVo.getHidden());
                metaMap.put("externalLink",resourceVo.getExternalLink());
                metaMap.put("externalLinkUrl",resourceVo.getExternalLinkUrl());
                metaMap.put("externalLinkOpen",resourceVo.getExternalLinkOpen());

                nodeMap.put("meta",metaMap);

                if (StrUtil.isBlank(resourceVo.getParentId())){
                    nodeList.add(new TreeNode<>(resourceVo.getResourceId(), "0", resourceVo.getComponentName(), sort)
                            .setExtra(nodeMap));
                }
                else{
                    nodeList.add(new TreeNode<>(resourceVo.getResourceId(), resourceVo.getParentId(), resourceVo.getComponentName(), sort)
                            .setExtra(nodeMap));
                }
            }
        }

        //转换器
        return TreeUtil.build(nodeList, "0", treeNodeConfig,new DefaultNodeParser<>());
    }

    @Override
    public List<Tree<String>> getOrgUserTree(){
        // 获取组织列表
        List<OrgVo> orgVoList = systemDao.getOrgList();
        // 获取用户列表
        List<UserVo> userVoList = systemDao.getUserList();
        // 分组
        Map<String, List<UserVo>> userListByOrgId = userVoList.stream().collect(Collectors.groupingBy(UserVo::getOrgId));
        for (OrgVo orgVo : orgVoList) {
            for (String orgId : userListByOrgId.keySet()) {
                if (orgVo.getOrgId().equals(orgId)){
                    orgVo.setUserList(userListByOrgId.get(orgId));
                }
            }
        }

        // 构建树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("sort");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("nickName");

        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        Map<String, Object> extra;
        Map<String, Object> userExtra;
        for (OrgVo orgVo : orgVoList) {
            extra = new HashMap<>();
            extra.put("orgName",orgVo.getOrgName());
            extra.put("orgCode",orgVo.getOrgCode());
            extra.put("orgType",orgVo.getOrgType());
            extra.put("description",orgVo.getDescription());
            extra.put("nodeId",orgVo.getNodeId());
            extra.put("nodeType","org");
            if (StrUtil.isBlank(orgVo.getParentId())){
                nodeList.add(new TreeNode<>(orgVo.getOrgId(), "0", orgVo.getOrgShortName(),
                        orgVo.getSort()).setExtra(extra));
                if (CollUtil.isNotEmpty(orgVo.getUserList())){
                    for (UserVo userVo : orgVo.getUserList()) {
                        userExtra = new HashMap<>();
                        userExtra.put("moreInfo",userVo);
                        userExtra.put("nodeType","user");
                        nodeList.add(new TreeNode<>(userVo.getUserId(),userVo.getOrgId(),userVo.getNickName(),1L).setExtra(userExtra));
                    }
                }
            }
            else{
                nodeList.add(new TreeNode<>(orgVo.getOrgId(), orgVo.getParentId(), orgVo.getOrgShortName(),
                        orgVo.getSort()).setExtra(extra));
                if (CollUtil.isNotEmpty(orgVo.getUserList())){
                    for (UserVo userVo : orgVo.getUserList()) {
                        userExtra = new HashMap<>();
                        userExtra.put("moreInfo",userVo);
                        userExtra.put("nodeType","user");
                        nodeList.add(new TreeNode<>(userVo.getUserId(),userVo.getOrgId(),userVo.getNickName(),1L).setExtra(userExtra));
                    }
                }
            }
        }

        //转换器
        return TreeUtil.build(nodeList, "0", treeNodeConfig,new DefaultNodeParser<>());
    }

    @Override
    public MicroUserVo getMicroUserInfo() {
        //查询当前用户Id
        //String userId = WebUtils.getUserId();
        Long userId = UserInfoUtil.getUserIdByHeader();
        List<UserNewVo> userNewVoList= BaseDataUtil.selectUserVoById(userId);
        /*if (StrUtil.isBlank(userId.toString())) {
            throw new BadRequestException("未获取到当前用户");
        }*/



        //userNewVoList.get
        //MicroUserVo microUserInfo = microUserDao.getMicroUserInfo(String.valueOf(userId));


        MicroUserVo microUserInfo=microUserDao.getMicroOrgInfoByOrgId(userNewVoList.get(0).getOrgId());
        if (null != microUserInfo){
            this.setUserInfo(microUserInfo,microUserInfo.getOrgId());
        }

        microUserInfo.setUserId(String.valueOf(userNewVoList.get(0).getUserId()));
        microUserInfo.setUsername(userNewVoList.get(0).getUserName());
        microUserInfo.setNickName(userNewVoList.get(0).getFullName());
        microUserInfo.setTenantId(userNewVoList.get(0).getTenantId());



        return microUserInfo;
    }

    @Override
    public List<NodeVo> findResourcesByCurrentUser2(Boolean isUseUserPermissions, String appId) {
        List<NodeVo> result = new ArrayList<>();
        //查询当前用户Id
        String userId = WebUtils.getUserId();
        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("未获取到当前用户");
        }
        // 获取当前用户信息
        User user = userDao.selectById(userId);
        //判断用户是否为admin
        if (null != user.getIsAdmin() && user.getIsAdmin() == 1) {
            isUseUserPermissions = false;
        }
        //isUseUserPermissions等于true时按权限查找,isUseUserPermissions等于false查所有
        if (isUseUserPermissions) {
            //查询当前用户拥有的目录和菜单资源
            //只查找基础数据平台应用下的角色
            //只查找目录和菜单资源
            List<ResourceVo> resourceVoList = resourceDao.selectResourceByTypeAndRole2(userId, appId);
            //构造资源Node节点
            if (CollectionUtils.isNotEmpty(resourceVoList)) {
                for (ResourceVo resource : resourceVoList) {
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setId(resource.getResourceId());
                    nodeVo.setLabel(resource.getResourceTitle());
                    nodeVo.setValue(resource.getResourceId());
                    nodeVo.setParentId(resource.getParentId());
                    nodeVo.setSort(resource.getSort());
                    nodeVo.setNoteType(null == resource.getResourceType() ? null : String.valueOf(resource.getResourceType()));
                    nodeVo.setMoreInfo(resource);
                    result.add(nodeVo);
                }
            }
        } else {
            //不按权限过滤根据应用查所有目录、菜单
            QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource> queryWrapper = new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>();
            queryWrapper.eq("app_id", appId);
            queryWrapper.orderByAsc("sort").orderByDesc("create_time");
            List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resourceList = resourceDao.selectList(queryWrapper);
            //构造资源Node节点
            if (CollectionUtils.isNotEmpty(resourceList)) {
                for (com.rhy.bdmp.base.modules.sys.domain.po.Resource resource : resourceList) {
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setId(resource.getResourceId());
                    nodeVo.setLabel(resource.getResourceTitle());
                    nodeVo.setValue(resource.getResourceId());
                    nodeVo.setParentId(resource.getParentId());
                    nodeVo.setSort(resource.getSort());
                    nodeVo.setNoteType(null == resource.getResourceType() ? null : String.valueOf(resource.getResourceType()));
                    nodeVo.setMoreInfo(resource);
                    result.add(nodeVo);
                }
            }
        }
        return result.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public int updatePass(UserPassVo userPassVo) throws Exception {
        if (!StrUtil.isNotBlank(userPassVo.getUserId())) {
            throw new BadRequestException("用户ID不能为空");
        }
        User userDb = userDao.selectOne(new QueryWrapper<User>().eq("user_id", userPassVo.getUserId()));
        if (null == userDb) {
            throw new EntityNotFoundException(User.class, "user_id", userPassVo.getUserId());
        }
        if (!passwordEncoder.matches(userPassVo.getOldPass(), userDb.getPassword())) {
            throw new BadRequestException("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(userPassVo.getNewPass(), userDb.getPassword())) {
            throw new BadRequestException("新密码不能与旧密码相同");
        }
        userDb.setPassword(passwordEncoder.encode(userPassVo.getNewPass()));
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        userDb.setUpdateBy(currentUser);
        userDb.setUpdateTime(currentDateTime);
        return userDao.updateById(userDb);
    }

    private TreeNodeConfig treeNodeConfig() {
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        // 自定义属性名 都要默认值的
        treeNodeConfig.setWeightKey("sort");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("nickName");
        return treeNodeConfig;
    }

    private void setUserInfo(MicroUserVo userVo,String orgId) {
        OrgVo org = systemDao.getOrgById(orgId);
        if (null == org){
            return;
        }
        String orgType = org.getOrgType();
        if ("000300".equals(orgType)){
            userVo.setOperationGroupId(org.getParentId());
            OrgVo parentNode = systemDao.getOrgById(org.getParentId());
            userVo.setOperationGroupName(parentNode.getOrgName());
            userVo.setOperationGroupShortName(parentNode.getOrgShortName());

            userVo.setOperationCompanyId(org.getOrgId());
            userVo.setOperationCompanyName(org.getOrgName());
            userVo.setOperationCompanyShortName(org.getOrgShortName());
        }
        else if("000200".equals(orgType)){
            userVo.setOperationGroupId(org.getOrgId());
            userVo.setOperationGroupName(org.getOrgName());
            userVo.setOperationGroupShortName(org.getOrgShortName());
            userVo.setOperationCompanyId(null);
        }
        else if(StrUtil.isBlank(org.getParentId()) || "000900".equals(orgType)){
            userVo.setOperationCompanyId(null);
            userVo.setOperationGroupId(null);
        }
        else {
            this.setUserInfo(userVo,org.getParentId());
        }
    }
}
