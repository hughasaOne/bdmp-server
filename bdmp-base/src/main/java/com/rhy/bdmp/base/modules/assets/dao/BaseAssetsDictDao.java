package com.rhy.bdmp.base.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 台账字典 数据操作接口
 * @author yanggj
 * @date 2021-09-24 15:36
 * @version V1.0
 **/
@Mapper
public interface BaseAssetsDictDao extends BaseMapper<Dict> {

}
