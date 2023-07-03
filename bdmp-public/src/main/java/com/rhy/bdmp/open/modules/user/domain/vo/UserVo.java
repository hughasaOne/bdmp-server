package com.rhy.bdmp.open.modules.user.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class UserVo{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "用户类型(0:普通用户,1:单兵)")
    private Integer userType;

    @ApiModelProperty(value = "用户名")
    private String nickName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织简称")
    private String orgShortName;

    @ApiModelProperty(value = "组织代码")
    private String orgCode;

    @ApiModelProperty(value = "组织类型")
    private String orgType;

    @ApiModelProperty(value = "组织类型名称")
    private String orgTypeName;

    @ApiModelProperty(value = "组织描述")
    private String description;

    @ApiModelProperty(value = "用户自定义配置")
    @TableField("user_config")
    @JsonRawValue
    private String userConfig;

    @ApiModelProperty(value = "用户岗位")
    private String post;

    @ApiModelProperty(value = "岗位级别")
    private String postLevel;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date expiredTime;

    @ApiModelProperty(value = "用户所属运营集团id")
    private String operationGroupId;

    @ApiModelProperty(value = "用户所属运营集团名称")
    private String operationGroupName;

    @ApiModelProperty(value = "用户所属运营集团简称")
    private String operationGroupShortName;

    @ApiModelProperty(value = "用户所属运营公司id")
    private String operationCompanyId;

    @ApiModelProperty(value = "用户所属运营公司名称")
    private String operationCompanyName;

    @ApiModelProperty(value = "用户所属运营公司简称")
    private String operationCompanyShortName;

}