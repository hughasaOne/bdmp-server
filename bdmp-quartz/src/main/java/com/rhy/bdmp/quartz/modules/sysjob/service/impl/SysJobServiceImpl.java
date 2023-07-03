package com.rhy.bdmp.quartz.modules.sysjob.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.rhy.bdmp.quartz.modules.common.dao.CommonDao;
import com.rhy.bdmp.quartz.modules.common.to.UserAppPermissions;
import com.rhy.bdmp.quartz.modules.sysjob.dao.SysJobDao;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobBo;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobExt;
import com.rhy.bdmp.quartz.modules.sysjob.service.ISysJobService;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author shuaichao
 * @create 2022-03-14 9:06
 */
@Service
public class SysJobServiceImpl implements ISysJobService {

    @Resource
    SysJobDao sysJobDao ;
    @Resource
    Scheduler scheduler;
    @Resource
    private CommonDao commonDao;

    @Override
    public SysJob selectByJobName(String jobName) {
        QueryWrapper<SysJob> wrapper = new QueryWrapper<>();
        wrapper.eq("job_name",jobName);
        SysJob sysJob = sysJobDao.selectOne(wrapper);
        return sysJob;
    }

    @Override
    public List<SysJobExt> list(SysJob sysJob) {
        List<SysJobExt> sysJobExts = sysJobDao.queryList(sysJob);
        return sysJobExts;

    }

    @Override
    public Page<SysJobExt> page(SysJobBo sysJobBo, Integer currentPage, Integer size)  {
        if (null == currentPage || 0 >= currentPage){
            currentPage = 1;
        }
        if (null == size || 0 >= size){
            size = 20;
        }
        // 获取用户应用权限
        String userId = WebUtils.getUserId();
        UserAppPermissions userAppPermissions = new UserAppPermissions();
        userAppPermissions = commonDao.getCurentUser(userId);
        userAppPermissions.setPermissionsIds(commonDao.getUserPermissions(userId));
        Page<SysJobExt> page = new Page<>(currentPage, size);

        return sysJobDao.selectSysJobPage(page, sysJobBo,userAppPermissions);
    }



    @Override
    public SysJobExt detail(String sysJobId)  {
        SysJobExt sysJobExt = sysJobDao.selectSysJobExtById(sysJobId);
        return sysJobExt;

    }

    @Override
    @Transactional
    public void create(SysJobExt sysJobExt){
        //判断任务名称是否存在
        QueryWrapper<SysJob> wrapper = new QueryWrapper<SysJob>();
        wrapper.eq("job_name",sysJobExt.getJobName());
        List<SysJob> sysJobs = sysJobDao.selectList(wrapper);
        if(CollectionUtil.isNotEmpty(sysJobs)){
             throw new BadRequestException("任务名称已存在");
        }
        //构建定时任务对象，使任务生效并持久化
        Class cls = null;
        try {
            cls = Class.forName(sysJobExt.getJobClassName());
            cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        //构建job信息
        JobDetail job = JobBuilder.newJob(cls).withIdentity(sysJobExt.getJobName(),
                sysJobExt.getJobGroup())
                .withDescription(sysJobExt.getDescription()).build();
        // 触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJobExt.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+sysJobExt.getJobName(), sysJobExt.getJobGroup())
                .startNow().withSchedule(cronScheduleBuilder).build();
        //交由Scheduler安排触发

        try {
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        //增加进自己的表
        SysJob sysJob = new SysJob();
        BeanUtil.copyProperties(sysJobExt,sysJob);
        sysJob.setCreateTime(new Date());
        sysJob.setCreateBy(WebUtils.getUserId());
        sysJobDao.insert(sysJob);
    }

    @Override
    public void update(SysJobExt sysJobExt) throws Exception{
        //删除正在执行的任务
        JobKey key = new JobKey(sysJobExt.getJobName(),sysJobExt.getJobGroup());
        scheduler.deleteJob(key);
        QueryWrapper<SysJob> wrapper = new QueryWrapper<SysJob>();
        wrapper.eq("job_name",sysJobExt.getJobName());
        SysJob sysJob = sysJobDao.selectOne(wrapper);
        //修改自己新增的表
        sysJob.setUrl(sysJobExt.getUrl());
        sysJob.setHeaderKey(sysJobExt.getHeaderKey());
        sysJob.setHeaderValue(sysJobExt.getHeaderValue());
        sysJob.setSysBusinessId(sysJobExt.getSysBusinessId());
        sysJob.setMethodType(sysJobExt.getMethodType());
        sysJob.setRequestData(sysJobExt.getRequestData());
        sysJob.setUpdateTime(new Date());
        sysJob.setUpdateBy(WebUtils.getUserId());
        sysJob.setDatastatusid(sysJobExt.getDatastatusid());
        sysJob.setSort(sysJobExt.getSort());
        sysJob.setAppId(sysJobExt.getAppId());
        sysJobDao.updateById(sysJob);
        //构建定时任务对象，使任务生效并持久化
        Class cls = Class.forName(sysJobExt.getJobClassName()) ;
        cls.newInstance();
        //构建job信息
        JobDetail job = JobBuilder.newJob(cls).withIdentity(sysJobExt.getJobName(),
                sysJobExt.getJobGroup())
                .withDescription(sysJobExt.getDescription()).build();
        // 触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJobExt.getCronExpression());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+sysJobExt.getJobName(), sysJobExt.getJobGroup())
                .startNow().withSchedule(cronScheduleBuilder).build();
        //交由Scheduler安排触发
        scheduler.scheduleJob(job, trigger);
    }

    @Override
    public void remove(List<String> jobIds) throws  Exception{
        List<SysJobExt> sysJobExts = sysJobDao.selectSysJobExtByIds(jobIds);
        for(SysJobExt sysJobExt :sysJobExts){
            TriggerKey triggerKey = TriggerKey.triggerKey(sysJobExt.getJobName(), sysJobExt.getJobGroup());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(sysJobExt.getJobName(), sysJobExt.getJobGroup()));
            //删除自建表的数据

            sysJobDao.deleteById(sysJobExt.getSysJobId());

        }
    }

}
