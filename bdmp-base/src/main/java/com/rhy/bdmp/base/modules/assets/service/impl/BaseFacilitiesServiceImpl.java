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
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesDao;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 路段设施 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseFacilitiesServiceImpl extends ServiceImpl<BaseFacilitiesDao, Facilities> implements IBaseFacilitiesService {

    /**
     * 路段设施列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Facilities> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Facilities> query = new Query<Facilities>(queryVO);
            QueryWrapper<Facilities> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Facilities> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Facilities> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 路段设施列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Facilities> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Facilities> query = new Query<Facilities>(queryVO);
            Page<Facilities> page = query.getPage();
            QueryWrapper<Facilities> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Facilities>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看路段设施(根据ID)
     * @param facilitiesId
     * @return
     */
    @Override
    public Facilities detail(String facilitiesId) {
        if (!StrUtil.isNotBlank(facilitiesId)) {
            throw new BadRequestException("not exist facilitiesId");
        }
        Facilities facilities = getBaseMapper().selectById(facilitiesId);
        return facilities;
    }

    /**
     * 新增路段设施
     * @param facilities
     * @return
     */
    @Override
    public int create(Facilities facilities) {
        if (StrUtil.isNotBlank(facilities.getFacilitiesId())) {
            throw new BadRequestException("A new Facilities cannot already have an facilitiesId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        facilities.setCreateBy(currentUser);
        facilities.setCreateTime(currentDateTime);
        facilities.setUpdateBy(currentUser);
        facilities.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(facilities);
        return result;
    }

    /**
     * 修改路段设施
     * @param facilities
     * @return
     */
    @Override
    public int update(Facilities facilities) {
       if (!StrUtil.isNotBlank(facilities.getFacilitiesId())) {
           throw new BadRequestException("A new Facilities not exist facilitiesId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       facilities.setUpdateBy(currentUser);
       facilities.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(facilities);
       return result;
    }

    /**
     * 删除路段设施
     * @param facilitiesIds
     * @return
     */
    @Override
    public int delete(Set<String> facilitiesIds) {
        int result = getBaseMapper().deleteBatchIds(facilitiesIds);
        return result;
    }

}
