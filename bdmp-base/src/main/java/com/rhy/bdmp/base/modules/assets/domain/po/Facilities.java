package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @description 路段设施 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="路段设施", description="路段设施信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_facilities")
public class Facilities implements Serializable {

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

    @ApiModelProperty(value = "设施图片")
    @TableField("fac_img")
    private String facImg;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    @TableField("direction")
    private String direction;

    @ApiModelProperty(value = "是否省界门架(0:否、1:是)", example = "1")
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
    @TableField(value = "begin_stake_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    @TableField(value = "begin_stake_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    @TableField("end_stake_no")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_des")
    @TableField("end_stake_no_des")
    private String endStakeNoDes;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    @TableField(value = "end_stake_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    @TableField(value = "end_stake_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer endStakeNoM;

    @ApiModelProperty(value = "中心桩号")
    @TableField("center_off_no")
    private String centerOffNo;

    @ApiModelProperty(value = "中心桩号_des")
    @TableField("center_off_no_des")
    private String centerOffNoDes;

    @ApiModelProperty(value = "中心桩号_k", example = "1")
    @TableField(value = "center_off_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer centerOffNoK;

    @ApiModelProperty(value = "中心桩号_m", example = "1")
    @TableField(value = "center_off_no_m",updateStrategy = FieldStrategy.IGNORED)
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

    @ApiModelProperty(value = "设施状态", example = "1")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "运营公司ID")
    @TableField("company_id")
    private String companyId;

    @ApiModelProperty(value = "是否集中集控点")
    @TableField("is_monitor")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    @TableField("map_url")
    private String mapUrl;

    @ApiModelProperty(value = "是否OTN站点")
    @TableField("is_otn_site")
    private String isOtnSite;

    @ApiModelProperty(value = "国干站点类型")
    @TableField("national_site_type")
    private String nationalSiteType;

    @ApiModelProperty(value = "省干站点类型")
    @TableField("provincial_site_type")
    private String provincialSiteType;

    @ApiModelProperty(value = "站点类型")
    @TableField("site_category")
    private String siteCategory;

    @ApiModelProperty(value = "机房形式")
    @TableField("computer_room_form")
    private String computerRoomForm;

    @ApiModelProperty(value = "otn备注")
    @TableField("otn_remark")
    private String otnRemark;

    @ApiModelProperty(value = "百度编码")
    @TableField("baidu_code")
    private String baiduCode;

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

    @ApiModelProperty(value = "布置形式")
    @TableField("layout_form")
    private String layoutForm;
}
