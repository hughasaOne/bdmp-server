package com.rhy.bdmp.open.modules.device.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo extends DeviceVo{

    @ApiModelProperty(value = "视频资源ID")
    private String id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "1-在线，2-离线，3-故障", example = "1")
    private String onlineStatus;

    @ApiModelProperty(value = "是否有云台控制(0:无，1:有)")
    private String hasPtz;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "相机类型（0：默认1：道路沿线 2：桥梁 3：隧道 4：收费广场 5：收费站 6：服务区 7：ETC门架 8：移动视频源", example = "1")
    private String locationType;

    @ApiModelProperty(value = "中心桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "方向（0：上行1：下行2：上下行（双向））")
    private String direction;
}
