package com.rhy.bdmp.base.modules.sys.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 组织机构 数据操作接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Mapper
public interface BaseOrgDao extends BaseMapper<Org> {

}
