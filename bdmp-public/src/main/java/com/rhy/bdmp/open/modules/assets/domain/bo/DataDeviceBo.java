package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 数据设备bo
 * @author weicaifu
 */
@Data
public class DataDeviceBo {
    @ApiModelProperty(value = "是否带用户权限")
    private Boolean isUseUserPermissions;

    @ApiModelProperty(value = "运营公司ID或路段ID 或 地理位置ID(收费站 服务区 等)参数为空 则选择用户有权限的位置下所有设备总况信息")
    private String orgId;

    @ApiModelProperty(value = "节点类型")
    private String nodeType;

    @ApiModelProperty(value = "设备字典id")
    private String deviceDictId;

    @ApiModelProperty(value = "设备类型集合")
    private List<String> deviceTypes;

    @NotNull(message = "当前页不为空")
    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @NotNull(message = "页大小不为空")
    @ApiModelProperty(value = "页大小")
    private Integer pageSize;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;
}
