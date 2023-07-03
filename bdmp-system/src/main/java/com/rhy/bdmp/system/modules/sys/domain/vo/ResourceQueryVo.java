package com.rhy.bdmp.system.modules.sys.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@ApiModel(value = "资源树请求参数", description = "资源树接口请求参数")
@Getter
@Setter
public class ResourceQueryVo implements Serializable {

    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "资源类型集合")
    private Set<Integer> resourceTypes;
}
