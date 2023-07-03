package com.rhy.bdmp.open.modules.assets.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created on 2021/11/12.
 * 计划里程详情
 * @author duke
 */
@ApiModel(value = "计划里程详情信息", description = "计划里程详情信息")
@Data
public class WayPlanDetailBO {

    @ApiModelProperty(value = "路段名称")
    private String sectionName;

    @ApiModelProperty(value = "建设里程")
    private BigDecimal buildMileage;

    @ApiModelProperty(value = "计划完工日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date planAchieveDate;
}
