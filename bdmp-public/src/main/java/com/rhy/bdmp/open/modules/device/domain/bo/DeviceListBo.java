package com.rhy.bdmp.open.modules.device.domain.bo;

import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DeviceListBo extends CommonBo {
    private Set<String> systemIds;

    private List<String> deviceIds;

    private List<String> sns;

    @ApiModelProperty("1：设备类型（设备大类），2：设备字典（设备小类）,3: 固定设备")
    private Integer categoryType;

    private String direction;

    @ApiModelProperty("车道号")
    private Integer laneNum;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("范围,单位m")
    private Integer range;

    @ApiModelProperty("查询个数: 按距离排序后，可取到最近多少个的设备")
    private Integer limit;

    @ApiModelProperty("开始桩号")
    private Double beginPile;

    @ApiModelProperty("结束桩号")
    private Double endPile;
}
