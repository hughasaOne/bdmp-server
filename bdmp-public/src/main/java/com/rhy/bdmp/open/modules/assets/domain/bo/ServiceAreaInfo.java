package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 服务区基本信息
 * @Date: 2021/11/10 15:27
 * @Version: 1.0.0
 */
@ApiModel(value = "服务区基本信息", description = "服务区基本信息")
@Data
public class ServiceAreaInfo {

    @ApiModelProperty(value = "服务区对数")
    private Integer serviceAreaNum;
    @ApiModelProperty(value = "1+类服务区数量. 单位：对")
    private Integer onePlusServiceAreaNum;
    @ApiModelProperty(value = "1类服务区数量. 单位：对")
    private Integer oneServiceAreaNum;
    @ApiModelProperty(value = "2类服务区数量. 单位：对")
    private Integer twoServiceAreaNum;
    @ApiModelProperty(value = "3类服务区数量. 单位：对")
    private Integer threeServiceAreaNum;
    @ApiModelProperty(value = "4类服务区数量. 单位：对")
    private Integer fourServiceAreaNum;
}
