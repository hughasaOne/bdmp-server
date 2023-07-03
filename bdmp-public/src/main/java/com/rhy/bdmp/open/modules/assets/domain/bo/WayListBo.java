package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 路段业务实体
 * @author weicaifu
 */
@Data
public class WayListBo {
    @ApiModelProperty(value = "是否使用用户权限过滤")
    private Boolean isUseUserPermissions;

    @ApiModelProperty(value = "节点id")
    private String orgId;

    @ApiModelProperty(value = "节点类型")
    private String nodeType;

    @ApiModelProperty(value = "当前页",example = "1")
    @NotNull(message = "当前页不为空")
    private Integer currentPage;

    @ApiModelProperty(value = "页大小",example = "20")
    @NotNull(message = "页大小不为空")
    private Integer pageSize;
}
