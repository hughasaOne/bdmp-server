package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bdmp.system.modules.assets.dao.DictPostDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictPostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 岗位字典管理
 *
 * @author weicaifu
 **/
@Service
public class DictPostServiceImpl implements IDictPostService {

    @Resource
    private DictPostDao dictPostDao;

    @Override
    public Object getOrgPostTree(String parentCode) {
        // 获取组织结构
        List<Map<String, Object>> orgDict = dictPostDao.getOrgDict(parentCode);
        // 获取岗位字典
        List<Map<String, Object>> dictPostList = dictPostDao.getDictPostList();
        // 岗位字典分组
        Map<String, List<Map<String, Object>>> dictPostMap = dictPostList.stream().collect(Collectors.groupingBy((map -> {
            return map.get("parentCode").toString();
        })));
        // 构建岗位上下级关系
        for (Map.Entry<String, List<Map<String, Object>>> entry : dictPostMap.entrySet()) {
            for (Map<String, Object> orgDict1 : orgDict) {
                if (orgDict1.get("code").equals(entry.getKey())){
                    for (Map<String, Object> post : entry.getValue()) {
                        post.put("parentId",orgDict1.get("id"));
                    }
                    break;
                }
            }
        }

        // 构建树
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        treeNodeConfig.setIdKey("id");
        treeNodeConfig.setNameKey("label");

        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        TreeNode treeNode1 = null;
        Map<String,Object> extra = null;
        // 组织结构
        for (Map<String, Object> org : orgDict) {
            extra = new HashMap<String,Object>();
            extra.put("disabled",true);
            treeNode1 = new TreeNode<>(org.get("id").toString(), org.get("parentId").toString(),
                    org.get("label").toString(), Integer.parseInt(org.get("sort").toString())).setExtra(extra);
            nodeList.add(treeNode1);
        }

        // 岗位
        for (Map.Entry<String, List<Map<String, Object>>> entry : dictPostMap.entrySet()) {
            for (Map<String, Object> post : entry.getValue()) {
                extra = new HashMap<String,Object>();
                extra.put("disabled",false);
                treeNode1 = new TreeNode<>(post.get("id").toString(), post.get("parentId").toString(),
                        post.get("label").toString(), Integer.parseInt(post.get("sort").toString())).setExtra(extra);
                nodeList.add(treeNode1);
            }
        }

        return TreeUtil.build(nodeList, "0", treeNodeConfig, (treeNode, tree) -> {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
            for (Map.Entry<String, Object> entry : treeNode.getExtra().entrySet()) {
                tree.putExtra(entry.getKey(),entry.getValue());
            }
        });
    }

    /**
     * 获取岗位字典
     *
     * @param dictBO 业务实体
     **/
    @Override
    public Object getDictPost(DictBO dictBO) {
        Integer currentPage = dictBO.getCurrentPage();
        Integer limit = dictBO.getLimit();
        if (currentPage == null) {
            // 不分页
            return dictPostDao.getDictPost(dictBO);
        }
        // 分页
        Page<Map<String, Object>> page = new Page<>(currentPage, limit);
        page.setOptimizeCountSql(true);
        return new PageUtils(dictPostDao.getDictPost(page, dictBO));
    }

    /**
     * 获取组织拥有的岗位
     * @param orgType 组织类型
     * @param queryType 查询类型（manage/query，默认为query）
     * @return
     */
    @Override
    public Object getOrgPost(String orgType) {
        List<Map<String, Object>> orgList = dictPostDao.getDictByCode(orgType);
        Set<String> orgCode = new HashSet<>();
        if (CollUtil.isNotEmpty(orgList)) {
            for (Map<String, Object> org : orgList) {
                orgCode.add(org.get("code").toString());
            }
        }
        if (CollUtil.isEmpty(orgCode)) {
            return null;
        }
        List<Map<String, Object>> orgPostList = dictPostDao.getOrgPost(orgCode);
        Map<String, List<Map<String, Object>>> orgPostMap = orgPostList.stream()
                .filter((map) -> StrUtil.isNotBlank(map.get("parentId").toString()))
                .collect(Collectors.groupingBy((map) -> map.get("parentId").toString()));
        for (Map<String, Object> org : orgList) {
            // 将机构类型设置为不可选
            org.put("disabled", true);
            for (Map.Entry<String, List<Map<String, Object>>> entry : orgPostMap.entrySet()) {
                if (entry.getKey().equals(org.get("code"))) {
                    org.put("children", entry.getValue());
                }
            }
        }
        // 移除没有children的
        orgList.removeIf(org -> null == org.get("children"));
        return orgList;
    }
}
