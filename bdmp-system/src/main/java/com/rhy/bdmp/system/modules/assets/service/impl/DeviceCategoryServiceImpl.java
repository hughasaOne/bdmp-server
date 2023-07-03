package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceCategoryService;
import com.rhy.bdmp.system.modules.assets.dao.DeviceCategoryDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryBindingDataBo;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryDeviceBo;
import com.rhy.bdmp.system.modules.assets.service.IDeviceCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author weicaifu
 */
@Service
public class DeviceCategoryServiceImpl implements IDeviceCategoryService {
    @Resource
    private IBaseDeviceCategoryService baseDeviceCategoryService;

    @Resource
    private DeviceCategoryDao deviceCategoryDao;

    /**
     * 设备分类树
     */
    @Override
    public List<Tree<String>> getCategoryTree() {
        List<DeviceCategory> list = baseDeviceCategoryService.list();
        if (CollUtil.isEmpty(list)){
            return null;
        }
        List<TreeNode<String>> nodeList = new ArrayList<>();

        for (DeviceCategory deviceCategory : list) {
            if (StrUtil.isBlank(deviceCategory.getParentId())){
                deviceCategory.setParentId("0");
            }
            deviceCategory.setSort(Optional.ofNullable(deviceCategory.getSort()).orElse(0L));
            Map<String,Object> extra = new HashMap<String,Object>();
            extra.put("categoryInfo",deviceCategory.getCategoryRule());
            extra.put("categoryCode",deviceCategory.getCategoryCode());
            extra.put("nodeType",deviceCategory.getNodeType());
            extra.put("datastatusid",deviceCategory.getDatastatusid());
            extra.put("categoryType",deviceCategory.getCategoryType());
            nodeList.add(new TreeNode<String>()
                    .setId(deviceCategory.getId())
                    .setName(deviceCategory.getName())
                    .setParentId(deviceCategory.getParentId())
                    .setWeight(deviceCategory.getSort())
                    .setExtra(extra)
            );
        }
        // 构造树结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * 设备分类选择项
     */
    @Override
    public List<Tree<String>> getCategoryItem() {

        List<Map<String, Object>> deviceTypes = deviceCategoryDao.getCategoryItemNode();
        if (CollUtil.isEmpty(deviceTypes)){
            return null;
        }

        List<TreeNode<String>> nodeList = new ArrayList<>();
        for (Map<String, Object> deviceType : deviceTypes) {
            TreeNode<String> deviceTypeNode = new TreeNode<String>();
            Map<String,Object> deviceTypeExtra = new HashMap<String,Object>();
            deviceTypeExtra.put("code",MapUtil.getStr(deviceType,"code"));
            deviceTypeExtra.put("value",MapUtil.getStr(deviceType,"value"));
            deviceTypeExtra.put("nodeType",MapUtil.getStr(deviceType,"nodeType"));
            deviceTypeNode
                    .setId(MapUtil.getStr(deviceType,"id"))
                    .setName(MapUtil.getStr(deviceType,"name"))
                    .setParentId(MapUtil.getStr(deviceType,"parentId"))
                    .setWeight(MapUtil.getStr(deviceType,"sort"))
                    .setExtra(deviceTypeExtra);
            nodeList.add(deviceTypeNode);
        }
        // 构造树结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    @Override
    public DeviceCategory detail(String categoryId) {
        return baseDeviceCategoryService.getById(categoryId);
    }

    @Override
    public Object getBindingData(CategoryBindingDataBo bindingDataBo) {
        String categoryId = bindingDataBo.getCategoryId();
        DeviceCategory category = baseDeviceCategoryService.getById(categoryId);
        if (null == category){
            return null;
        }
        if (StrUtil.isBlank(category.getCategoryRule())){
            return null;
        }
        // 解析JSON
        if (!JSONUtil.isJson(category.getCategoryRule())){
            throw new BadRequestException("分类规则格式错误");
        }
        JSONArray codes = JSONUtil.parseArray(category.getCategoryRule());
        // 判断存的是设备类型还是设备字典
        Integer categoryType = category.getCategoryType();
        if (null == categoryType){
            throw new BadRequestException("分类类型不存在");
        }
        List<Map<String,Object>> resList = null;
        if (CollUtil.isEmpty(codes)){
            return null;
        }
        if (1 == categoryType){
            resList = deviceCategoryDao.getDeviceType(codes);
        }
        else if(2 == categoryType){
            resList = deviceCategoryDao.getDeviceDict(codes);
        }
        else if(3 == categoryType){
            resList = deviceCategoryDao.getDeviceByCodes(codes);
        }
        return resList;
    }

    @Override
    public Object device(CategoryDeviceBo deviceBo) {
        DeviceCategory category = baseDeviceCategoryService.getById(deviceBo.getCategoryId());
        if (null == category){
            return null;
        }
        JSONArray codes = null;
        if (category.getCategoryType() == 3){

        }
        else {
            if (StrUtil.isBlank(category.getCategoryRule())){
                return null;
            }
            if (!JSONUtil.isJson(category.getCategoryRule())){
                throw new BadRequestException("分类规则格式错误");
            }

            codes = JSONUtil.parseArray(category.getCategoryRule());

            if (CollUtil.isEmpty(codes)){
                return null;
            }
        }

        Page<Map<String,Object>> page = new Page<>();
        page.setCurrent(deviceBo.getCurrentPage());
        page.setSize(deviceBo.getPageSize());
        if ((StrUtil.isBlank(deviceBo.getNodeId()) != (null == deviceBo.getNodeType()))) {
            throw new BadRequestException("nodeId 和 nodeType 必须同时为空或同时非空");
        }
        Page<Map<String,Object>> resPage = deviceCategoryDao.getDevice(page,codes,deviceBo,category.getCategoryType());
        return new PageUtil<>(resPage);
    }

    @Override
    public void add(DeviceCategory deviceCategory) {
        this.checkParams(deviceCategory.getNodeType(),deviceCategory.getCategoryRule());
        baseDeviceCategoryService.create(deviceCategory);
    }

    @Override
    public void update(DeviceCategory deviceCategory) {
        if (StrUtil.isBlank(deviceCategory.getId())){
            throw new BadRequestException("id不为空");
        }
        this.checkParams(deviceCategory.getNodeType(),deviceCategory.getCategoryRule());
        baseDeviceCategoryService.update(deviceCategory);
    }

    @Override
    public void delete(Set<String> ids) {
        if (CollUtil.isEmpty(ids)){
            return;
        }
        List<DeviceCategory> deviceCategories = baseDeviceCategoryService.list(new LambdaQueryWrapper<DeviceCategory>().in(DeviceCategory::getParentId,ids));
        if (CollUtil.isNotEmpty(deviceCategories)){
            throw new BadRequestException("请先删除子节点");
        }
        baseDeviceCategoryService.delete(ids);
    }

    private void checkParams(Integer nodeType, String categoryInfo) {
        if (null == nodeType){
            throw new BadRequestException("节点类型不为空");
        }
        else {
            List<Integer> nodeTypes = Arrays.asList(1, 2, 3, 4);
            if (nodeTypes.stream().noneMatch(node -> node == nodeType)){
                throw new BadRequestException("节点类型不存在");
            }
        }

        if (nodeType == 4){
            if (StrUtil.isNotBlank(categoryInfo)){
                if (!JSONUtil.isJson(categoryInfo)){
                    throw new BadRequestException("分类规则格式错误");
                }
            }
        }
    }
}
