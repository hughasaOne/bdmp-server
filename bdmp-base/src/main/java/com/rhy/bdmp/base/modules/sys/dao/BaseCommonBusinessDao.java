package com.rhy.bdmp.base.modules.sys.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.CommonBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Mapper
public interface BaseCommonBusinessDao extends BaseMapper<CommonBusiness> {

}
