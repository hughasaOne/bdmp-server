package com.rhy.bdmp.open.modules.assets.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 车道设备vo
 * @author weicaifu
 */
@Data
public class LaneDeviceVo {
    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "所属厂家")
    private String manufacturerId;

    @ApiModelProperty(value = "所属厂家名称")
    private String manufacturer;

    @ApiModelProperty(value = "所属车道")
    private String laneId;

    @ApiModelProperty(value = "所属车道名称")
    private String laneName;

    @ApiModelProperty(value = "网关id")
    private String gatewayId;

    @ApiModelProperty(value = "网关名称")
    private String gatewayName;

    @ApiModelProperty(value = "当前状态,0离线,1在线",example = "1")
    private Integer currentState;
}
