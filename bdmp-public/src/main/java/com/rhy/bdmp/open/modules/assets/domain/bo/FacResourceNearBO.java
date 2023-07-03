package com.rhy.bdmp.open.modules.assets.domain.bo;

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
@ApiModel(value = "附近设施资源", description = "附近设施资源")
@Data
public class FacResourceNearBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施ID")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名称")
    private String facilitiesName;

    @ApiModelProperty(value = "设施编号")
    private String facilitiesCode;

    @ApiModelProperty(value = "原ID")
    private String originId;

    @ApiModelProperty(value = "设施状态")
    private Integer status;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "设施类型")
    private String code;

    @ApiModelProperty(value = "是否省界门架(0:否、1:是)", example = "1")
    private Integer isProvince;

    @ApiModelProperty(value = "方向")
    private String derection;

    @ApiModelProperty(value = "桩号")
    private String pileNumber;

    @ApiModelProperty(value = "设施类型名称")
    private String codeName;

    @ApiModelProperty(value = "位置")
    private String location;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

    @ApiModelProperty(value = "距离（公里）")
    private Double distanceKm;

    @ApiModelProperty(value = "设施分类字典key")
    private String dictKey;

    @ApiModelProperty(value = "设施分类字典名称")
    private String dictName;

    @ApiModelProperty(value = "百度编码")
    private String baiduCode;

    @ApiModelProperty(value = "路段id")
    private String waysectionId;

    @ApiModelProperty(value = "路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "公司id")
    private String orgId;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;

    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;

}
