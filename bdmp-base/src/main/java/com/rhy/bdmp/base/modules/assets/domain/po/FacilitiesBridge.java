package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description 桥梁 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="桥梁", description="桥梁信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_facilities_bridge")
public class FacilitiesBridge extends FacilitiesExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "桥梁ID")
    @TableId("bridge_id")
    private String bridgeId;

    @ApiModelProperty(value = "管理单位代码")
    @TableField("manage_code")
    private String manageCode;

    @ApiModelProperty(value = "管理单位名称")
    @TableField("manage_name")
    private String manageName;

    @ApiModelProperty(value = "路线代码")
    @TableField("route_code")
    private String routeCode;

    @ApiModelProperty(value = "桥梁编号")
    @TableField("bridge_no")
    private String bridgeNo;

    @ApiModelProperty(value = "路线简称")
    @TableField("route_s_name")
    private String routeSName;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("admin_region_code")
    private String adminRegionCode;

    @ApiModelProperty(value = "行政区划名称")
    @TableField("admin_region_name")
    private String adminRegionName;

    @ApiModelProperty(value = "桥梁中心桩号")
    @TableField("bridge_center_stake")
    private String bridgeCenterStake;

    @ApiModelProperty(value = "桥梁名称")
    @TableField("bridge_name")
    private String bridgeName;

    @ApiModelProperty(value = "桥梁所在地点")
    @TableField("bridge_place")
    private String bridgePlace;

    @ApiModelProperty(value = "收费性质代码")
    @TableField("toll_nature_code")
    private String tollNatureCode;

    @ApiModelProperty(value = "收费性质名称")
    @TableField("toll_nature_name")
    private String tollNatureName;

    @ApiModelProperty(value = "桥梁起点桩号")
    @TableField("bridge_start_stake")
    private String bridgeStartStake;

    @ApiModelProperty(value = "桥梁止点桩号")
    @TableField("bridge_end_stake")
    private String bridgeEndStake;

    @ApiModelProperty(value = "管养单位所属行业类别代码")
    @TableField("maintenance_industry_code")
    private String maintenanceIndustryCode;

    @ApiModelProperty(value = "管养单位所属行业类别名称")
    @TableField("maintenance_industry_name")
    private String maintenanceIndustryName;

    @ApiModelProperty(value = "跨越地物类型代码")
    @TableField("across_place_type_code")
    private String acrossPlaceTypeCode;

    @ApiModelProperty(value = "跨越地物类型名称")
    @TableField("across_place_type_name")
    private String acrossPlaceTypeName;

    @ApiModelProperty(value = "跨越地物名称")
    @TableField("across_place_name")
    private String acrossPlaceName;

    @ApiModelProperty(value = "按用途分类代码")
    @TableField("use_type_code")
    private String useTypeCode;

    @ApiModelProperty(value = "按用途分类名称")
    @TableField("use_type_name")
    private String useTypeName;

    @ApiModelProperty(value = "按使用年限分代码")
    @TableField("use_year_type_code")
    private String useYearTypeCode;

    @ApiModelProperty(value = "按使用年限分名称")
    @TableField("use_year_type_name")
    private String useYearTypeName;

    @ApiModelProperty(value = "桥梁跨径分类代码")
    @TableField("across_radius_type_code")
    private String acrossRadiusTypeCode;

    @ApiModelProperty(value = "桥梁跨径分类名称")
    @TableField("across_radius_type_name")
    private String acrossRadiusTypeName;

    @ApiModelProperty(value = "技术状况评定代码")
    @TableField("technology_evaluate_code")
    private String technologyEvaluateCode;

    @ApiModelProperty(value = "技术状况评定名称")
    @TableField("technology_evaluate_name")
    private String technologyEvaluateName;

    @ApiModelProperty(value = "技术状况评定单位")
    @TableField("technology_evaluate_unit")
    private String technologyEvaluateUnit;

    @ApiModelProperty(value = "评定日期")
    @TableField("evaluate_date")
    private String evaluateDate;

    @ApiModelProperty(value = "最近定期检查日期")
    @TableField("lately_check_date")
    private String latelyCheckDate;

    @ApiModelProperty(value = "主要病害位置代码")
    @TableField("main_disease_code")
    private String mainDiseaseCode;

    @ApiModelProperty(value = "主要病害位置名称")
    @TableField("main_disease_name")
    private String mainDiseaseName;

    @ApiModelProperty(value = "主要病害位置描述")
    @TableField("main_disease_desc")
    private String mainDiseaseDesc;

    @ApiModelProperty(value = "是否重复代码")
    @TableField("is_repeat_code")
    private String isRepeatCode;

    @ApiModelProperty(value = "是否重复名称")
    @TableField("is_repeat_name")
    private String isRepeatName;

    @ApiModelProperty(value = "是否本年新增危桥代码")
    @TableField("is_native_year_unsafe_bridge_code")
    private String isNativeYearUnsafeBridgeCode;

    @ApiModelProperty(value = "是否本年新增危桥名称")
    @TableField("is_native_year_unsafe_bridge_name")
    private String isNativeYearUnsafeBridgeName;

    @ApiModelProperty(value = "是否列养代码")
    @TableField("is_am_code")
    private String isAmCode;

    @ApiModelProperty(value = "是否列养名称")
    @TableField("is_am_name")
    private String isAmName;

    @ApiModelProperty(value = "是否公铁立交代码")
    @TableField("is_iron_interchange_code")
    private String isIronInterchangeCode;

    @ApiModelProperty(value = "是否公铁立交名称")
    @TableField("is_iron_interchange_name")
    private String isIronInterchangeName;

    @ApiModelProperty(value = "立交桥类别代码")
    @TableField("interchange_type_code")
    private String interchangeTypeCode;

    @ApiModelProperty(value = "立交桥类别名称")
    @TableField("interchange_type_name")
    private String interchangeTypeName;

    @ApiModelProperty(value = "立交桥跨越各路线")
    @TableField("interchange_across_route")
    private String interchangeAcrossRoute;

    @ApiModelProperty(value = "立交桥连通各路线")
    @TableField("interchange_unicom_route")
    private String interchangeUnicomRoute;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "桥梁起点经度")
    @TableField("bridge_start_longitude")
    private String bridgeStartLongitude;

    @ApiModelProperty(value = "桥梁起点纬度")
    @TableField("bridge_start_latitude")
    private String bridgeStartLatitude;

    @ApiModelProperty(value = "桥梁止点经度")
    @TableField("bridge_end_longitude")
    private String bridgeEndLongitude;

    @ApiModelProperty(value = "桥梁止点纬度")
    @TableField("bridge_end_latitude")
    private String bridgeEndLatitude;

    @ApiModelProperty(value = "桥梁所在位置代码")
    @TableField("bridge_place_code")
    private String bridgePlaceCode;

    @ApiModelProperty(value = "桥梁所在位置名称")
    @TableField("bridge_place_name")
    private String bridgePlaceName;

    @ApiModelProperty(value = "是否宽路窄桥代码")
    @TableField("is_narrow_bridge_wide_road_code")
    private String isNarrowBridgeWideRoadCode;

    @ApiModelProperty(value = "是否宽路窄桥名称")
    @TableField("is_narrow_bridge_wide_road_name")
    private String isNarrowBridgeWideRoadName;

    @ApiModelProperty(value = "是否在长大桥梁目录中代码")
    @TableField("is_long_bridge_dir_code")
    private String isLongBridgeDirCode;

    @ApiModelProperty(value = "是否在长大桥梁目录中名称")
    @TableField("is_long_bridge_dir_name")
    private String isLongBridgeDirName;

    @ApiModelProperty(value = "桥梁全长(米)")
    @TableField("bridge_meter")
    private String bridgeMeter;

    @ApiModelProperty(value = "跨径总长(米)")
    @TableField("across_radius_meter")
    private String acrossRadiusMeter;

    @ApiModelProperty(value = "单孔最大跨径(米)")
    @TableField("a_hole_max_across_radius_meter")
    private String aHoleMaxAcrossRadiusMeter;

    @ApiModelProperty(value = "桥梁跨径组合(孔*米)")
    @TableField("bridge_across_radius_combination")
    private String bridgeAcrossRadiusCombination;

    @ApiModelProperty(value = "桥梁孔数(孔)")
    @TableField("bridge_hole_num")
    private String bridgeHoleNum;

    @ApiModelProperty(value = "主桥孔数(米)")
    @TableField("main_bridge_hole_num")
    private String mainBridgeHoleNum;

    @ApiModelProperty(value = "主桥主跨(米)")
    @TableField("main_bridge_main_across_meter")
    private String mainBridgeMainAcrossMeter;

    @ApiModelProperty(value = "主桥边跨(米)")
    @TableField("main_bridge_edge_across_meter")
    private String mainBridgeEdgeAcrossMeter;

    @ApiModelProperty(value = "前引桥长(米)")
    @TableField("before_qupte_bridge_meter")
    private String beforeQupteBridgeMeter;

    @ApiModelProperty(value = "后引桥长(米)")
    @TableField("after_qupte_bridge_meter")
    private String afterQupteBridgeMeter;

    @ApiModelProperty(value = "桥梁全宽(米)")
    @TableField("bridge_all_wide_meter")
    private String bridgeAllWideMeter;

    @ApiModelProperty(value = "桥面净宽(米)")
    @TableField("bridge_surface_wide_meter")
    private String bridgeSurfaceWideMeter;

    @ApiModelProperty(value = "人行道宽(米)")
    @TableField("pavement_wide_meter")
    private String pavementWideMeter;

    @ApiModelProperty(value = "行车道宽(米)")
    @TableField("driveway_wide_meter")
    private String drivewayWideMeter;

    @ApiModelProperty(value = "桥梁高度(米)")
    @TableField("bridge_hight_meter")
    private String bridgeHightMeter;

    @ApiModelProperty(value = "桥面标高(米)")
    @TableField("bridge_surface_hight_meter")
    private String bridgeSurfaceHightMeter;

    @ApiModelProperty(value = "桥下净空(米)")
    @TableField("bridge_below_hight_meter")
    private String bridgeBelowHightMeter;

    @ApiModelProperty(value = "匝道(平米)")
    @TableField("ramp_square_meters")
    private String rampSquareMeters;

    @ApiModelProperty(value = "匝道(公里)")
    @TableField("ramp_km")
    private String rampKm;

    @ApiModelProperty(value = "桥梁上部结构类型代码")
    @TableField("bridge_upper_structure_type_code")
    private String bridgeUpperStructureTypeCode;

    @ApiModelProperty(value = "桥梁上部结构类型名称")
    @TableField("bridge_upper_structure_type_name")
    private String bridgeUpperStructureTypeName;

    @ApiModelProperty(value = "桥梁下部结构形式代码")
    @TableField("bridge_below_structure_type_code")
    private String bridgeBelowStructureTypeCode;

    @ApiModelProperty(value = "桥梁下部结构形式名称")
    @TableField("bridge_below_structure_type_name")
    private String bridgeBelowStructureTypeName;

    @ApiModelProperty(value = "桥梁基础结构形式代码")
    @TableField("bridge_base_structure_type_code")
    private String bridgeBaseStructureTypeCode;

    @ApiModelProperty(value = "桥梁基础结构形式名称")
    @TableField("bridge_base_structure_type_name")
    private String bridgeBaseStructureTypeName;

    @ApiModelProperty(value = "桥梁引桥结构形式代码")
    @TableField("bridge_quote_structure_type_code")
    private String bridgeQuoteStructureTypeCode;

    @ApiModelProperty(value = "桥梁引桥结构形式名称")
    @TableField("bridge_quote_structure_type_name")
    private String bridgeQuoteStructureTypeName;

    @ApiModelProperty(value = "桥梁支座类型代码")
    @TableField("bridge_bearing_type_code")
    private String bridgeBearingTypeCode;

    @ApiModelProperty(value = "桥梁支座类型名称")
    @TableField("bridge_bearing_type_name")
    private String bridgeBearingTypeName;

    @ApiModelProperty(value = "桥梁桥台类型代码")
    @TableField("bridge_abutment_type_code")
    private String bridgeAbutmentTypeCode;

    @ApiModelProperty(value = "桥梁桥台类型名称")
    @TableField("bridge_abutment_type_name")
    private String bridgeAbutmentTypeName;

    @ApiModelProperty(value = "桥梁桥墩类型代码")
    @TableField("bridge_pier_type_code")
    private String bridgePierTypeCode;

    @ApiModelProperty(value = "桥梁桥墩类型名称")
    @TableField("bridge_pier_type_name")
    private String bridgePierTypeName;

    @ApiModelProperty(value = "桥梁墩台防撞设施类型代码")
    @TableField("bridge_pier_ca_type_code")
    private String bridgePierCaTypeCode;

    @ApiModelProperty(value = "桥梁墩台防撞设施类型名称")
    @TableField("bridge_pier_ca_type_name")
    private String bridgePierCaTypeName;

    @ApiModelProperty(value = "桥梁伸缩缝类型代码")
    @TableField("bridge_scaling_type_code")
    private String bridgeScalingTypeCode;

    @ApiModelProperty(value = "桥梁伸缩缝类型名称")
    @TableField("bridge_scaling_type_name")
    private String bridgeScalingTypeName;

    @ApiModelProperty(value = "桥梁拱桥矢跨比")
    @TableField("bridge_arch_across_rate")
    private String bridgeArchAcrossRate;

    @ApiModelProperty(value = "桥面纵坡")
    @TableField("bridge_surface_slope")
    private String bridgeSurfaceSlope;

    @ApiModelProperty(value = "桥面线型代码")
    @TableField("bridge_surface_line_code")
    private String bridgeSurfaceLineCode;

    @ApiModelProperty(value = "桥面线型名称")
    @TableField("bridge_surface_line_name")
    private String bridgeSurfaceLineName;

    @ApiModelProperty(value = "弯坡斜特征代码")
    @TableField("curve_slope_code")
    private String curveSlopeCode;

    @ApiModelProperty(value = "弯坡斜特征名称")
    @TableField("curve_slope_name")
    private String curveSlopeName;

    @ApiModelProperty(value = "桥位地形代码")
    @TableField("bridge_position_terrain_code")
    private String bridgePositionTerrainCode;

    @ApiModelProperty(value = "桥位地形名称")
    @TableField("bridge_position_terrain_name")
    private String bridgePositionTerrainName;

    @ApiModelProperty(value = "桥面铺装类型代码")
    @TableField("bridge_surface_decorate_code")
    private String bridgeSurfaceDecorateCode;

    @ApiModelProperty(value = "桥面铺装类型名称")
    @TableField("bridge_surface_decorate_name")
    private String bridgeSurfaceDecorateName;

    @ApiModelProperty(value = "桥面上部结构材料代码")
    @TableField("bridge_surface_upper_material_code")
    private String bridgeSurfaceUpperMaterialCode;

    @ApiModelProperty(value = "桥面上部结构材料名称")
    @TableField("bridge_surface_upper_material_name")
    private String bridgeSurfaceUpperMaterialName;

    @ApiModelProperty(value = "桥面下部材料类型")
    @TableField("bridge_surface_below_material_type")
    private String bridgeSurfaceBelowMaterialType;

    @ApiModelProperty(value = "主桥截面形式代码")
    @TableField("main_bridge_cross_code")
    private String mainBridgeCrossCode;

    @ApiModelProperty(value = "主桥截面形式名称")
    @TableField("main_bridge_cross_name")
    private String mainBridgeCrossName;

    @ApiModelProperty(value = "引桥截面型式代码")
    @TableField("quote_bridge_corss_code")
    private String quoteBridgeCorssCode;

    @ApiModelProperty(value = "引桥截面型式名称")
    @TableField("quote_bridge_corss_name")
    private String quoteBridgeCorssName;

    @ApiModelProperty(value = "设计荷载等级代码")
    @TableField("design_load_level_code")
    private String designLoadLevelCode;

    @ApiModelProperty(value = "设计荷载等级名称")
    @TableField("design_load_level_name")
    private String designLoadLevelName;

    @ApiModelProperty(value = "桥梁验算荷载代码")
    @TableField("check_load_code")
    private String checkLoadCode;

    @ApiModelProperty(value = "桥梁验算荷载名称")
    @TableField("check_load_name")
    private String checkLoadName;

    @ApiModelProperty(value = "抗震等级代码")
    @TableField("seismic_level_code")
    private String seismicLevelCode;

    @ApiModelProperty(value = "抗震等级名称")
    @TableField("seismic_level_name")
    private String seismicLevelName;

    @ApiModelProperty(value = "河床地质及纵坡代码")
    @TableField("river_slope_cdoe")
    private String riverSlopeCdoe;

    @ApiModelProperty(value = "河床地质及纵坡名称")
    @TableField("river_slope_name")
    private String riverSlopeName;

    @ApiModelProperty(value = "河床最低标高(米)")
    @TableField("river_low_height_meter")
    private String riverLowHeightMeter;

    @ApiModelProperty(value = "设计洪水频率(年)")
    @TableField("design_flood_frequency")
    private String designFloodFrequency;

    @ApiModelProperty(value = "设计洪水位")
    @TableField("design_water_level")
    private String designWaterLevel;

    @ApiModelProperty(value = "常年水位")
    @TableField("normal_water_level")
    private String normalWaterLevel;

    @ApiModelProperty(value = "寻常洪水位")
    @TableField("normal_flood_level")
    private String normalFloodLevel;

    @ApiModelProperty(value = "历史最高洪水位")
    @TableField("history_flood_level")
    private String historyFloodLevel;

    @ApiModelProperty(value = "河床变迁")
    @TableField("riverbed_change")
    private String riverbedChange;

    @ApiModelProperty(value = "通航等级代码")
    @TableField("sailing_level_code")
    private String sailingLevelCode;

    @ApiModelProperty(value = "通航等级名称")
    @TableField("sailing_level_name")
    private String sailingLevelName;

    @ApiModelProperty(value = "管养单位名称")
    @TableField("maintenance_name")
    private String maintenanceName;

    @ApiModelProperty(value = "管养单位性质代码")
    @TableField("maintenance_nature_code")
    private String maintenanceNatureCode;

    @ApiModelProperty(value = "管养单位性质名称")
    @TableField("maintenance_nature_name")
    private String maintenanceNatureName;

    @ApiModelProperty(value = "设计单位名称")
    @TableField("design_unit_name")
    private String designUnitName;

    @ApiModelProperty(value = "建设单位名称")
    @TableField("build_unit_name")
    private String buildUnitName;

    @ApiModelProperty(value = "施工单位名称")
    @TableField("implementation_unit_name")
    private String implementationUnitName;

    @ApiModelProperty(value = "监理单位名称")
    @TableField("supervision_unit_name")
    private String supervisionUnitName;

    @ApiModelProperty(value = "监管单位名称")
    @TableField("regulatory_unit_name")
    private String regulatoryUnitName;

    @ApiModelProperty(value = "工程号")
    @TableField("project_no")
    private String projectNo;

    @ApiModelProperty(value = "总造价")
    @TableField("total_price")
    private String totalPrice;

    @ApiModelProperty(value = "修建年度")
    @TableField("build_year")
    private String buildYear;

    @ApiModelProperty(value = "开工日期")
    @TableField("starts_date")
    private String startsDate;

    @ApiModelProperty(value = "竣工日期")
    @TableField("complete_date")
    private String completeDate;

    @ApiModelProperty(value = "通车日期")
    @TableField("to_date")
    private String toDate;

    @ApiModelProperty(value = "改建变更日期")
    @TableField("change_date")
    private String changeDate;

    @ApiModelProperty(value = "改建变更原因类型代码")
    @TableField("change_reason_code")
    private String changeReasonCode;

    @ApiModelProperty(value = "改建变更原因类型名称")
    @TableField("change_reason_name")
    private String changeReasonName;

    @ApiModelProperty(value = "变更原因说明")
    @TableField("change_reason_desc")
    private String changeReasonDesc;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "最近改造年度")
    @TableField("lately_remould_year")
    private String latelyRemouldYear;

    @ApiModelProperty(value = "最近改造完工日期")
    @TableField("lately_remould_complete_date")
    private String latelyRemouldCompleteDate;

    @ApiModelProperty(value = "最近改造部位代码")
    @TableField("lately_remould_part_code")
    private String latelyRemouldPartCode;

    @ApiModelProperty(value = "最近改造部位名称")
    @TableField("lately_remould_part_name")
    private String latelyRemouldPartName;

    @ApiModelProperty(value = "最近改造施工单位")
    @TableField("lately_remould_implementation_name")
    private String latelyRemouldImplementationName;

    @ApiModelProperty(value = "最近工程性质代码")
    @TableField("lately_project_nature_code")
    private String latelyProjectNatureCode;

    @ApiModelProperty(value = "最近工程性质名称")
    @TableField("lately_project_nature_name")
    private String latelyProjectNatureName;

    @ApiModelProperty(value = "是否部补助项目代码")
    @TableField("is_subsidies_project_code")
    private String isSubsidiesProjectCode;

    @ApiModelProperty(value = "是否部补助项目名称")
    @TableField("is_subsidies_project_name")
    private String isSubsidiesProjectName;

    @ApiModelProperty(value = "已采取交通管制措施代码")
    @TableField("has_traffic_control_code")
    private String hasTrafficControlCode;

    @ApiModelProperty(value = "已采取交通管制措施名称")
    @TableField("has_traffic_control_name")
    private String hasTrafficControlName;

    @ApiModelProperty(value = "计划项目唯一编码")
    @TableField("plan_project_id")
    private String planProjectId;

    @ApiModelProperty(value = "计划项目路线编码")
    @TableField("plan_project_route_code")
    private String planProjectRouteCode;

    @ApiModelProperty(value = "计划项目路线名称")
    @TableField("plan_project_route_name")
    private String planProjectRouteName;

    @ApiModelProperty(value = "计划项目桥梁桩号")
    @TableField("plan_project_bridge_stake")
    private String planProjectBridgeStake;

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

    @ApiModelProperty(value = "路网调整类型代码")
    @TableField("highway_adjust_code")
    private String highwayAdjustCode;

    @ApiModelProperty(value = "路网调整类型名称")
    @TableField("highway_adjust_name")
    private String highwayAdjustName;

    @ApiModelProperty(value = "是否桩号方向重排代码")
    @TableField("is_reverse_stake_code")
    private String isReverseStakeCode;

    @ApiModelProperty(value = "是否桩号方向重排名称")
    @TableField("is_reverse_stake_name")
    private String isReverseStakeName;

    @ApiModelProperty(value = "反排路线里程")
    @TableField("reverse_route_mileage")
    private String reverseRouteMileage;

    @ApiModelProperty(value = "是否删除代码")
    @TableField("is_del_code")
    private String isDelCode;

    @ApiModelProperty(value = "是否删除名称")
    @TableField("is_del_name")
    private String isDelName;

    @ApiModelProperty(value = "唯一ID")
    @TableField("unique_id")
    private String uniqueId;

    @ApiModelProperty(value = "标识编码")
    @TableField("id_code")
    private String idCode;

    @ApiModelProperty(value = "路网调整后行政等级代码")
    @TableField("highway_adjust_admin_code")
    private String highwayAdjustAdminCode;

    @ApiModelProperty(value = "路网调整后行政等级名称")
    @TableField("highway_adjust_admin_name")
    private String highwayAdjustAdminName;

    @ApiModelProperty(value = "运营路段ID")
    @TableField("operating_waysection_id")
    private String operatingWaysectionId;

    @ApiModelProperty(value = "设施ID")
    @TableField("facilities_id")
    private String facilitiesId;

    @ApiModelProperty(value = "桥梁分类")
    @TableField("bridge_type")
    private String bridgeType;

    @ApiModelProperty(value = "数据ID")
    @TableField("id_old")
    private String idOld;

    @ApiModelProperty(value = "数据源")
    @TableField("id_source")
    private String idSource;

    public void setFacilitiesId(String facilitiesId) {
        this.facilitiesId = facilitiesId;
    }
}
