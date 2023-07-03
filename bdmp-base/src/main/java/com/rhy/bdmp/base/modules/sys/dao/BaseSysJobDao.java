package com.rhy.bdmp.base.modules.sys.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author shuaichao
 * @date 2022-03-17 11:38
 * @version V1.0
 **/
@Mapper
public interface BaseSysJobDao extends BaseMapper<SysJob> {

}
