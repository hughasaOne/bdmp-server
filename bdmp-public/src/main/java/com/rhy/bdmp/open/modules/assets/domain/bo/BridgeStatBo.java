package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 桥梁统计
 * @Date: 2021/9/29 13:58
 * @Version: 1.0.0
 */
@ApiModel(value = "桥梁统计", description = "桥梁统计")
@Data
public class BridgeStatBo {

    @ApiModelProperty(value = "总里程数")
    private String totalLength;
    @ApiModelProperty(value = "数量")
    private String num;
    @ApiModelProperty(value = "名称", example = "特大桥")
    private String name;

}
