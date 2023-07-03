package com.rhy.bdmp.open.modules.device.service.impl;

import com.rhy.bdmp.open.modules.device.dao.CategoryDao;
import com.rhy.bdmp.open.modules.device.domain.bo.CategoryDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceCategoryBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceCategoryVo;
import com.rhy.bdmp.open.modules.device.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Resource
    private CategoryDao categoryDao;

    @Override
    public List<DeviceCategoryVo> getCategoryList(DeviceCategoryBo deviceCategoryBo) {
        return categoryDao.getCategoryList(deviceCategoryBo);
    }

    @Override
    public DeviceCategoryVo getCategoryDetail(CategoryDetailBo categoryBo) {
        return categoryDao.getCategoryDetail(categoryBo);
    }
}
