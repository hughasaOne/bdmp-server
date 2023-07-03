package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description 路段设施
 * @date 2021-05-07 13:36
 **/
@ApiModel(value = "设施信息", description = "设施信息")
@Data
@Accessors(chain = true)
public class FacilitiesInfoBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String name;
    private String facNickName;
    private String longitude;
    private String latitude;
    private Integer dictKey;
    private String dictName;
    private String id;
    private String originId;

    @ApiModelProperty(value = "国标编码")
    private String stationCountryId;

    @ApiModelProperty(value = "设施编号")
    private String infoNo;

    @ApiModelProperty(value = "设施状态")
    private String status;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

    @ApiModelProperty(value = "是否OTN站点")
    private String isOtnSite;

    @ApiModelProperty(value = "国干站点类型")
    private String nationalSiteType;

    @ApiModelProperty(value = "省干站点类型")
    private String provincialSiteType;

    @ApiModelProperty(value = "站点类型")
    private String siteCategory;

    @ApiModelProperty(value = "机房形式")
    private String computerRoomForm;

    @ApiModelProperty(value = "otn备注")
    private String otnRemark;

    @ApiModelProperty(value = "百度编码")
    private String baiduCode;

    @ApiModelProperty(value = "行政区代码")
    private String adminRegionCode;

    @ApiModelProperty(value = "行政区名称")
    private String adminRegionName;

    @ApiModelProperty(value = "邻省")
    private String neiProvinces;

    @ApiModelProperty(value = "通往方向")
    private String locDirection;

    @ApiModelProperty(value = "类型名称")
    private String typeName;

    @ApiModelProperty(value = "路段id")
    private String waysectionId;

    @ApiModelProperty(value = "路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "公司id")
    private String orgId;

    @ApiModelProperty(value = "公司名称")
    private String orgName;

   @ApiModelProperty(value = "中心桩号")
   private String centerOffNo;

   @ApiModelProperty(value = "起点桩号")
   private String beginStakeNo;

   @ApiModelProperty(value = "终点桩号")
   private String endStakeNo;

    @ApiModelProperty(value = "集控点联系电话")
    private String monitorTelPhone;

}
