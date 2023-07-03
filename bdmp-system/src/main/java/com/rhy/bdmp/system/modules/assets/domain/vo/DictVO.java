package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: DictVO
 * @Description:
 * @Author: 魏财富
 * @Date: 2021/5/8 11:43
 */
@ApiModel(value="字典VO", description="字典VO")
@Data
public class DictVO {
    @ApiModelProperty(value = "字典ID")
    private String dictId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "代码")
    private String code;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "字典内部父节点")
    private String innerParentId;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "节点类型（1:字典类型，2:字典）", example = "1")
    private Integer nodeType;

    @ApiModelProperty(value = "数据ID")
    private String idOld;

    @ApiModelProperty(value = "ID数据源")
    private String idSource;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "数据状态")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建人")
    private String createBy ;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "有无子节点")
    private Boolean hasChild;




}
