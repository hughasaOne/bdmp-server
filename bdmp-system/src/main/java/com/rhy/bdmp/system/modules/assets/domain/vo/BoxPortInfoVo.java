package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="端口外设信息", description="端口外设信息")
@Data
public class BoxPortInfoVo {

    @ApiModelProperty(value = "端口号")
    private String portNum;

    @ApiModelProperty(value = "设备Id")
    private String deviceId;

    @ApiModelProperty(value = "外设表Id")
    private String externalId;

    @ApiModelProperty(value = "设备Ip")
    private String deviceIp;

    @ApiModelProperty(value = "boxIp")
    private String boxIp;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备编号")
    private String deviceCode;

    @ApiModelProperty(value = "资产编号")
    private String assetsNo;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "所属系统")
    private String deviceSys;

    @ApiModelProperty(value = "所属系统Id")
    private String deviceSysId;

    @ApiModelProperty(value = "所属设施")
    private String deviceFac;

    @ApiModelProperty(value = "所属设施")
    private String deviceFacId;

    @ApiModelProperty(value = "安装位置")
    private String location;
}
