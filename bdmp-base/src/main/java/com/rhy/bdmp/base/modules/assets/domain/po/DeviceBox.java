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
 * @description 终端箱基本信息表 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="终端箱基本信息表", description="终端箱基本信息表信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_device_box")
public class DeviceBox extends DeviceExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public void setDevName(String devName) {
        this.devName = devName;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public void setBordIp(String bordIp) {
        this.bordIp = bordIp;
    }

    @Override
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    @ApiModelProperty(value = "主键")
    @TableId("device_id")
    private String deviceId;

    @ApiModelProperty(value = "设备编号")
    @TableField("sn")
    private String sn;

    @ApiModelProperty(value = "软件版本")
    @TableField("rom")
    private String rom;

    @ApiModelProperty(value = "单片机版本")
    @TableField("scm")
    private String scm;

    @ApiModelProperty(value = "重合闸版本")
    @TableField("reclosing")
    private String reclosing;

    @ApiModelProperty(value = "设备名称")
    @TableField("dev_name")
    private String devName;

    @ApiModelProperty(value = "主板IP")
    @TableField("bord_ip")
    private String bordIp;

    @ApiModelProperty(value = "生产厂商")
    @TableField("manufacturer")
    private String manufacturer;

    @ApiModelProperty(value = "联系方式")
    @TableField("tel")
    private String tel;

    @ApiModelProperty(value = "链路标志")
    @TableField("link_type")
    private String linkType;

    @ApiModelProperty(value = "空开版本")
    @TableField("air_switch_version")
    private String airSwitchVersion;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    @TableField("datastatusid")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;
}
