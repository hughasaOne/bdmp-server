package com.rhy.bdmp.open.modules.device.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceCategoryBo {
    @ApiModelProperty("主题id")
    private String topicId;
}
