package com.rhy.bdmp.open.modules.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户vo
 **/
@Data
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "用户名")
    private String nickName;

    @ApiModelProperty(value = "所属应用ID")
    private String appId;

    @ApiModelProperty(value = "用户类型", example = "1")
    private Integer userType;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "性别", example = "1")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "用户图片")
    private String avatar;

    @ApiModelProperty(value = "默认资源访问地址")
    private String defaultUrl;

}
