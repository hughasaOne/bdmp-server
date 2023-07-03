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
 * @description 视频资源 实体
 * @author weicaifu
 * @date 2022-12-27 09:52
 * @version V1.0
 **/
@ApiModel(value="视频资源", description="视频资源信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_device_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频资源ID")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "资源名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父节点ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "资源类型(10:目录、20:资源)", example = "1")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "状态 -1未启用，  0 正常 ， 1  网络中断，2  网络正常无图像，3  有图像图像存在问题。")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "1-在线，2-离线，3-故障")
    @TableField("online_status")
    private String onlineStatus;

    @ApiModelProperty(value = "是否有云台控制(0:无，1:有)")
    @TableField("has_ptz")
    private String hasPtz;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "运营路段ID")
    @TableField("way_id")
    private String wayId;

    @ApiModelProperty(value = "运营路段名称")
    @TableField("way_name")
    private String wayName;

    @ApiModelProperty(value = "设施ID")
    @TableField("geographyinfo_id")
    private String geographyinfoId;

    @ApiModelProperty(value = "安装位置")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "相机类型（0：默认1：道路沿线 2：桥梁 3：隧道 4：收费广场 5：收费站 6：服务区 7：ETC门架 8：移动视频源", example = "1")
    @TableField("location_type")
    private String locationType;

    @ApiModelProperty(value = "中心桩号")
    @TableField("center_off_no")
    private String centerOffNo;

    @ApiModelProperty(value = "方向（0：上行1：下行2：上下行（双向））")
    @TableField("direction")
    private String direction;

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

    @ApiModelProperty(value = "WGS84经度")
    @TableField("longitude_84")
    private String longitude84;

    @ApiModelProperty(value = "WGS84纬度")
    @TableField("latitude_84")
    private String latitude84;

    @ApiModelProperty(value = "直播流地址")
    @TableField("live_url")
    private String liveUrl;
}
