package com.rhy.bdmp.portal.modules.waitdo.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
* @description 待办信息
* @author jiangzhimin
* @date 2021-01-27 09:38
* @version V1.0
**/
@ApiModel(value="待办信息", description="待办信息")
@Data
@NoArgsConstructor
public class WaitDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "待办ID")
    private String id;

    @ApiModelProperty(value = "待办访问URL")
    private String url;

    @ApiModelProperty(value = "待办主题")
    private String title;

    @ApiModelProperty(value = "待办类型")
    private String typeName;

    @ApiModelProperty(value = "所属应用")
    private String appName;

    @ApiModelProperty(value = "发送者")
    private String sendName;

    @ApiModelProperty(value = "发送时间")
    private String sendTime;

    @ApiModelProperty(value = "应用Token")
    private String appToken;

}