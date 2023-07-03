package com.rhy.bdmp.system.modules.assets.service;

import cn.hutool.core.lang.tree.Tree;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryBindingDataBo;
import com.rhy.bdmp.system.modules.assets.domain.bo.CategoryDeviceBo;

import java.util.List;
import java.util.Set;

public interface IDeviceCategoryService {
    List<Tree<String>> getCategoryTree();

    List<Tree<String>> getCategoryItem();

    DeviceCategory detail(String categoryId);

    Object getBindingData(CategoryBindingDataBo bindingDataBo);

    Object device(CategoryDeviceBo deviceBo);

    void add(DeviceCategory deviceCategory);

    void update(DeviceCategory deviceCategory);

    void delete(Set<String> ids);

}
