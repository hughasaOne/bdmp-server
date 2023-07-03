package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 设施简要信息
 * @Date: 2021/11/4 13:48
 * @Version: 1.0.0
 */
@ApiModel(value = "设施简要信息", description = "设施简要信息")
@Data
public class FacShortVO {
    @ApiModelProperty(value = "设施id")
    private String facilitiesId;
    @ApiModelProperty(value = "设施名")
    private String facilitiesName;
    @ApiModelProperty(value = "设施类型")
    private String facilitiesType;
    @ApiModelProperty(value = "设施编号")
    private String facilitiesCode;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "路段id")
    private String waysectionId;
    @ApiModelProperty(value = "排序")
    private Long sort;
    @ApiModelProperty(value = "是否叶子节点")
    private Boolean isLeaf;
}
