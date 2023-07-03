package com.rhy.bdmp.system.modules.analysis.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName:RanKOperatingCompanyVO
 * @Description:
 * @Author:魏财富
 * @Date:2021/4/21 16:11
 */
@Data
public class RanKOperatingCompanyVO {
    @ApiModelProperty(value = "公司id")
    @TableField(exist = false)
    private String orgId;

    @ApiModelProperty(value = "公司名称")
    @TableField(exist = false)
    private String orgName;

    @ApiModelProperty(value = "总数")
    @TableField(exist = false)
    private Double num;

    @ApiModelProperty(value = "排名类型")
    @TableField(exist = false)
    private String rankType;

}
