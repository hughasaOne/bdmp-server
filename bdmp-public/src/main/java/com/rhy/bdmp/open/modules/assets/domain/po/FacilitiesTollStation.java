package com.rhy.bdmp.open.modules.assets.domain.po;

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
@TableName("t_bdmp_assets_facilities_toll_station")
public class FacilitiesTollStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收费站ID")
    @TableId("toll_station_id")
    private String tollStationId;

    @ApiModelProperty(value = "管理单位代码")
    @TableField("manage_code")
    private String manageCode;

    @ApiModelProperty(value = "管理单位名称")
    @TableField("manage_name")
    private String manageName;

    @ApiModelProperty(value = "路线代码")
    @TableField("route_code")
    private String routeCode;

    @ApiModelProperty(value = "收费站编号")
    @TableField("toll_station_no")
    private String tollStationNo;

    @ApiModelProperty(value = "路线简称")
    @TableField("route_s_name")
    private String routeSName;

    @ApiModelProperty(value = "收费站名称")
    @TableField("toll_station_name")
    private String tollStationName;

    @ApiModelProperty(value = "收费站桩号")
    @TableField("toll_station_stake")
    private String tollStationStake;

    @ApiModelProperty(value = "路段桩号起点")
    @TableField("road_start_stake")
    private String roadStartStake;

    @ApiModelProperty(value = "路段桩号止点")
    @TableField("road_end_stake")
    private String roadEndStake;

    @ApiModelProperty(value = "收费路段起点地名")
    @TableField("road_start_place")
    private String roadStartPlace;

    @ApiModelProperty(value = "收费路段止点地名")
    @TableField("road_end_place")
    private String roadEndPlace;

    @ApiModelProperty(value = "行政区划代码")
    @TableField("admin_region_code")
    private String adminRegionCode;

    @ApiModelProperty(value = "行政区划名称")
    @TableField("admin_region_name")
    private String adminRegionName;

    @ApiModelProperty(value = "收费方向代码")
    @TableField("toll_direction_code")
    private String tollDirectionCode;

    @ApiModelProperty(value = "收费方向名称")
    @TableField("toll_direction_name")
    private String tollDirectionName;

    @ApiModelProperty(value = "收费性质代码")
    @TableField("toll_nature_code")
    private String tollNatureCode;

    @ApiModelProperty(value = "收费性质名称")
    @TableField("toll_nature_name")
    private String tollNatureName;

    @ApiModelProperty(value = "收费站类型代码")
    @TableField("toll_station_type_code")
    private String tollStationTypeCode;

    @ApiModelProperty(value = "收费站类型名称")
    @TableField("toll_station_type_name")
    private String tollStationTypeName;

    @ApiModelProperty(value = "收费站位置类型代码")
    @TableField("toll_station_place_code")
    private String tollStationPlaceCode;

    @ApiModelProperty(value = "收费站位置类型名称")
    @TableField("toll_station_place_name")
    private String tollStationPlaceName;

    @ApiModelProperty(value = "收费里程(公里)")
    @TableField("toll_mileage")
    private String tollMileage;

    @ApiModelProperty(value = "桥、隧长(延米)")
    @TableField("bridge_tunnel_meter")
    private String bridgeTunnelMeter;

    @ApiModelProperty(value = "入口车道数")
    @TableField("entrance_driveway_num")
    private String entranceDrivewayNum;

    @ApiModelProperty(value = "入口ETC车道数")
    @TableField("entrance_driveway_etc_num")
    private String entranceDrivewayEtcNum;

    @ApiModelProperty(value = "出口车道数")
    @TableField("exit_driveway_num")
    private String exitDrivewayNum;

    @ApiModelProperty(value = "出口ETC车道数")
    @TableField("exit_driveway_etc_num")
    private String exitDrivewayEtcNum;

    @ApiModelProperty(value = "批准开始收费日期")
    @TableField("approval_toll_start_date")
    private String approvalTollStartDate;

    @ApiModelProperty(value = "批准收费年限")
    @TableField("approval_toll_year_limit")
    private String approvalTollYearLimit;

    @ApiModelProperty(value = "监督电话")
    @TableField("supervise_phone")
    private String supervisePhone;

    @ApiModelProperty(value = "产权单位名称")
    @TableField("property_right_unit_name")
    private String propertyRightUnitName;

    @ApiModelProperty(value = "产权单位代码")
    @TableField("property_right_unit_code")
    private String propertyRightUnitCode;

    @ApiModelProperty(value = "建成日期")
    @TableField("build_date")
    private String buildDate;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "标识编码")
    @TableField("id_code")
    private String idCode;

    @ApiModelProperty(value = "运营路段ID")
    @TableField("operating_waysection_id")
    private String operatingWaysectionId;

    @ApiModelProperty(value = "设施ID")
    @TableField("facilities_id")
    private String facilitiesId;
}
