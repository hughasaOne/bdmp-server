package com.rhy.bdmp.quartz.modules.sysjob.controller;

import com.rhy.bcp.common.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobBo;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobExt;
import com.rhy.bdmp.quartz.modules.sysjob.service.ISysJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * @description  前端控制器
 * @author shuaichao
 * @date 2022-03-11 17:47
 * @version V1.0
 **/
@Api(tags = {"任务管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/quartz/sys-job")
public class SysJobController {

    @Resource
    private ISysJobService sysJobService;
    @Resource
    Scheduler scheduler;

    @ApiOperation(value = "查询列表", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<SysJobExt>> list(@RequestBody(required = false)  SysJob sysJob) {
        List<SysJobExt> result = sysJobService.list(sysJob);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page/{currentPage}/{size}")
    public RespResult<PageUtil<SysJobExt>> page(@RequestBody SysJobBo sysJobBo, @PathVariable Integer currentPage, @PathVariable Integer size) {

        Page<SysJobExt> pageUtil =  sysJobService.page(sysJobBo,currentPage,size);
        return RespResult.ok(new PageUtil(pageUtil) );

    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{sysJobId}")
    public RespResult<SysJobExt> detail(@PathVariable("sysJobId") String sysJobId) {

        try {
            SysJobExt sysJob = sysJobService.detail(sysJobId);
            return RespResult.ok(sysJob);
        }catch (Exception e){
            return RespResult.error();
        }

    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody SysJobExt sysJobExt){
        sysJobService.create(sysJobExt);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update( @RequestBody SysJobExt quartz){
        try {
            sysJobService.update(quartz);
        }catch (Exception e){
            return RespResult.error();
        }
        return RespResult.ok();
    }

    @ApiOperation("手动触发一次任务")
    @PostMapping("/trigger")
    public  RespResult trigger(@RequestBody SysJobExt quartz) {

        try {
            JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return RespResult.error();
        }
        return RespResult.ok();
    }

    @ApiOperation("暂停任务")
    @PostMapping("/pause")
    public  RespResult pause(@RequestBody SysJobExt quartz) {
        try {
            JobKey key = new JobKey(quartz.getJobName(),quartz.getJobGroup());
            scheduler.pauseJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return RespResult.error();
        }
        return RespResult.ok();
    }

    @ApiOperation("恢复任务")
    @PostMapping("/resume")
    public  RespResult resume(@RequestBody SysJobExt sysJobExt) {
        try {
            JobKey key = new JobKey(sysJobExt.getJobName(),sysJobExt.getJobGroup());
            scheduler.resumeJob(key);
        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.error();
        }
        return RespResult.ok();
    }


    @ApiOperation("移除任务")
    @PostMapping("/remove")
    public  RespResult remove(@RequestBody List<String> jobIds) {
        try {
            sysJobService.remove(jobIds);

        } catch (Exception e) {
            e.printStackTrace();
            return RespResult.error();
        }
        return RespResult.ok();
    }


    @ApiOperation("正常测试")
    @GetMapping("/testSuccess")
    public RespResult testSuccess() throws InterruptedException {


        return  RespResult.ok();
    }

    @ApiOperation("测试超时")
    @GetMapping("/testTimeOut")
    public RespResult testTimeOut() throws InterruptedException {

        Thread.sleep(100000000);

        return  RespResult.ok();
    }

    @ApiOperation("测试异常")
    @GetMapping("/testException")
    public RespResult testException()  {
       int i = 1/0;
        return  RespResult.ok();
    }

    @ApiOperation("测试post")
    @PostMapping("/testPost")
    public RespResult testPost(@RequestBody SysJob sysJob) throws InterruptedException {
        return  RespResult.ok(sysJob);
    }
}
