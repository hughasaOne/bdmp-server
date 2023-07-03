package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.system.modules.sys.dao.AppDao;
import com.rhy.bdmp.system.modules.sys.dao.ResourceDao;
import com.rhy.bdmp.system.modules.sys.dao.RoleDao;
import com.rhy.bdmp.system.modules.sys.dao.UserDao;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceQueryVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.ResourceVo;
import com.rhy.bdmp.system.modules.sys.service.ResourceService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements ResourceService {
    @Resource
    private ResourceDao resourceDao;

    @Resource
    private AppDao appDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserDao userDao;

    /**
    * @Description: 删除资源
    * @Author: dongyu
    * @Date: 2021/4/15
    */
    @Transactional
    @Override
    public void delete(Set<String> resourceIds) {
        if (CollectionUtils.isNotEmpty(resourceIds)) {
            //如果只传一个id，判断id是否存在
            if (resourceIds.size() == 1) {
                if (resourceDao.selectById(String.valueOf(resourceIds.toArray()[0])) == null) {
                    throw new BadRequestException("资源ID不存在");
                }
            }
            for (String resourceId : resourceIds) {
                //判断是否存在子资源
                List<ResourceVo> resouceChilerenIds = resourceDao.findResouceChildren(resourceId);
                if (CollectionUtils.isNotEmpty(resouceChilerenIds) && resouceChilerenIds.size() > 1) {
                    throw new BadRequestException("不能直接删除存在子节点的资源");
                }
            }

        //同时删除角色资源权限表
        resourceDao.deleteRoleResourceByResourceId(resourceIds);
        //删除资源表
        resourceDao.deleteBatchIds(resourceIds);
        }
    }

    /**
     * @Description: 查询应用资源树节点
     * @Author: dongyu
     * @Date: 2021/4/15
     * @param resourceQueryVo
     */
    @Override
    public List<NodeVo> findAppResourceTree(ResourceQueryVo resourceQueryVo) {
        List<NodeVo> result = new ArrayList<>();
        //获取当前用户
        String userId = WebUtils.getUserId();
        if (StringUtils.isBlank(userId)) {
            throw new BadRequestException("未获取到当前用户");
        }
        //应用ID
        String appId = null;
        //资源Type集合
        Set<Integer> resourceTypes = new HashSet<>();
        if (resourceQueryVo != null) {
            appId = resourceQueryVo.getAppId();
            resourceTypes = resourceQueryVo.getResourceTypes();
        }
        if (CollectionUtils.isEmpty(resourceTypes)) {
            resourceTypes.add(1);
            resourceTypes.add(2);
            resourceTypes.add(3);
        }
        List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resources = new ArrayList<>();
        if (StrUtil.isNotBlank(appId)) {
            //根据应用id查找资源id
            resources = resourceDao.selectList(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>().eq("app_id", appId).in("resource_type",resourceTypes));
            if (CollectionUtils.isNotEmpty(resources)) {
                //资源集合去重
                List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> finalResources = resources.stream().distinct().collect(Collectors.toList());
                //构造资源Node节点
                if (CollectionUtils.isNotEmpty(finalResources)) {
                    for (com.rhy.bdmp.base.modules.sys.domain.po.Resource resource : finalResources) {
                        NodeVo nodeVo = new NodeVo();
                        nodeVo.setId(resource.getResourceId());
                        nodeVo.setLabel(resource.getResourceTitle());
                        nodeVo.setValue(resource.getResourceId());
                        nodeVo.setParentId(resource.getParentId());
                        nodeVo.setNoteType("resource");
                        nodeVo.setMoreInfo(resource);
                        result.add(nodeVo);
                    }
                }
            }
        }
        return result;
    }

    /**
    * @Description: 新增资源
    * @Author: dongyu
    * @Date: 2021/4/20
    */
    @Override
    public int create(com.rhy.bdmp.base.modules.sys.domain.po.Resource resource) {
        if (StrUtil.isNotBlank(resource.getResourceId())) {
            throw new BadRequestException("资源ID已存在，不能做新增操作");
        }
        //parentId是否存在
        if (StrUtil.isNotBlank(resource.getParentId())) {
            if (resourceDao.selectById(resource.getParentId()) == null) {
                throw new BadRequestException("资源parentId不存在");
            }
        }
        // 应用ID是否存在
        if (StrUtil.isNotBlank(resource.getAppId())) {
            if (appDao.selectById(resource.getAppId()) == null) {
                throw new BadRequestException("应用ID不存在");
            }
        }else {
            //应用ID不能为空
            throw new BadRequestException("应用ID不能为空");
        }
        //同一个应用下组件名称不能重复
        if (StrUtil.isNotBlank(resource.getComponentName())) {
            List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resourceByName = resourceDao.selectList(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>().eq("app_id", resource.getAppId()).eq("component_name", resource.getComponentName()));
            if (CollectionUtils.isNotEmpty(resourceByName)) {
                throw new BadRequestException("组件名称不能重复");
            }
        }
        //datastatusid默认为1
        if (resource.getDatastatusid() == null) {
            resource.setDatastatusid(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        resource.setCreateBy(currentUser);
        resource.setCreateTime(currentDateTime);
        resource.setUpdateBy(currentUser);
        resource.setUpdateTime(currentDateTime);
        int result = resourceDao.insert(resource);
        return result;
    }

    /**
     * 修改资源
     * @param resource
     * @return
     */
    @Override
    public int update(com.rhy.bdmp.base.modules.sys.domain.po.Resource resource) {
        if (StrUtil.isBlank(resource.getResourceId())) {
            throw new BadRequestException("资源ID不能为空");
        }else {
            //资源ID不存在
            if (resourceDao.selectById(resource.getResourceId()) == null) {
                throw new BadRequestException("资源ID不存在");
            }
        }
        //parentId是否存在
        if (StrUtil.isNotBlank(resource.getParentId())) {
            if (resourceDao.selectById(resource.getParentId()) == null) {
                throw new BadRequestException("资源parentId不存在");
            }
        }
        // 应用ID是否存在
        if (resource.getAppId() != null) {
            if (appDao.selectById(resource.getAppId()) == null) {
                throw new BadRequestException("appId不存在");
            }
        }
        //同一个应用下组件名称不能重复
        if (StrUtil.isNotBlank(resource.getComponentName())) {
            com.rhy.bdmp.base.modules.sys.domain.po.Resource resourceByName = resourceDao.selectOne(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>().eq("app_id", resource.getAppId()).eq("component_name", resource.getComponentName()));
            if (resourceByName != null && !resourceByName.getResourceId().equals(resource.getResourceId())) {
                throw new BadRequestException("组件名称不能重复");
            }
        }
        //不能设置自己以及自己的下级资源为父级
        if (StrUtil.isNotBlank(resource.getParentId())) {
            //查找当前资源的子节点（包括当前节点）
            List<ResourceVo> resouceChildren = resourceDao.findResouceChildren(resource.getResourceId());
            if (CollectionUtils.isNotEmpty(resouceChildren)) {
                List<String> resourceIds = resouceChildren.stream().map(ResourceVo::getResourceId).distinct().collect(Collectors.toList());
                if (resourceIds.contains(resource.getParentId())) {
                    throw new BadRequestException("不能修改当前节点以及下级节点为父节点");
                }
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        resource.setUpdateBy(currentUser);
        resource.setUpdateTime(currentDateTime);
        int result = resourceDao.updateById(resource);
        return result;
    }

    /**
     * 资源列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public Object list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<com.rhy.bdmp.base.modules.sys.domain.po.Resource> query = new Query<com.rhy.bdmp.base.modules.sys.domain.po.Resource>(queryVO);
            Page<com.rhy.bdmp.base.modules.sys.domain.po.Resource> page = query.getPage();
            QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource> queryWrapper = query.getQueryWrapper();
            queryWrapper.orderByAsc("sort").orderByDesc("create_time");
            if (null != page) {
                page = resourceDao.selectPage(page, queryWrapper);
                //获取Resource
                List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resourceList = page.getRecords();
                //Resource转换成ResourceVo
                List<ResourceVo> resourceVoList = convertVo(resourceList);
                Page<ResourceVo> resourceVoPage = new Page<>();
                //User的分页信息复制到UserVo的分页信息
                BeanUtils.copyProperties(page, resourceVoPage);
                resourceVoPage.setRecords(resourceVoList);
                return resourceVoPage;
            }
            List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> entityList = resourceDao.selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(resourceDao.selectList(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>().orderByAsc("sort").orderByDesc("create_time")));
    }

    /**
     * 查看资源(根据ID)
     * @param resourceId
     * @return
     */
    @Override
    public ResourceVo detail(String resourceId) {
        if (!StrUtil.isNotBlank(resourceId)) {
            throw new BadRequestException("资源ID不能为空");
        }
        com.rhy.bdmp.base.modules.sys.domain.po.Resource resource = resourceDao.selectById(resourceId);
        if (resource != null) {
            List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resources = new ArrayList<>();
            resources.add(resource);
            return convertVo(resources).get(0);
        }
        return null;
    }

    /**
     * Resource转换成ResourceVo
     * @param resourceList
     * @return
     */
    public List<ResourceVo> convertVo(List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resourceList) {
        //资源列表
        List<com.rhy.bdmp.base.modules.sys.domain.po.Resource> resources = resourceDao.selectList(new QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource>());
        //资源Vo列表
        List<ResourceVo> resourceVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(resourceList)) {
            for (com.rhy.bdmp.base.modules.sys.domain.po.Resource resource : resourceList) {
                ResourceVo resourceVo = new ResourceVo();
                BeanUtils.copyProperties(resource, resourceVo);
                if (CollectionUtils.isNotEmpty(resources)) {
                    //上级资源标题、上级资源类型
                    resources.forEach(o -> {
                        if (o.getResourceId().equals(resource.getParentId())) {
                            resourceVo.setParentTitle(o.getResourceTitle());
                            resourceVo.setParentType(o.getResourceType());
                        }
                    });
                }
                resourceVoList.add(resourceVo);
            }
        }
        return resourceVoList;
    }

    /**
     * @Description: 查询当前用户拥有的资源权限（返回资源树节点）
     * @Author: dongyu
     * @Date: 2021/4/27
     * @param isUseUserPermissions
     * @param appId
     */
    @Override
    public List<NodeVo> findResourcesByCurrentUser(Boolean isUseUserPermissions, String appId) {
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
            List<ResourceVo> resourceVoList = resourceDao.selectResourceByTypeAndRole(userId, appId);
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
            queryWrapper.and(wrapper -> wrapper.eq("resource_type", 1).or().eq("resource_type", 2));
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
        //去重
        List<NodeVo> distinctResult = new ArrayList<>();
        distinctResult = result.stream().distinct().collect(Collectors.toList());
        return distinctResult;
    }

    /**
     * @Description: 查询当前用户拥有的目录、菜单、按钮资源权限（返回资源树节点）
     * @Author: dongyu
     * @Date: 2021/4/27
     * @param isUseUserPermissions
     * @param appId
     */
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
        //去重
        List<NodeVo> distinctResult = new ArrayList<>();
        distinctResult = result.stream().distinct().collect(Collectors.toList());
        return distinctResult;
    }

    public List<Tree<String>> getUserMenuTree(Boolean isUseUserPermissions, String appId){
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
        List<ResourceVo> resourceVoList;
        if (isUseUserPermissions) {
            //查询当前用户拥有的目录和菜单资源
            resourceVoList = resourceDao.selectResourceByTypeAndRole2(userId, appId);
        } else {
            //不按权限过滤根据应用查所有目录、菜单
            resourceVoList = resourceDao.getUserMenu(appId);
        }
        //去重
        List<ResourceVo> distinctResult;
        distinctResult = resourceVoList.stream().distinct().collect(Collectors.toList());

        //配置
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

    /**
     * @Description: 查询资源子节点（根据应用ID、资源ID）
     * @Author: dongyu
     * @Date: 2021/5/7
     * @return
     */
    @Override
    public List<ResourceVo> findResourceChildren(String appId, String parentId, String includeId, QueryVO queryVO) {
        List<ResourceVo> resourceVoList = new ArrayList<>();
        if (StrUtil.isNotBlank(appId)) {
            Query<ResourceVo> query = new Query<>(queryVO);
            QueryWrapper<ResourceVo> queryWrapper = query.getQueryWrapper();
            resourceVoList = resourceDao.findResourceChildren(appId, parentId, includeId, queryWrapper);
        } else {
            throw new BadRequestException("应用ID不能为空");
        }
        return resourceVoList;
    }

    /**
     * 根据父节点获取资源列表
     * @param queryVO 查询条件
     * @return
     */
    public List<ResourceVo> listByParentId(QueryVO queryVO){
        //1、有查询条件
        if (null != queryVO) {
            Query<com.rhy.bdmp.base.modules.sys.domain.po.Resource> query = new Query<com.rhy.bdmp.base.modules.sys.domain.po.Resource>(queryVO);
            QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource> queryWrapper = query.getQueryWrapper();

            Map<String, Object> paramsMap = queryVO.getParamsMap();
            String parentId = MapUtil.getStr(paramsMap, "parentId");
            if (StrUtil.isNotBlank(parentId)){
                queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getParentId, parentId)
                        .orderByAsc(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getSort)
                        .orderByDesc(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getCreateTime);
            }
            else {
                queryWrapper.and(wrapper -> wrapper.isNull("parent_id")
                        .or(wrapper1 -> wrapper1.eq("parent_id","")));
                if (0 == queryWrapper.getExpression().getNormal().size() && StrUtil.isBlank(queryWrapper.getExpression().getSqlSegment())){
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().nested(i -> i.isNull(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getParentId)
                                    .or()
                                    .eq(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getParentId, ""))
                                    .orderByAsc(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getSort)
                                    .orderByDesc(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getCreateTime);
                }
                else {
                    queryWrapper.orderByAsc("sort").orderByDesc("create_time");
                }
            }
            return resourceDao.listByParentId(queryWrapper);
        }
        //2、无查询条件
        QueryWrapper<com.rhy.bdmp.base.modules.sys.domain.po.Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().nested(i -> i.isNull(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getParentId).or().eq(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getParentId, "")).orderByAsc(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getSort).orderByDesc(com.rhy.bdmp.base.modules.sys.domain.po.Resource::getCreateTime);
        return resourceDao.listByParentId(queryWrapper);
    }
}
