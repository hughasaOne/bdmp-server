package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 路段简要信息
 * @Date: 2021/11/10 17:54
 * @Version: 1.0.0
 */
@ApiModel(value = "路段简要信息", description = "路段简要信息")
@Data
public class WaySectionShort {

    @ApiModelProperty(value = "所属路段Id")
    private String belongSectionId;
    @ApiModelProperty(value = "所属路段")
    private String belongSectionName;
    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;
    @ApiModelProperty(value = "止点桩号")
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
    private Double manageMileage;
    @ApiModelProperty(value = "桥隧比. 单位：1")
    private Double bridgeTunnelRate;

}
