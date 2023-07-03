package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 隧道基本信息
 * @Date: 2021/11/10 15:21
 * @Version: 1.0.0
 */
@ApiModel(value = "隧道基本信息", description = "隧道基本信息")
@Data
public class TunnelInfo {

    @ApiModelProperty(value = "隧道数量")
    private Integer tunnelNum;
    @ApiModelProperty(value = "特长隧道数量")
    private Integer especialLongTunnelNum;
    @ApiModelProperty(value = "长隧道数量")
    private Integer longTunnelNum;
    @ApiModelProperty(value = "中隧道数量")
    private Integer middleTunnelNum;
    @ApiModelProperty(value = "小隧道数量")
    private Integer smallTunnelNum;
    @ApiModelProperty(value = "隧道里程")
    private Double tunnelMileage;

}
