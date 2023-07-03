package com.rhy.bdmp.base.modules.sys.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @description  实体
 * @author shuaichao
 * @date 2022-03-17 11:38
 * @version V1.0
 **/
@ApiModel(value="", description="信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_quartz_sys_job")
public class SysJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("sys_job_id")
    private String sysJobId;

    @ApiModelProperty(value = "目录id")
    @TableField("sys_business_id")
    private String sysBusinessId;

    @ApiModelProperty(value = "所属应用id")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "任务名称")
    @TableField("job_name")
    private String jobName;

    @ApiModelProperty(value = "请求头KEY")
    @TableField("header_key")
    private String headerKey;

    @ApiModelProperty(value = "请求头Value")
    @TableField("header_value")
    private String headerValue;

    @TableField("create_by")
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @TableField("update_by")
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "要调用http请求的地址")
    @TableField("url")
    private String url;

    @TableField("datastatusid")
    private Integer datastatusid;

    @TableField("sort")
    private Long sort;

    @ApiModelProperty(value = "方法类型 只支持get post")
    @TableField("method_type")
    private String methodType;

    @ApiModelProperty(value = "post请求体数据，get请求无效")
    @TableField("request_data")
    private String requestData;
}
