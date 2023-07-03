package com.rhy.bdmp.open.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 应用目录vo
 **/
@ApiModel(value = "应用信息", description = "应用信息信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AppDirVo {
    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "应用入口")
    private String appUrl;

    @ApiModelProperty(value = "应用入口类型(1:前端，2:后台)")
    private Integer appUrlType;

    @ApiModelProperty(value = "应用图标")
    private String appIcon;

    @ApiModelProperty(value = "应用图片")
    private String appImg;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "应用分类ID")
    private String appTypeId;

    @ApiModelProperty(value = "应用待办获取URL")
    private String waitDoUrl;

    @ApiModelProperty(value = "排序", example = "1")
    private Integer sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "是否接入统一认证中心（0：不接入，1：接入）", example = "1")
    private Integer isJoinAuthCenter;
}
