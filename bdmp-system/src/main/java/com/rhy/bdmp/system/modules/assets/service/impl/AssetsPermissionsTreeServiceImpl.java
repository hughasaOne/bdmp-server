package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.assets.adapter.UserPermissionsAdapter;
import com.rhy.bdmp.system.modules.assets.dao.AssetsPermissionsTreeDao;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacShortVO;
import com.rhy.bdmp.system.modules.assets.domain.vo.NodeExtVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.OrgShortVO;
import com.rhy.bdmp.system.modules.assets.domain.vo.WayShortVO;
import com.rhy.bdmp.system.modules.assets.enums.NodeTypeEnum;
import com.rhy.bdmp.system.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.system.modules.assets.permissions.FacUserPermissions;
import com.rhy.bdmp.system.modules.assets.permissions.OrgUserPermissions;
import com.rhy.bdmp.system.modules.assets.permissions.WayUserPermissions;
import com.rhy.bdmp.system.modules.assets.service.IAssetsPermissionsTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description
 * @date 2021-04-19 11:32
 **/
@Service
public class AssetsPermissionsTreeServiceImpl implements IAssetsPermissionsTreeService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);
    @Resource
    private AssetsPermissionsTreeDao assetsPermissionsTreeDao;

    @Resource
    private OrgUserPermissions orgUserPermissions;

    @Resource
    private WayUserPermissions wayUserPermissions;

    @Resource
    private FacUserPermissions facUserPermissions;

    @Override
    public User getCurrentUser() {
        String userId = WebUtils.getUserId();
        User user = assetsPermissionsTreeDao.getUserById(userId);
        if (null == user) {
            throw new BadRequestException("用户信息不存在");
        }
        return user;
    }


    /*
     * 根据用户id获取用户对象
     * */
    public User getUserById(String userId) {
        return assetsPermissionsTreeDao.getUserById(userId);
    }

    /**
     * 获取台账资源组织树
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return
     */
    @Override
    public List<NodeVo> getAssetsOrgTree(Boolean isUseUserPermissions) {
        List<NodeVo> nodeList = new ArrayList<>();
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        if ((null != dataPermissionsLevel && 0 != dataPermissionsLevel) || !isUseUserPermissions) {
            // 获取运营集团
            List<OrgShortVO> groupList = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel);
            List<OrgShortVO> orgList = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel);
            for (OrgShortVO org : groupList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType(NodeTypeEnum.GROUP.getName());
                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(org.getOrgShortName());
                nodeExtVo.setType(org.getOrgType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }

            for (OrgShortVO org : orgList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType("org");
                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(org.getOrgShortName());
                nodeExtVo.setType(org.getOrgType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }
        } else {
            throw new BadRequestException("您没有浏览该数据的权限");
        }
        return nodeList;
    }

    /**
     * 获取台账资源组织路段树
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return
     */
    @Override
    public List<NodeVo> getAssetsOrgWayTree(Boolean isUseUserPermissions) {
        List<NodeVo> nodeList = new ArrayList<>();
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        if ((null != dataPermissionsLevel && 0 != dataPermissionsLevel) || !isUseUserPermissions) {
            // 获取运营集团
            List<OrgShortVO> groupList = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel);
            List<OrgShortVO> orgList = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel);
            List<WayShortVO> waysectionList = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel);
            for (OrgShortVO org : groupList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType(NodeTypeEnum.GROUP.getName());
                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(org.getOrgShortName());
                nodeExtVo.setType(org.getOrgType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }

            for (OrgShortVO org : orgList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType("org");
                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(org.getOrgShortName());
                nodeExtVo.setType(org.getOrgType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }
            for (WayShortVO waysection : waysectionList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(waysection.getWaysectionId());
                nodeVo.setValue(waysection.getWaysectionId());
                nodeVo.setLabel(waysection.getWaysectionSName());
                nodeVo.setParentId(waysection.getManageId());
                nodeVo.setSort(waysection.getSort());
                nodeVo.setNoteType("way");
                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(waysection.getWaysectionSName());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }
        } else {
            throw new BadRequestException("您没有浏览该数据的权限");
        }
        return nodeList;
    }

    /**
     * 获取台账资源组织路段设施树
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @param isShowChildren       是否获取子设施
     * @return
     */
    @Override
    public List<NodeVo> getAssetsOrgWayFacTree(Boolean isUseUserPermissions, Boolean isShowChildren) {
        List<NodeVo> nodeList = new ArrayList<>();
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        if ((null != dataPermissionsLevel && 0 != dataPermissionsLevel) || !isUseUserPermissions) {
            // 获取运营集团
            List<OrgShortVO> groupList = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel);
            List<OrgShortVO> orgList = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel);
            List<WayShortVO> waysectionList = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel);
            List<FacShortVO> facilitiesList = assetsPermissionsTreeDao.getAssetsFac(isUseUserPermissions, userId, dataPermissionsLevel);
            for (OrgShortVO org : groupList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType(NodeTypeEnum.GROUP.getName());

                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(org.getOrgShortName());
                nodeExtVo.setType(org.getOrgType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }

            for (OrgShortVO org : orgList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType("org");

                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(org.getOrgShortName());
                nodeExtVo.setType(org.getOrgType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }
            for (WayShortVO waysection : waysectionList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(waysection.getWaysectionId());
                nodeVo.setValue(waysection.getWaysectionId());
                nodeVo.setLabel(waysection.getWaysectionSName());
                nodeVo.setParentId(waysection.getManageId());
                nodeVo.setSort(waysection.getSort());
                nodeVo.setNoteType("way");

                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(waysection.getWaysectionSName());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }
            for (FacShortVO facilities : facilitiesList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(facilities.getFacilitiesId());
                nodeVo.setValue(facilities.getFacilitiesId());
                nodeVo.setLabel(facilities.getFacilitiesName());
                nodeVo.setParentId(facilities.getWaysectionId());
                nodeVo.setSort(facilities.getSort());
                nodeVo.setNoteType("fac");

                NodeExtVo nodeExtVo = new NodeExtVo();
                nodeExtVo.setShortName(facilities.getFacilitiesName());
                nodeExtVo.setType(facilities.getFacilitiesType());
                nodeVo.setMoreInfo(nodeExtVo);
                nodeList.add(nodeVo);
            }
            if (isShowChildren) {
                List<Facilities> facilitiesChildrenList = assetsPermissionsTreeDao.getAssetsFacChildren(isUseUserPermissions, userId, dataPermissionsLevel);
                for (Facilities facilities : facilitiesChildrenList) {
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setId(facilities.getFacilitiesId());
                    nodeVo.setValue(facilities.getFacilitiesId());
                    nodeVo.setLabel(facilities.getFacilitiesName());
                    if (StrUtil.isBlank(facilities.getParentId())) {
                        nodeVo.setParentId(facilities.getWaysectionId());
                    } else {
                        nodeVo.setParentId(facilities.getParentId());
                    }
                    nodeVo.setSort(facilities.getSort());
                    nodeVo.setNoteType("fac");

                    NodeExtVo nodeExtVo = new NodeExtVo();
                    nodeExtVo.setShortName(facilities.getFacilitiesName());
                    nodeExtVo.setLongitude(facilities.getLongitude());
                    nodeExtVo.setLatitude(facilities.getLatitude());
                    nodeExtVo.setType(facilities.getFacilitiesType());
                    nodeVo.setMoreInfo(nodeExtVo);
                    //FacilitiesShortVo facilitiesShortVo = new FacilitiesShortVo();
                    //BeanUtil.copyProperties(facilities, facilitiesShortVo);
                    //nodeVo.setMoreInfo(facilitiesShortVo);
                    nodeList.add(nodeVo);
                }
            }
        } else {
            throw new BadRequestException("您没有浏览该数据的权限");
        }
        return nodeList;
    }


    /**
     * 获取台账资源组织路段设施树
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return
     */
    @Override
    public List<Tree<String>> getAssetsOrgWayFacTree(Boolean isUseUserPermissions) {
        List<TreeNode<String>> nodeList = new ArrayList<>();
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        // 获取运营集团
        List<OrgShortVO> groupList = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel);
        // 运营公司
        List<OrgShortVO> orgList = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel);
        // 路段
        List<WayShortVO> waysectionList = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel);
        List<FacShortVO> facList = assetsPermissionsTreeDao.getAssetsFac(isUseUserPermissions, userId, dataPermissionsLevel);
        if (CollUtil.isNotEmpty(groupList)){
            groupList.forEach(group ->
                    nodeList.add(new TreeNode<String>()
                            .setId(group.getOrgId())
                            .setName(group.getOrgName())
                            .setParentId("0")// group的parentId设置为0,方便构造树, 不考虑group的上级机构了
                            .setExtra(JSONUtil.createObj(jsonConfig)
                                    .putOnce("nodeType", NodeTypeEnum.GROUP.getCode())
                                    .putOnce("shortName", group.getOrgShortName())
                                    .putOnce("type", group.getOrgType())
                                    .putOnce("isLeaf", group.getIsLeaf())
                                    .putOnce("checked",false)
                            )));
            orgList.forEach(org ->
                    nodeList.add(new TreeNode<String>()
                            .setId(org.getOrgId())
                            .setName(org.getOrgName())
                            .setParentId(org.getParentId())// parentId为null时,该节点可能会漏掉
                            .setExtra(JSONUtil.createObj(jsonConfig)
                                    .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                    .putOnce("shortName", StrUtil.isBlank(org.getOrgShortName()) ? "default_name" : org.getOrgShortName())
                                    .putOnce("type", org.getOrgType())
                                    .putOnce("isLeaf", org.getIsLeaf())
                                    .putOnce("checked",false)
                            )));

        }
        else {
            orgList.forEach(org ->
                    nodeList.add(new TreeNode<String>()
                            .setId(org.getOrgId())
                            .setName(org.getOrgName())
                            .setParentId("0")// parentId为null时,该节点可能会漏掉
                            .setExtra(JSONUtil.createObj(jsonConfig)
                                    .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                    .putOnce("shortName", StrUtil.isBlank(org.getOrgShortName()) ? "default_name" : org.getOrgShortName())
                                    .putOnce("type", org.getOrgType())
                                    .putOnce("isLeaf", org.getIsLeaf())
                                    .putOnce("checked",false)
                            )));
        }
        waysectionList.forEach(waySection ->
                nodeList.add(new TreeNode<String>()
                        .setId(waySection.getWaysectionId())
                        .setName(waySection.getWaysectionName())
                        .setParentId(waySection.getManageId())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.WAY.getCode())
                                .putOnce("shortName", StrUtil.isBlank(waySection.getWaysectionSName()) ? "default_name" : waySection.getWaysectionSName())
                                .putOnce("type", "way")
                                .putOnce("isLeaf", waySection.getIsLeaf())
                                .putOnce("checked",false)
                        )));
        facList.forEach(fac ->
                nodeList.add(new TreeNode<String>()
                        .setId(fac.getFacilitiesId())
                        .setName(fac.getFacilitiesName())
                        .setParentId(fac.getWaysectionId()).setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType",NodeTypeEnum.FAC.getCode())
                                .putOnce("shortName",StrUtil.isBlank(fac.getFacilitiesName()) ? "default_name" : fac.getFacilitiesName())
                                .putOnce("type","fac")
                                .putOnce("isLeaf",true)
                                .putOnce("checked",false)
                        )));
        // 构造树结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * 获取台账资源组织路段树
     *
     * @param isUseUserPermissions 是否使用用户权限过滤
     * @return
     */
    @Override
    public List<Tree<String>> getAssetsOrgWay2Tree(Boolean isUseUserPermissions) {
        List<TreeNode<String>> nodeList = new ArrayList<>();
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
            else {
                if (null == dataPermissionsLevel || !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                    throw new BadRequestException("您没有浏览该数据的权限");
                }
            }
        }
        // 获取运营集团
        List<OrgShortVO> groupList = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel);
        // 运营公司
        List<OrgShortVO> orgList = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel);
        // 路段
        List<WayShortVO> waysectionList = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel);

        if (CollUtil.isEmpty(groupList)){
            orgList.forEach(org ->
                    nodeList.add(new TreeNode<String>()
                            .setId(org.getOrgId())
                            .setName(org.getOrgName())
                            .setParentId("0")// parentId为null时,该节点可能会漏掉
                            .setExtra(JSONUtil.createObj(jsonConfig)
                                    .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                    .putOnce("shortName", org.getOrgShortName())
                                    .putOnce("type", org.getOrgType())
                                    .putOnce("isLeaf", org.getIsLeaf())
                            )));
        }
        else {
            groupList.forEach(group ->
                    nodeList.add(new TreeNode<String>()
                            .setId(group.getOrgId())
                            .setName(group.getOrgName())
                            .setParentId("0")// group的parentId设置为0,方便构造树, 不考虑group的上级机构了
                            .setExtra(JSONUtil.createObj(jsonConfig)
                                    .putOnce("nodeType", NodeTypeEnum.GROUP.getCode())
                                    .putOnce("shortName", group.getOrgShortName())
                                    .putOnce("type", group.getOrgType())
                                    .putOnce("isLeaf", group.getIsLeaf())
                            )));
            orgList.forEach(org ->
                    nodeList.add(new TreeNode<String>()
                            .setId(org.getOrgId())
                            .setName(org.getOrgName())
                            .setParentId(org.getParentId())// parentId为null时,该节点可能会漏掉
                            .setExtra(JSONUtil.createObj(jsonConfig)
                                    .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                    .putOnce("shortName", org.getOrgShortName())
                                    .putOnce("type", org.getOrgType())
                                    .putOnce("isLeaf", org.getIsLeaf())
                            )));
        }
        waysectionList.forEach(waySection ->
                nodeList.add(new TreeNode<String>()
                        .setId(waySection.getWaysectionId())
                        .setName(waySection.getWaysectionName())
                        .setParentId(waySection.getManageId())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.WAY.getCode())
                                .putOnce("shortName", waySection.getWaysectionSName())
                                .putOnce("type", "way")
                                .putOnce("isLeaf", waySection.getIsLeaf())
                        )));
        // 构造树结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public Object asyncTree(String search, Boolean isUseUserPermissions) {
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        List<Map<String, Object>> mapList = assetsPermissionsTreeDao.asyncTree(search);
        List<Map<String, Object>> resMap = new ArrayList<>();
        Set<String> permissions = new HashSet<>();
        if (isUseUserPermissions){
            if (null == dataPermissionsLevel){
                throw new BadRequestException("用户权限不足！");
            }

            switch (dataPermissionsLevel){
                case 1:
                    permissions = new UserPermissionsAdapter(orgUserPermissions).getPermissions(userId);
                    break;
                case 2:
                    permissions = new UserPermissionsAdapter(wayUserPermissions).getPermissions(userId);
                    break;
                case 3:
                    permissions = new UserPermissionsAdapter(facUserPermissions).getPermissions(userId);
                    break;
                default:
                    throw new BadRequestException("用户权限不足！");
            }

            for (Map<String, Object> map : mapList) {
                String id = MapUtil.getStr(map, "id");
                boolean hasPermission = permissions.stream().anyMatch(permission -> permission.equals(id));
                if (hasPermission){
                    resMap.add(map);
                }
            }
        }
        else {
            resMap.addAll(mapList);
        }
        return resMap;
    }

    @Override
    public List<Tree<String>> getFacByWayId(Boolean isUseUserPermissions, String waysectionId) {
        if (StrUtil.isBlank(waysectionId)) {
            throw new BadRequestException("waysectionId 不得为空");
        }
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        List<FacShortVO> facList = assetsPermissionsTreeDao.getFacByWayId(isUseUserPermissions, userId, dataPermissionsLevel, waysectionId);
        List<TreeNode<String>> nodeList = new ArrayList<>();
        facList.forEach(facilities ->
                nodeList.add(new TreeNode<String>()
                        .setId(facilities.getFacilitiesId())
                        .setName(facilities.getFacilitiesName())
                        .setParentId(waysectionId)
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.FAC.getCode())
                                .putOnce("shortName", facilities.getFacilitiesName())
                                .putOnce("type", StrUtil.isBlank(facilities.getFacilitiesType())?"":facilities.getFacilitiesType())
                                .putOnce("isLeaf", facilities.getIsLeaf())
                        )));
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, waysectionId, treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public List<Tree<String>> getChildFacilities(Boolean isUseUserPermissions, String facilitiesId) {
        if (StrUtil.isBlank(facilitiesId)) {
            throw new BadRequestException("facilitiesId 不得为空");
        }
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        List<TreeNode<String>> nodeList = new ArrayList<>();
        List<FacShortVO> childFacList = assetsPermissionsTreeDao.getChildFacilities(isUseUserPermissions, userId, dataPermissionsLevel, facilitiesId);
        childFacList.forEach(facilities ->
                nodeList.add(new TreeNode<String>()
                        .setId(facilities.getFacilitiesId())
                        .setName(facilities.getFacilitiesName())
                        .setParentId(facilitiesId)
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.FAC.getCode())
                                .putOnce("shortName", facilities.getFacilitiesName())
                                .putOnce("type", StrUtil.isBlank(facilities.getFacilitiesType())?"":facilities.getFacilitiesType())
                                .putOnce("isLeaf", facilities.getIsLeaf())
                        )));
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, facilitiesId, treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * 用户组织机构权限树
     */
    @Override
    public Object getUserOrgTree(Boolean isUseUserPermissions) {
        // 用户信息
        Integer dataPermissionsLevel = null;
        String userId = "";
        if (isUseUserPermissions) {
            // 带用户权限
            User user = getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("您没有浏览该数据的权限");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }

        List<Org> orgList = assetsPermissionsTreeDao.getOrgList(isUseUserPermissions,dataPermissionsLevel,userId);

        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("label");

        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        int i = 1;
        for (Org org : orgList) {
            if (StrUtil.isBlank(org.getParentId())){
                if (null == org.getSort()){
                    org.setSort(1L);
                }
                nodeList.add(new TreeNode<>(org.getOrgId(), "0", org.getOrgShortName(), org.getSort().intValue()));
            }else{
                nodeList.add(new TreeNode<>(org.getOrgId(), org.getParentId(), org.getOrgShortName(), i++));
            }
        }

        //转换器
        List<Tree<String>> treeNodes = TreeUtil.build(nodeList, "0", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
        });

        return treeNodes;
    }
}