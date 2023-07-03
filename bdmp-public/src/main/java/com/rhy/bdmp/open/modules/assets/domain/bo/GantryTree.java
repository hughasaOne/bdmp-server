package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: yanggj
 * @Description: 门架设备树
 * @Date: 2021/9/29 14:59
 * @Version: 1.0.0
 */
@ApiModel(value = "门架设备树", description = "门架设备树")
@Data
public class GantryTree {

    @ApiModelProperty(value = "门架编号")
    private String id;
    @ApiModelProperty(value = "门架名称")
    private String name;
    @ApiModelProperty(value = "门架原始id")
    private String originId;
    @ApiModelProperty(value = "门架状态")
    private String status;
    @ApiModelProperty(value = "设备编号")
    private String deviceNo;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "子节点")
    private List<?> children;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

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
}
