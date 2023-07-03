package com.rhy.bdmp.portal.modules.daylischedule.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.base.modules.sys.domain.po.DailySchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @description  数据操作接口
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Mapper
public interface DailyScheduleDao extends BaseMapper<DailySchedule> {

    List<DailySchedule> findByScheduleDate(@Param("scheduleDate") Date scheduleDate,@Param("userId") String userId);
}
