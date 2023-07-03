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
 * @description 应用信息 实体
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@ApiModel(value="应用信息", description="应用信息信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_sys_app")
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用ID")
    @TableId("app_id")
    private String appId;

    @ApiModelProperty(value = "应用名称")
    @TableField("app_name")
    private String appName;

    @ApiModelProperty(value = "应用入口")
    @TableField("app_url")
    private String appUrl;

    @ApiModelProperty(value = "应用入口类型(1:前端，2:后台)")
    @TableField("app_url_type")
    private Integer appUrlType;

    @ApiModelProperty(value = "应用图标")
    @TableField("app_icon")
    private String appIcon;

    @ApiModelProperty(value = "应用图片")
    @TableField("app_img")
    private String appImg;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "应用分类ID")
    @TableField("app_type_id")
    private String appTypeId;

    @ApiModelProperty(value = "应用待办获取URL")
    @TableField("wait_do_url")
    private String waitDoUrl;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    @TableField("datastatusid")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "是否接入统一认证中心（0：不接入，1：接入）", example = "1")
    @TableField("is_join_auth_center")
    private Integer isJoinAuthCenter;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;
}
