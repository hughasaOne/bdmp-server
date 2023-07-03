package com.rhy.bdmp.base.modules.sys.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description  数据操作接口
 * @author shuaichao
 * @date 2022-03-07 13:39
 * @version V1.0
 **/
@Mapper
public interface BaseNewsNoticeDao extends BaseMapper<NewsNotice> {

}
