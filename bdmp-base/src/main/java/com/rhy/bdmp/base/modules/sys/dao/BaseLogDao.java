package com.rhy.bdmp.base.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 日志 数据操作接口
 * @author weicaifu
 * @date 2022-10-17 17:21
 * @version V1.0
 **/
@Mapper
public interface BaseLogDao extends BaseMapper<Log> {

}
