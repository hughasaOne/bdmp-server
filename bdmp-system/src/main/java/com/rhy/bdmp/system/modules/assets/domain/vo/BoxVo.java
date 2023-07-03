package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 终端箱 视图实体
 */
@Data
public class BoxVo {
    @ApiModelProperty(value = "主键")
    private String deviceId;

    @ApiModelProperty(value = "设备编号")
    private String sn;

    @ApiModelProperty(value = "软件版本")
    private String rom;

    @ApiModelProperty(value = "单片机版本")
    private String scm;

    @ApiModelProperty(value = "重合闸版本")
    private String reclosing;

    @ApiModelProperty(value = "设备名称")
    private String devName;

    @ApiModelProperty(value = "主板IP")
    private String bordIp;

    @ApiModelProperty(value = "生产厂商")
    private String manufacturer;

    @ApiModelProperty(value = "联系方式")
    private String tel;

    @ApiModelProperty(value = "链路标志")
    private String linkType;

    @ApiModelProperty(value = "空开版本")
    private String airSwitchVersion;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "经度")
    private Double lon;

    @ApiModelProperty(value = "纬度")
    private Double lat;
}
