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
 * @description 设备分类 实体
 * @author weicaifu
 * @date 2022-10-17 10:45
 * @version V1.0
 **/
@ApiModel(value="设备分类", description="设备分类信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_device_category")
public class DeviceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId("id")
    private String id;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "父节点")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "分类类型（1：设备类型，2：设备字典，3：固定设备）")
    @TableField("category_type")
    private Integer categoryType;

    @ApiModelProperty(value = "分类规则（JSON）")
    @TableField("category_rule")
    private String categoryRule;

    @ApiModelProperty(value = "分类code")
    @TableField("category_code")
    private String categoryCode;

    @ApiModelProperty(value = "节点类型（1：目录，2：项目，3：主题，4：分类）", example = "1")
    @TableField("node_type")
    private Integer nodeType;

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
