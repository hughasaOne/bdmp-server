package com.rhy.bdmp.system.modules.analysis.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 机构下的用户树形VO
 *
 * @author PSQ
 */
@Data
public class OrgUserVO {

    @ApiModelProperty("机构id或用户id")
    private String id;

    @ApiModelProperty("父id")
    private String pid;

    @ApiModelProperty("名称")
    private String label;

    @ApiModelProperty("数据状态是否有效")
    private Boolean disabled;

    @ApiModelProperty("是否为用户数据")
    private Boolean isUser;

    @ApiModelProperty("排序序号")
    private Integer sort;

    @ApiModelProperty("子对象")
    private List<OrgUserVO> children;

}
