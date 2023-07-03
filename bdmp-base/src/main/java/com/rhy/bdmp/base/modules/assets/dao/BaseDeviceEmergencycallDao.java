package com.rhy.bdmp.base.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 紧急电话 数据操作接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Mapper
public interface BaseDeviceEmergencycallDao extends BaseMapper<DeviceEmergencycall> {

}
