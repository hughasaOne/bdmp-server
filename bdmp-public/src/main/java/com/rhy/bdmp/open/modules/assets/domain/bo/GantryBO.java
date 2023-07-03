package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: TODO
 * @Date: 2021/11/10 15:57
 * @Version: 1.0.0
 */
@Data
public class GantryBO {

    private String id;
    private String name;
    private String originId;
    private Integer status;
    private String facilitiesId;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;

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
