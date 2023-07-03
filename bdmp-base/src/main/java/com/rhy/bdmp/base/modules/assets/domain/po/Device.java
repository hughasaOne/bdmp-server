package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 设备 实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="设备", description="设备信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId("device_id")
    private String deviceId;

    @ApiModelProperty(value = "设备字典id")
    @TableField("device_dict_id")
    private String deviceDictId;

    @ApiModelProperty(value = "排序id")
    @TableField("sort_id")
    private String sortId;

    @ApiModelProperty(value = "设备名称")
    @TableField("device_name")
    private String deviceName;

    @ApiModelProperty(value = "设备别名")
    @TableField("device_alias")
    private String deviceAlias;

    @ApiModelProperty(value = "设备代码(设备编号)")
    @TableField("device_code")
    private String deviceCode;

    @ApiModelProperty(value = "设备里程")
    @TableField("device_mileage")
    private BigDecimal deviceMileage;

    @ApiModelProperty(value = "设备类型")
    @TableField("device_type")
    private String deviceType;

    @ApiModelProperty(value = "设备小类")
    @TableField("device_type_sub")
    private String deviceTypeSub;

    @ApiModelProperty(value = "所属设施ID")
    @TableField("facilities_id")
    private String facilitiesId;

    @ApiModelProperty(value = "所属路段ID")
    @TableField("waysection_id")
    private String waysectionId;

    @ApiModelProperty(value = "设备连接方式", example = "1")
    @TableField("device_conn")
    private Integer deviceConn;

    @ApiModelProperty(value = "安装位置")
    @TableField("location")
    private String location;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    @TableField("direction")
    private String direction;

    @ApiModelProperty(value = "系统ID")
    @TableField("system_id")
    private String systemId;

    @ApiModelProperty(value = "是否隧道设备(0:主线设备、1:隧道设备)", example = "1")
    @TableField("is_tunnel")
    private Integer isTunnel;

    @ApiModelProperty(value = "网关地址1")
    @TableField("in_gateway_addr")
    private String inGatewayAddr;

    @ApiModelProperty(value = "网关地址2")
    @TableField("out_gateway_addr")
    private String outGatewayAddr;

    @ApiModelProperty(value = "子网掩码")
    @TableField("subnet_mask")
    private String subnetMask;

    @ApiModelProperty(value = "网络IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "设备端口")
    @TableField("port")
    private String port;

    @ApiModelProperty(value = "设备登录名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "设备登录密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "命令延时(秒)", example = "1")
    @TableField("cmd_delay")
    private Integer cmdDelay;

    @ApiModelProperty(value = "前端使用设备定位字段")
    @TableField("top_distance")
    private BigDecimal topDistance;

    @ApiModelProperty(value = "前端使用设备定位字段")
    @TableField("left_distance")
    private BigDecimal leftDistance;

    @ApiModelProperty(value = "地图定位X")
    @TableField("position_x")
    private BigDecimal positionX;

    @ApiModelProperty(value = "地图定位Y")
    @TableField("position_y")
    private BigDecimal positionY;

    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private String latitude;

    @ApiModelProperty(value = "起点桩号")
    @TableField("begin_stake_no")
    private String beginStakeNo;

    @ApiModelProperty(value = "起点桩号_des")
    @TableField("begin_stake_no_des")
    private String beginStakeNoDes;

    @ApiModelProperty(value = "起点桩号_k", example = "1")
    @TableField(value = "begin_stake_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    @TableField(value = "begin_stake_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    @TableField("end_stake_no")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_des")
    @TableField("end_stake_no_des")
    private String endStakeNoDes;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    @TableField(value = "end_stake_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    @TableField(value = "end_stake_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer endStakeNoM;

    @ApiModelProperty(value = "中心桩号")
    @TableField("center_off_no")
    private String centerOffNo;

    @ApiModelProperty(value = "中心桩号_des")
    @TableField("center_off_no_des")
    private String centerOffNoDes;

    @ApiModelProperty(value = "中心桩号_k", example = "1")
    @TableField(value = "center_off_no_k",updateStrategy = FieldStrategy.IGNORED)
    private Integer centerOffNoK;

    @ApiModelProperty(value = "中心桩号_m", example = "1")
    @TableField(value = "center_off_no_m",updateStrategy = FieldStrategy.IGNORED)
    private Integer centerOffNoM;

    @ApiModelProperty(value = "距中心线偏移量-左")
    @TableField("l_center_offset_value")
    private BigDecimal lCenterOffsetValue;

    @ApiModelProperty(value = "距中心线偏移量-右")
    @TableField("r_center_offset_value")
    private BigDecimal rCenterOffsetValue;

    @ApiModelProperty(value = "二维码")
    @TableField("qr_code_url")
    private String qrCodeUrl;

    @ApiModelProperty(value = "设备型号")
    @TableField("device_model")
    private String deviceModel;

    @ApiModelProperty(value = "额定功率(单位:KW)", example = "1")
    @TableField("device_power")
    private Integer devicePower;

    @ApiModelProperty(value = "是否联动", example = "1")
    @TableField("linkage")
    private Integer linkage;

    @ApiModelProperty(value = "产品序列号")
    @TableField("seria_number")
    private String seriaNumber;

    @ApiModelProperty(value = "规格")
    @TableField("specifications")
    private String specifications;

    @ApiModelProperty(value = "品种")
    @TableField("variety")
    private String variety;

    @ApiModelProperty(value = "重量")
    @TableField("weight")
    private BigDecimal weight;

    @ApiModelProperty(value = "重量单位")
    @TableField("unit")
    private String unit;

    @ApiModelProperty(value = "设备品牌ID")
    @TableField("brand_id")
    private String brandId;

    @ApiModelProperty(value = "设备品牌")
    @TableField("brand_name")
    private String brandName;

    @ApiModelProperty(value = "材质")
    @TableField("material")
    private String material;

    @ApiModelProperty(value = "供配电")
    @TableField("gpd")
    private String gpd;

    @ApiModelProperty(value = "光缆")
    @TableField("optical_cable")
    private String opticalCable;

    @ApiModelProperty(value = "布局方式")
    @TableField("layout_model")
    private String layoutModel;

    @ApiModelProperty(value = "服务年限")
    @TableField("service_year")
    private String serviceYear;

    @ApiModelProperty(value = "设备ID(旧)")
    @TableField("device_id_old")
    private String deviceIdOld;

    @ApiModelProperty(value = "设备ID来源(旧)")
    @TableField("device_id_source")
    private String deviceIdSource;

    @ApiModelProperty(value = "产权单位ID")
    @TableField("property_right_unit_id")
    private String propertyRightUnitId;

    @ApiModelProperty(value = "管理单位ID")
    @TableField("manage_unit_id")
    private String manageUnitId;

    @ApiModelProperty(value = "管理部门ID")
    @TableField("manage_department_id")
    private String manageDepartmentId;

    @ApiModelProperty(value = "责任部门ID")
    @TableField("zr_department_id")
    private String zrDepartmentId;

    @ApiModelProperty(value = "使用部门ID")
    @TableField("use_department_id")
    private String useDepartmentId;

    @ApiModelProperty(value = "责任人ID")
    @TableField("zr_user_id")
    private String zrUserId;

    @ApiModelProperty(value = "责任人")
    @TableField("zr_user_name")
    private String zrUserName;

    @ApiModelProperty(value = "使用者ID")
    @TableField("use_user_id")
    private String useUserId;

    @ApiModelProperty(value = "使用者")
    @TableField("use_user_name")
    private String useUserName;

    @ApiModelProperty(value = "资产编码")
    @TableField("assets_no")
    private String assetsNo;

    @ApiModelProperty(value = "资产来源")
    @TableField("assets_source")
    private String assetsSource;

    @ApiModelProperty(value = "养护编码")
    @TableField("maintain_no")
    private String maintainNo;

    @ApiModelProperty(value = "管理状态(旧)")
    @TableField("old_manage_status")
    private String oldManageStatus;

    @ApiModelProperty(value = "管理状态")
    @TableField("manage_status")
    private String manageStatus;

    @ApiModelProperty(value = "工作状态(旧)")
    @TableField("old_work_status")
    private String oldWorkStatus;

    @ApiModelProperty(value = "工作状态")
    @TableField("work_status")
    private String workStatus;

    @ApiModelProperty(value = "建卡日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("record_date")
    private Date recordDate;

    @ApiModelProperty(value = "开始使用日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("start_use_date")
    private Date startUseDate;

    @ApiModelProperty(value = "安装日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("install_date")
    private Date installDate;

    @ApiModelProperty(value = "步长", example = "1")
    @TableField("step")
    private Integer step;

    @ApiModelProperty(value = "使用年限")
    @TableField("use_year")
    private String useYear;

    @ApiModelProperty(value = "供应商ID")
    @TableField("supplier_id")
    private String supplierId;

    @ApiModelProperty(value = "供应商")
    @TableField("supplier")
    private String supplier;

    @ApiModelProperty(value = "生产商ID")
    @TableField("manufacturer_id")
    private String manufacturerId;

    @ApiModelProperty(value = "生产商")
    @TableField("manufacturer")
    private String manufacturer;

    @ApiModelProperty(value = "制造国别")
    @TableField("made_country")
    private String madeCountry;

    @ApiModelProperty(value = "出厂编号")
    @TableField("factory_number")
    private String factoryNumber;

    @ApiModelProperty(value = "出厂序列号")
    @TableField("factory_no")
    private String factoryNo;

    @ApiModelProperty(value = "出厂日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("factory_date")
    private Date factoryDate;

    @ApiModelProperty(value = "厂家保修有效起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("effective_start_date")
    private Date effectiveStartDate;

    @ApiModelProperty(value = "厂家保修有效终止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("effective_end_date")
    private Date effectiveEndDate;

    @ApiModelProperty(value = "图号")
    @TableField("drawing_number")
    private String drawingNumber;

    @ApiModelProperty(value = "设备图片")
    @TableField("pic")
    private String pic;

    @ApiModelProperty(value = "操作系统")
    @TableField("operating_system")
    private String operatingSystem;

    @ApiModelProperty(value = "数据库")
    @TableField("databasess")
    private String databasess;

    @ApiModelProperty(value = "是否为新件", example = "1")
    @TableField("is_new_obj")
    private Integer isNewObj;

    @ApiModelProperty(value = "购置部门ID")
    @TableField("purchase_dept_id")
    private String purchaseDeptId;

    @ApiModelProperty(value = "购置部门")
    @TableField("purchase_dept_name")
    private String purchaseDeptName;

    @ApiModelProperty(value = "购置人ID")
    @TableField("purchase_user_id")
    private String purchaseUserId;

    @ApiModelProperty(value = "购置人")
    @TableField("purchase_user_name")
    private String purchaseUserName;

    @ApiModelProperty(value = "购买日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("buy_date")
    private Date buyDate;

    @ApiModelProperty(value = "购买价")
    @TableField("buy_price")
    private BigDecimal buyPrice;

    @ApiModelProperty(value = "验收日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("check_date")
    private Date checkDate;

    @ApiModelProperty(value = "发票号码")
    @TableField("invoice_no")
    private String invoiceNo;

    @ApiModelProperty(value = "供应联系人")
    @TableField("supplier_contact")
    private String supplierContact;

    @ApiModelProperty(value = "联系方式")
    @TableField("supplier_contact_no")
    private String supplierContactNo;

    @ApiModelProperty(value = "验收情况")
    @TableField("check_situation")
    private String checkSituation;

    @ApiModelProperty(value = "是否有备件:是/否", example = "1")
    @TableField("exist_spare")
    private Integer existSpare;

    @ApiModelProperty(value = "合同编号")
    @TableField("contract_no")
    private String contractNo;

    @ApiModelProperty(value = "协议适用范围")
    @TableField("protocol_apply_scope")
    private String protocolApplyScope;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "采集类型(0:无数据采集、1:被动采集、2:主动推送、3：可采可推)")
    @TableField("data_collect_type")
    private Integer dataCollectType;

    @ApiModelProperty(value = "显示分辨率")
    @TableField("display_ratio")
    private String displayRatio;


    @ApiModelProperty(value = "显示色彩1:全彩、2：双基色")
    @TableField("display_color")
    private Integer displayColor;

    @ApiModelProperty(value = "设备所属车道号")
    @TableField("lane_num")
    private Integer laneNum;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Long sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    @TableField("datastatusid")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "方向描述")
    @TableField("direction_desc")
    private String directionDesc;
}
