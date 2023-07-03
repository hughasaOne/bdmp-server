package com.rhy.bdmp.system.modules.gateway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayRouteVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author author
 * @version V1.0
 * @description 数据操作接口
 * @date 2022-05-30 17:13
 **/
@Mapper
public interface GatewayConfigDao extends BaseMapper<GatewayRouteVO> {
}
