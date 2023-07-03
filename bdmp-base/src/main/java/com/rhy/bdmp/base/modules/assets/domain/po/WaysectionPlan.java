package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @description 计划里程 实体
 * @author duke
 * @date 2021-11-12 10:58
 * @version V1.0
 **/
@ApiModel(value="计划里程", description="计划里程信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_waysection_plan")
public class WaysectionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "路段名称")
    @TableField("section_name")
    private String sectionName;

    @ApiModelProperty(value = "所属运营公司ID")
    @TableField("belong_company_id")
    private String belongCompanyId;

    @ApiModelProperty(value = "所属运营公司名称")
    @TableField("belong_company_name")
    private String belongCompanyName;

    @ApiModelProperty(value = "计划年份")
    @TableField("plan_achieve_year")
    private String planAchieveYear;

    @ApiModelProperty(value = "计划完工日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("plan_achieve_date")
    private Date planAchieveDate;

    @ApiModelProperty(value = "建设里程")
    @TableField("build_mileage")
    private BigDecimal buildMileage;

    @ApiModelProperty(value = "是否现有路段(1:是，2:否)", example = "1")
    @TableField("is_exist_way")
    private Integer isExistWay;

    @ApiModelProperty(value = "现有路段ID")
    @TableField("way_id")
    private String wayId;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Long sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    @TableField("datastatusid")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;
}
