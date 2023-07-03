package com.rhy.bdmp.open.modules.device.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DevicePageBo extends DeviceListBo {
    private Integer currentPage;

    private Integer pageSize;
}
