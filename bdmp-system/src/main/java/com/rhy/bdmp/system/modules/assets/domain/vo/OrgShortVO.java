package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 机构简短信息
 * @Date: 2021/11/4 13:47
 * @Version: 1.0.0
 */
@ApiModel(value="机构简短信息", description="机构简短信息")
@Data
public class OrgShortVO {

    @ApiModelProperty(value = "机构id")
    private String orgId;
    @ApiModelProperty(value = "机构名称")
    private String orgName;
    @ApiModelProperty(value = "机构简称")
    private String orgShortName;
    @ApiModelProperty(value = "机构类型")
    private String orgType;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "排序")
    private Long sort;
    @ApiModelProperty(value = "是否叶子节点")
    private Boolean isLeaf;

}
