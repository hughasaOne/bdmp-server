package com.rhy.bdmp.quartz.modules.sysjob.job;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.rhy.bdmp.base.modules.sys.dao.BaseSysLogDao;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.rhy.bdmp.base.modules.sys.domain.po.SysLog;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.JobResponse;
import com.rhy.bdmp.quartz.modules.sysjob.service.ISysJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.zip.Inflater;

/**
 * 实现序列化接口、防止重启应用出现quartz Couldn't retrieve job because a required class was not found 的问题
 * Job 的实例要到该执行它们的时候才会实例化出来。每次 Job 被执行，一个新的 Job 实例会被创建。
 * 其中暗含的意思就是你的 Job 不必担心线程安全性，因为同一时刻仅有一个线程去执行给定 Job 类的实例，甚至是并发执行同一 Job 也是如此。
 * @DisallowConcurrentExecution 保证上一个任务执行完后，再去执行下一个任务，这里的任务是同一个任务
 */
@DisallowConcurrentExecution
@Component
@Slf4j
public class HttpClientJob implements  Job,Serializable {

    private static final long serialVersionUID = 1L;

    @Resource
    ISysJobService sysJobService ;
    @Resource
    BaseSysLogDao baseSysLogDao;


    @Override
    public void execute(JobExecutionContext context){
        /**
         * 获取任务中保存的方法名字，动态调用方法
         */
        log.info("开始执行定时任务");
        //获取任务中保存的任务名称
        String jobName = context.getTrigger().getJobKey().getName();
        log.info("当前执行任务名称==>"+jobName);
        //获取定时任务 主要是获取要调用的http地址
        SysJob sysJob = sysJobService.selectByJobName(jobName);



        String methodName = sysJob.getMethodType();

        SysLog sysLog = new SysLog();
        Date startDate = new Date();

        HttpClientJob job = new HttpClientJob();
        try {
            Method method = job.getClass().getMethod(methodName,sysJob.getClass());

            JobResponse result = (JobResponse) method.invoke(job,sysJob);
            sysLog.setExecuteResult(result.getResult());
            sysLog.setExecuteStatus(result.getStatus());
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            sysLog.setExecuteResult(errors.toString());
            sysLog.setExecuteStatus(JobResponse.STATUS_FAIL);
        }

        Date endDate = new Date();
        Long executeTime = endDate.getTime()-startDate.getTime();
        sysLog.setCreateTime(endDate);
        sysLog.setStartTime(startDate);
        sysLog.setEndTime(endDate);
        sysLog.setExecuteTime(executeTime);
        sysLog.setSysJobId(sysJob.getSysJobId());
        baseSysLogDao.insert(sysLog);
        log.info("任务执行完毕==>"+jobName);
    }
    //get请求方法
    public JobResponse get(SysJob sysJob) {
        JobResponse response = new JobResponse();
        try {
            String result = HttpRequest.get(sysJob.getUrl())
                    .header(sysJob.getHeaderKey(), sysJob.getHeaderValue())
                    .timeout(30000).execute().body();
            response.setResult(result);
            JSONObject json = new JSONObject(result);
            String code = json.getStr("code");
            if("200".equals(code)){
                response.setStatus(JobResponse.STATUS_SUCCESS);
            }else{
                response.setStatus(JobResponse.STATUS_FAIL);
            }

        }catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            response.setStatus(JobResponse.STATUS_TIME_OUT);
            response.setResult(errors.toString());
        }

        return response;


    }

    //get请求方法
    public JobResponse post(SysJob sysJob) {
        JobResponse response = new JobResponse();
        try {
            String result = HttpRequest.post(sysJob.getUrl())
                    .header(sysJob.getHeaderKey(), sysJob.getHeaderValue())
                    .body(sysJob.getRequestData())
                    .timeout(30000).execute().body();
            response.setResult(result);
            if(result.indexOf("\"200\",")==-1){
                response.setStatus(JobResponse.STATUS_FAIL);
            }else{
                response.setStatus(JobResponse.STATUS_SUCCESS);
            }

        }catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            response.setStatus(JobResponse.STATUS_TIME_OUT);
            response.setResult(errors.toString());
        }

        return response;
    }



}