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
 * @description 日志 实体
 * @author weicaifu
 * @date 2022-10-17 17:21
 * @version V1.0
 **/
@ApiModel(value="日志", description="日志信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_rhy_log")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志ID")
    @TableId("log_id")
    private String logId;

    @ApiModelProperty(value = "操作用户")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "方法名")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "参数")
    @TableField("params")
    private String params;

    @ApiModelProperty(value = "日志类型")
    @TableField("log_type")
    private String logType;

    @ApiModelProperty(value = "请求IP")
    @TableField("request_ip")
    private String requestIp;

    @ApiModelProperty(value = "地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "浏览器")
    @TableField("browser")
    private String browser;

    @ApiModelProperty(value = "请求耗时", example = "1")
    @TableField("use_time")
    private Long useTime;

    @ApiModelProperty(value = "异常详细")
    @TableField("exception_detail")
    private String exceptionDetail;

    @ApiModelProperty(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;
}
