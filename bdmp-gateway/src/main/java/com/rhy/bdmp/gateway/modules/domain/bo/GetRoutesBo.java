package com.rhy.bdmp.gateway.modules.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GetRoutesBo {
    @NotNull(message = "当前页不为空")
    private Integer currentPage;

    @NotNull(message = "页大小不为空")
    private Integer limit;

    @NotBlank(message = "环境id不为空")
    private String envId;

    private String name;
}
