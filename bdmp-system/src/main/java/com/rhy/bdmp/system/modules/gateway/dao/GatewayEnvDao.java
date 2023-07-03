package com.rhy.bdmp.system.modules.gateway.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayEnvVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author author
 * @date 2022-06-22 09:40
 * @version V1.0
 **/
@Mapper
public interface GatewayEnvDao extends BaseMapper<GatewayEnvVO> {

}
