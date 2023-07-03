package com.rhy.bdmp.gateway.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.gateway.modules.domain.po.GatewayRoutePo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author weicaifu
 * @date 2022-05-27 16:13
 * @version V1.0
 **/
@Mapper
public interface BaseGatewayRouteDao extends BaseMapper<GatewayRoutePo> {

}
