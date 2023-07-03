package com.rhy.bdmp.base.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceExternal;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 外接设备 数据操作接口
 * @author weicaifu
 * @date 2022-10-28 16:43
 * @version V1.0
 **/
@Mapper
public interface BaseDeviceExternalDao extends BaseMapper<DeviceExternal> {

}
