package com.rhy.bdmp.system.modules.sys.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@ApiModel(value="用户应用数据权限", description="用户应用数据权限表相关参数")
@Getter
@Setter
public class SysUserDataQueryVo implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "权限ID集合")
    private Set<String> permissionsIds;

    @ApiModelProperty(value = "应用id")
    private String appId;

}
