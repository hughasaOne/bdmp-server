package com.rhy.bdmp.open.modules.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.config.CustomProperties;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.enums.NodeTypeEnum;
import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.common.domain.vo.TreeNode;
import com.rhy.bdmp.open.modules.common.service.CommonService;
import com.rhy.bdmp.open.modules.device.dao.DeviceDao;
import com.rhy.bdmp.open.modules.device.domain.bo.*;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.device.domain.vo.StatDeviceNumByTypeVo;
import com.rhy.bdmp.open.modules.device.service.IDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "deviceServiceImplV1")
public class DeviceServiceImpl implements IDeviceService {
    @Resource
    private CommonService commonService;

    @Resource(name = "DeviceDaoV1")
    private DeviceDao deviceDao;

    @Resource
    private CustomProperties customProperties;

    @Override
    public List<StatDeviceNumByTypeVo> statDeviceNumByType(StatDeviceNumByTypeBo commonBo) {
        UserPermissions userPermissions = this.setValue(commonBo);
        List<StatDeviceNumByTypeVo> statDeviceNumByTypeVos = deviceDao.statDeviceNumByType(commonBo, userPermissions);
        if (CollUtil.isEmpty(statDeviceNumByTypeVos)){
            return statDeviceNumByTypeVos;
        }
        for (StatDeviceNumByTypeVo statDeviceNumByTypeVo : statDeviceNumByTypeVos) {
            statDeviceNumByTypeVo.setCategoryType(commonBo.getCategoryType());
        }
        return statDeviceNumByTypeVos;
    }

    @Override
    public List<DeviceVo> list(DeviceListBo deviceListBo) {
        UserPermissions userPermissions = this.setValue(deviceListBo);
        if (CollUtil.isEmpty(deviceListBo.getSystemIds())){
            deviceListBo.setSystemIds(null);
        }
        this.checkDistanceParams(deviceListBo);
        return deviceDao.getDeviceList(deviceListBo,userPermissions);
    }

    @Override
    public PageUtil<DeviceVo> getDevicePage(DevicePageBo devicePageBo) {
        if ((null == devicePageBo.getCurrentPage() || 0 >= devicePageBo.getCurrentPage()) || (null == devicePageBo.getPageSize() || 0 >= devicePageBo.getPageSize())){
            throw new BadRequestException("分页参数不合法");
        }
        UserPermissions userPermissions = this.setValue(devicePageBo);
        if (CollUtil.isEmpty(devicePageBo.getSystemIds())){
            devicePageBo.setSystemIds(null);
        }
        Page<DeviceVo> page = new Page<>();
        page.setCurrent(devicePageBo.getCurrentPage());
        page.setSize(devicePageBo.getPageSize());
        return new PageUtil<DeviceVo>(deviceDao.getDeviceList(page,devicePageBo,userPermissions));
    }

    @Override
    public DeviceVo detail(DeviceDetailBo deviceDetailBo) {
        if (StrUtil.isBlank(deviceDetailBo.getDeviceId())){
            return null;
        }
        DeviceVo deviceVo = deviceDao.getDeviceDetail(deviceDetailBo);
        if (null == deviceVo){
            return null;
        }
        String pic = deviceVo.getPic();
        String url = "";
        if (StrUtil.isNotBlank(pic)){
            String[] split = pic.split(";sep;");
            if (split.length > 0){
                for (int i = 0; i < split.length; i++) {
                    if (i == 0){
                        url = customProperties.getFileDownUrl() + split[0];
                    }
                    else {
                        url += ";sep;" + customProperties.getFileDownUrl() + split[i];
                    }
                }
            }

        }
        deviceVo.setPic(url);
        return deviceVo;
    }

    @Override
    public Object getAllNodeTree(AllNodeTreeBo allNodeTreeBo) {
        List<DeviceVo> deviceList = this.list(allNodeTreeBo);
        if (CollUtil.isEmpty(deviceList)){
            return null;
        }
        Set<String> facIds = deviceList.stream().map(DeviceVo::getFacilitiesId).collect(Collectors.toSet());
        Set<String> wayIds = deviceList.stream().map(DeviceVo::getWaysectionId).collect(Collectors.toSet());
        Set<String> deviceTypes = deviceList.stream().map(DeviceVo::getDeviceType).collect(Collectors.toSet());
        List<TreeNode> allNode = deviceDao.getAllNode(facIds,wayIds,deviceTypes,allNodeTreeBo.getNodeType());

        // 假定每个设施节点拥有的设备类型都一致
        List<TreeNode> deviceTypeNodeList = allNode.stream()
                .filter(node -> StrUtil.equals("deviceType", node.getNodeType()))
                .collect(Collectors.toList());
        List<TreeNode> facNodeList = allNode.stream().filter(node -> StrUtil.equals("fac", node.getNodeType())).collect(Collectors.toList());
        // 设施设备类型的父节点
        for (TreeNode facNode : facNodeList) {
            List<TreeNode> facChildren = deviceTypeNodeList.stream().map(deviceTypeNode -> {
                TreeNode treeNode = new TreeNode();
                BeanUtil.copyProperties(deviceTypeNode,treeNode);
                deviceTypeNode.setId(IdUtil.simpleUUID());
                deviceTypeNode.setParentId(facNode.getId());
                return treeNode;
            }).collect(Collectors.toList());
            allNode.addAll(facChildren);
        }
        // 将没有parentId的节点剔除
        allNode = allNode.stream().filter(node -> StrUtil.isNotBlank(node.getParentId())).collect(Collectors.toList());
        List<TreeNode> tree = this.buildTree(allNode, allNode.stream().filter(node -> StrUtil.equals("0", node.getParentId())).collect(Collectors.toList()));

        List<TreeNode> allNodeList = this.tree2List(tree, deviceList);

        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = new ArrayList<>();

        allNodeList.forEach(node -> {
            cn.hutool.core.lang.tree.TreeNode<String> temp = new cn.hutool.core.lang.tree.TreeNode<String>()
                    .setId(node.getId())
                    .setName(node.getName())
                    .setParentId(node.getParentId())
                    .setWeight(node.getSort());

            if (StrUtil.equals(node.getNodeType(),"device")){
                List<DeviceVo> collect = deviceList.stream()
                        .filter(device -> StrUtil.equals(device.getDeviceId(), node.getId()))
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(collect)){
                    DeviceVo deviceVo = collect.get(0);
                    temp.setExtra(JSONUtil.createObj()
                            .putOnce("nodeType", node.getNodeType())
                            .putOnce("shortName", node.getShortName())
                            .putOnce("code", node.getType())
                            .putOnce("deviceDictId", StrUtil.isBlank(deviceVo.getDeviceDictId()) ? "" : deviceVo.getDeviceDictId())
                            .putOnce("centerOffNo", StrUtil.isBlank(deviceVo.getCenterOffNo()) ? "" : deviceVo.getCenterOffNo())
                            .putOnce("desc", StrUtil.isBlank(deviceVo.getRemark()) ? "" : deviceVo.getRemark())
                            .putOnce("direction", StrUtil.isBlank(deviceVo.getDirection()) ? "" : deviceVo.getDirection())
                    );
                }
            }
            else {
                temp.setExtra(JSONUtil.createObj()
                                .putOnce("nodeType", node.getNodeType())
                                .putOnce("shortName", node.getShortName())
                                .putOnce("code", node.getType()));
            }
            nodeList.add(temp);
        });

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public DeviceVo belongToDevice(String deviceId) {
        return deviceDao.belongToDevice(deviceId);
    }

