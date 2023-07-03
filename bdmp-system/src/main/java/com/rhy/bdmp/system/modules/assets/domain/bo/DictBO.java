package com.rhy.bdmp.system.modules.assets.domain.bo;

import com.rhy.bcp.common.util.QueryVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="字典业务实体")
public class DictBO extends QueryVO.QueryPage {

    @ApiModelProperty("是否是目录")
    @NotNull(message = "isDirectory不为空")
    private Boolean isDirectory;

    @ApiModelProperty("是否查询设备字典")
    private Boolean isDeviceDict;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("编号、代码")
    private String code;

    @ApiModelProperty("数据id")
    private String id;

    @ApiModelProperty("父节点id")
    private String parentId;

    @ApiModelProperty("内部父节点id")
    private String innerParentId;
}
