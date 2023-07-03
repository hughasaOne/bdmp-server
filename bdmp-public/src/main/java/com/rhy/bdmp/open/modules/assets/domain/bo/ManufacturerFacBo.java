package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 厂商设施bo
 * @author weicaifu
 */
@Data
public class ManufacturerFacBo {
    @ApiModelProperty(value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息")
    private String orgId;

    @ApiModelProperty(value = "节点类型")
    private String nodeType;

    @ApiModelProperty(value = "是否异步加载")
    private Boolean isAsync;

    @ApiModelProperty(value = "设施类型集合")
    private List<String> facilitiesTypes;
}
