package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: yanggj
 * @Description: 桥梁分类统计
 * @Date: 2021/9/29 11:56
 * @Version: 1.0.0
 */
@ApiModel(value = "桥梁分类统计信息", description = "桥梁分类统计信息")
@Data
public class BridgeClassifyStatBo {
    @ApiModelProperty(value = "总数")
    private String total;
    @ApiModelProperty(value = "总里程")
    private String totalLength;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "统计信息")
    private List<BridgeStatBo> statistics;

}
