package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.base.modules.assets.domain.po.VpResource;
import com.rhy.bdmp.base.modules.assets.service.IBaseVpResourceService;
import com.rhy.bdmp.system.modules.assets.domain.bo.IPTelListBo;
import com.rhy.bdmp.system.modules.assets.service.IIPTelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IPTelServiceImpl implements IIPTelService {

    @Resource
    private IBaseVpResourceService vpResourceService;

    /**
     * ip电话目录
     */
    @Override
    public Object getDir() {
        List<VpResource> vpList = vpResourceService.list(new LambdaQueryWrapper<VpResource>().eq(VpResource::getNodeType, 1));
        if (CollUtil.isEmpty(vpList)){
            return null;
        }
        List<VpResource> tempList = new ArrayList<>();
        tempList.addAll(vpList);
        List<TreeNode<String>> nodeList = new ArrayList<>();

        for (VpResource vp : vpList) {
            if (StrUtil.isBlank(vp.getParentId())){
                vp.setParentId("0");
            }
            long sort = Optional.ofNullable(vp.getSort()).orElse(0L);
            nodeList.add(new TreeNode<String>()
                    .setId(vp.getId())
                    .setName(vp.getName())
                    .setParentId(vp.getParentId())
                    .setWeight(sort)
                    .setExtra(JSONUtil.createObj()
                            .putOnce("isLeaf", tempList.stream().noneMatch(vp1 -> StrUtil.equals(vp.getId(),vp1.getParentId())))
                            .putOnce("datastatusid", vp.getDatastatusid())
                    ));
        }

        // 构造树结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * IP电话资源列表
     */
    @Override
    public Object getResourceList(IPTelListBo ipTelListBo) {
        LambdaQueryWrapper<VpResource> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 父节点
        if (StrUtil.isNotBlank(ipTelListBo.getDirId())){
            lambdaQueryWrapper.eq(VpResource::getParentId,ipTelListBo.getDirId());
        }
        // 名称
        if (StrUtil.isNotBlank(ipTelListBo.getName())){
            lambdaQueryWrapper.like(VpResource::getName,ipTelListBo.getName());
        }
        // 数据状态
        if (null != ipTelListBo.getDatastatusid()){
            lambdaQueryWrapper.eq(VpResource::getDatastatusid,ipTelListBo.getDatastatusid());
        }
        // 在线状态
        if (null != ipTelListBo.getStatus()){
            lambdaQueryWrapper.eq(VpResource::getStatus,ipTelListBo.getStatus());
        }
        lambdaQueryWrapper.orderByAsc(VpResource::getSort);
        Page<VpResource> vpPage = new Page<>();
        vpPage.setSize(ipTelListBo.getPageSize());
        vpPage.setCurrent(ipTelListBo.getCurrentPage());
        return new PageUtil<>(vpResourceService.page(vpPage,lambdaQueryWrapper));
    }

    @Override
    public Object detail(String id) {
        return vpResourceService.detail(id);
    }

    @Override
    public void add(VpResource vpResource) {
        if (StrUtil.isBlank(vpResource.getName())){
            throw new BadRequestException("名称不为空");
        }
        vpResource.setDatastatusid(Optional.ofNullable(vpResource.getDatastatusid()).orElse(1));
        vpResource.setSort(Optional.ofNullable(vpResource.getSort()).orElse(0L));
        vpResourceService.create(vpResource);
    }

    @Override
    public void delete(Set<String> ids) {
        if (CollUtil.isEmpty(ids)){
            return;
        }
        List<VpResource> vpList = vpResourceService.list(new LambdaQueryWrapper<VpResource>().in(VpResource::getId,ids));
        vpList = vpList.stream().map(vp -> {
            vp.setDatastatusid(0);
            return vp;
        }).collect(Collectors.toList());
        vpResourceService.updateBatchById(vpList);
    }

    @Override
    public void update(VpResource vpResource) {
        if (StrUtil.isBlank(vpResource.getId())){
            throw new BadRequestException("id不为空");
        }
        if (StrUtil.isBlank(vpResource.getName())){
            throw new BadRequestException("名称不为空");
        }
        vpResourceService.update(vpResource);
    }
}
