package com.rhy.bdmp.open.modules.assets.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FacilitiesVo {
    @ApiModelProperty(value = "设施ID")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名称")
    private String facilitiesName;

    @ApiModelProperty("设施名称拼接桩号")
    private String facNickName;

    @ApiModelProperty(value = "设施代码")
    private String facilitiesCode;

    @ApiModelProperty(value = "设施类型")
    private String facilitiesType;

    @ApiModelProperty(value = "所属路段ID")
    private String waysectionId;

    @ApiModelProperty(value = "所属路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "所属组织id")
    private String orgId;

    @ApiModelProperty(value = "所属组织名称")
    private String orgName;

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

    @ApiModelProperty(value = "是否省界门架(0:否、1:是)", example = "1")
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

    @ApiModelProperty(value = "设施状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "运营公司ID")
    private String companyId;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

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

    @ApiModelProperty(value = "是否是叶子节点")
    private Boolean isLeaf;
}
