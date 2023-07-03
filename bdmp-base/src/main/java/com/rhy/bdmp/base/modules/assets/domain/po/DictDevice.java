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
 * @description 设备字典 实体
 * @author yanggj
 * @date 2021-10-26 09:05
 * @version V1.0
 **/
@ApiModel(value="设备字典", description="设备字典信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_dict_device")
public class DictDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备字典ID")
    @TableId("device_dict_id")
    private String deviceDictId;

    @ApiModelProperty(value = "排序标识")
    @TableField("sort_id")
    private String sortId;

    @ApiModelProperty(value = "上级设备字典ID")
    @TableField("p_device_dict_id")
    private String pDeviceDictId;

    @ApiModelProperty(value = "设备类型ID")
    @TableField("device_type_id")
    private String deviceTypeId;


    @ApiModelProperty(value = "字典CODE")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "字典名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "字典别名")
    @TableField("alias_name")
    private String aliasName;

    @ApiModelProperty(value = "品种")
    @TableField("variety")
    private String variety;

    @ApiModelProperty(value = "计量单位")
    @TableField("unit")
    private String unit;

    @ApiModelProperty(value = "概要信息")
    @TableField("summary")
    private String summary;

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

    @ApiModelProperty(value = "数据ID")
    @TableField("id_old")
    private String idOld;

    @ApiModelProperty(value = "数据源")
    @TableField("id_source")
    private String idSource;
}
