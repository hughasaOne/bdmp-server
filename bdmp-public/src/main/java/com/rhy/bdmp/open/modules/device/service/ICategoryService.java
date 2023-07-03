package com.rhy.bdmp.open.modules.device.service;

import com.rhy.bdmp.open.modules.device.domain.bo.CategoryDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceCategoryBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceCategoryVo;

import java.util.List;

public interface ICategoryService {
    List<DeviceCategoryVo> getCategoryList(DeviceCategoryBo deviceCategoryBo);

    DeviceCategoryVo getCategoryDetail(CategoryDetailBo categoryBo);
}
