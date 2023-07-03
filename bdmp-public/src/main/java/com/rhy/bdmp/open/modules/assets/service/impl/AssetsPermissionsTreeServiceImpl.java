package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.jtkj.cloud.common.utils.BaseDataUtil;
import com.jtkj.cloud.common.utils.UserInfoUtil;
import com.jtkj.cloud.common.vo.UserNewVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.assets.dao.AssetsPermissionsTreeDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.DataFacBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.Org;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.open.modules.assets.enums.FacilitiesTypeEnum;
import com.rhy.bdmp.open.modules.assets.enums.NodeTypeEnum;
import com.rhy.bdmp.open.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public User getCurrentUser() {
        String userId = WebUtils.getUserId();
        User user = assetsPermissionsTreeDao.getUserById(userId);
        if (null == user) {
            throw new BadRequestException("用户信息不存在");
        }
        return user;
    }

    @Override
    public UserNewVo getCurrentUserNew() {
        Long userId = UserInfoUtil.getUserIdByHeader();
        List<UserNewVo> userNewVoList=BaseDataUtil.selectUserVoById(userId);
        //UserNewVo user=UserInfoUtil.findUserById1(userId);

        if (userNewVoList.size()==0) {
            throw new BadRequestException("用户信息不存在");
        }
        UserNewVo user=userNewVoList.get(0);
        return user;
    }

    @Override
    public List<Tree<String>> getAssetsOrgWayFacTree(Boolean isUseUserPermissions, String orgId, String nodeType) {
        List<TreeNode<String>> nodeList = new ArrayList<>();

        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        //Long userId = UserInfoUtil.getUserIdByHeader();
        //List<UserNewVo> userNewVoList=BaseDataUtil.selectUserVoById(userId);
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        List<Org> groupList = new ArrayList<>();
        List<Org> orgList = new ArrayList<>();
        List<Waysection> waysectionList = new ArrayList<>();
        List<Facilities> facilitiesList = new ArrayList<>();
        if (String.valueOf(dataPermissionsLevel).equals(PermissionsEnum.ORG.getCode()) || !isUseUserPermissions) {
            // 集团权限
            boolean isJtLimit = true;
            String[] jtLimitStrArray = new String[]{"740F3570-CC48-45BD-A8AC-118CB8C1CBE1","D3D78EC7-6A77-46E9-A7E8-219B0C7330C5","170A1F48-40BE-4559-8C7C-2F41C4887C7B","B6D8D7D8-3B69-4713-9150-9C3D8540DE02","CAB25F00-2484-4157-8AD4-D8C2C0EEF9FD","475923E7-4117-4F88-98E5-112FB4A43550","65ADDCDF-BDF4-4297-BF75-8BA3456AEDEB","3F0C6086-4316-4F01-9DC2-4606EFC5E56A","7DB8E4FC-C9D6-426A-A3FF-E1067823204C"};
            // 运营公司
            orgList = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType,false);
            String orgIds = "";
            for (Org org : orgList) {
                orgIds += org.getOrgId() + ",";
            }
            for (String jtLimit : jtLimitStrArray) {
                if (-1 == orgIds.indexOf(jtLimit)) {
                    isJtLimit = false;
                    break;
                }
            }
            if (isJtLimit || !isUseUserPermissions) {
                // 获取运营集团
                groupList = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType,false);
            }
            waysectionList = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType,false);
            //facilitiesList = assetsPermissionsTreeDao.getAssetsFac(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
        }
        if (String.valueOf(dataPermissionsLevel).equals(PermissionsEnum.WAY.getCode()) || !isUseUserPermissions) {
            // 路段
            waysectionList = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType,false);
            //facilitiesList = assetsPermissionsTreeDao.getAssetsFac(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType);
        }
        if (String.valueOf(dataPermissionsLevel).equals(PermissionsEnum.FAC.getCode()) || !isUseUserPermissions) {
            waysectionList = assetsPermissionsTreeDao.getFacPermissions(isUseUserPermissions, userId, orgId, nodeType);
        }
        groupList.forEach(group ->
                nodeList.add(new TreeNode<String>()
                        .setId(group.getOrgId())
                        .setName(group.getOrgName())
                        .setParentId("0")// group的parentId设置为0,方便构造树, 不考虑group的上级机构了
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.GROUP.getCode())
                                .putOnce("orgId", group.getOrgId())
                                .putOnce("oriWaynetId", "")
                                .putOnce("shortName", group.getOrgShortName())
                                .putOnce("nodeId", group.getNodeId())
                                .putOnce("longitude", null)
                                .putOnce("latitude", null)
                                .putOnce("type", group.getOrgType())
                        )));
        List<Org> finalGroupList = groupList;
        orgList.forEach(org ->
                nodeList.add(new TreeNode<String>()
                        .setId(org.getOrgId())
                        .setName(org.getOrgName())
                        .setParentId(finalGroupList.isEmpty() ? "0" : org.getParentId())// parentId为null时,该节点可能会漏掉
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                .putOnce("orgId", org.getOrgId())
                                .putOnce("oriWaynetId", "")
                                .putOnce("shortName", org.getOrgShortName())
                                .putOnce("nodeId", org.getNodeId())
                                .putOnce("longitude", null)
                                .putOnce("latitude", null)
                                .putOnce("type", org.getOrgType())
                        )));
        List<Org> finalOrgList = orgList;
        waysectionList.forEach(waySection ->
                nodeList.add(new TreeNode<String>()
                        .setId(waySection.getWaysectionId())
                        .setName(waySection.getWaysectionName())
                        .setParentId(finalOrgList.isEmpty() ? "0" : waySection.getManageId())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.WAY.getCode())
                                .putOnce("orgId", waySection.getWaysectionId())
                                .putOnce("oriWaynetId", waySection.getOriWaynetId())
                                .putOnce("shortName", waySection.getWaysectionSName())
                                .putOnce("nodeId", waySection.getNodeId())
                                .putOnce("longitude", null)
                                .putOnce("latitude", null)
                                .putOnce("type", "way")
                        )));
