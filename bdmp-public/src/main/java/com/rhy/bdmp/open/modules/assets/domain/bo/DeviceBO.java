package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: yanggj
 * @Description: 设备基本信息
 * @Date: 2021/9/29 15:52
 * @Version: 1.0.0
 */
@ApiModel(value = "设备基本信息", description = "设备基本信息")
@Data
public class DeviceBO {
    @ApiModelProperty(value = "设备编号")
    private String deviceNo;
    @ApiModelProperty(value = "原始设备id")
    private String originId;
    @ApiModelProperty(value = "设备工作状态")
    private Integer workStatus;
    @ApiModelProperty(value = "路段名称")
    private String waySectionName;
    @ApiModelProperty(value = "路段id")
    private String waySectionId;
    @ApiModelProperty(value = "系统名称")
    private String systemName;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "公司名")
    private String compName;
    @ApiModelProperty(value = "设备主键id")
    private String deviceRecordId;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "桩号")
    private String centerOffNo;
    @ApiModelProperty(value = "方向")
    private String direction;
    @ApiModelProperty(value = "路段编号")
    private String oriWaysectionNo;
    @ApiModelProperty(value = "设备类型")
    private String deviceType;
    @ApiModelProperty(value = "设备类型名称")
    private String typeName;
    @ApiModelProperty(value = "设备里程")
    private BigDecimal deviceMileage;
    @ApiModelProperty(value = "数据来源")
    private String dataSource;

    @ApiModelProperty(value = "设施id")
    private String facilitiesId;
    @ApiModelProperty(value = "设施名称")
    private String facilitiesName;
    @ApiModelProperty(value = "运营公司id")
    private String orgId;
    @ApiModelProperty(value = "位置")
    private String location;
    @ApiModelProperty(value = "位置类型")
    private String locationType;

    @ApiModelProperty(value = "采集类型(0:无数据采集、1:被动采集、2:主动推送、3：可采可推)")
    private Integer dataCollectType;

    @ApiModelProperty(value = "设备网络ip")
    private String ip;
    @ApiModelProperty(value = "端口号")
    private String port;
    @ApiModelProperty(value = "显示分辨率")
    private String displayRatio;
    @ApiModelProperty(value = "显示色彩1:全彩、2：双基色")
    private Integer displayColor;

    @ApiModelProperty(value = "设备所属车道号")
    private Integer laneNum;

    @ApiModelProperty(value = "设备序列号")
    private String seriaNumber;

    @ApiModelProperty(value = "是否有云台控制(0:无，1:有)")
    private Integer hasPTZ;

    @ApiModelProperty(value = "组织简称")
    private String shortName;

    @ApiModelProperty(value = "组织简称")
    private String remark;

    @ApiModelProperty(value = "设备类型名称")
    private String deviceTypeName;

    @ApiModelProperty(value = "设备字典名称")
    private String deviceDictName;

    @ApiModelProperty(value = "设备字典id")
    private String deviceDictId;
}
