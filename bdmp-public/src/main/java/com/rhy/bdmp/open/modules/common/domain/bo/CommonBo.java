package com.rhy.bdmp.open.modules.common.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class CommonBo {
    @ApiModelProperty("code集合")
    private Set<String> codes;

    @ApiModelProperty("是否使用权限")
    private Boolean isUseUserPermissions;

    @ApiModelProperty("节点id,多个节点英文逗号分割")
    private String nodeId;

    @ApiModelProperty("节点类型")
    private String nodeType;

    @ApiModelProperty("模糊搜索字段")
    private String search;
}
