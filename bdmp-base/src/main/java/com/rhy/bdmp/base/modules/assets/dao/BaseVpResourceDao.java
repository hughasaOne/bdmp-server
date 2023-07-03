package com.rhy.bdmp.base.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.VpResource;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 可视对讲资源表 数据操作接口
 * @author weicaifu
 * @date 2022-10-12 11:19
 * @version V1.0
 **/
@Mapper
public interface BaseVpResourceDao extends BaseMapper<VpResource> {

}
