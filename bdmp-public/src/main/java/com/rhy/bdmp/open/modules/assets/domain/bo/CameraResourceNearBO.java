package com.rhy.bdmp.open.modules.assets.domain.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ApiModel(value = "附近视频资源", description = "附近视频资源")
@Data
public class CameraResourceNearBO implements Serializable {

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

    @ApiModelProperty(value = "路段名称")
    private String wayName;

    @ApiModelProperty(value = "路段ID")
    private String wayId;

    @ApiModelProperty(value = "位置描述")
    private String location;

    @ApiModelProperty(value = "位置类型")
    private Integer locationType;

    @ApiModelProperty(value = "数据来源(1.科技, 2.楚天)")
    private String dataSource;

    @ApiModelProperty(value = "距离（公里）")
    private Double distanceKm;

    @ApiModelProperty(value = "设备状态(1:在线, 0:离线)")
    private String onlineStatus;

    @ApiModelProperty(value = "tluui")
    @JsonIgnore
    private String uuid;

    @ApiModelProperty(value = "是否有云台控制(0:无，1:有)")
    private Integer hasPTZ;

    @ApiModelProperty(value = "设施id")
    private String facilitiesId;

    @ApiModelProperty(value = "中心桩号")
    private String centerOffNo;
}