    private UserPermissions setValue(CommonBo commonBo){
        commonService.checkParams(commonBo.getNodeId(),commonBo.getNodeType());
        UserPermissions userPermissions = commonService.getUserPermissions(commonBo.getIsUseUserPermissions());
        if (CollUtil.isEmpty(commonBo.getCodes())){
            commonBo.setCodes(null);
        }
        commonBo.setNodeType(NodeTypeEnum.getName(commonBo.getNodeType()));
        return userPermissions;
    }

    private void checkDistanceParams(DeviceListBo deviceListBo) {
        if (null == deviceListBo.getLongitude()){
            if (null != deviceListBo.getLatitude())
                throw new BadRequestException("经纬度必须同时非空");
        }

        if (null == deviceListBo.getLatitude()){
            if (null != deviceListBo.getLongitude())
                throw new BadRequestException("经纬度必须同时非空");
        }

        if (null != deviceListBo.getLongitude() && null != deviceListBo.getLatitude()){
            if (deviceListBo.getLongitude() > 180){
                throw new BadRequestException("经度不大于180");
            }
            if (deviceListBo.getLongitude() < -179){
                throw new BadRequestException("经度不小于179");
            }
            if(deviceListBo.getLatitude() > 90){
                throw new BadRequestException("纬度不大于90");
            }
            if(deviceListBo.getLatitude() < -90){
                throw new BadRequestException("纬度不小于90");
            }
        }
    }

    private List<TreeNode> buildTree(List<TreeNode> sources,List<TreeNode> parentNodes){
        parentNodes = parentNodes.stream()
                .sorted(Comparator.comparing(TreeNode::getSort))
                .collect(Collectors.toList());

        List<TreeNode> resList = new ArrayList<>();
        List<TreeNode> childrenList = new ArrayList<>();
        for (TreeNode parent : parentNodes) {
            List<TreeNode> children = sources.stream()
                    .filter(source -> StrUtil.equals(parent.getId(), source.getParentId()))
                    .collect(Collectors.toList());

            if (CollUtil.isNotEmpty(children)){
                childrenList.addAll(children);
            }
            parent.setChildren(children);
            resList.add(parent);
        }
        if (CollUtil.isNotEmpty(childrenList)){
            this.buildTree(sources,childrenList);
        }
        return resList;
    }

    private List<TreeNode> tree2List(List<TreeNode> tree,List<DeviceVo> deviceVoList){
        List<TreeNode> resList = new ArrayList<>();

        if (CollUtil.isEmpty(tree)){
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        for (TreeNode root : tree) {
            queue.offer(root);
        }
        while (!queue.isEmpty()){
            TreeNode treeNode = queue.poll();
            if (CollUtil.isNotEmpty(treeNode.getChildren())){
                for (TreeNode child : treeNode.getChildren()) {
                    queue.offer(child);
                }
            }
            else {
                List<DeviceVo> deviceList = deviceVoList.stream()
                        .filter(deviceVo -> (StrUtil.equals(deviceVo.getDeviceType(), treeNode.getType()) &&
                                StrUtil.equals(deviceVo.getFacilitiesId(), treeNode.getParentId()))).collect(Collectors.toList());
                List<TreeNode> deviceNodeList = deviceList.stream().map(device -> {
                    TreeNode deviceVo = new TreeNode();
                    deviceVo.setId(device.getDeviceId());
                    deviceVo.setName(device.getDeviceName());
                    deviceVo.setNodeType("device");
                    deviceVo.setParentId(treeNode.getId());
                    deviceVo.setShortName(device.getDeviceName());
                    deviceVo.setType(device.getDeviceType());
                    deviceVo.setSort(device.getSort());
                    return deviceVo;
                }).collect(Collectors.toList());
                resList.addAll(deviceNodeList);
            }
            resList.add(treeNode);
        }
        return resList;
    }
}
