package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yanggj
 * @version V1.0
 * @description 视频资源 实体
 * @date 2021-10-20 15:31
 **/
@ApiModel(value = "附近设备资源", description = "附近设备资源")
@Data
public class DeviceResourceNearBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    private String deviceRecordId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备编号")
    private String deviceNo;

    @ApiModelProperty(value = "原ID")
    private String originId;

    @ApiModelProperty(value = "设备工作状态")
    private Integer workStatus;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "设备类型")
    private String code;

    @ApiModelProperty(value = "设备类型名称")
    private String codeName;

    @ApiModelProperty(value = "方向")
    private String direction;

    @ApiModelProperty(value = "桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "安装位置")
    private String location;

    @ApiModelProperty(value = "距离（公里）")
    private Double distanceKm;

    @ApiModelProperty(value = "设施id")
    private String facilitiesId;

}
