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
@TableName("t_bdmp_assets_facilities_service_area")
public class FacilitiesServiceArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id", example = "1")
    @TableId("service_area_id")
    private Integer serviceAreaId;

    @ApiModelProperty(value = "省份")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "服务区管理平台对应服务区id")
    @TableField("server_id")
    private String serverId;

    @ApiModelProperty(value = "产权隶属单位")
    @TableField("belong_to")
    private String belongTo;

    @ApiModelProperty(value = "高速名称")
    @TableField("highspeed_name")
    private String highspeedName;

    @ApiModelProperty(value = "高速编号")
    @TableField("highspeed_no")
    private String highspeedNo;

    @ApiModelProperty(value = "路段类型")
    @TableField("road_type")
    private String roadType;

    @ApiModelProperty(value = "路段名称")
    @TableField("road_part")
    private String roadPart;

    @ApiModelProperty(value = "路段桩号")
    @TableField("road_no")
    private String roadNo;

    @ApiModelProperty(value = "建成时间")
    @TableField("done_built_date")
    private String doneBuiltDate;

    @ApiModelProperty(value = "开通时间")
    @TableField("open_date")
    private String openDate;

    @ApiModelProperty(value = "类别")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "联系电话")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty(value = "经营模式")
    @TableField("management_model")
    private String managementModel;

    @ApiModelProperty(value = "服务区方向")
    @TableField("direction")
    private String direction;

    @ApiModelProperty(value = "服务区方向名称")
    @TableField("direction_name")
    private String directionName;

    @ApiModelProperty(value = "服务区简介")
    @TableField("introduction")
    private String introduction;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "业主单位管理人数")
    @TableField("owner_manager_count")
    private String ownerManagerCount;

    @ApiModelProperty(value = "经营单位管理人数")
    @TableField("management_manager_count")
    private String managementManagerCount;

    @ApiModelProperty(value = "经营单位服务人数")
    @TableField("management_server_count")
    private String managementServerCount;

    @ApiModelProperty(value = "信息化运维人数")
    @TableField("info_run_count")
    private String infoRunCount;

    @ApiModelProperty(value = "物业保障员工人数")
    @TableField("daily_worker_count")
    private String dailyWorkerCount;

    @ApiModelProperty(value = "总占地面积")
    @TableField("area_covered")
    private String areaCovered;

    @ApiModelProperty(value = "总建筑面积")
    @TableField("building_covered")
    private String buildingCovered;

    @ApiModelProperty(value = "餐饮面积")
    @TableField("eating_covered")
    private String eatingCovered;

    @ApiModelProperty(value = "商超面积")
    @TableField("bussiness_covered")
    private String bussinessCovered;

    @ApiModelProperty(value = "停车场面积")
    @TableField("park_covered")
    private String parkCovered;

    @ApiModelProperty(value = "加油站面积")
    @TableField("oil_covered")
    private String oilCovered;

    @ApiModelProperty(value = "充电站面积")
    @TableField("charge_covered")
    private String chargeCovered;

    @ApiModelProperty(value = "绿化面积")
    @TableField("plant_covered")
    private String plantCovered;

    @ApiModelProperty(value = "去年日均男卫生间如厕人数")
    @TableField("daily_male_toilet_last")
    private String dailyMaleToiletLast;

    @ApiModelProperty(value = "去年日均女卫生间如厕人数")
    @TableField("daily_female_toilet_last")
    private String dailyFemaleToiletLast;

    @ApiModelProperty(value = "去年日均货车断面流量")
    @TableField("daily_good_car_flow_last")
    private String dailyGoodCarFlowLast;

    @ApiModelProperty(value = "去年日均客车断面流量")
    @TableField("daily_customer_car_flow_last")
    private String dailyCustomerCarFlowLast;

    @ApiModelProperty(value = "去年日均客流量")
    @TableField("s")
    private String s;

    @ApiModelProperty(value = "去年日均驶入率")
    @TableField("daily_drive_in_last")
    private String dailyDriveInLast;

    @ApiModelProperty(value = "今年日均男卫生间如厕人数")
    @TableField("daily_male_toilet_this")
    private String dailyMaleToiletThis;

    @ApiModelProperty(value = "今年日均女卫生间如厕人数")
    @TableField("daily_female_toilet_this")
    private String dailyFemaleToiletThis;

    @ApiModelProperty(value = "今年日均货车断面流量")
    @TableField("daily_good_car_flow_this")
    private String dailyGoodCarFlowThis;

    @ApiModelProperty(value = "今年日均客车断面流量")
    @TableField("daily_customer_car_flow_this")
    private String dailyCustomerCarFlowThis;

    @ApiModelProperty(value = "今年日均客流量")
    @TableField("daily_customer_flow_this")
    private String dailyCustomerFlowThis;

    @ApiModelProperty(value = "今年日均驶入率")
    @TableField("daily_drive_in_this")
    private String dailyDriveInThis;

    @ApiModelProperty(value = "大型货车位")
    @TableField("big_good_park_count")
    private String bigGoodParkCount;

    @ApiModelProperty(value = "普通货车位")
    @TableField("normal_good_park_count")
    private String normalGoodParkCount;

    @ApiModelProperty(value = "大型客车位")
    @TableField("big_customer_park_count")
    private String bigCustomerParkCount;

    @ApiModelProperty(value = "小型客车位")
    @TableField("small_customer_park_count")
    private String smallCustomerParkCount;

    @ApiModelProperty(value = "危化品运输车位")
    @TableField("dangerous_park_count")
    private String dangerousParkCount;

    @ApiModelProperty(value = "牲畜运输车位")
    @TableField("animal_park_count")
    private String animalParkCount;

    @ApiModelProperty(value = "女性车位")
    @TableField("female_park_count")
    private String femaleParkCount;

    @ApiModelProperty(value = "无障碍车位")
    @TableField("easy_park_count")
    private String easyParkCount;

    @ApiModelProperty(value = "是否设置客运汽车停靠站")
    @TableField("is_set_carstop_station")
    private String isSetCarstopStation;

    @ApiModelProperty(value = "加油站运营单位")
    @TableField("gas_company")
    private String gasCompany;

    @ApiModelProperty(value = "汽油加油枪数量")
    @TableField("gas_gun_count")
    private String gasGunCount;

    @ApiModelProperty(value = "柴油加油枪数量")
    @TableField("oil_gun_count")
    private String oilGunCount;

    @ApiModelProperty(value = "充电站运营单位")
    @TableField("charge_company")
    private String chargeCompany;

    @ApiModelProperty(value = "充电桩数量")
    @TableField("charge_count")
    private String chargeCount;

    @ApiModelProperty(value = "男小便位")
    @TableField("male_pee_count")
    private String malePeeCount;

    @ApiModelProperty(value = "男蹲便位")
    @TableField("male_squat_poo_count")
    private String maleSquatPooCount;

    @ApiModelProperty(value = "男坐便位")
    @TableField("male_sit_poo_count")
    private String maleSitPooCount;

    @ApiModelProperty(value = "女蹲便位")
    @TableField("female_squat_poo_count")
    private String femaleSquatPooCount;

    @ApiModelProperty(value = "女坐便位")
    @TableField("female_sit_poo_count")
    private String femaleSitPooCount;

    @ApiModelProperty(value = "第三卫生间个数")
    @TableField("third_toilet_count")
    private String thirdToiletCount;

    @ApiModelProperty(value = "第三卫生间便位")
    @TableField("third_toilet_poo_count")
    private String thirdToiletPooCount;

    @ApiModelProperty(value = "无障碍卫生间个数")
    @TableField("easy_toilet_count")
    private String easyToiletCount;

    @ApiModelProperty(value = "无障碍卫生间便位")
    @TableField("easy_toilet_poo_count")
    private String easyToiletPooCount;

    @ApiModelProperty(value = "第三卫生间无障碍卫生间是否共用")
    @TableField("is_use_together")
    private String isUseTogether;

    @ApiModelProperty(value = "有无餐厅")
    @TableField("has_restaurant")
    private String hasRestaurant;

    @ApiModelProperty(value = "餐厅名称")
    @TableField("restaurant_name")
    private String restaurantName;

    @ApiModelProperty(value = "餐厅类型")
    @TableField("restaurant_type")
    private String restaurantType;

    @ApiModelProperty(value = "有无商超")
    @TableField("has_bussiness")
    private String hasBussiness;

    @ApiModelProperty(value = "商超名称")
    @TableField("bussiness_name")
    private String bussinessName;

    @ApiModelProperty(value = "是否有客房")
    @TableField("has_guest_room")
    private String hasGuestRoom;

    @ApiModelProperty(value = "总床位数")
    @TableField("bed_count")
    private String bedCount;

    @ApiModelProperty(value = "单人间房间数")
    @TableField("single_room_count")
    private String singleRoomCount;

    @ApiModelProperty(value = "双人间房间数")
    @TableField("double_room_count")
    private String doubleRoomCount;

    @ApiModelProperty(value = "是否有淋浴")
    @TableField("has_shower")
    private String hasShower;

    @ApiModelProperty(value = "淋浴总数量")
    @TableField("shower_count")
    private String showerCount;

    @ApiModelProperty(value = "男淋浴数量")
    @TableField("male_shower_count")
    private String maleShowerCount;

    @ApiModelProperty(value = "女淋浴数量")
    @TableField("female_shower_count")
    private String femaleShowerCount;

    @ApiModelProperty(value = "是否区分男女淋浴")
    @TableField("is_distinguish")
    private String isDistinguish;

    @ApiModelProperty(value = "是否有车辆维修")
    @TableField("has_car_repair")
    private String hasCarRepair;

    @ApiModelProperty(value = "车辆维修联系电话")
    @TableField("car_repair_mobile")
    private String carRepairMobile;

    @ApiModelProperty(value = "线上预约")
    @TableField("book_online")
    private String bookOnline;

    @ApiModelProperty(value = "支付方式")
    @TableField("pay_way")
    private String payWay;

    @ApiModelProperty(value = "是否有母婴室")
    @TableField("has_mom_baby_room")
    private String hasMomBabyRoom;

    @ApiModelProperty(value = "母婴室数量")
    @TableField("mom_baby_room_count")
    private String momBabyRoomCount;

    @ApiModelProperty(value = "母婴室是否有卫生间")
    @TableField("has_mom_baby_room_toilet")
    private String hasMomBabyRoomToilet;

    @ApiModelProperty(value = "母婴室便位数量")
    @TableField("mom_baby_room_toilet_count")
    private String momBabyRoomToiletCount;

    @ApiModelProperty(value = "母婴室面积")
    @TableField("mom_baby_room_covered")
    private String momBabyRoomCovered;

    @ApiModelProperty(value = "是否设有母婴休息设施")
    @TableField("has_mom_baby_room_rest")
    private String hasMomBabyRoomRest;

    @ApiModelProperty(value = "无障碍通道数量")
    @TableField("has_passageway")
    private String hasPassageway;

    @ApiModelProperty(value = "无障碍通道数量")
    @TableField("passageway_count")
    private String passagewayCount;

    @ApiModelProperty(value = "是否实现无障碍通道全覆盖")
    @TableField("is_all_covered")
    private String isAllCovered;

    @ApiModelProperty(value = "是否有应急救援服务")
    @TableField("has_emergency_help")
    private String hasEmergencyHelp;

    @ApiModelProperty(value = "是否有医疗卫生服务")
    @TableField("has_medical_server")
    private String hasMedicalServer;

    @ApiModelProperty(value = "是否设置医务站（所/室）")
    @TableField("has_medical_room")
    private String hasMedicalRoom;

    @ApiModelProperty(value = "是否有服务监督")
    @TableField("has_service_watch")
    private String hasServiceWatch;

    @ApiModelProperty(value = "服务监督电话号码")
    @TableField("watch_mobile")
    private String watchMobile;

    @ApiModelProperty(value = "有无配置服务设施经营项目营收统计系统")
    @TableField("has_management_system")
    private String hasManagementSystem;

    @ApiModelProperty(value = "有无配置服务设施商业区域客流统计系统")
    @TableField("has_customer_flow_system")
    private String hasCustomerFlowSystem;

    @ApiModelProperty(value = "有无配置服务设施运营服务质量日常巡检系统")
    @TableField("has_check_system")
    private String hasCheckSystem;

    @ApiModelProperty(value = "是否有信息发布")
    @TableField("has_info_send")
    private String hasInfoSend;

    @ApiModelProperty(value = "公共场区是否全覆盖WIFI服务")
    @TableField("is_wifi_covered")
    private String isWifiCovered;

    @ApiModelProperty(value = "服务区名称")
    @TableField("server_name")
    private String serverName;

    @ApiModelProperty(value = "是否有清障救援服务")
    @TableField("has_clean_help")
    private String hasCleanHelp;

    @ApiModelProperty(value = "是否有救援呼叫服务")
    @TableField("has_help_call")
    private String hasHelpCall;

    @ApiModelProperty(value = "是否有救援直升机停机坪")
    @TableField("has_help_air")
    private String hasHelpAir;

    @ApiModelProperty(value = "'司机之家'配套设施")
    @TableField("driver_home_equipment")
    private String driverHomeEquipment;

    @ApiModelProperty(value = "助农兴农农副产品营销")
    @TableField("farm_product_sell")
    private String farmProductSell;

    @ApiModelProperty(value = "运营路段ID")
    @TableField("waysection_id")
    private String waysectionId;

    @ApiModelProperty(value = "设施ID")
    @TableField("facilities_id")
    private String facilitiesId;
}
