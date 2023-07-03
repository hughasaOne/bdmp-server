package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created on 2021/11/12.
 * 设备概要信息
 * @author duke
 */
@ApiModel(value = "设备概要信息", description = "设备概要信息")
@Data
public class DeviceInfoBO {

    @ApiModelProperty(value = "设备类型")
    private String type;
    @ApiModelProperty(value = "设备类型名称")
    private String name;
    @ApiModelProperty(value = "正常设备数")
    private int normalDeviceNum;
    @ApiModelProperty(value = "异常设备数")
    private int abnormalDeviceNum;
}
