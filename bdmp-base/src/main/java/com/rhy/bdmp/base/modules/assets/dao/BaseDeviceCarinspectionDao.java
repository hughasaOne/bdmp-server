package com.rhy.bdmp.base.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 车检器 数据操作接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Mapper
public interface BaseDeviceCarinspectionDao extends BaseMapper<DeviceCarinspection> {

}
