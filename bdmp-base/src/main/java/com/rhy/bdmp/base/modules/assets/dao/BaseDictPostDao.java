package com.rhy.bdmp.base.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.assets.domain.po.DictPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 岗位字典 数据操作接口
 * @author weicaifu
 * @date 2022-03-16 09:22
 * @version V1.0
 **/
@Mapper
public interface BaseDictPostDao extends BaseMapper<DictPost> {

}
