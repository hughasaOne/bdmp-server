package com.rhy.bdmp.open.modules.common.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TreeNode {
    @ApiModelProperty("节点id")
    private String id;

    @ApiModelProperty("节点名称")
    private String name;

    @ApiModelProperty("节点简称")
    private String shortName;

    @ApiModelProperty("父节点id")
    private String parentId;

    @ApiModelProperty("节点类型")
    private String nodeType;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("节点code")
    private String type;

    private List<TreeNode> children;
}
