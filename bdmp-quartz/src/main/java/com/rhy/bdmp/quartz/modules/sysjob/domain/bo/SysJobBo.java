package com.rhy.bdmp.quartz.modules.sysjob.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SysJobBo {

    @ApiModelProperty(value = "主键")
    private String sysJobId;

    @ApiModelProperty(value = "目录id")
    private String dirId;

    @ApiModelProperty(value = "任务状态")
    private String triggerState;

    @ApiModelProperty(value = "任务名称")
    private String jobName;
}
