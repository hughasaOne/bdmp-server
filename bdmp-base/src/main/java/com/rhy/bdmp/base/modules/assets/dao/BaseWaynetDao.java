package com.rhy.bdmp.base.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 高速路网 数据操作接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Mapper
public interface BaseWaynetDao extends BaseMapper<Waynet> {

}
