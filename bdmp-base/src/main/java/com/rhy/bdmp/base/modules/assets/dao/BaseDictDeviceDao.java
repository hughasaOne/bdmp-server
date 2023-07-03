package com.rhy.bdmp.base.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 设备字典 数据操作接口
 * @author yanggj
 * @date 2021-10-26 09:05
 * @version V1.0
 **/
@Mapper
public interface BaseDictDeviceDao extends BaseMapper<DictDevice> {

}
