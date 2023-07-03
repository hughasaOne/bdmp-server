package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.assets.dao.VPDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.UpdateVPStatusBo;
import com.rhy.bdmp.open.modules.assets.domain.po.Resource;
import com.rhy.bdmp.open.modules.assets.enums.NodeTypeEnum;
import com.rhy.bdmp.open.modules.assets.service.IVPService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VPServiceImpl implements IVPService {

    @javax.annotation.Resource
    private VPDao vpDao;

    @Override
    public void updateVPStatus(List<UpdateVPStatusBo> updateVPStatusBos) {
        if (CollUtil.isNotEmpty(updateVPStatusBos)){
            for (UpdateVPStatusBo vpStatusBo : updateVPStatusBos) {
                Resource resource = new Resource();
                resource.setId(vpStatusBo.getId());
                resource.setStatus(vpStatusBo.getStatus());
                resource.setUpdateTime(new Date());
                resource.setUpdateBy(WebUtils.getUserId());
                vpDao.updateById(resource);
            }
        }
    }

    @Override
    public List<Tree<String>> getContactTree(String orgId, String nodeType, String name, String exclude) {
        // nodeId和nodeType 成对出现
        this.checkParams(orgId,nodeType);
        nodeType = this.convertNodeType(nodeType);
        if (!(StrUtil.equals(nodeType,"group") || StrUtil.equals(nodeType,"org"))){
          return null;
        }

        // 终端
        List<Resource> rootList = vpDao.getContactTree(orgId,nodeType,name);
        List<Resource> tempList = new ArrayList<>();
        if (CollUtil.isNotEmpty(rootList)){
            tempList.addAll(rootList);
            this.findChildren(rootList,tempList);
        }

        if (CollUtil.isEmpty(tempList)){
            return null;
        }

        if (StrUtil.equals("org",nodeType)){
            // 节点为运营公司时，查询父节点,及其子节点
            List<Resource> parents = null;
            List<Resource> pcSeat = null;
            if (CollUtil.isNotEmpty(rootList)){
                parents = vpDao.findParent(rootList);
                pcSeat = vpDao.findPCSeat(rootList.get(0));
            }

            if (CollUtil.isNotEmpty(parents)){
                tempList.addAll(parents);
            }
            if (CollUtil.isNotEmpty(pcSeat)){
                tempList.addAll(pcSeat);
            }
        }

        // 过滤掉不需要的数据
        tempList = tempList.stream().filter(resource -> !resource.getName().contains("测试")).collect(Collectors.toList());

        // 去重
        tempList = tempList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(Resource::getId))), ArrayList::new));

        // 构建树
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("name");

        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        TreeNode<String> node;
        Map<String,Object> extra;
        for (Resource resource : tempList) {
            node = new TreeNode<>();
            extra = new HashMap<>();
            extra.put("nodeType",resource.getNodeType());
            extra.put("status",resource.getStatus());
            extra.put("tollStationId",resource.getRelTollStationId());
            node.setId(resource.getId());
            node.setName(resource.getName());
            node.setWeight(resource.getSort());
            node.setExtra(extra);
            node.setParentId(resource.getParentId());
            if("0".equals(exclude)){
                nodeList.add(node);
            }else{
                if (exclude.equals(resource.getId())){
                    continue;
                }else{
                    nodeList.add(node);
                }
            }
        }

        // 按指定的规则排序
        /*if (CollUtil.isNotEmpty(nodeList)) {
            for (TreeNode<String> treeNode : nodeList) {
                String name1 = treeNode.getName().toString();
                if (name1.contains("手持")){
                    treeNode.setWeight(1);
                }
                else if (name1.contains("手台")){
                    treeNode.setWeight(2);
                }
                else if (name1.contains("入口")){
                    treeNode.setWeight(3);
                }
                else if (name1.contains("出口")){
                    treeNode.setWeight(4);
                }
                else if (name1.contains("调度") && name1.contains("话机")){
                    treeNode.setWeight(5);
                }
                else if (name1.contains("坐席")){
                    treeNode.setWeight(6);
                }
                else {
                    treeNode.setWeight(7);
                }
            }
        }*/
        return TreeUtil.build(nodeList, "0",treeNodeConfig,new DefaultNodeParser<>());
    }

    /*@Override
    public List<Tree<String>> getContactTree(String orgId, String nodeType, String name, String exclude) {
        // nodeId和nodeType 成对出现
        this.checkParams(orgId,nodeType);
        nodeType = this.convertNodeType(nodeType);

        // 终端
        List<Resource> terminalList = vpDao.getContactTree(orgId,nodeType,name);
        // 目录
        List<Resource> dirList = vpDao.selectList(new QueryWrapper<Resource>().eq("node_type", 1));
        List<Resource> relDirList = new ArrayList<>();
        // 找出与终端相关的目录
        this.findDir(dirList,terminalList,relDirList);

        // 构建树
        //配置
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("name");

        if (StrUtil.isNotBlank(orgId)){
            // 当根据节点查询时,运营集团及当前运营公司下的节点也查询返回
            List<Resource> org = relDirList.stream()
                    .filter(resource -> resource.getName().contains("公司"))
                    .collect(Collectors.toList());
            List<Resource> temp = null;
            if (CollUtil.isNotEmpty(org)){
                temp = vpDao.getTerminalByOrg(org.get(0),nodeType);

            }
            if (CollUtil.isNotEmpty(temp)){
                terminalList.addAll(temp);
            }
        }

        relDirList.addAll(terminalList);

        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        TreeNode<String> node;
        Map<String,Object> extra;
        for (Resource resource : relDirList) {
            node = new TreeNode<>();
            extra = new HashMap<>();
            extra.put("nodeType",resource.getNodeType());
            extra.put("status",resource.getStatus());
            extra.put("tollStationId",resource.getRelTollStationId());
            node.setId(resource.getId());
            node.setName(resource.getName());
            node.setWeight(resource.getSort());
            node.setExtra(extra);
            if ("".equals(resource.getParentId())){
                node.setParentId("0");
            }
            else {
                node.setParentId(resource.getParentId());
            }
            if("0".equals(exclude)){
                nodeList.add(node);
            }else{
                if (exclude.equals(resource.getId())){
                    continue;
                }else{
                    nodeList.add(node);
                }
            }
        }

        // 按指定的规则排序
        if (CollUtil.isNotEmpty(nodeList)) {
            for (TreeNode<String> treeNode : nodeList) {
                String name1 = treeNode.getName().toString();
                if (name1.contains("手持")){
                    treeNode.setWeight(1);
                }
                else if (name1.contains("手台")){
                    treeNode.setWeight(2);
                }
                else if (name1.contains("入口")){
                    treeNode.setWeight(3);
                }
                else if (name1.contains("出口")){
                    treeNode.setWeight(4);
                }
                else if (name1.contains("调度") && name1.contains("话机")){
                    treeNode.setWeight(5);
                }
                else if (name1.contains("坐席")){
                    treeNode.setWeight(6);
                }
                else {
                    treeNode.setWeight(7);
                }
            }
        }
        return TreeUtil.build(nodeList, "0",treeNodeConfig,new DefaultNodeParser<>());
    }*/

    private void findDir(List<Resource> dirList, List<Resource> childrenList, List<Resource> relDirList) {
        // 当size两次都没变时，已耗尽
        int size = dirList.size();
        if (CollUtil.isNotEmpty(dirList)){
            Iterator<Resource> iterator = dirList.iterator();
            while (iterator.hasNext()){
                Resource resource = iterator.next();
                boolean removed = false;
                for (Resource resource1 : childrenList) {
                    if (resource.getId().equals(resource1.getParentId())){
                        relDirList.add(resource);
                        iterator.remove();
                        removed = true;
                        break;
                    }
                    if ("".equals(resource.getParentId())){
                        relDirList.add(resource);
                        iterator.remove();
                        removed = true;
                        break;
                    }
                }
                if (!removed && size == dirList.size()){
                    iterator.remove();
                }
            }
            this.findDir(dirList,relDirList,relDirList);
        }
    }

    private void checkParams(String orgId, String nodeType) {
        if ((StrUtil.isBlank(orgId) != StrUtil.isBlank(nodeType))) {
            throw new BadRequestException("orgId 和 nodeType 必须同时为空或同时非空");
        }
        // 判断 nodeType 是否合法
        if (StrUtil.isNotBlank(nodeType) && !NodeTypeEnum.include(nodeType)) {
            throw new BadRequestException("nodeType:" + nodeType + " 为非法参数,请检查");
        }
    }

    private String convertNodeType(String nodeType) {
        return NodeTypeEnum.getName(nodeType);
    }


    private void findChildren(List<Resource> rootList, List<Resource> tempList) {
        List<String> ids = rootList.stream().map(resource -> resource.getId()).collect(Collectors.toList());
        List<Resource> children = vpDao.findChildren(ids);
        if (CollUtil.isNotEmpty(children)){
            tempList.addAll(children);
            this.findChildren(children,tempList);
        }
    }
}
