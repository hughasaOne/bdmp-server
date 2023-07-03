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
 * @description 用户 实体
 * @author jiangzhimin
 * @date 2021-04-15 19:45
 * @version V1.0
 **/
@ApiModel(value="用户", description="用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_sys_user")
public class UserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId("user_id")
    private String userId;

    @ApiModelProperty(value = "账号")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "用户名")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "用户类型", example = "1")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty(value = "所属应用ID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "过期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("expired_time")
    private Date expiredTime;

    @ApiModelProperty(value = "密码重置时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("pwd_reset_time")
    private Date pwdResetTime;

    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private String orgId;

    @ApiModelProperty(value = "组织类型")
    @TableField(exist = false)
    private String orgType;

    @ApiModelProperty(value = "组织名称")
    @TableField(exist = false)
    private String orgName;

    @ApiModelProperty(value = "性别", example = "1")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "籍贯")
    @TableField("native_place")
    private String nativePlace;

    @ApiModelProperty(value = "民族")
    @TableField("national")
    private String national;

    @ApiModelProperty(value = "政治面貌")
    @TableField("politics_status")
    private String politicsStatus;

    @ApiModelProperty(value = "岗位")
    @TableField("post")
    private String post;

    @ApiModelProperty(value = "岗位名称")
    @TableField(value = "post",exist = false)
    private String postName;

    @ApiModelProperty(value = "职称")
    @TableField("post_level")
    private String postLevel;

    @ApiModelProperty(value = "最高学历")
    @TableField("most_education")
    private String mostEducation;

    @ApiModelProperty(value = "毕业学校")
    @TableField("graduated_school")
    private String graduatedSchool;

    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "证件类型", example = "1")
    @TableField("certificate_type")
    private Integer certificateType;

    @ApiModelProperty(value = "证件号")
    @TableField("certificate_no")
    private String certificateNo;

    @ApiModelProperty(value = "用户图片")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "默认资源访问地址")
    @TableField("default_url")
    private String defaultUrl;

    @ApiModelProperty(value = "数据权限级别（1:机构，2:路段， 3:设施）", example = "1")
    @TableField("data_permissions_level")
    private Integer dataPermissionsLevel;

    @ApiModelProperty(value = "是否Admin(1:是)", example = "1")
    @TableField("is_admin")
    private Integer isAdmin;

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

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;
}
