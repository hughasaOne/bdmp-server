package com.rhy.bdmp.open.modules.org.domain.bo;

import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrgTreeBo extends CommonBo {

    @ApiModelProperty("搜索类型：1:全部节点，2：下级节点及本身，3：上级点及本身，默认为1")
    private Integer searchType;
}
