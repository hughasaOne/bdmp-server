package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.DailySchedule;
import com.rhy.bdmp.base.modules.sys.dao.BaseDailyScheduleDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseDailyScheduleService;
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
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Service
public class BaseDailyScheduleServiceImpl extends ServiceImpl<BaseDailyScheduleDao, DailySchedule> implements IBaseDailyScheduleService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DailySchedule> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DailySchedule> query = new Query<DailySchedule>(queryVO);
            QueryWrapper<DailySchedule> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                List<DailySchedule> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DailySchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DailySchedule> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DailySchedule> query = new Query<DailySchedule>(queryVO);
            Page<DailySchedule> page = query.getPage();
            QueryWrapper<DailySchedule> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DailySchedule>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param dailyScheduleId
     * @return
     */
    @Override
    public DailySchedule detail(String dailyScheduleId) {
        if (!StrUtil.isNotBlank(dailyScheduleId)) {
            throw new BadRequestException("not exist dailyScheduleId");
        }
        DailySchedule dailySchedule = getBaseMapper().selectById(dailyScheduleId);
        return dailySchedule;
    }

    /**
     * 新增
     * @param dailySchedule
     * @return
     */
    @Override
    public int create(DailySchedule dailySchedule) {
        if (StrUtil.isNotBlank(dailySchedule.getDailyScheduleId())) {
            throw new BadRequestException("A new DailySchedule cannot already have an dailyScheduleId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        dailySchedule.setCreateBy(currentUser);
        dailySchedule.setCreateTime(currentDateTime);
        dailySchedule.setUpdateBy(currentUser);
        dailySchedule.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(dailySchedule);
        return result;
    }

    /**
     * 修改
     * @param dailySchedule
     * @return
     */
    @Override
    public int update(DailySchedule dailySchedule) {
       if (!StrUtil.isNotBlank(dailySchedule.getDailyScheduleId())) {
           throw new BadRequestException("A new DailySchedule not exist dailyScheduleId");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       dailySchedule.setUpdateBy(currentUser);
       dailySchedule.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(dailySchedule);
       return result;
    }

    /**
     * 删除
     * @param dailyScheduleIds
     * @return
     */
    @Override
    public int delete(Set<String> dailyScheduleIds) {
        int result = getBaseMapper().deleteBatchIds(dailyScheduleIds);
        return result;
    }

}
