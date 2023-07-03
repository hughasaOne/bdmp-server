package com.rhy.bdmp.base.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.WayMapping;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author weicaifu
 * @date 2023-01-09 09:19
 * @version V1.0
 **/
@Mapper
public interface BaseWayMappingDao extends BaseMapper<WayMapping> {

}
