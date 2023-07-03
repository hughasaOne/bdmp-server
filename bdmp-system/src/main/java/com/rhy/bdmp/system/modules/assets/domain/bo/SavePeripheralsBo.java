package com.rhy.bdmp.system.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SavePeripheralsBo {
    @ApiModelProperty("终端箱id")
    @NotBlank(message = "终端箱id不为空")
    private String boxId;

    @ApiModelProperty("端口号")
    @NotNull(message = "端口号不为空")
    private Integer portNum;

    @ApiModelProperty("外设id")
    @NotBlank(message = "外设id不为空")
    private String deviceId;
}
