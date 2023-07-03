package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 路段基本信息
 * @Date: 2021/11/10 15:28
 * @Version: 1.0.0
 */
@ApiModel(value = "路段基本信息", description = "路段基本信息")
@Data
public class WaySectionInfo {
    @ApiModelProperty(value = "所属路段Id")
    private String belongSectionId;
    @ApiModelProperty(value = "所属路段")
    private String belongSectionName;
    @ApiModelProperty(value = "桩号")
    private String pileNumber;
    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;
    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;
    @ApiModelProperty(value = "路段编号")
    private String oriWaynetNo;
    @ApiModelProperty(value = "所属运营公司ID")
    private String belongCompanyId;
    @ApiModelProperty(value = "所属运营公司")
    private String belongCompanyName;
    @ApiModelProperty(value = "所属运营公司-简称")
    private String belongCompanySName;
    @ApiModelProperty(value = "管辖里程. 单位：公里")
    private Double manageMileage = 0d;
    @ApiModelProperty(value = "桥隧比. 单位：1")
    private Double bridgeTunnelRate;

    @ApiModelProperty(value = "桥梁数量")
    private Integer bridgeNum = 0;
    @ApiModelProperty(value = "特长桥数量")
    private Integer especialLongBridgeNum = 0;
    @ApiModelProperty(value = "长桥数量")
    private Integer longBridgeNum = 0;
    @ApiModelProperty(value = "中桥数量")
    private Integer middleBridgeNum = 0;
    @ApiModelProperty(value = "小桥数量")
    private Integer smallBridgeNum = 0;
    @ApiModelProperty(value = "桥梁里程")
    private Double bridgeMileage = 0d;

    @ApiModelProperty(value = "隧道数量")
    private Integer tunnelNum = 0;
    @ApiModelProperty(value = "特长隧道数量")
    private Integer especialLongTunnelNum = 0;
    @ApiModelProperty(value = "长隧道数量")
    private Integer longTunnelNum = 0;
    @ApiModelProperty(value = "中隧道数量")
    private Integer middleTunnelNum = 0;
    @ApiModelProperty(value = "小隧道数量")
    private Integer smallTunnelNum = 0;
    @ApiModelProperty(value = "隧道里程")
    private Integer tunnelMileage = 0;

    @ApiModelProperty(value = "收费站数量")
    private Integer chargeStationNum = 0;
    @ApiModelProperty(value = "收费站正常通行数量")
    private Integer chargeStationNormalPassNum = 0;
    @ApiModelProperty(value = "收费站临时关闭数量")
    private Integer chargeStationTemporaryCloseNum = 0;

    @ApiModelProperty(value = "服务区对数")
    private Integer serviceAreaNum = 0;
    @ApiModelProperty(value = "1+类服务区数量. 单位：对")
    private Integer onePlusServiceAreaNum = 0;
    @ApiModelProperty(value = "1类服务区数量. 单位：对")
    private Integer oneServiceAreaNum = 0;
    @ApiModelProperty(value = "2类服务区数量. 单位：对")
    private Integer twoServiceAreaNum = 0;
    @ApiModelProperty(value = "3类服务区数量. 单位：对")
    private Integer threeServiceAreaNum = 0;
    @ApiModelProperty(value = "4类服务区数量. 单位：对")
    private Integer fourServiceAreaNum = 0;

}
