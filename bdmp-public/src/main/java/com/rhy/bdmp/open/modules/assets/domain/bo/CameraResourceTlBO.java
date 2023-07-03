package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yanggj
 * @version V1.0
 * @description 视频资源(腾路) 实体
 * @date 2021-10-20 15:31
 **/
@ApiModel(value = "视频资源(腾路)", description = "视频资源(腾路)信息")
@Data
public class CameraResourceTlBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频资源ID")
    private String cameraId;

    @ApiModelProperty(value = "视频资源名称")
    private String cameraName;

    @ApiModelProperty(value = "视频资源IP")
    private String cameraIp;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "路段编号")
    private String oriWaysectionNo;

    @ApiModelProperty(value = "路段名称")
    private String wayName;

    @ApiModelProperty(value = "公司名称")
    private String compName;

    @ApiModelProperty(value = "位置描述")
    private String infoName;

    @ApiModelProperty(value = "数据来源(1.科技, 2.楚天)")
    private String dataSource;

    @ApiModelProperty(value = "设备状态(1:在线, 0:离线)")
    private String onlineStatus;

    @ApiModelProperty(value = "位置类型(10:收费站、11:收费站广场、 20:隧道、  30:外场、40:服务区)",example = "1")
    private Integer locationType;

    @ApiModelProperty(value = "公司简称")
    private String shortName;
}
