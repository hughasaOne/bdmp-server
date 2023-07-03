package com.rhy.bdmp.system.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class RequiredFieldsBo {

    @ApiModelProperty("设施id")
    private String facId;

    @ApiModelProperty("设备id（修改时传此参数）")
    private String deviceId;

    @ApiModelProperty("同一设施下ip不重复")
    private String ip;

    @ApiModelProperty("设备序列号不重复")
    private String seriaNumber;
}
