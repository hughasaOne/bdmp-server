package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 隧道统计信息
 * @Date: 2021/9/29 15:34
 * @Version: 1.0.0
 */
@ApiModel(value = "隧道统计信息", description = "隧道统计信息")
@Data
public class TunnelStatInfo {
    @ApiModelProperty(value = "隧道类型中文名称", example = "长隧道")
    private String name;
    @ApiModelProperty(value = "当前隧道类型总里程")
    private Double length;
    @ApiModelProperty(value = "隧道总量")
    private Integer total;
    @ApiModelProperty(value = "占比", example = "9.82表示9.82%")
    private Double percentage;

}
