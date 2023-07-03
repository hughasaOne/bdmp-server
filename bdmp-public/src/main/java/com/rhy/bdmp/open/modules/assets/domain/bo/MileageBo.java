package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yanggj
 * @Description: 桥梁基本信息
 * @Date: 2021/11/10 15:21
 * @Version: 1.0.0
 */
@ApiModel(value = "里程信息", description = "里程信息(公司、路段)")
@Data
public class MileageBo {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "简称")
    private String shortName;

    @ApiModelProperty(value = "关联地图ID")
    private String nodeId;

    @ApiModelProperty(value = "节点类型")
    private String nodeType;

    @ApiModelProperty(value = "里程数")
    private BigDecimal mileage;

}
