package com.rhy.bdmp.open.modules.assets.domain.po;

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
 * @description  实体
 * @author yanggj
 * @date 2021-10-20 15:34
 * @version V1.0
 **/
@ApiModel(value="", description="信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_facilities_tunnel")
public class FacilitiesTunnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "隧道ID")
    @TableId("tunnel_id")
    private String tunnelId;

    @ApiModelProperty(value = "管理单位代码")
    @TableField("manage_code")
    private String manageCode;

    @ApiModelProperty(value = "管理单位名称")
    @TableField("manage_name")
    private String manageName;

    @ApiModelProperty(value = "路线代码")
    @TableField("route_code")
    private String routeCode;

    @ApiModelProperty(value = "隧道编号")
    @TableField("tunnel_no")
    private String tunnelNo;

    @ApiModelProperty(value = "路线简称")
    @TableField("route_s_name")
    private String routeSName;

    @ApiModelProperty(value = "隧道中心桩号")
    @TableField("tunnel_center_stake")
    private String tunnelCenterStake;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("admin_region_code")
    private String adminRegionCode;

    @ApiModelProperty(value = "行政区划名称")
    @TableField("admin_region_name")
    private String adminRegionName;

    @ApiModelProperty(value = "隧道名称")
    @TableField("tunnel_name")
    private String tunnelName;

    @ApiModelProperty(value = "隧道所在地点")
    @TableField("tunnel_palce")
    private String tunnelPalce;

    @ApiModelProperty(value = "隧道入口桩号")
    @TableField("tunnel_entrance_stake")
    private String tunnelEntranceStake;

    @ApiModelProperty(value = "隧道按长度分类代码")
    @TableField("tunnel_long_type_code")
    private String tunnelLongTypeCode;

    @ApiModelProperty(value = "隧道按长度分类名称")
    @TableField("tunnel_long_type_name")
    private String tunnelLongTypeName;

    @ApiModelProperty(value = "隧道长度(米)")
    @TableField("tunnel_meter")
    private String tunnelMeter;

    @ApiModelProperty(value = "隧道全宽(米)")
    @TableField("tunnel_all_wide")
    private String tunnelAllWide;

    @ApiModelProperty(value = "隧道净宽(米)")
    @TableField("tunnel_clean_wide")
    private String tunnelCleanWide;

    @ApiModelProperty(value = "隧道净高(米)")
    @TableField("tunnel_clean_height")
    private String tunnelCleanHeight;

    @ApiModelProperty(value = "人行道宽(米)")
    @TableField("pavement_wide")
    private String pavementWide;

    @ApiModelProperty(value = "路面面层类型代码")
    @TableField("road_surface_layer_type_code")
    private String roadSurfaceLayerTypeCode;

    @ApiModelProperty(value = "路面面层类型名称")
    @TableField("road_surface_layer_type_name")
    private String roadSurfaceLayerTypeName;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "隧道养护等级代码")
    @TableField("tunnel_maintenance_level_code")
    private String tunnelMaintenanceLevelCode;

    @ApiModelProperty(value = "隧道养护等级名称")
    @TableField("tunnel_maintenance_level_name")
    private String tunnelMaintenanceLevelName;

    @ApiModelProperty(value = "是否在长大隧道目录中代码")
    @TableField("is_long_tunnel_directory_code")
    private String isLongTunnelDirectoryCode;

    @ApiModelProperty(value = "是否在长大隧道目录中名称")
    @TableField("is_long_tunnel_directory_name")
    private String isLongTunnelDirectoryName;

    @ApiModelProperty(value = "标识编码")
    @TableField("id_code")
    private String idCode;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "洞口形式代码")
    @TableField("hole_shape_code")
    private String holeShapeCode;

    @ApiModelProperty(value = "洞口形式名称")
    @TableField("hole_shape_name")
    private String holeShapeName;

    @ApiModelProperty(value = "断面形式代码")
    @TableField("surface_shape_code")
    private String surfaceShapeCode;

    @ApiModelProperty(value = "断面形式名称")
    @TableField("surface_shape_name")
    private String surfaceShapeName;

    @ApiModelProperty(value = "衬砌材料代码")
    @TableField("material_code")
    private String materialCode;

    @ApiModelProperty(value = "衬砌材料名称")
    @TableField("material_name")
    private String materialName;

    @ApiModelProperty(value = "隧道排水类型代码")
    @TableField("tunnel_drainage_type_code")
    private String tunnelDrainageTypeCode;

    @ApiModelProperty(value = "隧道排水类型名称")
    @TableField("tunnel_drainage_type_name")
    private String tunnelDrainageTypeName;

    @ApiModelProperty(value = "安全通道数量")
    @TableField("secure_channel_num")
    private String secureChannelNum;

    @ApiModelProperty(value = "隧道照明代码")
    @TableField("tunnel_lighting_code")
    private String tunnelLightingCode;

    @ApiModelProperty(value = "隧道照明名称")
    @TableField("tunnel_lighting_name")
    private String tunnelLightingName;

    @ApiModelProperty(value = "隧道通风代码")
    @TableField("tunnel_ventilate_code")
    private String tunnelVentilateCode;

    @ApiModelProperty(value = "隧道通风名称")
    @TableField("tunnel_ventilate_name")
    private String tunnelVentilateName;

    @ApiModelProperty(value = "消防设施代码")
    @TableField("tunnel_fire_code")
    private String tunnelFireCode;

    @ApiModelProperty(value = "消防设施名称")
    @TableField("tunnel_fire_name")
    private String tunnelFireName;

    @ApiModelProperty(value = "隧道电子设备代码")
    @TableField("tunnel_electronic_code")
    private String tunnelElectronicCode;

    @ApiModelProperty(value = "隧道电子设备名称")
    @TableField("tunnel_electronic_name")
    private String tunnelElectronicName;

    @ApiModelProperty(value = "是否水下隧道代码")
    @TableField("is_water_below_code")
    private String isWaterBelowCode;

    @ApiModelProperty(value = "是否水下隧道名称")
    @TableField("is_water_below_name")
    private String isWaterBelowName;

    @ApiModelProperty(value = "建设单位名称")
    @TableField("build_unit_name")
    private String buildUnitName;

    @ApiModelProperty(value = "设计单位名称")
    @TableField("design_unit_name")
    private String designUnitName;

    @ApiModelProperty(value = "施工单位名称")
    @TableField("implementation_unit_name")
    private String implementationUnitName;

    @ApiModelProperty(value = "监理单位名称")
    @TableField("supervision_unit_name")
    private String supervisionUnitName;

    @ApiModelProperty(value = "管养单位名称")
    @TableField("maintenance_name")
    private String maintenanceName;

    @ApiModelProperty(value = "管养单位性质代码")
    @TableField("maintenance_nature_code")
    private String maintenanceNatureCode;

    @ApiModelProperty(value = "管养单位性质名称")
    @TableField("maintenance_nature_name")
    private String maintenanceNatureName;

    @ApiModelProperty(value = "所属行业类别代码")
    @TableField("industry_code")
    private String industryCode;

    @ApiModelProperty(value = "所属行业类别名称")
    @TableField("industry_name")
    private String industryName;

    @ApiModelProperty(value = "监管单位名称")
    @TableField("regulatory_unit_name")
    private String regulatoryUnitName;

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

    @ApiModelProperty(value = "建设工程号")
    @TableField("build_project_no")
    private String buildProjectNo;

    @ApiModelProperty(value = "建设总造价(万元)")
    @TableField("build_total_price")
    private String buildTotalPrice;

    @ApiModelProperty(value = "修建年度")
    @TableField("build_year")
    private String buildYear;

    @ApiModelProperty(value = "改造年度")
    @TableField("remould_year")
    private String remouldYear;

    @ApiModelProperty(value = "技术状况评定单位")
    @TableField("technology_evaluate_unit")
    private String technologyEvaluateUnit;

    @ApiModelProperty(value = "评定日期")
    @TableField("evaluate_date")
    private String evaluateDate;

    @ApiModelProperty(value = "改造完工日期")
    @TableField("remould_complete_date")
    private String remouldCompleteDate;

    @ApiModelProperty(value = "隧道改造部位代码")
    @TableField("tunnel_remould_part_code")
    private String tunnelRemouldPartCode;

    @ApiModelProperty(value = "隧道改造部位名称")
    @TableField("tunnel_remould_part_name")
    private String tunnelRemouldPartName;

    @ApiModelProperty(value = "工程性质代码")
    @TableField("project_nature_code")
    private String projectNatureCode;

    @ApiModelProperty(value = "工程性质名称")
    @TableField("project_nature_name")
    private String projectNatureName;

    @ApiModelProperty(value = "隧道主要病害部位代码")
    @TableField("tunnel_main_disease_code")
    private String tunnelMainDiseaseCode;

    @ApiModelProperty(value = "隧道主要病害部位名称")
    @TableField("tunnel_main_disease_name")
    private String tunnelMainDiseaseName;

    @ApiModelProperty(value = "隧道主要病害描述")
    @TableField("tunnel_main_disease_desc")
    private String tunnelMainDiseaseDesc;

    @ApiModelProperty(value = "路网调整后行政等级代码")
    @TableField("highway_adjust_admin_code")
    private String highwayAdjustAdminCode;

    @ApiModelProperty(value = "路网调整后行政等级名称")
    @TableField("highway_adjust_admin_name")
    private String highwayAdjustAdminName;

    @ApiModelProperty(value = "评定等级代码(总体)")
    @TableField("all_evaluate_level_code")
    private String allEvaluateLevelCode;

    @ApiModelProperty(value = "评定等级名称(总体)")
    @TableField("all_evaluate_level_name")
    private String allEvaluateLevelName;

    @ApiModelProperty(value = "评定日期(总体)")
    @TableField("all_evaluate_date")
    private String allEvaluateDate;

    @ApiModelProperty(value = "评定单位(总体)")
    @TableField("all_evaluate_unit")
    private String allEvaluateUnit;

    @ApiModelProperty(value = "评定等级代码(土建结构)")
    @TableField("civil_evaluate_level_code")
    private String civilEvaluateLevelCode;

    @ApiModelProperty(value = "评定等级名称(土建结构)")
    @TableField("civil_evaluate_level_name")
    private String civilEvaluateLevelName;

    @ApiModelProperty(value = "评定日期(土建结构)")
    @TableField("civil_evaluate_date")
    private String civilEvaluateDate;

    @ApiModelProperty(value = "评定单位(土建结构)")
    @TableField("civil_evaluate_unit")
    private String civilEvaluateUnit;

    @ApiModelProperty(value = "评定等级代码(机电设施)")
    @TableField("mechanical_evaluate_level_code")
    private String mechanicalEvaluateLevelCode;

    @ApiModelProperty(value = "评定等级名称(机电设施)")
    @TableField("mechanical_evaluate_level_name")
    private String mechanicalEvaluateLevelName;

    @ApiModelProperty(value = "评定日期(机电设施)")
    @TableField("mechanical_evaluate_date")
    private String mechanicalEvaluateDate;

    @ApiModelProperty(value = "评定单位(机电设施)")
    @TableField("mechanical_evaluate_unit")
    private String mechanicalEvaluateUnit;

    @ApiModelProperty(value = "评定等级代码(其他工程设施)")
    @TableField("other_evaluate_level_code")
    private String otherEvaluateLevelCode;

    @ApiModelProperty(value = "评定等级名称(其他工程设施)")
    @TableField("other_evaluate_level_name")
    private String otherEvaluateLevelName;

    @ApiModelProperty(value = "评定日期(其他工程设施)")
    @TableField("other_evaluate_date")
    private String otherEvaluateDate;

    @ApiModelProperty(value = "评定单位(其他工程设施)")
    @TableField("other_evaluate_unit")
    private String otherEvaluateUnit;

    @ApiModelProperty(value = "计划项目唯一编码")
    @TableField("plan_project_id")
    private String planProjectId;

    @ApiModelProperty(value = "计划项目路线编码")
    @TableField("plan_project_route_code")
    private String planProjectRouteCode;

    @ApiModelProperty(value = "计划项目路线名称")
    @TableField("plan_project_route_name")
    private String planProjectRouteName;

    @ApiModelProperty(value = "计划项目隧道桩号")
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

    @ApiModelProperty(value = "操作顺序代码")
    @TableField("operation_sort_code")
    private String operationSortCode;

    @ApiModelProperty(value = "操作顺序名称")
    @TableField("operation_sort_name")
    private String operationSortName;

    @ApiModelProperty(value = "平移值")
    @TableField("translation_value")
    private String translationValue;

    @ApiModelProperty(value = "路网调整类型代码")
    @TableField("highway_adjust_code")
    private String highwayAdjustCode;

    @ApiModelProperty(value = "路网调整类型名称")
    @TableField("highway_adjust_name")
    private String highwayAdjustName;

    @ApiModelProperty(value = "是否桩号反向重排代码")
    @TableField("is_reverse_stake_code")
    private String isReverseStakeCode;

    @ApiModelProperty(value = "是否桩号反向重排名称")
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

    @ApiModelProperty(value = "运营路段ID")
    @TableField("operating_waysection_id")
    private String operatingWaysectionId;

    @ApiModelProperty(value = "设施ID")
    @TableField("facilities_id")
    private String facilitiesId;

    @ApiModelProperty(value = "经度84")
    @TableField("longitude_84")
    private String longitude84;

    @ApiModelProperty(value = "纬度84")
    @TableField("latitude_84")
    private String latitude84;
}
