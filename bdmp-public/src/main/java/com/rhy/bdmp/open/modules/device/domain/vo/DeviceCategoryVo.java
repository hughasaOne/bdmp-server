package com.rhy.bdmp.open.modules.device.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description 设备分类 实体
 * @author weicaifu
 * @date 2022-10-17 10:45
 * @version V1.0
 **/
@ApiModel(value="设备分类", description="设备分类信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceCategoryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "父节点")
    private String parentId;

    @ApiModelProperty(value = "分类类型（1：设备类型，2：设备字典）")
    private Integer categoryType;

    @ApiModelProperty(value = "分类规则（JSON）")
    private String categoryRule;

    @ApiModelProperty(value = "分类code")
    private String categoryCode;

    @ApiModelProperty(value = "节点类型（1：目录，2：项目，3：主题，4：分类）", example = "1")
    private Integer nodeType;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;
}
