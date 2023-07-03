package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 路段简要信息
 * @Date: 2021/11/4 13:47
 * @Version: 1.0.0
 */
@ApiModel(value = "路段简要信息", description = "路段简要信息")
@Data
public class WayShortVO {

    @ApiModelProperty(value = "路段id")
    private String waysectionId;
    @ApiModelProperty(value = "路段名")
    private String waysectionName;
    @ApiModelProperty(value = "路段简称")
    private String waysectionSName;
    @ApiModelProperty(value = "管理机构id")
    private String manageId;
    @ApiModelProperty(value = "排序")
    private Long sort;
    @ApiModelProperty(value = "是否叶子节点")
    private Boolean isLeaf;
}
