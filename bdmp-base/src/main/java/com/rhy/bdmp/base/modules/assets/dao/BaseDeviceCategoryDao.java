package com.rhy.bdmp.base.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 设备分类 数据操作接口
 * @author weicaifu
 * @date 2022-10-17 10:45
 * @version V1.0
 **/
@Mapper
public interface BaseDeviceCategoryDao extends BaseMapper<DeviceCategory> {

}