/*        List<Waysection> finalWaysectionList = waysectionList;
        facilitiesList.forEach(facilities ->
                nodeList.add(new TreeNode<String>()
                        .setId(facilities.getFacilitiesId())
                        .setName(facilities.getFacilitiesName())
                        .setParentId(finalWaysectionList.isEmpty() ? "0" : facilities.getWaysectionId())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.FAC.getCode())
                                .putOnce("orgId", facilities.getWaysectionId())
                                .putOnce("shortName", facilities.getFacilitiesName())
                                .putOnce("nodeId", "")
                                .putOnce("longitude", facilities.getLongitude())
                                .putOnce("latitude", facilities.getLatitude())
                                .putOnce("type", facilities.getFacilitiesType())
                        )));*/
        // 构造树结构
        return TreeUtil.build(nodeList, "0");
    }

    /**
     * 2.7.2.1 组织-数据资产树（集团-运营公司）
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                节点编号
     * @param nodeType             节点类型(集团、运营公司、路段)
     * @param isAsync              是否异步（是否向下查找）
     */
    @Override
    public List<Tree<String>> getGroupOrgTree(Boolean isUseUserPermissions, String orgId, String nodeType,Boolean isAsync) {
        // 用户信息
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        // 数据
        List<Org> assetsGroup = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel,orgId,nodeType,isAsync);
        List<Org> assetsOrg = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel,orgId,nodeType,isAsync);

        // 构造参数
        List<TreeNode<String>> nodeList = new ArrayList<>();
        assetsGroup.forEach(group ->
                nodeList.add(new TreeNode<String>()
                        .setId(group.getOrgId())
                        .setName(group.getOrgName())
                        .setParentId("0")// group的parentId设置为0,方便构造树, 不考虑group的上级机构了
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.GROUP.getCode())
                                .putOnce("orgId", group.getOrgId())
                                .putOnce("oriWaynetId", "")
                                .putOnce("shortName", group.getOrgShortName())
                                .putOnce("nodeId", group.getNodeId())
                                .putOnce("type", group.getOrgType())
                                .putOnce("isLeaf", group.getIsLeaf())
                        )));
        List<Org> finalGroupList = assetsGroup;
        assetsOrg.forEach(org ->
                nodeList.add(new TreeNode<String>()
                        .setId(org.getOrgId())
                        .setName(org.getOrgName())
                        .setParentId(finalGroupList.isEmpty() ? "0" : org.getParentId())// parentId为null时,该节点可能会漏掉
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                .putOnce("orgId", org.getOrgId())
                                .putOnce("oriWaynetId", "")
                                .putOnce("shortName", org.getOrgShortName())
                                .putOnce("nodeId", org.getNodeId())
                                .putOnce("type", org.getOrgType())
                                .putOnce("isLeaf", org.getIsLeaf())
                        )));
        return TreeUtil.build(nodeList, "0");
    }

    // 2.7.2.2 组织路段-数据资产树（集团-运营公司-路段）
    //    Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync
    @Override
    public List<Tree<String>> getGroupOrgWayTree(Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync) {
        // 用户信息
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        // 数据
        List<Org> assetsGroup = assetsPermissionsTreeDao.getAssetsGroup(isUseUserPermissions, userId, dataPermissionsLevel,orgId,nodeType,isAsync);
        List<Org> assetsOrg = assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, userId, dataPermissionsLevel,orgId,nodeType,isAsync);
        List<Waysection> assetsWay = assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, isAsync);

        // 构造参数
        List<TreeNode<String>> nodeList = new ArrayList<>();
        assetsGroup.forEach(group ->
                nodeList.add(new TreeNode<String>()
                        .setId(group.getOrgId())
                        .setName(group.getOrgName())
                        .setParentId("0")// group的parentId设置为0,方便构造树, 不考虑group的上级机构了
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.GROUP.getCode())
                                .putOnce("orgId", group.getOrgId())
                                .putOnce("oriWaynetId", "")
                                .putOnce("shortName", group.getOrgShortName())
                                .putOnce("nodeId", group.getNodeId())
                                .putOnce("type", group.getOrgType())
                                .putOnce("isLeaf", group.getIsLeaf())
                        )));
        List<Org> finalGroupList = assetsGroup;
        assetsOrg.forEach(org ->
                nodeList.add(new TreeNode<String>()
                        .setId(org.getOrgId())
                        .setName(org.getOrgName())
                        .setParentId(finalGroupList.isEmpty() ? "0" : org.getParentId())// parentId为null时,该节点可能会漏掉
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.ORG.getCode())
                                .putOnce("orgId", org.getOrgId())
                                .putOnce("oriWaynetId", "")
                                .putOnce("shortName", org.getOrgShortName())
                                .putOnce("nodeId", org.getNodeId())
                                .putOnce("type", org.getOrgType())
                                .putOnce("isLeaf", org.getIsLeaf())
                        )));
        List<Org> finalOrgList = assetsOrg;
        assetsWay.forEach(waySection ->
                nodeList.add(new TreeNode<String>()
                        .setId(waySection.getWaysectionId())
                        .setName(waySection.getWaysectionName())
                        .setParentId(finalOrgList.isEmpty() ? "0" : waySection.getManageId())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.WAY.getCode())
                                .putOnce("orgId", waySection.getWaysectionId())
                                .putOnce("oriWaynetId", waySection.getOriWaynetId())
                                .putOnce("shortName", waySection.getWaysectionSName())
                                .putOnce("nodeId", waySection.getNodeId())
                                .putOnce("type", "way")
                                .putOnce("isLeaf", waySection.getIsLeaf())
                        )));
        return TreeUtil.build(nodeList, "0");
    }

    // 2.7.2.3 设施一级
    //    Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync, List<String> facilitiesTypes
    @Override
    public List<Tree<String>> getFac(DataFacBo dataFacBo) {
        Boolean isUseUserPermissions = dataFacBo.getIsUseUserPermissions();
        String orgId = dataFacBo.getOrgId();
        String nodeType = dataFacBo.getNodeType();
        Boolean isAsync = dataFacBo.getIsAsync();
        List<String> facilitiesTypes = dataFacBo.getFacilitiesTypes();

        // 用户信息
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        List<TreeNode<String>> nodeList = new ArrayList<>();
        List<Facilities> assetsFac = assetsPermissionsTreeDao.getAssetsFac(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, isAsync, facilitiesTypes);
        assetsFac.forEach(fac ->
                nodeList.add(new TreeNode<String>()
                        .setId(fac.getFacilitiesId())
                        .setName(fac.getFacilitiesName())
                        .setParentId(orgId)
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", NodeTypeEnum.FAC.getCode())
                                .putOnce("orgId", fac.getFacilitiesId())
                                .putOnce("nodeId", null)
                                .putOnce("type", fac.getFacilitiesType())
                                .putOnce("isLeaf", fac.getIsLeaf())
                        )));
        return TreeUtil.build(nodeList,orgId);
    }

    // 2.7.2.4 设施二级
    //    Boolean isUseUserPermissions, String orgId, String nodeType, Boolean isAsync, List<String> facilitiesTypes
    @Override
    public List<Tree<String>> getSubFac(DataFacBo dataFacBo) {
        Boolean isUseUserPermissions = dataFacBo.getIsUseUserPermissions();
        String orgId = dataFacBo.getOrgId();
        String nodeType = dataFacBo.getNodeType();
        Boolean isAsync = dataFacBo.getIsAsync();
        List<String> facilitiesTypes = dataFacBo.getFacilitiesTypes();

        // 用户信息
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        List<TreeNode<String>> nodeList = new ArrayList<>();
        List<Facilities> assetsFacChildren = assetsPermissionsTreeDao.getAssetsFacChildren(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, isAsync, facilitiesTypes);
        assetsFacChildren.forEach(fac ->
                nodeList.add(new TreeNode<String>()
                        .setId(fac.getFacilitiesId())
                        .setName(fac.getFacilitiesName())
                        .setParentId(orgId)
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", "4")
                                .putOnce("orgId", fac.getFacilitiesId())
                                .putOnce("nodeId", null)
                                .putOnce("type", fac.getFacilitiesType())
                                .putOnce("isLeaf", fac.getIsLeaf())
                        )));
        return TreeUtil.build(nodeList, orgId);
    }

    @Override
    public List<Org> getAssetsOrg(Boolean isUseUserPermissions) {
        return getAssetsOrg(isUseUserPermissions, "", "");
    }

    @Override
    public List<Org> getAssetsOrg(Boolean isUseUserPermissions, String nodeType, String orgId) {
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        //String userId = userPermissions.getUserId();
        Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return assetsPermissionsTreeDao.getAssetsOrg(isUseUserPermissions, String.valueOf(userId), dataPermissionsLevel, orgId, nodeType,false);
    }

    @Override
    public List<Waysection> getAssetsWay(Boolean isUseUserPermissions) {
        return getAssetsWay(isUseUserPermissions, "", "");
    }

    @Override
    public List<Waysection> getAssetsWay(Boolean isUseUserPermissions, String nodeType, String orgId) {
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        //String userId = userPermissions.getUserId();
        Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        return assetsPermissionsTreeDao.getAssetsWay(isUseUserPermissions, String.valueOf(userId), dataPermissionsLevel, orgId, nodeType,false);
    }

    @Override
    public List<Facilities> getAssetsFac(Boolean isUseUserPermissions) {
        return getAssetsFac(isUseUserPermissions, "", "");
    }

    @Override
    public List<Facilities> getAssetsFac(Boolean isUseUserPermissions, String nodeType, String orgId) {
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        //String userId = userPermissions.getUserId();
        Long userId=UserInfoUtil.getUserIdByHeader();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();

        // 获取所有设施类型
        List<String> facilitiesTypes = Lists.newArrayList(
                FacilitiesTypeEnum.DOOR_FRAME.getCode(),
                FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode(),
                FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
        List<Facilities> list = new ArrayList<>();
        // 收费站
        List<Facilities> facTollStationList = assetsPermissionsTreeDao.getTollStation(isUseUserPermissions, String.valueOf(userId),
                dataPermissionsLevel, orgId, nodeType,false, FacilitiesTypeEnum.TOLL_STATION.getCode());
        list.addAll(facTollStationList);

        // 隧道
        List<Facilities> facTunnelList = assetsPermissionsTreeDao.getTunnel(isUseUserPermissions, String.valueOf(userId),
                dataPermissionsLevel, orgId, nodeType,false, FacilitiesTypeEnum.TUNNEL.getCode());
        list.addAll(facTunnelList);

        // 门架
        List<Facilities> facGantryList = assetsPermissionsTreeDao.getGantry(isUseUserPermissions, String.valueOf(userId),
                dataPermissionsLevel, orgId, nodeType,false, facilitiesTypes);
        list.addAll(facGantryList);

        // 服务区
        List<Facilities> facServiceAreaList = assetsPermissionsTreeDao.getServiceArea(isUseUserPermissions, String.valueOf(userId),
                dataPermissionsLevel, orgId, nodeType,false, FacilitiesTypeEnum.SERVICE_AREA.getCode());
        list.addAll(facServiceAreaList);

        // 桥梁
        List<Facilities> facBridgeList = assetsPermissionsTreeDao.getAssetsFacBridge(isUseUserPermissions, String.valueOf(userId), dataPermissionsLevel, orgId, nodeType, FacilitiesTypeEnum.BRIDGE.getCode());
        list.addAll(facBridgeList);
        return list;
    }

    @Override
    public List<Facilities> getAssetsFac(Boolean isUseUserPermissions, String nodeType, String orgId, List<String> facilitiesTypes) {
        UserPermissions userPermissions = getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        List<String> searchFacilitiesTypes = new ArrayList<>();
        List<Facilities> facBridgeList = null;
        for (String facilitiesType : facilitiesTypes){
            if (FacilitiesTypeEnum.DOOR_FRAME.getCode().equals(facilitiesType)){
                searchFacilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME.getCode());
                searchFacilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD1.getCode());
                searchFacilitiesTypes.add(FacilitiesTypeEnum.DOOR_FRAME_CHILD2.getCode());
            }else if(FacilitiesTypeEnum.BRIDGE.getCode().equals(facilitiesType)){
                facBridgeList = assetsPermissionsTreeDao.getAssetsFacBridge(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType, FacilitiesTypeEnum.BRIDGE.getCode());
            } else {
                searchFacilitiesTypes.add(facilitiesType);
            }
        }
        List<Facilities> facList = assetsPermissionsTreeDao.getAssetsFac(isUseUserPermissions, userId, dataPermissionsLevel, orgId, nodeType,false, facilitiesTypes);
        if (null != facBridgeList){
            facList.addAll(facBridgeList);
        }
        return facList;
    }

    @Override
    public UserPermissions getUserPermissions(Boolean isUseUserPermissions) {
        UserPermissions userPermissions = new UserPermissions();
        // 带用户权限
        if (isUseUserPermissions) {
            //User user = getCurrentUser();
            UserNewVo user = getCurrentUserNew();
            userPermissions.setDataPermissionsLevel(user.getDataPermissionsLevel());
            userPermissions.setUserId(String.valueOf(user.getUserId()));
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                // admin用户则不考虑权限
                isUseUserPermissions = false;
            } else if (!PermissionsEnum.include(String.valueOf(userPermissions.getDataPermissionsLevel()))) {
                // 非admin用户,数据权限为null时不通过
                throw new BadRequestException("您没有浏览该数据的权限");
            }
        }
        userPermissions.setIsUseUserPermissions(isUseUserPermissions);
        return userPermissions;
    }

    @Override
    public Object getOrgByWay(String wayId,String orgTypes) {
        // 运营集团、运营公司、监控中心
        String[] orgTypesArray = null;
        if (StrUtil.isNotBlank(orgTypes)){
            orgTypesArray = orgTypes.split(",");
        }
        List<Org> groupOrgMonitorList = assetsPermissionsTreeDao.getGroupOrgMonitorByWay(wayId,orgTypesArray);

        // 集控点(与路段有关的集控点)
        // 1、找到路段下的设施
        List<String> facIds = assetsPermissionsTreeDao.getFacIds(wayId);

        // 2、找到路段所属公司下集控点下的用户(与路段下的设施有关的用户)
        List<Org> company = groupOrgMonitorList.stream()
                .filter(org -> org.getOrgType().equals("000300"))
                .collect(Collectors.toList());
        List<String> users = null;
        if (CollUtil.isNotEmpty(company) && CollUtil.isNotEmpty(facIds)){
            users = assetsPermissionsTreeDao.getUsers(company.get(0).getOrgId(),facIds);
        }
        // 3、根据用户查集控点
        List<Org> controlPointList = null;
        if (CollUtil.isNotEmpty(users)){
            controlPointList = assetsPermissionsTreeDao.getControlPointList(users);
        }

        List<JSONObject> orgList = new ArrayList<>();
        if (CollUtil.isNotEmpty(groupOrgMonitorList)){
            orgList = groupOrgMonitorList.stream().map(org -> JSONUtil.createObj()
                    .putOnce("orgId", org.getOrgId())
                    .putOnce("orgName", org.getOrgName())
                    .putOnce("orgShortName", org.getOrgShortName())
                    .putOnce("orgType", org.getOrgType())
                    .putOnce("sort", org.getSort() == null ? 0 : org.getSort())
            ).collect(Collectors.toList());

            if (CollUtil.isNotEmpty(controlPointList)){
                orgList.addAll(controlPointList.stream().map(org -> JSONUtil.createObj()
                        .putOnce("orgId", org.getOrgId())
                        .putOnce("orgName", org.getOrgName())
                        .putOnce("orgShortName", org.getOrgShortName())
                        .putOnce("orgType", org.getOrgType())
                        .putOnce("sort", org.getSort() == null ? 0 : org.getSort())
                ).collect(Collectors.toList()));
            }
        }
        int maxSort = orgList.stream().mapToInt(org -> Integer.parseInt(org.get("sort").toString())).max().getAsInt() + 1;
        for (JSONObject obj : orgList) {
            int sort = Integer.parseInt(obj.get("sort").toString());
            String orgType = "";
            if (null != obj.get("orgType")){
                orgType = obj.get("orgType").toString();
            }
            if ("000200".equals(orgType)){
                obj.set("sort",1);
            }
            else if("000300".equals(orgType)){
                obj.set("sort",2);
            }
            else {
                if (0 == sort){
                    obj.set("sort",maxSort);
                }
                else {
                    obj.set("sort",sort);
                }
            }
        }
        orgList.sort(Comparator.comparing(org -> Integer.parseInt(org.get("sort").toString())));
        List<JSONObject> tempList = new ArrayList<>();
        List<JSONObject> tempList1 = orgList;
        if (ArrayUtil.isNotEmpty(orgTypesArray)){
            for (String orgType1 : orgTypesArray) {
                tempList1 = orgList.stream().filter(obj -> {
                    String orgType = obj.get("orgType").toString();
                    return orgType.equals(orgType1);
                }).collect(Collectors.toList());
                tempList.addAll(tempList1);
            }
        }
        else {
            tempList = orgList;
        }
        return tempList;
    }
}