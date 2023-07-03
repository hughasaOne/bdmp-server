package com.rhy.bdmp.open.modules.fac.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseTunnelVo {

    @ApiModelProperty(value = "隧道ID")
    private String tunnelId;

    @ApiModelProperty(value = "管理单位代码")
    private String manageCode;

    @ApiModelProperty(value = "管理单位名称")
    private String manageName;

    @ApiModelProperty(value = "路线代码")
    private String routeCode;

    @ApiModelProperty(value = "隧道编号")
    private String tunnelNo;

    @ApiModelProperty(value = "路线简称")
    private String routeSName;

    @ApiModelProperty(value = "隧道中心桩号")
    private String tunnelCenterStake;

    @ApiModelProperty(value = "行政区划名称")
    private String adminRegionName;

    @ApiModelProperty(value = "隧道名称")
    private String tunnelName;

    @ApiModelProperty(value = "隧道所在地点")
    private String tunnelPalce;

    @ApiModelProperty(value = "隧道入口桩号")
    private String tunnelEntranceStake;

    @ApiModelProperty(value = "隧道长度(米)")
    private String tunnelMeter;

    @ApiModelProperty(value = "隧道净宽(米)")
    private String tunnelCleanWide;

    @ApiModelProperty(value = "隧道净高(米)")
    private String tunnelCleanHeight;

    @ApiModelProperty(value = "人行道宽(米)")
    private String pavementWide;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "评定等级代码(总体)")
    private String allEvaluateLevelCode;

    @ApiModelProperty(value = "评定等级名称(总体)")
    private String allEvaluateLevelName;

    @ApiModelProperty(value = "运营路段ID")
    @TableField("operating_waysection_id")
    private String operatingWaysectionId;

    @ApiModelProperty(value = "设施ID")
    private String facilitiesId;

    @ApiModelProperty(value = "经度84")
    private String longitude84;

    @ApiModelProperty(value = "纬度84")
    private String latitude84;

    @ApiModelProperty(value = "方向")
    private String direction;
}
