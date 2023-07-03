package com.rhy.bdmp.quartz.modules.sysjob.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author shuaichao
 * @create 2022-03-11 16:58
 */
@Data
public class SysJobExt {
    @ApiModelProperty("定时任务ID")
    private String sysJobId;
    @ApiModelProperty("业务ID")
    private String sysBusinessId;//应用ID
    @ApiModelProperty(value = "所属应用id")
    private String appId;
    @ApiModelProperty(value = "数据状态",example = "1")
    private Integer datastatusid;
    @ApiModelProperty(value = "排序字段",example = "1")
    private Long sort;
    @ApiModelProperty("请求头key")
    private String headerKey;//请求头Key
    @ApiModelProperty("请求头value")
    private String headerValue;//请求头Value
    @ApiModelProperty(value = "调用地址",example = "http://localhost:60006/bdmp/quartz/sys-job/test")
    private String url;

    @ApiModelProperty(value = "任务名称")
    private String jobName;//任务名称
    @ApiModelProperty(value = "任务分组",example = "default_group")
    private String jobGroup;//任务分组
    @ApiModelProperty("任务描述")
    private String description;//任务描述
    @ApiModelProperty(value = "执行类全名",example = "com.rhy.bdmp.quartz.modules.core.job.HttpClientJob")
    private String jobClassName;//执行类
    @ApiModelProperty("执行方法")
    private String methodType;//执行方法
    @ApiModelProperty(value = "请求参数",example = "{}")
    private String requestData;
    @ApiModelProperty(value = "cron表达式",example = "*/5 * * * * ?")
    private String cronExpression;//执行时间
    @ApiModelProperty("执行器名称")
    private String triggerName;//执行器名称
    @ApiModelProperty("任务状态")
    private String triggerState;//任务状态
    @ApiModelProperty("业务名称")
    private String name;

}
