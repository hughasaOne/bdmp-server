package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: TODO
 * @Date: 2021/10/26 17:29
 * @Version: 1.0.0
 */
@Data
public class GantryDetailBO {

    @ApiModelProperty(value = "门架名称")
    private String gantryName;

    @ApiModelProperty(value = "桩号")
    private String pileNumber;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

    @ApiModelProperty(value = "门架状态")
    private Integer status;

    @ApiModelProperty(value = "原ID")
    private String originId;

    @ApiModelProperty(value = "路段id")
    private String waySectionId;

    @ApiModelProperty(value = "路段编号")
    private String oriWaySectionNo;

    @ApiModelProperty(value = "路段名称")
    private String waySectionName;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "设施ID")
    private String gantryId;

    @ApiModelProperty(value = "门架类型")
    private String gantryType;

    @ApiModelProperty(value = "门架排序")
    private String gantryOrder;

    @ApiModelProperty(value = "HEX码")
    private String hex;

    @ApiModelProperty(value = "站所名称")
    private String station;

    @ApiModelProperty(value = "方向")
    private String derection;

    @ApiModelProperty(value = "断面")
    private String section;

    @ApiModelProperty(value = "门架实际编号")
    private String realGantryId;

    @ApiModelProperty(value = "所属业主")
    private String company;

    @ApiModelProperty(value = "所属业主id")
    private String companyId;

    @ApiModelProperty(value = "是否省界", example = "1")
    private Integer isProvince;

    @ApiModelProperty(value = "邻省")
    private String neiProvinces;

    @ApiModelProperty(value = "通往方向")
    private String locDirection;

    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;

    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;

}
