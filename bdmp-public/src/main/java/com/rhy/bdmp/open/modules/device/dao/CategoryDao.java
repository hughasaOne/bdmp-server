package com.rhy.bdmp.open.modules.device.dao;

import com.rhy.bdmp.open.modules.device.domain.bo.CategoryDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceCategoryBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceCategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryDao {
    List<DeviceCategoryVo> getCategoryList(@Param("deviceCategoryBo") DeviceCategoryBo deviceCategoryBo);

    DeviceCategoryVo getCategoryDetail(@Param("categoryBo") CategoryDetailBo categoryBo);
}
