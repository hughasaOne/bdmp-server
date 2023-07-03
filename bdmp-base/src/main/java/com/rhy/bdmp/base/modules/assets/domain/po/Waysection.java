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
 * @description 运营路段 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="运营路段", description="运营路段信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_waysection")
public class Waysection implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路段ID")
    @TableId("waysection_id")
    private String waysectionId;

    @ApiModelProperty(value = "路段名称")
    @TableField("waysection_name")
    private String waysectionName;

    @ApiModelProperty(value = "路段简称")
    @TableField("waysection_s_name")
    private String waysectionSName;

    @ApiModelProperty(value = "路段代码")
    @TableField("waysection_code")
    private String waysectionCode;

    @ApiModelProperty(value = "区域编号")
    @TableField("area_no")
    private String areaNo;

    @ApiModelProperty(value = "所属路网ID")
    @TableField("waynet_id")
    private String waynetId;

    @ApiModelProperty(value = "原所属路网ID")
    @TableField("ori_waynet_id")
    private String oriWaynetId;

    @ApiModelProperty(value = "管理机构ID")
    @TableField("manage_id")
    private String manageId;

    @ApiModelProperty(value = "所属收费区域")
    @TableField("toll_region")
    private String tollRegion;

    @ApiModelProperty(value = "所属行政区域")
    @TableField("barrio")
    private String barrio;

    @ApiModelProperty(value = "里程数")
    @TableField("mileage")
    private BigDecimal mileage;

    @ApiModelProperty(value = "管理类型(自建自营,委托经营,代管)")
    @TableField("manage_type")
    private String manageType;

    @ApiModelProperty(value = "路段队伍ID")
    @TableField("road_team_id")
    private String roadTeamId;

    @ApiModelProperty(value = "路段队伍信息")
    @TableField("road_team_info")
    private String roadTeamInfo;

    @ApiModelProperty(value = "求援队伍ID")
    @TableField("rescue_team_id")
    private String rescueTeamId;

    @ApiModelProperty(value = "求援队伍信息")
    @TableField("rescue_team_info")
    private String rescueTeamInfo;

    @ApiModelProperty(value = "是否全程监控", example = "1")
    @TableField("if_whole_monitor")
    private Integer ifWholeMonitor;

    @ApiModelProperty(value = "是否收费", example = "1")
    @TableField("if_charge")
    private Integer ifCharge;

    @ApiModelProperty(value = "是否采集", example = "1")
    @TableField("is_collection")
    private Integer isCollection;

    @ApiModelProperty(value = "是否为数据报送主体", example = "1")
    @TableField("is_send_data_body")
    private Integer isSendDataBody;

    @ApiModelProperty(value = "起点站ID")
    @TableField("begin_station_id")
    private String beginStationId;

    @ApiModelProperty(value = "中心站ID")
    @TableField("centre_station_id")
    private String centreStationId;

    @ApiModelProperty(value = "终点站ID")
    @TableField("end_station_id")
    private String endStationId;

    @ApiModelProperty(value = "起点桩号")
    @TableField("begin_stake_no")
    private String beginStakeNo;

    @ApiModelProperty(value = "起点桩号_k", example = "1")
    @TableField(value = "begin_stake_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    @TableField(value = "begin_stake_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    @TableField("end_stake_no")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    @TableField(value = "end_stake_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    @TableField(value = "end_stake_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer endStakeNoM;

    @ApiModelProperty(value = "路段ID(旧)")
    @TableField("waysection_id_old")
    private String waysectionIdOld;

    @ApiModelProperty(value = "路段ID来源(旧)")
    @TableField("waysection_id_source")
    private String waysectionIdSource;

    @ApiModelProperty(value = "关联地图")
    @TableField("node_id")
    private String nodeId;

    @ApiModelProperty(value = "跨区行政ID集合，逗号分割")
    @TableField("district_ids")
    private String districtIds;

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
