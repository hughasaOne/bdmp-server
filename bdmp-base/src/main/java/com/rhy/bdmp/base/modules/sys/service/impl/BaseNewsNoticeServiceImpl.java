package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
import com.rhy.bdmp.base.modules.sys.dao.BaseNewsNoticeDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseNewsNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description  服务实现
 * @author shuaichao
 * @date 2022-03-07 13:39
 * @version V1.0
 **/
@Service
public class BaseNewsNoticeServiceImpl extends ServiceImpl<BaseNewsNoticeDao, NewsNotice> implements IBaseNewsNoticeService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<NewsNotice> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<NewsNotice> query = new Query<NewsNotice>(queryVO);
            QueryWrapper<NewsNotice> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                List<NewsNotice> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<NewsNotice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<NewsNotice> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<NewsNotice> query = new Query<NewsNotice>(queryVO);
            Page<NewsNotice> page = query.getPage();
            QueryWrapper<NewsNotice> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<NewsNotice>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param newsNoticeId
     * @return
     */
    @Override
    public NewsNotice detail(String newsNoticeId) {
        if (!StrUtil.isNotBlank(newsNoticeId)) {
            throw new BadRequestException("not exist newsNoticeId");
        }
        NewsNotice newsNotice = getBaseMapper().selectById(newsNoticeId);
        return newsNotice;
    }

    /**
     * 新增
     * @param newsNotice
     * @return
     */
    @Override
    public int create(NewsNotice newsNotice) {
        if (StrUtil.isNotBlank(newsNotice.getNewsNoticeId())) {
            throw new BadRequestException("A new NewsNotice cannot already have an newsNoticeId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        newsNotice.setCreateBy(currentUser);
        newsNotice.setCreateTime(currentDateTime);
        int result = getBaseMapper().insert(newsNotice);
        return result;
    }

    /**
     * 修改
     * @param newsNotice
     * @return
     */
    @Override
    public int update(NewsNotice newsNotice) {
       if (!StrUtil.isNotBlank(newsNotice.getNewsNoticeId())) {
           throw new BadRequestException("A new NewsNotice not exist newsNoticeId");
       }
       int result = getBaseMapper().updateById(newsNotice);
       return result;
    }

    /**
     * 删除
     * @param newsNoticeIds
     * @return
     */
    @Override
    public int delete(Set<String> newsNoticeIds) {
        int result = getBaseMapper().deleteBatchIds(newsNoticeIds);
        return result;
    }

}
