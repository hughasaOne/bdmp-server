package com.rhy.bdmp.open.modules.dict.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeviceDictBo {
    @ApiModelProperty("为空则查询全部的设备类型树")
    private String categoryId;
}
