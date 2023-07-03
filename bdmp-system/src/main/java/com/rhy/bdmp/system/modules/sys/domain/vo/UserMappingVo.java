package com.rhy.bdmp.system.modules.sys.domain.vo;

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
 * @description 用户映射 实体
 * @author jiangzhimin
 * @date 2021-04-15 19:45
 * @version V1.0
 **/
@ApiModel(value="用户映射", description="用户映射信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_sys_user_mapping")
public class UserMappingVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId("user_mapping_id")
    private String userMappingId;

    @ApiModelProperty(value = "用户ID")
    @TableField("user_id")
    private String userId;

    //平台用户名
    @TableField(exist = false)
    private String nickName;

    //应用用户名
    @TableField(exist = false)
    private String appUserName;

    //应用用户账号
    @TableField(exist = false)
    private String appUserAccount;

    //平台用户账号
    @TableField(exist = false)
    private String userAccount;

    //应用名
    @TableField(exist = false)
    private String appName;

    @ApiModelProperty(value = "应用用户ID")
    @TableField("app_user_id")
    private String appUserId;

    @ApiModelProperty(value = "应用ID")
    @TableField("app_id")
    private String appId;

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

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;
}
