package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @description 路段 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="路段", description="路段信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_route_road")
public class RouteRoad implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路段ID")
    @TableId("road_id")
    private String roadId;

    @ApiModelProperty(value = "管理单位代码")
    @TableField("manage_code")
    private String manageCode;

    @ApiModelProperty(value = "管理单位名称")
    @TableField("manage_name")
    private String manageName;

    @ApiModelProperty(value = "路线代码")
    @TableField("route_code")
    private String routeCode;

    @ApiModelProperty(value = "路线简称")
    @TableField("route_s_name")
    private String routeSName;

    @ApiModelProperty(value = "路线地方名称")
    @TableField("route_native_name")
    private String routeNativeName;

    @ApiModelProperty(value = "管养单位所属行业类别代码")
    @TableField("maintenance_industry_code")
    private String maintenanceIndustryCode;

    @ApiModelProperty(value = "管养单位所属行业类别名称")
    @TableField("maintenance_industry_name")
    private String maintenanceIndustryName;

    @ApiModelProperty(value = "管养单位名称")
    @TableField("maintenance_name")
    private String maintenanceName;

    @ApiModelProperty(value = "路段桩号起点")
    @TableField("start_road_stake")
    private String startRoadStake;

    @ApiModelProperty(value = "路段桩号终点")
    @TableField("end_road_stake")
    private String endRoadStake;

    @ApiModelProperty(value = "路段名称起点")
    @TableField("start_road_name")
    private String startRoadName;

    @ApiModelProperty(value = "路段名称止点")
    @TableField("end_road_name")
    private String endRoadName;

    @ApiModelProperty(value = "路段编号")
    @TableField("road_no")
    private String roadNo;

    @ApiModelProperty(value = "里程(公里)")
    @TableField("road_mileage")
    private String roadMileage;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("admin_region_code")
    private String adminRegionCode;

    @ApiModelProperty(value = "行政区划名称")
    @TableField("admin_region_name")
    private String adminRegionName;

    @ApiModelProperty(value = "亚洲公路网编号")
    @TableField("asia_highway_no")
    private String asiaHighwayNo;

    @ApiModelProperty(value = "断链类型代码")
    @TableField("break_chain_type_code")
    private String breakChainTypeCode;

    @ApiModelProperty(value = "断链类型名称")
    @TableField("break_chain_type_name")
    private String breakChainTypeName;

    @ApiModelProperty(value = "省内顺序号")
    @TableField("province_inner_sort")
    private String provinceInnerSort;

    @ApiModelProperty(value = "省际出入口标识代码")
    @TableField("province_in_out_id")
    private String provinceInOutId;

    @ApiModelProperty(value = "省际出入口标识名称")
    @TableField("province_in_out_name")
    private String provinceInOutName;

    @ApiModelProperty(value = "起点是否为分界点代码")
    @TableField("is_start_boundary_code")
    private String isStartBoundaryCode;

    @ApiModelProperty(value = "起点是否为分界点名称")
    @TableField("is_start_boundary_name")
    private String isStartBoundaryName;

    @ApiModelProperty(value = "起点分界点类别代码")
    @TableField("start_boundary_type_code")
    private String startBoundaryTypeCode;

    @ApiModelProperty(value = "起点分界点类别名称")
    @TableField("start_boundary_type_name")
    private String startBoundaryTypeName;

    @ApiModelProperty(value = "起点经度")
    @TableField("start_longitude")
    private String startLongitude;

    @ApiModelProperty(value = "起点纬度")
    @TableField("start_latitude")
    private String startLatitude;

    @ApiModelProperty(value = "止点是否为分界点代码")
    @TableField("is_end_boundary_code")
    private String isEndBoundaryCode;

    @ApiModelProperty(value = "止点是否为分界点名称")
    @TableField("is_end_boundary_name")
    private String isEndBoundaryName;

    @ApiModelProperty(value = "止点分界点类别代码")
    @TableField("end_boundary_type_code")
    private String endBoundaryTypeCode;

    @ApiModelProperty(value = "止点分界点类别名称")
    @TableField("end_boundary_type_name")
    private String endBoundaryTypeName;

    @ApiModelProperty(value = "止点经度")
    @TableField("end_longitude")
    private String endLongitude;

    @ApiModelProperty(value = "止点纬度")
    @TableField("end_latitude")
    private String endLatitude;

    @ApiModelProperty(value = "乡镇名称")
    @TableField("towns_name")
    private String townsName;

    @ApiModelProperty(value = "路段收费性质代码")
    @TableField("road_toll_nature_code")
    private String roadTollNatureCode;

    @ApiModelProperty(value = "路段收费性质名称")
    @TableField("road_toll_nature_name")
    private String roadTollNatureName;

    @ApiModelProperty(value = "是否城管路段代码")
    @TableField("is_cg_road_code")
    private String isCgRoadCode;

    @ApiModelProperty(value = "是否城管路段名称")
    @TableField("is_cg_road_name")
    private String isCgRoadName;

    @ApiModelProperty(value = "是否断头路路段代码")
    @TableField("is_break_road_code")
    private String isBreakRoadCode;

    @ApiModelProperty(value = "是否断头路路段名称")
    @TableField("is_break_road_name")
    private String isBreakRoadName;

    @ApiModelProperty(value = "是否一幅高速代码")
    @TableField("is_a_highway_code")
    private String isAHighwayCode;

    @ApiModelProperty(value = "是否一幅高速名称")
    @TableField("is_a_highway_name")
    private String isAHighwayName;

    @ApiModelProperty(value = "技术等级代码")
    @TableField("technical_grade_code")
    private String technicalGradeCode;

    @ApiModelProperty(value = "技术等级名称")
    @TableField("technical_grade_name")
    private String technicalGradeName;

    @ApiModelProperty(value = "车道分类代码")
    @TableField("lane_type_code")
    private String laneTypeCode;

    @ApiModelProperty(value = "车道分类名称")
    @TableField("lane_type_name")
    private String laneTypeName;

    @ApiModelProperty(value = "面层类型代码")
    @TableField("surface_layer_type_code")
    private String surfaceLayerTypeCode;

    @ApiModelProperty(value = "面层类型名称")
    @TableField("surface_layer_type_name")
    private String surfaceLayerTypeName;

    @ApiModelProperty(value = "面层厚度(厘米)")
    @TableField("surface_layer_hight")
    private String surfaceLayerHight;

    @ApiModelProperty(value = "路基宽度(米)")
    @TableField("subgrade_width")
    private String subgradeWidth;

    @ApiModelProperty(value = "路面宽度(米)")
    @TableField("road_surface_width")
    private String roadSurfaceWidth;

    @ApiModelProperty(value = "设计车速")
    @TableField("design_speed")
    private String designSpeed;

    @ApiModelProperty(value = "设计小时交通量")
    @TableField("design_hour_traffic")
    private String designHourTraffic;

    @ApiModelProperty(value = "地貌代码")
    @TableField("landscape_code")
    private String landscapeCode;

    @ApiModelProperty(value = "地貌名称")
    @TableField("landscape_name")
    private String landscapeName;

    @ApiModelProperty(value = "最大纵坡")
    @TableField("max_slope")
    private String maxSlope;

    @ApiModelProperty(value = "圆曲线最小半径(米)")
    @TableField("min_circle_radius")
    private String minCircleRadius;

    @ApiModelProperty(value = "养护里程(公里)")
    @TableField("maintenance_mileage")
    private String maintenanceMileage;

    @ApiModelProperty(value = "养护类型代码(按时间分)")
    @TableField("maintenance_type_code_by_time")
    private String maintenanceTypeCodeByTime;

    @ApiModelProperty(value = "养护类型名称(按时间分)")
    @TableField("maintenance_type_name_by_time")
    private String maintenanceTypeNameByTime;

    @ApiModelProperty(value = "养护类型代码(按资金来源分)")
    @TableField("maintenance_type_code_by_money")
    private String maintenanceTypeCodeByMoney;

    @ApiModelProperty(value = "养护类型名称(按资金来源分)")
    @TableField("maintenance_type_name_by_money")
    private String maintenanceTypeNameByMoney;

    @ApiModelProperty(value = "是否晴雨通车代码")
    @TableField("is_sr_traffic_code")
    private String isSrTrafficCode;

    @ApiModelProperty(value = "是否晴雨通车名称")
    @TableField("is_sr_traffic_name")
    private String isSrTrafficName;

    @ApiModelProperty(value = "可绿化里程(公里)")
    @TableField("can_greening_mileage")
    private String canGreeningMileage;

    @ApiModelProperty(value = "已绿化里程(公里)")
    @TableField("has_greening_mileage")
    private String hasGreeningMileage;

    @ApiModelProperty(value = "实施GBM里程(公里)")
    @TableField("gbm_effect_mileage")
    private String gbmEffectMileage;

    @ApiModelProperty(value = "GBM建设性质代码")
    @TableField("gbm_nature_code")
    private String gbmNatureCode;

    @ApiModelProperty(value = "GBM建设性质名称")
    @TableField("gbm_nature_name")
    private String gbmNatureName;

    @ApiModelProperty(value = "已实施GBM起点桩号")
    @TableField("has_gbm_start_stake")
    private String hasGbmStartStake;

    @ApiModelProperty(value = "已实施GBM止点桩号")
    @TableField("has_gbm_end_stake")
    private String hasGbmEndStake;

    @ApiModelProperty(value = "已实施文明样板路里程(公里)")
    @TableField("has_civilization_model_mileage")
    private String hasCivilizationModelMileage;

    @ApiModelProperty(value = "文明样板路起点桩号")
    @TableField("civilization_model_start_stake")
    private String civilizationModelStartStake;

    @ApiModelProperty(value = "文明样板路止点桩号")
    @TableField("civilization_model_end_stake")
    private String civilizationModelEndStake;

    @ApiModelProperty(value = "涵洞道")
    @TableField("culvert_lane")
    private String culvertLane;

    @ApiModelProperty(value = "涵洞米")
    @TableField("culvert_meter")
    private String culvertMeter;

    @ApiModelProperty(value = "漫水工程名称")
    @TableField("water_project_name")
    private String waterProjectName;

    @ApiModelProperty(value = "漫水工程(处)")
    @TableField("water_project_place")
    private String waterProjectPlace;

    @ApiModelProperty(value = "路线代码(重复路段)")
    @TableField("re_route_code")
    private String reRouteCode;

    @ApiModelProperty(value = "管理单位代码(重复路段)")
    @TableField("re_manage_code")
    private String reManageCode;

    @ApiModelProperty(value = "管理单位名称(重复路段)")
    @TableField("re_manage_name")
    private String reManageName;

    @ApiModelProperty(value = "起点桩号(重复路段)")
    @TableField("re_start_stake")
    private String reStartStake;

    @ApiModelProperty(value = "止点桩号(重复路段)")
    @TableField("re_end_stake")
    private String reEndStake;

    @ApiModelProperty(value = "修建年度")
    @TableField("build_year")
    private String buildYear;

    @ApiModelProperty(value = "改建年度")
    @TableField("reconstruction_year")
    private String reconstructionYear;

    @ApiModelProperty(value = "开工日期")
    @TableField("starts_date")
    private String startsDate;

    @ApiModelProperty(value = "竣工日期")
    @TableField("completion_date")
    private String completionDate;

    @ApiModelProperty(value = "通车日期")
    @TableField("to_date")
    private String toDate;

    @ApiModelProperty(value = "最近一次修复养护年度")
    @TableField("lately_repair_year")
    private String latelyRepairYear;

    @ApiModelProperty(value = "改建变更日期")
    @TableField("rebuild_date")
    private String rebuildDate;

    @ApiModelProperty(value = "改建变更原因代码")
    @TableField("rebuild_reason_code")
    private String rebuildReasonCode;

    @ApiModelProperty(value = "改建变更原因名称")
    @TableField("rebuild_reason_name")
    private String rebuildReasonName;

    @ApiModelProperty(value = "变更原因说明")
    @TableField("rebuild_reason_desc")
    private String rebuildReasonDesc;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "匝道方向代码")
    @TableField("ramp_code")
    private String rampCode;

    @ApiModelProperty(value = "匝道方向名称")
    @TableField("ramp_name")
    private String rampName;

    @ApiModelProperty(value = "连接主线编码")
    @TableField("cml_no")
    private String cmlNo;

    @ApiModelProperty(value = "连接主线桩号")
    @TableField("cml_stake")
    private String cmlStake;

    @ApiModelProperty(value = "项目类型代码")
    @TableField("project_type_code")
    private String projectTypeCode;

    @ApiModelProperty(value = "项目类型名称")
    @TableField("project_type_name")
    private String projectTypeName;

    @ApiModelProperty(value = "计划项目唯一编码")
    @TableField("plan_project_id")
    private String planProjectId;

    @ApiModelProperty(value = "计划项目路线编码")
    @TableField("plan_project_route_code")
    private String planProjectRouteCode;

    @ApiModelProperty(value = "计划项目路线名称")
    @TableField("plan_project_route_name")
    private String planProjectRouteName;

    @ApiModelProperty(value = "是否专用通达路线代码(国有农、林场)")
    @TableField("is_special_road_code")
    private String isSpecialRoadCode;

    @ApiModelProperty(value = "是否专用通达路线名称(国有农、林场)")
    @TableField("is_special_road_name")
    private String isSpecialRoadName;

    @ApiModelProperty(value = "专用通达路线编码(国有农、林场)")
    @TableField("special_road_code")
    private String specialRoadCode;

    @ApiModelProperty(value = "专用通达路线名称(国有农、林场)")
    @TableField("special_road_name")
    private String specialRoadName;

    @ApiModelProperty(value = "GBM计划里程")
    @TableField("gbm_plan_mileage")
    private String gbmPlanMileage;

    @ApiModelProperty(value = "GBM计划投资")
    @TableField("gbm_plan_investment")
    private String gbmPlanInvestment;

    @ApiModelProperty(value = "GBM完成里程")
    @TableField("gbm_complete_mileage")
    private String gbmCompleteMileage;

    @ApiModelProperty(value = "GBM完成投资")
    @TableField("gbm_complete_investment")
    private String gbmCompleteInvestment;

    @ApiModelProperty(value = "列养总里程")
    @TableField("am_mileage")
    private String amMileage;

    @ApiModelProperty(value = "列养路段起点桩号")
    @TableField("am_start_stake")
    private String amStartStake;

    @ApiModelProperty(value = "列养路段止点桩号")
    @TableField("am_end_stake")
    private String amEndStake;

    @ApiModelProperty(value = "列养里程可绿化里程")
    @TableField("am_can_greening_mileage")
    private String amCanGreeningMileage;

    @ApiModelProperty(value = "列养里程已绿化里程")
    @TableField("am_has_greening_mileage")
    private String amHasGreeningMileage;

    @ApiModelProperty(value = "补助养护里程")
    @TableField("subsidies_maintenance_mileage")
    private String subsidiesMaintenanceMileage;

    @ApiModelProperty(value = "水泥路上年底里程(公里)")
    @TableField("cr_lately_year_mileage")
    private String crLatelyYearMileage;

    @ApiModelProperty(value = "水泥路上年底投资(万元)")
    @TableField("cr_lately_year_investment")
    private String crLatelyYearInvestment;

    @ApiModelProperty(value = "水泥路本年投资(万元)")
    @TableField("cr_native_year_investment")
    private String crNativeYearInvestment;

    @ApiModelProperty(value = "水泥路本年里程(公里)")
    @TableField("cr_native_year_mileage")
    private String crNativeYearMileage;

    @ApiModelProperty(value = "原管理单位代码")
    @TableField("original_manage_code")
    private String originalManageCode;

    @ApiModelProperty(value = "原管理单位名称")
    @TableField("original_manage_name")
    private String originalManageName;

    @ApiModelProperty(value = "原路线代码")
    @TableField("original_route_code")
    private String originalRouteCode;

    @ApiModelProperty(value = "原操作顺序代码")
    @TableField("original_operation_sort_code")
    private String originalOperationSortCode;

    @ApiModelProperty(value = "原操作顺序名称")
    @TableField("original_operation_sort_name")
    private String originalOperationSortName;

    @ApiModelProperty(value = "平移值")
    @TableField("translation_value")
    private String translationValue;

    @ApiModelProperty(value = "原路网调整类型代码")
    @TableField("original_highway_adjust_code")
    private String originalHighwayAdjustCode;

    @ApiModelProperty(value = "原路网调整类型名称")
    @TableField("original_highway_adjust_name")
    private String originalHighwayAdjustName;

    @ApiModelProperty(value = "是否按干线公路管理接养代码")
    @TableField("is_main_highway_maintenance_code")
    private String isMainHighwayMaintenanceCode;

    @ApiModelProperty(value = "是否按干线公路管理接养名称")
    @TableField("is_main_highway_maintenance_name")
    private String isMainHighwayMaintenanceName;

    @ApiModelProperty(value = "路网调整前管理单位代码")
    @TableField("highway_adjust_before_manage_code")
    private String highwayAdjustBeforeManageCode;

    @ApiModelProperty(value = "路网调整前管理单位名称")
    @TableField("highway_adjust_before_manage_name")
    private String highwayAdjustBeforeManageName;

    @ApiModelProperty(value = "路网调整说明")
    @TableField("highway_adjust_desc")
    private String highwayAdjustDesc;

    @ApiModelProperty(value = "国省道桩号传递预留里程")
    @TableField("reserved_mileage")
    private String reservedMileage;

    @ApiModelProperty(value = "路网调整前路段起点桩号")
    @TableField("highway_adjust_before_start_stake")
    private String highwayAdjustBeforeStartStake;

    @ApiModelProperty(value = "路网调整前路线代码")
    @TableField("highway_adjust_before_route_code")
    private String highwayAdjustBeforeRouteCode;

    @ApiModelProperty(value = "年均日交通量(辆/日)")
    @TableField("year_avg__day_traffic")
    private String yearAvgDayTraffic;

    @ApiModelProperty(value = "唯一ID(历史)")
    @TableField("history_id")
    private String historyId;

    @ApiModelProperty(value = "是否桩号反向重排代码(历史)")
    @TableField("history_is_reverse_stake_code")
    private String historyIsReverseStakeCode;

    @ApiModelProperty(value = "是否桩号反向重排名称(历史)")
    @TableField("history_is_reverse_stake_name")
    private String historyIsReverseStakeName;

    @ApiModelProperty(value = "反排路线里程(历史)")
    @TableField("history_reverse_route_mileage")
    private String historyReverseRouteMileage;

    @ApiModelProperty(value = "是否删除代码(历史)")
    @TableField("history_is_del_code")
    private String historyIsDelCode;

    @ApiModelProperty(value = "是否删除名称(历史)")
    @TableField("history_is_del_name")
    private String historyIsDelName;

    @ApiModelProperty(value = "标识编码")
    @TableField("id_code")
    private String idCode;

    @ApiModelProperty(value = "上年路线编码")
    @TableField("lately_year_route_code")
    private String latelyYearRouteCode;

    @ApiModelProperty(value = "上年起点桩号")
    @TableField("lately_year_start_stake")
    private String latelyYearStartStake;

    @ApiModelProperty(value = "上年止点桩号")
    @TableField("lately_year_end_stake")
    private String latelyYearEndStake;

    @ApiModelProperty(value = "运营路段")
    @TableField("operating_waysection")
    private String operatingWaysection;

    @ApiModelProperty(value = "路线ID")
    @TableField("route_id")
    private String routeId;
}
