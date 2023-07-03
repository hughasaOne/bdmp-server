package com.rhy.bdmp.portal.modules.daylischedule.service;

import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.DailySchedule;

import java.util.List;
import java.util.Set;

/**
 * @description  服务接口
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
public interface DailyScheduleService {

    /**
     * 列表查询
     * @param dailySchedule 查询条件
     * @return
     */
    List<DailySchedule> list( DailySchedule dailySchedule);



    /**
     * 查看(根据ID)
     * @param dailyScheduleId
     * @return
     */
    DailySchedule detail(String dailyScheduleId);

    /**
     * 新增
     * @param dailySchedule
     * @return
     */
    int create(DailySchedule dailySchedule);

    /**
     * 修改
     * @param dailySchedule
     * @return
     */
    int update(DailySchedule dailySchedule);

    /**
     * 删除
     * @param dailyScheduleIds
     * @return
     */
    int delete(Set<String> dailyScheduleIds);


    List<DailySchedule> findByScheduleDate(Long scheduleDate);
}
