package com.rhy.bdmp.open.modules.user.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GetUserByOrgBo {
    @ApiModelProperty("是否查找下级机构的用户，默认为false")
    private Boolean includeSubOrgUser = false;

    @ApiModelProperty("是否需要组织机构信息（运营集团、运营公司）,当用户处于深层节点时，该属性为true查询会很慢")
    private Boolean includeOrgInfo = false;

    @ApiModelProperty("机构id")
    @NotBlank(message = "机构id不为空")
    private String orgId;
}
