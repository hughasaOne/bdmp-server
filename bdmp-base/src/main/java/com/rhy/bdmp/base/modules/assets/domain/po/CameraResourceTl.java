package com.rhy.bdmp.base.modules.assets.domain.po;

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
 * @description 视频资源(腾路) 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="视频资源(腾路)", description="视频资源(腾路)信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_camera_resource_tl")
public class CameraResourceTl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频资源ID")
    @TableId("camera_id")
    private String cameraId;

    @ApiModelProperty(value = "视频资源名称")
    @TableField("camera_name")
    private String cameraName;

    @ApiModelProperty(value = "资源类型(1:视管平台摄像头、6:网关摄像头、0:全部)", example = "1")
    @TableField("device_type")
    private Integer deviceType;

    @ApiModelProperty(value = "视频资源IP")
    @TableField("camera_ip")
    private String cameraIp;

    @ApiModelProperty(value = "位置类型(10:收费站、11:收费站广场、 20:隧道、  30:外场、40:服务区)",example = "1")
    @TableField("location_type")
    private Integer locationType;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "经度84")
    @TableField("longitude_84")
    private String longitude84;

    @ApiModelProperty(value = "纬度84")
    @TableField("latitude_84")
    private String latitude84;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Long sort;

    @ApiModelProperty(value = "数据状态", example = "1")
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

    @ApiModelProperty(value = "路段ID")
    @TableField("way_id")
    private String wayId;

    @ApiModelProperty(value = "路段名称")
    @TableField("way_name")
    private String wayName;

    @ApiModelProperty(value = "设施ID")
    @TableField("geographyinfo_id")
    private String geographyinfoId;

    @ApiModelProperty(value = "安装位置")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "位置描述")
    @TableField("info_name")
    private String infoName;

    @ApiModelProperty(value = "在线状态")
    @TableField("online_status")
    private String onlineStatus;

    @ApiModelProperty(value = "云台资源ID")
    @TableField("uuid")
    private String uuid;
}
