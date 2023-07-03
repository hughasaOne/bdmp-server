package com.rhy.bdmp.open.modules.org.domain.bo;

import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrgListBo extends CommonBo {

    @ApiModelProperty("页大小")
    private Integer pageSize;

    @ApiModelProperty("当前页（值为-1时，不分页,默认为-1）")
    private Integer currentPage;
}
