package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetUrlByFacIdBo {
    @ApiModelProperty("设施id")
    private String facId;

    @ApiModelProperty("数据来源[1:云台 2:腾路 3:上云]")
    private String dataSource;

    @ApiModelProperty("网络来源[1:外网 2:内网],默认外网")
    private Integer netType;

    @ApiModelProperty("码率，0表示主码流，1表示子码流，2表示转码流，优先取子码流，当子码为空，用主码流")
    private Integer rateType;
}
