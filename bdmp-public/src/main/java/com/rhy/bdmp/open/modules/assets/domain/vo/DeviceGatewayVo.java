package com.rhy.bdmp.open.modules.assets.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 设备实体vo（网关专用）
 * @author weicaifu
 */
@Data
public class DeviceGatewayVo {
    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "设备类型（设备字典code）")
    private String deviceType;

    @ApiModelProperty(value = "设备小类id")
    private String deviceDictId;

    @ApiModelProperty(value = "设备小类名称")
    private String deviceDictName;

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

    @ApiModelProperty(value = "所属路段")
    private String waysectionName;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "所属系统")
    private String systemName;

    @ApiModelProperty(value = "安装位置")
    private String location;

    @ApiModelProperty(value = "中心桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "厂商")
    private String manufacturer;

    @ApiModelProperty(value = "厂商id")
    private String manufacturerId;

    @ApiModelProperty(value = "设施id")
    private String facilitiesId;

    @ApiModelProperty(value = "路段id")
    private String waysectionId;

    @ApiModelProperty(value = "系统id")
    private String systemId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "所属组织id")
    private String orgId;

    @ApiModelProperty(value = "所属组织名称")
    private String orgName;

    @ApiModelProperty(value = "设备所属网关")
    private DeviceVo gateway;

    @ApiModelProperty(value = "所属收费站id")
    private String tollStationId;

    @ApiModelProperty(value = "所属收费站名称")
    private String tollStationName;

    @ApiModelProperty(value = "创建人")
    private String createBy;

    @ApiModelProperty(value = "创建人")
    private String updateBy;

    @ApiModelProperty(value = "设备图片")
    private String pic;

    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @ApiModelProperty(value = "收费系统协议")
    private String protocolApplyScope;

    @ApiModelProperty(value = "车道id")
    private String laneId;

    @ApiModelProperty(value = "采集类型(0:无数据采集、1:被动采集、2:主动推送、3：可采可推)")
    private Integer dataCollectType;

    @ApiModelProperty(value = "设备所属车道号")
    private Integer laneNum;

    @ApiModelProperty(value = "设备序列号")
    private String seriaNumber;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "设备id(旧)")
    private String deviceIdOld;
}
