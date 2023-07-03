package com.rhy.bdmp.open.modules.fac.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseFacVo {

    @ApiModelProperty(value = "设施ID")
    private String facilitiesId;

    @ApiModelProperty(value = "设施名称")
    private String facilitiesName;

    @ApiModelProperty(value = "设施代码")
    private String facilitiesCode;

    @ApiModelProperty(value = "设施类型")
    private String facilitiesType;

    @ApiModelProperty(value = "所属路段ID")
    private String waysectionId;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "设施位置(路段名/设施名称)")
    private String location;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "设施图片")
    private String facImg;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    private String direction;

    @ApiModelProperty(value = "是否省界门架(0:否、1:是)", example = "1")
    private Integer isProvinceDoorFrame;

    @ApiModelProperty(value = "中心桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "设施ID(旧)")
    private String facilitiesIdOld;

    @ApiModelProperty(value = "运营公司ID")
    private String companyId;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

    @ApiModelProperty(value = "是否OTN站点")
    private String isOtnSite;

    @ApiModelProperty(value = "国干站点类型")
    private String nationalSiteType;

    @ApiModelProperty(value = "省干站点类型")
    private String provincialSiteType;

    @ApiModelProperty(value = "站点类型")
    private String siteCategory;

    @ApiModelProperty(value = "机房形式")
    private String computerRoomForm;

    @ApiModelProperty(value = "otn备注")
    private String otnRemark;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "设施类型名称")
    private String facTypeName;

    @ApiModelProperty(value = "布置形式")
    private String layoutForm;
}
