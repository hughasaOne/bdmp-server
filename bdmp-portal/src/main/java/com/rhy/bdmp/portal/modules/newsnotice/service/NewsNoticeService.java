package com.rhy.bdmp.portal.modules.newsnotice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;

import java.util.Date;
import java.util.List;

/**
 * @author shuaichao
 * @create 2022-03-07 13:47
 */
public interface NewsNoticeService  {
    /**
     * 查询当天新闻
     * @return
     */
    List<NewsNotice>  list();

    void deleteAndinsertByJsoup(List<NewsNotice> newsNotices);

    List<NewsNotice> getByJsoup();

    Long deleteByCreateDate(Date date);

    Integer insertByJsoup();

    NewsNotice findById(String newsNoticeId);
}
