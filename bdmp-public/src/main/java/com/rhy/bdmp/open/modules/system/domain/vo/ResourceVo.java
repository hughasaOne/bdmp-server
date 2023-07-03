package com.rhy.bdmp.open.modules.system.domain.vo;

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
 * @description 资源 实体
 * @author jiangzhimin
 * @date 2021-04-15 19:45
 * @version V1.0
 **/
@ApiModel(value="资源", description="资源信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ResourceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "资源ID")
    @TableId("resource_id")
    private String resourceId;

    @ApiModelProperty(value = "资源标题")
    @TableField("resource_title")
    private String resourceTitle;

    @ApiModelProperty(value = "上级ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "上级资源标题")
    private String parentTitle;

    @ApiModelProperty(value = "资源类型", example = "1")
    @TableField("resource_type")
    private Integer resourceType;

    @ApiModelProperty(value = "上级类型", example = "1")
    private Integer parentType;

    @ApiModelProperty(value = "应用ID")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty(value = "是否外链", example = "1")
    private Integer externalLink;

    @ApiModelProperty(value = "外链地址", example = "1")
    private Integer externalLinkUrl;

    @ApiModelProperty(value = "外链打开方式（_blank/_self/_parent/_top/framename）", example = "1")
    private Integer externalLinkOpen;

    @ApiModelProperty(value = "是否缓存", example = "1")
    private Integer cache;

    @ApiModelProperty(value = "是否可见", example = "1")
    private Integer hidden;

    @ApiModelProperty(value = "资源地址")
    private String path;

    @ApiModelProperty(value = "组件名称")
    private String componentName;

    @ApiModelProperty(value = "组件路径")
    private String componentPath;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "权限标识")
    private String permission;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;

    @TableField(exist = false)
    private Boolean hasChild;
}
