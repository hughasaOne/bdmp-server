package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @auther xiabei
 * @Description
 * @date 2021/4/23
 */
@ApiModel(value="路段设施", description="路段设施信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FacilitiesVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施ID")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名称")
    private String facilitiesName;

    @ApiModelProperty(value = "设施代码")
    private String facilitiesCode;

    @ApiModelProperty(value = "设施类型")
    private String facilitiesType;

    @ApiModelProperty(value = "所属路段ID")
    private String waysectionId;

    @ApiModelProperty(value = "是否有子节点")
    private Boolean hasChildren;

    @ApiModelProperty(value = "是否显示添加按钮")
    private Boolean addAble;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "级别", example = "1")
    private Integer level;

    @ApiModelProperty(value = "设施位置(路段名/设施名称)")
    private String location;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    private String direction;

    @ApiModelProperty(value = "是否省界门架(0:否、1:是)", example = "0")
    private Integer isProvinceDoorFrame;

    @ApiModelProperty(value = "关联收费站ID")
    private String associatedTollStationId;

    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;

    @ApiModelProperty(value = "起点桩号_des")
    private String beginStakeNoDes;

    @ApiModelProperty(value = "起点桩号_k", example = "1")
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_des")
    private String endStakeNoDes;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    private Integer endStakeNoM;

    @ApiModelProperty(value = "中心桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "中心桩号_des")
    private String centerOffNoDes;

    @ApiModelProperty(value = "中心桩号_k", example = "1")
    private Integer centerOffNoK;

    @ApiModelProperty(value = "中心桩号_m", example = "1")
    private Integer centerOffNoM;

    @ApiModelProperty(value = "距中心线偏移量-左")
    private BigDecimal lCenterOffsetValue;

    @ApiModelProperty(value = "距中心线偏移量-右")
    private BigDecimal rCenterOffsetValue;

    @ApiModelProperty(value = "设施ID(旧)")
    private String facilitiesIdOld;

    @ApiModelProperty(value = "设施ID来源(旧)")
    private String facilitiesIdSource;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;

    @ApiModelProperty(value = "路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "路段简称")
    private String waysectionSName;

    @ApiModelProperty(value = "父节点名称")
    private String parentName;

    @ApiModelProperty(value = "设施类型")
    private String facilitiesTypeName;

}
