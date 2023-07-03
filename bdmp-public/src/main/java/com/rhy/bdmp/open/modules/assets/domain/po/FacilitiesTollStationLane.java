package com.rhy.bdmp.open.modules.assets.domain.po;

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
import java.util.Date;

/**
 * @description  实体
 * @author jiangzhimin
 * @date 2021-12-02 10:06
 * @version V1.0
 **/
@ApiModel(value="", description="信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_facilities_toll_station_lane")
public class FacilitiesTollStationLane implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "车道ID")
    @TableField("lane_id")
    private String laneId;

    @ApiModelProperty(value = "车道名称")
    @TableField("lane_name")
    private String laneName;

    @ApiModelProperty(value = "车道编号")
    @TableField("lane_no")
    private String laneNo;

    @ApiModelProperty(value = "车道IP")
    @TableField("lane_ip")
    private String laneIp;

    @ApiModelProperty(value = "设施id")
    @TableField("facilities_id")
    private String facilitiesId;

    @ApiModelProperty(value = "车道类型(1:etc，2:mtc)", example = "1")
    @TableField("etc_mtc")
    private Integer etcMtc;

    @ApiModelProperty(value = "出口类型(1:入口，2:出口)", example = "1")
    @TableField("in_out")
    private Integer inOut;

    @ApiModelProperty(value = "收费站编号")
    @TableField("toll_station_no")
    private String tollStationNo;

    @ApiModelProperty(value = "收费站名称")
    @TableField("toll_station_name")
    private String tollStationName;

    @ApiModelProperty(value = "路段编号")
    @TableField("way_no")
    private String wayNo;

    @ApiModelProperty(value = "路段名称")
    @TableField("way_name")
    private String wayName;

    @ApiModelProperty(value = "收费站ID")
    @TableField("toll_station_id")
    private String tollStationId;

    @ApiModelProperty(value = "数据源")
    @TableField("id_source")
    private String idSource;

    @ApiModelProperty(value = "数据ID")
    @TableField("id_old")
    private String idOld;

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
