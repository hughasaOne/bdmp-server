package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @description 路线 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="路线", description="路线信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_route")
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路线ID")
    @TableId("route_id")
    private String routeId;

    @ApiModelProperty(value = "管理单位代码")
    @TableField("manage_code")
    private String manageCode;

    @ApiModelProperty(value = "管理单位名称")
    @TableField("manage_name")
    private String manageName;

    @ApiModelProperty(value = "路线代码")
    @TableField("route_code")
    private String routeCode;

    @ApiModelProperty(value = "行政等级代码")
    @TableField("barrio_code")
    private String barrioCode;

    @ApiModelProperty(value = "行政等级名称")
    @TableField("barrio_name")
    private String barrioName;

    @ApiModelProperty(value = "路线简称")
    @TableField("route_s_name")
    private String routeSName;

    @ApiModelProperty(value = "填报单位名称")
    @TableField("fill_unit")
    private String fillUnit;

    @ApiModelProperty(value = "路线全称")
    @TableField("route_name")
    private String routeName;

    @ApiModelProperty(value = "路线性质代码")
    @TableField("route_nature_code")
    private String routeNatureCode;

    @ApiModelProperty(value = "路线性质")
    @TableField("route_nature_name")
    private String routeNatureName;

    @ApiModelProperty(value = "管养单位名称")
    @TableField("maintenance_unit")
    private String maintenanceUnit;

    @ApiModelProperty(value = "管养单位性质代码")
    @TableField("maintenance_nature_code")
    private String maintenanceNatureCode;

    @ApiModelProperty(value = "管养单位性质")
    @TableField("maintenance_nature_name")
    private String maintenanceNatureName;

    @ApiModelProperty(value = "验收日期")
    @TableField("acceptance_date")
    private String acceptanceDate;

    @ApiModelProperty(value = "通车日期")
    @TableField("to_date")
    private String toDate;

    @ApiModelProperty(value = "路线用途代码")
    @TableField("route_use_code")
    private String routeUseCode;

    @ApiModelProperty(value = "路线用途")
    @TableField("route_use_name")
    private String routeUseName;

    @ApiModelProperty(value = "途径行政区域代码")
    @TableField("route_admin_code")
    private String routeAdminCode;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "路线起点桩号")
    @TableField("start_route_stake")
    private String startRouteStake;

    @ApiModelProperty(value = "路线名称起点")
    @TableField("start_route_name")
    private String startRouteName;

    @ApiModelProperty(value = "起点是否为分界点代码")
    @TableField("start_is_boundary_point_code")
    private String startIsBoundaryPointCode;

    @ApiModelProperty(value = "起点是否为分界点")
    @TableField("start_is_boundary_point_name")
    private String startIsBoundaryPointName;

    @ApiModelProperty(value = "起点分界点类别代码")
    @TableField("start_boundary_point_type_code")
    private String startBoundaryPointTypeCode;

    @ApiModelProperty(value = "起点分界点类别")
    @TableField("start_boundary_point_type_name")
    private String startBoundaryPointTypeName;

    @ApiModelProperty(value = "起点经度")
    @TableField("start_longitude")
    private String startLongitude;

    @ApiModelProperty(value = "起点纬度")
    @TableField("start_latitude")
    private String startLatitude;

    @ApiModelProperty(value = "路线终点桩号")
    @TableField("end_route_stake")
    private String endRouteStake;

    @ApiModelProperty(value = "路线名称终点")
    @TableField("end_route_name")
    private String endRouteName;

    @ApiModelProperty(value = "止点是否为分界点代码")
    @TableField("end_is_boundary_point_code")
    private String endIsBoundaryPointCode;

    @ApiModelProperty(value = "止点是否为分界点")
    @TableField("end_is_boundary_point_name")
    private String endIsBoundaryPointName;

    @ApiModelProperty(value = "止点分界点类别代码")
    @TableField("end_boundary_point_type_code")
    private String endBoundaryPointTypeCode;

    @ApiModelProperty(value = "止点分界点类别")
    @TableField("end_boundary_point_type_name")
    private String endBoundaryPointTypeName;

    @ApiModelProperty(value = "迄点经度")
    @TableField("end_longitude")
    private String endLongitude;

    @ApiModelProperty(value = "迄点纬度")
    @TableField("end_latitude")
    private String endLatitude;

    @ApiModelProperty(value = "断链桩号")
    @TableField("break_chain_stake")
    private String breakChainStake;

    @ApiModelProperty(value = "断链值(公里)")
    @TableField("break_chain_distance")
    private String breakChainDistance;

    @ApiModelProperty(value = "原路线代码")
    @TableField("original_route_code")
    private String originalRouteCode;

    @ApiModelProperty(value = "路线调整代码")
    @TableField("route_adjust_code")
    private String routeAdjustCode;

    @ApiModelProperty(value = "路线调整情况")
    @TableField("route_adjust_name")
    private String routeAdjustName;

    @ApiModelProperty(value = "原路线简称")
    @TableField("original_route_s_name")
    private String originalRouteSName;

    @ApiModelProperty(value = "原路线起点桩号")
    @TableField("original_start_stake")
    private String originalStartStake;

    @ApiModelProperty(value = "原路线止点桩号")
    @TableField("original_end_stake")
    private String originalEndStake;

    @ApiModelProperty(value = "运营路段ID")
    @TableField("operating_waysection_id")
    private String operatingWaysectionId;

    @ApiModelProperty(value = "养护里程")
    @TableField("maintenance_mileage")
    private BigDecimal maintenanceMileage;

    @ApiModelProperty(value = "数据ID")
    @TableField("id_old")
    private String idOld;

    @ApiModelProperty(value = "数据源")
    @TableField("id_source")
    private String idSource;


}
