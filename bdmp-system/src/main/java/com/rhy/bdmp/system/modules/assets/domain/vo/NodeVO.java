package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: NodeFacVO
 * @Description:
 * @Author: 魏财富
 * @Date: 2021/4/30 14:37
 */
@ApiModel(value="树节点", description="树节点")
@Data
public class NodeVO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "节点ID")
    private String id;

    @ApiModelProperty(value = "节点名称")
    private String label;

    @ApiModelProperty(value = "节点值")
    private String value;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "节点类型")
    private String noteType;

    @ApiModelProperty(value = "更多信息")
    private Object moreInfo;

    @ApiModelProperty(value = "排序")
    private Long sort;

    @ApiModelProperty(value = "是否叶子节点")
    private Boolean isLeaf;

    @ApiModelProperty(value = "是否选中")
    private Boolean checked;

    @ApiModelProperty(value = "所属运营公司id")
    private String operationCompanyId;
}
