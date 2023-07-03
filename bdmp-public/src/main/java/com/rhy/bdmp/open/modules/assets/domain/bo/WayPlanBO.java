package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created on 2021/11/12.
 * 计划总里程信息
 * @author duke
 */
@ApiModel(value = "计划总里程信息", description = "计划总里程信息")
@Data
public class WayPlanBO {

    @ApiModelProperty(value = "计划年份")
    private String planAchieveYear;

    @ApiModelProperty(value = "计划总里程")
    private BigDecimal planAchieveTotalMileage;

    @ApiModelProperty(value = "实际总里程")
    private BigDecimal actualTotalMileage;

}
