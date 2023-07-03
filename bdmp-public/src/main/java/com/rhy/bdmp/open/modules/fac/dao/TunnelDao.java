package com.rhy.bdmp.open.modules.fac.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "TunnelDaoV1")
public interface TunnelDao extends BaseMapper<FacilitiesTunnel> {

}
