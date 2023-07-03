package com.rhy.bdmp.portal.modules.newsnotice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description  数据操作接口
 * @author shuaichao
 * @date 2022-03-07 13:39
 * @version V1.0
 **/
@Mapper
public interface NewsNoticeDao extends BaseMapper<NewsNotice> {

     List<NewsNotice> findByCurrentDate(@Param("currentDate") String currentDate);

    Long deleteByCreateDate(@Param("currentDate") Date currentDate);
}
