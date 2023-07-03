package com.rhy.bdmp.open.modules.org.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrgDetailBo {

    @ApiModelProperty("组织id")
    private String orgId;
}
