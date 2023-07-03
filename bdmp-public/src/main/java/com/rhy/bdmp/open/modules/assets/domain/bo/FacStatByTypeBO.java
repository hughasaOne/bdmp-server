package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: yanggj
 * @Description: 设施分类实体对象
 * @Date: 2021/10/25 14:53
 * @Version: 1.0.0
 */
@ApiModel(value = "设施分类实体对象", description = "设施分类实体对象")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacStatByTypeBO {

    @ApiModelProperty(value = "总数")
    private Integer total;
    @ApiModelProperty(value = "路段id")
    private String waysectionId;
    @ApiModelProperty(value = "路网编号")
    private String waysectionNo;
    @ApiModelProperty(value = "路段名称")
    private String waysectionName;
    @ApiModelProperty(value = "原路网编号")
    private String oriWaysectionNo;
    @ApiModelProperty(value = "设施类型编号")
    private String code;
    @ApiModelProperty(value = "设施类型名字")
    private String name;
    @ApiModelProperty(value = "设施id")
    private String facilitiesId;

    public FacStatByTypeBO(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
