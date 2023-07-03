package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @description 视频资源(云台) 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="视频资源(云台)", description="视频资源(云台)信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_camera_resource_yt")
public class CameraResourceYt implements Serializable {

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

    @ApiModelProperty(value = "位置类型(10:收费站、11:收费站广场、 20:隧道、  30:外场、40:服务区)",example = "1")
    @TableField("location_type")
    private Integer locationType;

    @ApiModelProperty(value = "视频资源数", example = "1")
    @TableField("car_sum")
    private Integer carSum;

    @ApiModelProperty(value = "层级（如：1:湖北交投、2:运营集团、3:运营公司、4:路段、5:设施、6:视频资源）")
    @TableField("camera_level")
    private String cameraLevel;

    @ApiModelProperty(value = "视频资源状态(10:在线、20:离线)", example = "1")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "是否有云台控制(0:无，1:有)", example = "1")
    @TableField("has_ptz")
    private Integer hasPtz;

    @ApiModelProperty(value = "坐标X")
    @TableField("coord_x")
    private String coordX;

    @ApiModelProperty(value = "坐标Y")
    @TableField("coord_y")
    private String coordY;

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
}
