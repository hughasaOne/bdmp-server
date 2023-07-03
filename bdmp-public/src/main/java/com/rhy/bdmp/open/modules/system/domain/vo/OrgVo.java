package com.rhy.bdmp.open.modules.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 组织机构vo
 **/
@Data
public class OrgVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织代码")
    private String orgCode;

    @ApiModelProperty(value = "组织简称")
    private String orgShortName;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "组织类型")
    private String orgType;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "关联地图")
    private String nodeId;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;

    @ApiModelProperty(value = "用户列表", example = "1")
    private List<UserVo> userList;
}
