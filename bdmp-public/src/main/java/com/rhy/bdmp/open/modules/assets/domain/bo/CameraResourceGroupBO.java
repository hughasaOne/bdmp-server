package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yanggj
 * @version V1.0
 * @description 视频资源 实体
 * @date 2021-10-20 15:31
 **/
@ApiModel(value = "视频资源组", description = "视频资源组（云台 + 腾路 + 上云）")
@Data
public class CameraResourceGroupBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "云台视频资源列表")
    private List<CameraResourceBO> cameraYtList;

    @ApiModelProperty(value = "腾路视频资源列表")
    private List<CameraResourceBO> cameraTlList;

    @ApiModelProperty(value = "腾路视频资源列表")
    private List<CameraResourceBO> cameraSyList;

}
