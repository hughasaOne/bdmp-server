package com.rhy.bdmp.open.modules.pts.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户权限vo
 * @author weicaifu
 */
@Data
public class UserPermissionVo {
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "机构id")
    private String orgId;

    @ApiModelProperty(value = "机构名称")
    private String orgName;

    @ApiModelProperty(value = "机构类型")
    private String orgType;

    @ApiModelProperty(value = "机构类型名称")
    private String orgTypeName;

    @ApiModelProperty(value = "应用名")
    private String appName;
}
