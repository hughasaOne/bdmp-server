package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.WaysectionPlan;
import com.rhy.bdmp.base.modules.assets.dao.BaseWaysectionPlanDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaysectionPlanService;
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
 * @description 计划里程 服务实现
 * @author duke
 * @date 2021-11-12 10:58
 * @version V1.0
 **/
@Service
public class BaseWaysectionPlanServiceImpl extends ServiceImpl<BaseWaysectionPlanDao, WaysectionPlan> implements IBaseWaysectionPlanService {

    /**
     * 计划里程列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<WaysectionPlan> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<WaysectionPlan> query = new Query<WaysectionPlan>(queryVO);
            QueryWrapper<WaysectionPlan> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<WaysectionPlan> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<WaysectionPlan> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 计划里程列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<WaysectionPlan> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<WaysectionPlan> query = new Query<WaysectionPlan>(queryVO);
            Page<WaysectionPlan> page = query.getPage();
            QueryWrapper<WaysectionPlan> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<WaysectionPlan>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看计划里程(根据ID)
     * @param id
     * @return
     */
    @Override
    public WaysectionPlan detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        WaysectionPlan waysectionPlan = getBaseMapper().selectById(id);
        return waysectionPlan;
    }

    /**
     * 新增计划里程
     * @param waysectionPlan
     * @return
     */
    @Override
    public int create(WaysectionPlan waysectionPlan) {
        if (StrUtil.isNotBlank(waysectionPlan.getId())) {
            throw new BadRequestException("A new WaysectionPlan cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        waysectionPlan.setCreateBy(currentUser);
        waysectionPlan.setCreateTime(currentDateTime);
        waysectionPlan.setUpdateBy(currentUser);
        waysectionPlan.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(waysectionPlan);
        return result;
    }

    /**
     * 修改计划里程
     * @param waysectionPlan
     * @return
     */
    @Override
    public int update(WaysectionPlan waysectionPlan) {
       if (!StrUtil.isNotBlank(waysectionPlan.getId())) {
           throw new BadRequestException("A new WaysectionPlan not exist id");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       waysectionPlan.setUpdateBy(currentUser);
       waysectionPlan.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(waysectionPlan);
       return result;
    }

    /**
     * 删除计划里程
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
