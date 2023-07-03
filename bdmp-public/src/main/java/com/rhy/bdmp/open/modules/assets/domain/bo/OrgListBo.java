package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 组织列表业务实体
 * @author weicaifu
 */
@Data
public class OrgListBo {
    @ApiModelProperty(value = "组织类型（000900等）")
    private Set<String> orgTypes;

    @ApiModelProperty(value = "当前页",example = "1")
    @NotNull(message = "当前页不为空")
    private Integer currentPage;

    @ApiModelProperty(value = "页大小",example = "20")
    @NotNull(message = "页大小不为空")
    private Integer pageSize;
}
