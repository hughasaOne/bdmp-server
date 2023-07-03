package com.rhy.bdmp.base.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 系统字典 数据操作接口
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
@Mapper
public interface BaseDictSystemDao extends BaseMapper<DictSystem> {

}
