package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictPostDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DictPost;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictPostService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 岗位字典 服务实现
 * @author weicaifu
 * @date 2022-03-16 09:22
 * @version V1.0
 **/
@Service
public class BaseDictPostServiceImpl extends ServiceImpl<BaseDictPostDao, DictPost> implements IBaseDictPostService {

    /**
     * 岗位字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DictPost> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictPost> query = new Query<DictPost>(queryVO);
            QueryWrapper<DictPost> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DictPost> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DictPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 岗位字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DictPost> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictPost> query = new Query<DictPost>(queryVO);
            Page<DictPost> page = query.getPage();
            QueryWrapper<DictPost> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DictPost>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看岗位字典(根据ID)
     * @param postId
     * @return
     */
    @Override
    public DictPost detail(String postId) {
        if (!StrUtil.isNotBlank(postId)) {
            throw new BadRequestException("not exist postId");
        }
        DictPost dictPost = getBaseMapper().selectById(postId);
        return dictPost;
    }

    /**
     * 新增岗位字典
     * @param dictPost
     * @return
     */
    @Override
    public int create(DictPost dictPost) {
        if (StrUtil.isNotBlank(dictPost.getPostId())) {
            throw new BadRequestException("A new DictPost cannot already have an postId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        dictPost.setCreateBy(currentUser);
        dictPost.setCreateTime(currentDateTime);
        dictPost.setUpdateBy(currentUser);
        dictPost.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(dictPost);
        return result;
    }

    /**
     * 修改岗位字典
     * @param dictPost
     * @return
     */
    @Override
    public int update(DictPost dictPost) {
       if (!StrUtil.isNotBlank(dictPost.getPostId())) {
           throw new BadRequestException("A new DictPost not exist postId");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       dictPost.setUpdateBy(currentUser);
       dictPost.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(dictPost);
       return result;
    }

    /**
     * 删除岗位字典
     * @param postIds
     * @return
     */
    @Override
    public int delete(Set<String> postIds) {
        int result = getBaseMapper().deleteBatchIds(postIds);
        return result;
    }

}
