package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 收费站基本信息
 * @Date: 2021/11/10 15:28
 * @Version: 1.0.0
 */
@ApiModel(value = "收费站基本信息", description = "收费站基本信息")
@Data
public class ChargeStationInfo {

    @ApiModelProperty(value = "收费站数量")
    private Integer chargeStationNum;
    @ApiModelProperty(value = "收费站正常通行数量")
    private Integer chargeStationNormalPassNum;
    @ApiModelProperty(value = "收费站临时关闭数量")
    private Integer chargeStationTemporaryCloseNum;

}
