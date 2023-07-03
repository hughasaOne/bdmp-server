package com.rhy.bdmp.open.modules.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import com.rhy.bdmp.open.modules.common.domain.vo.TreeNode;
import com.rhy.bdmp.open.modules.dict.dao.DictDao;
import com.rhy.bdmp.open.modules.dict.domain.bo.DeviceDictBo;
import com.rhy.bdmp.open.modules.dict.domain.bo.DictBo;
import com.rhy.bdmp.open.modules.dict.service.IDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service(value = "DictServiceImplV1")
public class DictServiceImpl implements IDictService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource(name = "DictDaoV1")
    private DictDao dictDao;

    @Override
    public Object getSystemTree() {
        List<TreeNode> systemDictList = dictDao.getSystemTree();
        if (CollUtil.isEmpty(systemDictList)){
            return null;
        }

        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = new ArrayList<>();
        systemDictList.forEach(node ->
                nodeList.add(new cn.hutool.core.lang.tree.TreeNode<String>()
                        .setId(node.getId())
                        .setName(node.getName())
                        .setParentId(node.getParentId())
                        .setWeight(node.getSort())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", node.getNodeType())
                                .putOnce("shortName", node.getShortName())
                                .putOnce("type", node.getType()))
                ));

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public Object getDeviceTree(DeviceDictBo deviceDictBo) {
        String categoryId = "";
        if (null != deviceDictBo){
            categoryId = deviceDictBo.getCategoryId();
        }
        DeviceCategory deviceCategory = dictDao.getCategoryById(categoryId);

        Integer categoryType = 0;
        JSONArray categoryRules = null;
        if (null != deviceCategory){
            categoryType = deviceCategory.getCategoryType();
            String categoryRule = deviceCategory.getCategoryRule();
            if (JSONUtil.isJson(categoryRule)){
                categoryRules = JSONUtil.parseArray(categoryRule);
            }
        }

        List<TreeNode> deviceNodeList = dictDao.getDeviceDictNode(categoryType,categoryRules);

        if (CollUtil.isEmpty(deviceNodeList)){
            return null;
        }

        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = new ArrayList<>();
        deviceNodeList.forEach(node ->
                nodeList.add(new cn.hutool.core.lang.tree.TreeNode<String>()
                        .setId(node.getId())
                        .setName(node.getName())
                        .setParentId(node.getParentId())
                        .setWeight(node.getSort())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", node.getNodeType())
                                .putOnce("shortName", node.getShortName())
                                .putOnce("code", node.getType()))
                ));

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public Object getDeviceDictList(String deviceType) {
        return dictDao.getDeviceDictList(deviceType);
    }

    @Override
    public Object getDictTree(DictBo dictBo) {
        List<TreeNode> dictList = dictDao.getDictList(dictBo);

        if (CollUtil.isEmpty(dictList)){
            return null;
        }

        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = new ArrayList<>();
        dictList.forEach(node ->
                nodeList.add(new cn.hutool.core.lang.tree.TreeNode<String>()
                        .setId(node.getId())
                        .setName(node.getName())
                        .setParentId(node.getParentId())
                        .setWeight(node.getSort())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", node.getNodeType())
                                .putOnce("shortName", node.getShortName())
                                .putOnce("type", node.getType()))
                ));

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public Object getDictDetail(String code) {
        return dictDao.getDictDetail(code);
    }
}
