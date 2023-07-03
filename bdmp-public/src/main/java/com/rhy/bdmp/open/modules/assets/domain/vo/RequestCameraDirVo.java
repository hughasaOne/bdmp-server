package com.rhy.bdmp.open.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description 视频资源请求参数实体
 * @date 2022-03-30 10:50
 **/
@ApiModel(value = "视频资源请求参数实体")
@Data
public class RequestCameraDirVo {

    @ApiModelProperty(value = "是否使用用户权限")
    private Boolean isUseUserPermissions;

    @ApiModelProperty(value = "节点id")
    private String orgId;

    @ApiModelProperty(value = "节点类型(1:运营公司,2:路段,3:设施),不传时查询集团及其下的所有运营公司")
    private Integer nodeType;



}