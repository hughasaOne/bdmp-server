package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 路段设施 实体
 * @author jiangzhimin
 * @date 2021-05-07 13:36
 * @version V1.0
 **/
@ApiModel(value="路段设施", description="路段设施信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("t_bdmp_assets_facilities")
public class FacilitiesShortVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施ID")
    @TableId("facilities_id")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名称")
    @TableField("facilities_name")
    private String facilitiesName;

    @ApiModelProperty(value = "设施代码")
    @TableField("facilities_code")
    private String facilitiesCode;

    @ApiModelProperty(value = "设施类型")
    @TableField("facilities_type")
    private String facilitiesType;

    @ApiModelProperty(value = "所属路段ID")
    @TableField("waysection_id")
    private String waysectionId;

    @ApiModelProperty(value = "父节点ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "级别", example = "1")
    @TableField("level")
    private Integer level;

    @ApiModelProperty(value = "设施位置(路段名/设施名称)")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    @TableField("direction")
    private String direction;

    @ApiModelProperty(value = "是否省界门架(0:否、1:是)", example = "0")
    @TableField("is_province_door_frame")
    private Integer isProvinceDoorFrame;

    @ApiModelProperty(value = "关联收费站ID")
    @TableField("associated_toll_station_id")
    private String associatedTollStationId;

    @ApiModelProperty(value = "起点桩号")
    @TableField("begin_stake_no")
    private String beginStakeNo;

    @ApiModelProperty(value = "起点桩号_des")
    @TableField("begin_stake_no_des")
    private String beginStakeNoDes;

    @ApiModelProperty(value = "起点桩号_k", example = "1")
    @TableField("begin_stake_no_k")
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    @TableField("begin_stake_no_m")
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    @TableField("end_stake_no")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_des")
    @TableField("end_stake_no_des")
    private String endStakeNoDes;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    @TableField("end_stake_no_k")
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    @TableField("end_stake_no_m")
    private Integer endStakeNoM;

    @ApiModelProperty(value = "中心桩号")
    @TableField("center_off_no")
    private String centerOffNo;

    @ApiModelProperty(value = "中心桩号_des")
    @TableField("center_off_no_des")
    private String centerOffNoDes;

    @ApiModelProperty(value = "中心桩号_k", example = "1")
    @TableField("center_off_no_k")
    private Integer centerOffNoK;

    @ApiModelProperty(value = "中心桩号_m", example = "1")
    @TableField("center_off_no_m")
    private Integer centerOffNoM;

    @ApiModelProperty(value = "距中心线偏移量-左")
    @TableField("l_center_offset_value")
    private BigDecimal lCenterOffsetValue;

    @ApiModelProperty(value = "距中心线偏移量-右")
    @TableField("r_center_offset_value")
    private BigDecimal rCenterOffsetValue;

    @ApiModelProperty(value = "设施ID(旧)")
    @TableField("facilities_id_old")
    private String facilitiesIdOld;

    @ApiModelProperty(value = "设施ID来源(旧)")
    @TableField("facilities_id_source")
    private String facilitiesIdSource;

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
