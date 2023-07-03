package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@ApiModel(value = "设施分类统计及明细列表业务类")
public class FacStatisticsBo {
    @ApiModelProperty("是否使用用户权限")
    private Boolean isUseUserPermissions;

    @ApiModelProperty("节点id")
    private String orgId;

    @ApiModelProperty("节点类型（0：集团，1：公司，2：路段，3：设施）")
    private Integer nodeType;

    @ApiModelProperty("设施类型")
    @NotEmpty(message = "设施类型不为空")
    private Set<String> facTypes;

    @ApiModelProperty("搜索")
    private String search;

    @ApiModelProperty("桥梁等级(1:特大桥,2:大桥,3:中桥,4:小桥)")
    private Set<String> bridgeLevels;
}
