package com.rhy.bdmp.base.modules.sys.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 资源 数据操作接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Mapper
public interface BaseResourceDao extends BaseMapper<Resource> {

}
