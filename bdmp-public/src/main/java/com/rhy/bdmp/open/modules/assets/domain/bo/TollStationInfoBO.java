package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 收费站总况
 * @Date: 2021/9/27 9:18
 * @Version: 1.0.0
 */
@ApiModel(value = "收费站总况", description = "收费站总况")
@Data
public class TollStationInfoBO {

    @ApiModelProperty(value = "收费站总数")
    private Integer total;
    @ApiModelProperty(value = "正常运行收费站数量")
    private Integer normalNumber;
    @ApiModelProperty(value = "临时关闭收费站数量")
    private Integer closeNumber;
    @ApiModelProperty(value = "ETC车道数")
    private Integer etcNumber;
    @ApiModelProperty(value = "MTC车道数")
    private Integer mtcNumber;

}
