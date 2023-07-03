package com.rhy.bdmp.collect.modules.vp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.collect.modules.vp.domain.po.Resource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseVPDao extends BaseMapper<Resource> {
}
