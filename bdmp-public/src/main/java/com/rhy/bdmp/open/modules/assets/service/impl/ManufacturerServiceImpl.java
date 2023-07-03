package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.assets.dao.AssetsPermissionsTreeDao;
import com.rhy.bdmp.open.modules.assets.dao.ManufacturerDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.ManufacturerFacBo;
import com.rhy.bdmp.open.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.open.modules.assets.domain.po.Org;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.open.modules.assets.enums.NodeTypeEnum;
import com.rhy.bdmp.open.modules.assets.service.IManufacturerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 厂商业务接口实现
 * @author weicaifu
 */
@Service
public class ManufacturerServiceImpl implements IManufacturerService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    private ManufacturerDao manufacturerDao;

    /**
     * 2.7.3.1  厂商资产树（集团-公司）
     * @param orgId        节点编号
     * @param nodeType    节点类型(集团、运营公司、路段)
     * @param isAsync 是否异步
     */
    @Override
    public List<Tree<String>> getGroupOrgManufacturerTree(String orgId, String nodeType,Boolean isAsync) {
        // 获取当前用户的厂商
        String manufacturerId = this.getUserManufacturer();
        if (StrUtil.isNotBlank(manufacturerId)){
            List<Org> groupManufacturer = manufacturerDao.getGroupManufacturer(orgId, nodeType,manufacturerId,isAsync);
            List<Org> orgManufacturer = manufacturerDao.getOrgManufacturer(orgId, nodeType,manufacturerId,isAsync);
            // 构造参数
            List<TreeNode<String>> nodeList = new ArrayList<>();
            groupManufacturer.forEach(group ->
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
            List<Org> finalGroupList = groupManufacturer;
            orgManufacturer.forEach(org ->
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
        }else{
            return null;
        }
    }

    /**
     * 2.7.3.2  厂商资产树（集团-公司-路段）
     * @param orgId        节点编号
     * @param nodeType    节点类型(集团、运营公司、路段)
     * @param isAsync 是否异步
     */
    @Override
    public List<Tree<String>> getGroupOrgWayManufacturerTree(String orgId, String nodeType, Boolean isAsync) {
        // 获取当前用户的厂商
        String manufacturerId = this.getUserManufacturer();
        if (StrUtil.isNotBlank(manufacturerId)){
            List<Org> groupManufacturer = manufacturerDao.getGroupManufacturer(orgId, nodeType,manufacturerId,isAsync);
            List<Org> orgManufacturer = manufacturerDao.getOrgManufacturer(orgId, nodeType,manufacturerId,isAsync);
            List<Waysection> wayManufacturer = manufacturerDao.getWayManufacturer(orgId, nodeType,manufacturerId,isAsync);
            // 构造参数
            List<TreeNode<String>> nodeList = new ArrayList<>();
            groupManufacturer.forEach(group ->
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
            List<Org> finalGroupList = groupManufacturer;
            orgManufacturer.forEach(org ->
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
            List<Org> finalOrgList = orgManufacturer;
            wayManufacturer.forEach(waySection ->
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
        }else{
            return null;
        }
    }

    /**
     * 2.7.3.3  厂商资产树（设施一级）
     */
    @Override
    public List<Tree<String>> getFacManufacturer(ManufacturerFacBo manufacturerFacBo) {
        String orgId = manufacturerFacBo.getOrgId();
        String nodeType = manufacturerFacBo.getNodeType();
        Boolean isAsync = manufacturerFacBo.getIsAsync();
        List<String> facilitiesTypes = manufacturerFacBo.getFacilitiesTypes();

        String manufacturerId = this.getUserManufacturer();
        if (StrUtil.isNotBlank(manufacturerId)){
            List<TreeNode<String>> nodeList = new ArrayList<>();
            List<Facilities> facList = manufacturerDao.getFacManufacturer(orgId, nodeType,manufacturerId,isAsync,facilitiesTypes);
            facList.forEach(fac ->
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
        }else{
            return null;
        }
    }

    /**
     * 2.7.3.4  厂商资产树（设施二级）
     */
    @Override
    public List<Tree<String>> getSubFacManufacturerTree(ManufacturerFacBo manufacturerFacBo) {
        String orgId = manufacturerFacBo.getOrgId();
        String nodeType = manufacturerFacBo.getNodeType();
        Boolean isAsync = manufacturerFacBo.getIsAsync();
        List<String> facilitiesTypes = manufacturerFacBo.getFacilitiesTypes();

        String manufacturerId = this.getUserManufacturer();
        if (StrUtil.isNotBlank(manufacturerId)){
            List<TreeNode<String>> nodeList = new ArrayList<>();
            List<Facilities> facList = manufacturerDao.getSubFacManufacturerTree(orgId, nodeType,manufacturerId,isAsync,facilitiesTypes);
            facList.forEach(fac ->
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
        }else{
            return null;
        }
    }

    /**
     * 查询用户所属厂商
     */
    private String getUserManufacturer() {
        String userId = WebUtils.getUserId();
        return manufacturerDao.getUserManufacturer(userId);
    }
}
