package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@ApiModel(value="端口号Vo", description="端口号Vo")
@Data
public class BoxEnablePortVo {
    @ApiModelProperty(value = "端口号1")
    private Boolean enablePortOne = true;
    private Integer portOne = 1;

    @ApiModelProperty(value = "端口号2")
    private Boolean enablePortTwo = true;
    private Integer portTwo = 2;

    @ApiModelProperty(value = "端口号3")
    private Boolean enablePortThree = true;
    private Integer portThree = 3;

    @ApiModelProperty(value = "端口号4")
    private Boolean enablePortFour = true;
    private Integer portFour = 4;

    @ApiModelProperty(value = "端口号5")
    private Boolean enablePortFive = true;
    private Integer portFive = 5;

    @ApiModelProperty(value = "端口号6")
    private Boolean enablePortSix = true;
    private Integer portSix = 6;

    @ApiModelProperty(value = "端口号7")
    private Boolean enablePortSeven = true;
    private Integer portSeven = 7;

    @ApiModelProperty(value = "端口号8")
    private Boolean enablePortEight = true;
    private Integer PortEight = 8;

    private List<Map<String,String>> enablePeripherals;
}
