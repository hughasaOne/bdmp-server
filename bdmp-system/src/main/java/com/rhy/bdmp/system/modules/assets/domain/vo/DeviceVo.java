package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
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
import java.util.List;

/**
 * @description 设备 实体
 * @author jiangzhimin
 * @date 2021-04-15 19:51
 * @version V1.0
 **/
@ApiModel(value="设备", description="设备信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId("device_id")
    private String deviceId;

    @ApiModelProperty(value = "设备小类id")
    private String deviceDictId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备别名")
    private String deviceAlias;

    @ApiModelProperty(value = "设备代码(设备编号)")
    private String deviceCode;

    @ApiModelProperty(value = "设备类型")
    private String deviceType;

    @ApiModelProperty(value = "设备小类")
    private String deviceTypeSub;

    @ApiModelProperty(value = "所属设施ID")
    private String facilitiesId;

    @ApiModelProperty(value = "所属路段ID")
    private String waysectionId;

    @ApiModelProperty(value = "设备连接方式", example = "1")
    private Integer deviceConn;

    @ApiModelProperty(value = "安装位置")
    private String location;

    @ApiModelProperty(value = "方向(1:上行、2:下行、3:双向)")
    private String direction;

    @ApiModelProperty(value = "系统ID")
    private String systemId;

    @ApiModelProperty(value = "是否隧道设备(0:主线设备、1:隧道设备)")
    private Integer isTunnel;

    @ApiModelProperty(value = "网关地址1")
    private String inGatewayAddr;

    @ApiModelProperty(value = "网关地址2")
    private String outGatewayAddr;

    @ApiModelProperty(value = "子网掩码")
    private String subnetMask;

    @ApiModelProperty(value = "网络IP")
    private String ip;

    @ApiModelProperty(value = "设备端口")
    private String port;

    @ApiModelProperty(value = "设备登录名")
    private String username;

    @ApiModelProperty(value = "设备登录密码")
    private String password;

    @ApiModelProperty(value = "命令延时(秒)")
    private Integer cmdDelay;

    @ApiModelProperty(value = "前端使用设备定位字段")
    private BigDecimal topDistance;

    @ApiModelProperty(value = "前端使用设备定位字段")
    private BigDecimal leftDistance;

    @ApiModelProperty(value = "地图定位x")
    private BigDecimal positionX;

    @ApiModelProperty(value = "地图定位y")
    private BigDecimal positionY;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;

    @ApiModelProperty(value = "起点桩号_des")
    private String beginStakeNoDes;

    @ApiModelProperty(value = "起点桩号_k", example = "1")
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_des")
    private String endStakeNoDes;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    private Integer endStakeNoM;

    @ApiModelProperty(value = "中心桩号")
    private String centerOffNo;

    @ApiModelProperty(value = "中心桩号_des")
    private String centerOffNoDes;

    @ApiModelProperty(value = "中心桩号_k", example = "1")
    private Integer centerOffNoK;

    @ApiModelProperty(value = "中心桩号_m", example = "1")
    private Integer centerOffNoM;

    @ApiModelProperty(value = "距中心线偏移量-左")
    private BigDecimal lCenterOffsetValue;

    @ApiModelProperty(value = "距中心线偏移量-右")
    private BigDecimal rCenterOffsetValue;

    @ApiModelProperty(value = "二维码")
    private String qrCodeUrl;

    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @ApiModelProperty(value = "额定功率(单位:KW)", example = "1")
    private Integer devicePower;

    @ApiModelProperty(value = "是否联动", example = "1")
    private Integer linkage;

    @ApiModelProperty(value = "产品序列号")
    private String seriaNumber;

    @ApiModelProperty(value = "规格")
    private String specifications;

    @ApiModelProperty(value = "品种")
    private String variety;

    @ApiModelProperty(value = "重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "重量单位")
    private String unit;

    @ApiModelProperty(value = "设备品牌ID")
    private String brandId;

    @ApiModelProperty(value = "设备品牌")
    private String brandName;

    @ApiModelProperty(value = "材质")
    private String material;

    @ApiModelProperty(value = "供配电")
    private String gpd;

    @ApiModelProperty(value = "光缆")
    private String opticalCable;

    @ApiModelProperty(value = "布局方式")
    private String layoutModel;

    @ApiModelProperty(value = "服务年限")
    private String serviceYear;

    @ApiModelProperty(value = "设备ID(旧)")
    private String deviceIdOld;

    @ApiModelProperty(value = "设备ID来源(旧)")
    private String deviceIdSource;

    @ApiModelProperty(value = "产权单位ID")
    private String propertyRightUnitId;

    @ApiModelProperty(value = "管理单位ID")
    private String manageUnitId;

    @ApiModelProperty(value = "管理部门ID")
    private String manageDepartmentId;

    @ApiModelProperty(value = "责任部门ID")
    private String zrDepartmentId;

    @ApiModelProperty(value = "使用部门ID")
    private String useDepartmentId;

    @ApiModelProperty(value = "责任人ID")
    private String zrUserId;

    @ApiModelProperty(value = "责任人")
    private String zrUserName;

    @ApiModelProperty(value = "使用者ID")
    private String useUserId;

    @ApiModelProperty(value = "使用者")
    private String useUserName;

    @ApiModelProperty(value = "资产编码")
    private String assetsNo;

    @ApiModelProperty(value = "资产来源")
    private String assetsSource;

    @ApiModelProperty(value = "养护编码")
    private String maintainNo;

    @ApiModelProperty(value = "管理状态(旧)")
    private String oldManageStatus;

    @ApiModelProperty(value = "管理状态")
    private String manageStatus;

    @ApiModelProperty(value = "工作状态(旧)")
    private String oldWorkStatus;

    @ApiModelProperty(value = "工作状态")
    private String workStatus;

    @ApiModelProperty(value = "建卡日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date recordDate;

    @ApiModelProperty(value = "开始使用日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startUseDate;

    @ApiModelProperty(value = "安装日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date installDate;

    @ApiModelProperty(value = "步长", example = "1")
    private Integer step;

    @ApiModelProperty(value = "使用年限")
    private String useYear;

    @ApiModelProperty(value = "供应商ID")
    private String supplierId;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "生产商ID")
    private String manufacturerId;

    @ApiModelProperty(value = "生产商")
    private String manufacturer;

    @ApiModelProperty(value = "制造国别")
    private String madeCountry;

    @ApiModelProperty(value = "出厂编号")
    private String factoryNumber;

    @ApiModelProperty(value = "出厂序列号")
    private String factoryNo;

    @ApiModelProperty(value = "出厂日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date factoryDate;

    @ApiModelProperty(value = "厂家保修有效起始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date effectiveStartDate;

    @ApiModelProperty(value = "厂家保修有效终止日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date effectiveEndDate;

    @ApiModelProperty(value = "图号")
    private String drawingNumber;

    @ApiModelProperty(value = "设备图片")
    private String pic;

    @ApiModelProperty(value = "操作系统")
    private String operatingSystem;

    @ApiModelProperty(value = "数据库")
    private String databasess;

    @ApiModelProperty(value = "是否为新件", example = "1")
    private Integer isNewObj;

    @ApiModelProperty(value = "购置部门ID")
    private String purchaseDeptId;

    @ApiModelProperty(value = "购置部门")
    private String purchaseDeptName;

    @ApiModelProperty(value = "购置人ID")
    private String purchaseUserId;

    @ApiModelProperty(value = "购置人")
    private String purchaseUserName;

    @ApiModelProperty(value = "购买日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date buyDate;

    @ApiModelProperty(value = "购买价")
    private BigDecimal buyPrice;

    @ApiModelProperty(value = "验收日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date checkDate;

    @ApiModelProperty(value = "发票号码")
    private String invoiceNo;

    @ApiModelProperty(value = "供应联系人")
    private String supplierContact;

    @ApiModelProperty(value = "联系方式")
    private String supplierContactNo;

    @ApiModelProperty(value = "验收情况")
    private String checkSituation;

    @ApiModelProperty(value = "是否有备件:是/否", example = "1")
    private Integer existSpare;

    @ApiModelProperty(value = "合同编号")
    private String contractNo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;

    @ApiModelProperty(value = "数据状态(启用/停用)", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;

    @ApiModelProperty(value = "路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "设施名称")
    private String facilitiesName;

    @ApiModelProperty(value = "设备类型")
    private String deviceTypeName;

    @ApiModelProperty(value = "所属系统")
    private String systemName;

    @ApiModelProperty(value = "扩展表")
    private List<Object> deviceExt;
}
