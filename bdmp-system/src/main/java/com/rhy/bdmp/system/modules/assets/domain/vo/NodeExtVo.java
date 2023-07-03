package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 节点额外属性
 * @Date: 2021/10/28 11:39
 * @Version: 1.0.0
 */
@ApiModel(value = "ext节点", description = "ext节点")
@Data
public class NodeExtVo {
    @ApiModelProperty(value = "简要名称")
    private String shortName;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "类型")
    private String type;
}
