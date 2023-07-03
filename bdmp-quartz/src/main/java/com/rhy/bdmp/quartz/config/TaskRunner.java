//package com.rhy.bdmp.quartz.config;
//
//import com.rhy.bdmp.quartz.modules.sysjob.domain.po.SysJobExt;
//import org.quartz.*;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * 启动 初始化定时任务
// * @author shuaichao
// * @create 2022-03-11 16:55
// */
//@Component
//public class TaskRunner implements ApplicationRunner {
//
//    @Resource
//    private Scheduler scheduler;
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//
//        /**
//         * 系统启动的时候会初始化一个任务
//         */
//        int count =0;
//        if(count==1){
//            SysJobExt quartz = new SysJobExt();
//            quartz.setJobName("test01");
//            quartz.setJobGroup("test");
//            quartz.setDescription("测试任务");
//            quartz.setJobClassName("com.rhy.bdmp.quartz.modules.sysjob.job.HttpClientJob");
//            quartz.setCronExpression("* * * * * ?");
//
//            Class cls = Class.forName(quartz.getJobClassName()) ;
//            cls.newInstance();
//            //构建job信息
//            JobDetail job = JobBuilder.newJob(cls).withIdentity(quartz.getJobName(),
//                    quartz.getJobGroup())
//                    .withDescription(quartz.getDescription()).build();
//            job.getJobDataMap().put("jobMethodName", "test1");
//            // 触发时间点
//            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(quartz.getCronExpression());
//            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger"+quartz.getJobName(), quartz.getJobGroup())
//                    .startNow().withSchedule(cronScheduleBuilder).build();
//            //交由Scheduler安排触发
//            scheduler.scheduleJob(job, trigger);
//        }
//
//
//
//
//    }
//}
