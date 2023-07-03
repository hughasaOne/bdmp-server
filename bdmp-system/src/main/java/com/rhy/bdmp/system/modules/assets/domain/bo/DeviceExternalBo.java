package com.rhy.bdmp.system.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 1、不能选择自己
 * 2、已是自己外设的不能选择
 * 3、只能选与自己同设施下的
 */
@Data
public class DeviceExternalBo {
    @ApiModelProperty("设备的设施id")
    private String facId;

    @ApiModelProperty("设备的id")
    private String deviceId;

    @ApiModelProperty("要排除的id")
    private List<String> excludeId;
}
