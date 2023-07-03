package com.rhy.bdmp.open.modules.device.domain.bo;

import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StatDeviceNumByTypeBo extends CommonBo {
    @ApiModelProperty("分类类型")
    private Integer categoryType;
}
