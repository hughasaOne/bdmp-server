package com.rhy.bdmp.open.modules.device.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatDeviceNumByTypeVo {
    @ApiModelProperty("设备类型code")
    private String code;

    @ApiModelProperty("设备数量")
    private Integer num;

    @ApiModelProperty("设备类型名称")
    private String name;

    @ApiModelProperty("分类类型 1：设备类型，2：设备字典")
    private Integer categoryType;
}
