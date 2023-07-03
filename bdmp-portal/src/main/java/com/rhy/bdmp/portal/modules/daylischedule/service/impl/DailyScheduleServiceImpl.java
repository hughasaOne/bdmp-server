package com.rhy.bdmp.portal.modules.daylischedule.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.DailySchedule;
import com.rhy.bdmp.portal.modules.daylischedule.dao.DailyScheduleDao;
import com.rhy.bdmp.portal.modules.daylischedule.service.DailyScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class DailyScheduleServiceImpl implements DailyScheduleService {

    @Resource
    DailyScheduleDao dailyScheduleDao;
    /**
     * 列表查询
     * @param dailySchedule 查询条件
     * @return
     */
    @Override
    public List<DailySchedule> list(DailySchedule dailySchedule) {
        //2、无查询条件
        String userId = WebUtils.getUserId();
        QueryWrapper<DailySchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.orderByDesc("create_time");
        return dailyScheduleDao.selectList(queryWrapper);
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
        dailySchedule.setUserId(currentUser);
        dailySchedule.setCreateBy(currentUser);
        dailySchedule.setCreateTime(currentDateTime);
        dailySchedule.setUpdateBy(currentUser);
        dailySchedule.setUpdateTime(currentDateTime);
        int result = dailyScheduleDao.insert(dailySchedule);
        return result;
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
        DailySchedule dailySchedule = dailyScheduleDao.selectById(dailyScheduleId);
        return dailySchedule;
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
       dailySchedule.setUserId(currentUser);
       dailySchedule.setUpdateBy(currentUser);
       dailySchedule.setUpdateTime(currentDateTime);
       int result = dailyScheduleDao.updateById(dailySchedule);
       return result;
    }

    /**
     * 删除
     * @param dailyScheduleIds
     * @return
     */
    @Override
    public int delete(Set<String> dailyScheduleIds) {
        int result = dailyScheduleDao.deleteBatchIds(dailyScheduleIds);
        return result;
    }

    @Override
    public List<DailySchedule> findByScheduleDate(Long scheduleDate) {
        String userId = WebUtils.getUserId();
        List<DailySchedule> schedules =  dailyScheduleDao.findByScheduleDate(new Date(scheduleDate),userId);
        return schedules;
    }

}
