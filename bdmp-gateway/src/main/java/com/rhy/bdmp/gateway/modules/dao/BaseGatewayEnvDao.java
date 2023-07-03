package com.rhy.bdmp.gateway.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.gateway.modules.domain.po.GatewayEnvPo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author weicaifu
 * @date 2022-06-07 12:42
 * @version V1.0
 **/
@Mapper
public interface BaseGatewayEnvDao extends BaseMapper<GatewayEnvPo> {

}
