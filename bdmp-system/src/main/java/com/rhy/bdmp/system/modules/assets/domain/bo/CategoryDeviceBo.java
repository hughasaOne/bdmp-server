package com.rhy.bdmp.system.modules.assets.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CategoryDeviceBo {
    @NotBlank(message = "分类id不为空")
    private String categoryId;

    @NotNull(message = "当前页不为空")
    private Integer currentPage;

    @NotNull(message = "页大小不为空")
    private Integer pageSize;

    private String deviceName;

    private String deviceType;

    private String systemId;

    private String nodeId;

    private Integer nodeType;
}
