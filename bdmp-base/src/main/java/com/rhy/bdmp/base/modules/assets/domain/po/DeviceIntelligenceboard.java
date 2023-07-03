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
 * @description 情报板 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="情报板", description="情报板信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_device_intelligenceboard")
public class DeviceIntelligenceboard extends DeviceExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId("device_id")
    private String deviceId;

    @ApiModelProperty(value = "像素宽", example = "1")
    @TableField("pixel_x")
    private Integer pixelX;

    @ApiModelProperty(value = "像素高", example = "1")
    @TableField("pixel_y")
    private Integer pixelY;

    @ApiModelProperty(value = "图片像素", example = "1")
    @TableField("picture_size")
    private Integer pictureSize;

    @ApiModelProperty(value = "显示效果")
    @TableField("style")
    private String style;

    @ApiModelProperty(value = "显示时长(单位秒)", example = "1")
    @TableField("dwell_time")
    private Integer dwellTime;

    @ApiModelProperty(value = "对齐方式(0左上1左中2左下3中上4 居中5中下6 右上7 右中8 右下)", example = "1")
    @TableField("alignment")
    private Integer alignment;

    @ApiModelProperty(value = "情报板类型(1 常用2 天气3 其他)", example = "1")
    @TableField("common_type")
    private Integer commonType;

    @ApiModelProperty(value = "发送次数", example = "1")
    @TableField("send_count")
    private Integer sendCount;

    @ApiModelProperty(value = "使用类型(0 常用库 1播放类表 2 当前包访)", example = "1")
    @TableField("use_type")
    private Integer useType;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

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

    public void setDeviceId(String deviceId){
        this.deviceId = deviceId;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
