package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 用户权限
 * @Date: 2021/10/28 10:54
 * @Version: 1.0.0
 */
@ApiModel(value = "用户权限", description = "用户权限")
@Data
public class UserPermissions {
    @ApiModelProperty(value = "是否使用权限")
    private Boolean isUseUserPermissions;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "用户数据权限等级")
    private Integer dataPermissionsLevel;
}
