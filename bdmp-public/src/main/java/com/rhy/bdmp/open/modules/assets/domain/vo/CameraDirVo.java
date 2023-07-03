package com.rhy.bdmp.open.modules.assets.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description 摄像头资源目录
 * @author jiangzhimin
 * @date 2022-03-30 09:43
 * @version V1.0
 **/
@Data
public class CameraDirVo {

    @ApiModelProperty(value = "节点ID(运营集团、运营公司、路段、设施、设备)")
    private String id;

    @ApiModelProperty(value = "节点名称（运营集团、运营公司、路段、设施、设备）")
    private String name;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "节点类型(0:运营集团,1:运营公司,2:路段,3:设施,4:设备)")
    private Integer nodeType;

    @ApiModelProperty(value = "是否有孩子节点")
    private Boolean hasChild;


}