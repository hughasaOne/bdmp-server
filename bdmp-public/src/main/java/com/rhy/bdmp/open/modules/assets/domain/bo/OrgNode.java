package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: yanggj
 * @Description: 机构节点(集团-运营公司-路段-设施)
 * @Date: 2021/9/28 14:28
 * @Version: 1.0.0
 */
@ApiModel(value = "机构节点", description = "机构节点")
@Data
public class OrgNode {

    @ApiModelProperty(value = "运营集团id-运营公司id-路段id-地理位置id")
    private String id;
    @ApiModelProperty(value = "运营集团名称-运营公司名称-路段名称-地理位置名称")
    private String name;
    @ApiModelProperty(value = "运营集团简称-运营公司简称-路段简称-地理位置简称")
    private String shortName;
    @ApiModelProperty(value = "节点类型（0，1，2，3）")
    private String nodeType;
    @ApiModelProperty(value = "资源树的父节点id")
    private String parentId;
    @ApiModelProperty(value = "Area_Id - Area_Id -ORI_WaySection_No- InfoNo")
    private String orgId;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "类型")
    private String type;

}
