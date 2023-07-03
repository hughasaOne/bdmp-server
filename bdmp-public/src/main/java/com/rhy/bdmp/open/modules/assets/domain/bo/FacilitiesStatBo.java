package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 桥梁分类统计
 * @Date: 2021/9/29 11:56
 * @Version: 1.0.0
 */
@ApiModel(value = "设施统计信息", description = "设施统计信息")
@Data
public class FacilitiesStatBo
{
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "收费站数")
    private Long tollStationNum;

    @ApiModelProperty(value = "门架数")
    private Long doorFrameNum;

    @ApiModelProperty(value = "桥梁数")
    private Long bridgeNum;

    @ApiModelProperty(value = "隧道数")
    private Long tunnelNum;

    @ApiModelProperty(value = "服务区数")
    private Long serviceAreaNum;


}
