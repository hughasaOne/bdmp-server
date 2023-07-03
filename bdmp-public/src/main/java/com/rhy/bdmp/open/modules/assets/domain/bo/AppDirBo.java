package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 应用目录业务类
 * @author weicaifu
 */
@Data
public class AppDirBo {
    @ApiModelProperty(value = "是否带用户权限")
    private Boolean isUseUserPermissions;

    @ApiModelProperty(value = "应用类型id")
    private String appTypeId;
}
