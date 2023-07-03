package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 收费站下设备
 * @Date: 2021/9/27 9:18
 * @Version: 1.0.0
 */
@ApiModel(value = "收费站下设备", description = "收费站下设备")
@Data
public class TollStationDeviceBO {

    @ApiModelProperty(value = "系统id")
    private String systemId;
    @ApiModelProperty(value = "系统名字")
    private String systemName;
    @ApiModelProperty(value = "设备id")
    private String id;
    @ApiModelProperty(value = "设备名字")
    private String name;
    @ApiModelProperty(value = "设备源id")
    private String originId;
    @ApiModelProperty(value = "设备ip")
    private String ip;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "状态")
    private String status;

}
