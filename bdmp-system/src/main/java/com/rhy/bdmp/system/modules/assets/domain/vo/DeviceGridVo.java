package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/23
 */
@ApiModel(value="设备", description="设备信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceGridVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备代码(设备编号)")
    private String deviceCode;

    @ApiModelProperty(value = "网络IP")
    private String ip;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    private String direction;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;

    @ApiModelProperty(value = "所属设施")
    private String facilitiesName;

    @ApiModelProperty(value = "所属设施id")
    private String facilitiesId;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "所属系统")
    private String systemName;

    @ApiModelProperty(value = "桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "位置")
    private String location;

    @ApiModelProperty(value = "设备序列号")
    private String seriaNumber;
}
