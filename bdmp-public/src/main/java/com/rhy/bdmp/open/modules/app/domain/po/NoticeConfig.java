package com.rhy.bdmp.open.modules.app.domain.po;

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
 * @description 移动通知配置 实体
 * @author weicaifu
 * @date 2022-02-28 11:24
 * @version V1.0
 **/
@ApiModel(value="移动通知配置", description="移动通知配置信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_app_notice_config")
public class NoticeConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "用户ID(或账号名)")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty(value = "设备ID")
    @TableField("device_id")
    private String deviceId;

    @ApiModelProperty(value = "设备token")
    @TableField("device_token")
    private String deviceToken;

    @ApiModelProperty(value = "设备类型")
    @TableField("device_type")
    private String deviceType;

    @ApiModelProperty(value = "固件版本")
    @TableField("app_version")
    private String appVersion;

    @ApiModelProperty(value = "应用ID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "操作系统")
    @TableField("os")
    private String os;

    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("last_live_time")
    private Date lastLiveTime;

    @ApiModelProperty(value = "业务标识")
    @TableField("app_ident")
    private String appIdent;

    @ApiModelProperty(value = "业务标识名")
    @TableField("app_ident_name")
    private String appIdentName;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Long sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    @TableField("datastatusid")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;
}
