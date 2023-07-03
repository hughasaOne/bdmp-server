package com.rhy.bdmp.open.modules.assets.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 收费站详情
 * @Date: 2021/9/27 9:18
 * @Version: 1.0.0
 */
@ApiModel(value = "收费站详情", description = "收费站详情")
@Data
public class TollStationDetailBO {

    @ApiModelProperty(value = "收费站名称")
    private String stationName;
    @ApiModelProperty(value = "桩号")
    private String pileNumber;
    @ApiModelProperty(value = "原ID")
    private String originId;
    @ApiModelProperty(value = "所属路段ID")
    private String belongSectionId;
    @ApiModelProperty(value = "路段编号")
    private String oriWaysectionNo;
    @ApiModelProperty(value = "所属路段")
    private String belongSectionName;
    @ApiModelProperty(value = "所属运营公司ID")
    private String belongCompanyId;
    @ApiModelProperty(value = "所属运营公司")
    private String belongCompanyName;
    @ApiModelProperty(value = "所属运营公司简称")
    private String belongCompanySName;
    @ApiModelProperty(value = "设施图片")
    private String facImg;
    @ApiModelProperty(value = "行政区域")
    private String district;

    @ApiModelProperty(value = "行政区域CODE")
    private String districtCode;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "是否集中集控点")
    private String isMonitor;

    @ApiModelProperty(value = "VR实景地址")
    private String mapUrl;

    @ApiModelProperty(value = "是否OTN站点")
    private String isOtnSite;

    @ApiModelProperty(value = "国干站点类型")
    private String nationalSiteType;

    @ApiModelProperty(value = "省干站点类型")
    private String provincialSiteType;

    @ApiModelProperty(value = "站点类型")
    private String siteCategory;

    @ApiModelProperty(value = "机房形式")
    private String computerRoomForm;

    @ApiModelProperty(value = "otn备注")
    private String otnRemark;

    @ApiModelProperty(value = "百度编码")
    private String baiduCode;

    @ApiModelProperty(value = "入口车道")
    private String totalEntranceDriveWay;

    @ApiModelProperty(value = "出口车道")
    private String totalExitDriveWay;

    @ApiModelProperty(value = "ETC入口车道")
    private String etcEntranceDriveWay;
    @ApiModelProperty(value = "ETC出口车道")
    private String etcExitDriveWay;
    @ApiModelProperty(value = "MTC入口车道")
    private String mtcEntranceDriveWay;
    @ApiModelProperty(value = "MTC出口车道")
    private String mtcExitDriveWay;

    @ApiModelProperty(value = "设备总数")
    private String deviceTotalNum;
    @ApiModelProperty(value = "正常运行设备数")
    private String normalRunningDeviceNum;
    @ApiModelProperty(value = "设备故障数")
    private String abnormalDeviceNum;

    @ApiModelProperty(value = "位置描述")
    private String locationDesc;

    @ApiModelProperty(value = "省内编号")
    private String stationProvinceId;

    @ApiModelProperty(value = "国标编码")
    private String stationCountryId;

    @ApiModelProperty(value = "车道详情")
    private Map<String, Object> laneData;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;

    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;

    @ApiModelProperty(value = "集控点联系电话")
    private String monitorTelPhone;

    @ApiModelProperty(value = "员工列表")
    private List<Map<String,Object>> employees;
}
