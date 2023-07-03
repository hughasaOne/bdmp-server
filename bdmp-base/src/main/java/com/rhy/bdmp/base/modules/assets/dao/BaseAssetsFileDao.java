package com.rhy.bdmp.base.modules.assets.dao;

import com.rhy.bdmp.base.modules.assets.domain.po.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 文件 数据操作接口
 * @author yanggj
 * @date 2021-09-24 15:36
 * @version V1.0
 **/
@Mapper
public interface BaseAssetsFileDao extends BaseMapper<File> {

}
