package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author shuaichao
 * @date 2022-03-07 13:39
 * @version V1.0
 **/
public interface IBaseNewsNoticeService extends IService<NewsNotice> {

    /**
     * 列表查询
     *
     * @param queryVO 查询条件
     * @return
     */
    List<NewsNotice> list(QueryVO queryVO);

    /**
     * 列表查询(分页)
     *
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<NewsNotice> page(QueryVO queryVO);

    /**
     * 查看(根据ID)
     *
     * @param newsNoticeId
     * @return
     */
    NewsNotice detail(String newsNoticeId);

    /**
     * 新增
     *
     * @param newsNotice
     * @return
     */
    int create(NewsNotice newsNotice);

    /**
     * 修改
     *
     * @param newsNotice
     * @return
     */
    int update(NewsNotice newsNotice);

    /**
     * 删除
     *
     * @param newsNoticeIds
     * @return
     */
    int delete(Set<String> newsNoticeIds);
}
