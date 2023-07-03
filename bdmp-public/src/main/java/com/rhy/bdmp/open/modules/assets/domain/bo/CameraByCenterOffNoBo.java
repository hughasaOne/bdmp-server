package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CameraByCenterOffNoBo {
    @ApiModelProperty(value = "1: 方向顺向最近摄像头，2: 方向双向(上行、下行)最近摄像头，3: 桩号最近摄像头",required = true,example = "1")
    @NotNull(message = "搜索规则不为空")
    private Integer searchRule;

    @ApiModelProperty(value = "桩号",example = "ZK123+45",required = true)
    @NotBlank(message = "桩号不为空")
    private String centerOffNo;

    @ApiModelProperty(value = "路段id",required = true)
    @NotBlank(message = "路段id不为空")
    private String waysectionId;

    @ApiModelProperty(value = "方向",example = "上行/下行")
    private String direction;
}
