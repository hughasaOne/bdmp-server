package com.rhy.bdmp.base.modules.sys.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description oauth2认证配置 实体
 * @author shuaichao
 * @date 2022-03-23 17:22
 * @version V1.0
 **/
@ApiModel(value="oauth2认证配置", description="oauth2认证配置信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oauth_client_details")
public class AuthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private String id;

    @ApiModelProperty(value = "主键(客户端ID)")
    @TableField("client_id")
    private String clientId;

    @ApiModelProperty(value = "资源id集合(为空则能访问所有资源)")
    @TableField("resource_ids")
    private String resourceIds;

    @ApiModelProperty(value = "访问密匙",example = "123456")
    @TableField("client_secret")
    private String clientSecret;

    @ApiModelProperty(value = "权限范围",example = "all")
    @TableField("scope")
    private String scope;

    @ApiModelProperty(value = "授权类型",example = "password,refresh_token")
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    @ApiModelProperty(value = "重定向URI")
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    @ApiModelProperty(value = "指定权限值")
    @TableField("authorities")
    private String authorities;

    @ApiModelProperty(value = "访问token有效时间值(单位:秒)", example = "43200")
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    @ApiModelProperty(value = "刷新token有效时间值(单位:秒)", example = "43200")
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    @ApiModelProperty(value = "预留字段(JSON格式)")
    @TableField("additional_information")
    private String additionalInformation;

    @ApiModelProperty(value = "自动审核")
    @TableField("autoapprove")
    private String autoapprove;

    @ApiModelProperty(value = "应用ID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "创建人")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    @TableField("datastatusid")
    private Integer datastatusid;
}
