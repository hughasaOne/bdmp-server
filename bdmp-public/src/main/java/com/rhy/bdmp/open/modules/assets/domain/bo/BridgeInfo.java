package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 桥梁基本信息
 * @Date: 2021/11/10 15:21
 * @Version: 1.0.0
 */
@ApiModel(value = "桥梁基本信息", description = "桥梁基本信息")
@Data
public class BridgeInfo {

    @ApiModelProperty(value = "桥梁数量")
    private Integer bridgeNum;
    @ApiModelProperty(value = "特长桥数量")
    private Integer especialLongBridgeNum;
    @ApiModelProperty(value = "长桥数量")
    private Integer longBridgeNum;
    @ApiModelProperty(value = "中桥数量")
    private Integer middleBridgeNum;
    @ApiModelProperty(value = "小桥数量")
    private Integer smallBridgeNum;
    @ApiModelProperty(value = "桥梁里程")
    private Double bridgeMileage;


}
