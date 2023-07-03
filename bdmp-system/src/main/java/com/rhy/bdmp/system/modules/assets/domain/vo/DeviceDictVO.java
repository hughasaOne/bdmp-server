package com.rhy.bdmp.system.modules.assets.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: yanggj
 * @Description: 设备字典查询vo
 * @Date: 2021/10/26 10:36
 * @Version: 1.0.0
 */
@ApiModel(value = "设备字典查询条件", description = "设备字典查询条件")
@Data
public class DeviceDictVO implements Serializable {

    @ApiModelProperty(value = "设备字典ID")
    private String deviceDictId;

    @ApiModelProperty(value = "字典CODE")
    private String code;

    @ApiModelProperty(value = "字典名称")
    private String name;

    @ApiModelProperty(value = "字典别名")
    private String aliasName;

}
