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
 * @description  实体
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@ApiModel(value="", description="信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_facilities_gantry")
public class FacilitiesGantry extends FacilitiesExt implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设施ID")
    @TableId("gantry_id")
    private String gantryId;

    @ApiModelProperty(value = "收费单元")
    @TableField("fee_unit")
    private String feeUnit;

    @ApiModelProperty(value = "门架编号")
    @TableField("gantry_code")
    private String gantryCode;

    @ApiModelProperty(value = "行驶方向")
    @TableField("road_section")
    private String roadSection;

    @ApiModelProperty(value = "省份", example = "1")
    @TableField("province")
    private Integer province;

    @ApiModelProperty(value = "出/入口类型")
    @TableField("in_out")
    private String inOut;

    @ApiModelProperty(value = "门架类型")
    @TableField("gantry_type")
    private String gantryType;

    @ApiModelProperty(value = "门架排序")
    @TableField("gantry_order")
    private String gantryOrder;

    @ApiModelProperty(value = "方向")
    @TableField("derection")
    private String derection;

    @ApiModelProperty(value = "断面")
    @TableField("section")
    private String section;

    @ApiModelProperty(value = "门架实际编号")
    @TableField("real_gantry_id")
    private String realGantryId;

    @ApiModelProperty(value = "HEX码")
    @TableField("hex")
    private String hex;

    @ApiModelProperty(value = "rsu厂商名称")
    @TableField("rsu_name")
    private String rsuName;

    @ApiModelProperty(value = "牌识厂商名称")
    @TableField("vlp_name")
    private String vlpName;

    @ApiModelProperty(value = "主工控机IP")
    @TableField("ipc_ip1")
    private String ipcIp1;

    @ApiModelProperty(value = "备工控机IP")
    @TableField("ipc_ip2")
    private String ipcIp2;

    @ApiModelProperty(value = "工控机虚拟IP")
    @TableField("ipc_v_ip")
    private String ipcVIp;

    @ApiModelProperty(value = "主服务器IP")
    @TableField("server_ip1")
    private String serverIp1;

    @ApiModelProperty(value = "备服务器IP")
    @TableField("server_ip2")
    private String serverIp2;

    @ApiModelProperty(value = "服务器虚拟IP")
    @TableField("server_v_ip")
    private String serverVIp;

    @ApiModelProperty(value = "rsu控制器IP")
    @TableField("rsu_ip")
    private String rsuIp;

    @ApiModelProperty(value = "时钟同步服务器地址")
    @TableField("ntp_ip")
    private String ntpIp;

    @ApiModelProperty(value = "PSAM地址")
    @TableField("psam_ip")
    private String psamIp;

    @ApiModelProperty(value = "站所编号")
    @TableField("organ_id")
    private String organId;

    @ApiModelProperty(value = "站所名称")
    @TableField("station")
    private String station;

    @ApiModelProperty(value = "所属分中心")
    @TableField("center")
    private String center;

    @ApiModelProperty(value = "所属业主")
    @TableField("company")
    private String company;

    @TableField("user_mark")
    private Integer userMark;

    @ApiModelProperty(value = "分中心编号")
    @TableField("center_id")
    private String centerId;

    @ApiModelProperty(value = "虚拟门架标志", example = "1")
    @TableField("virtual_gantry_flag")
    private Integer virtualGantryFlag;

    @ApiModelProperty(value = "门架工控机数量", example = "1")
    @TableField("ipc_count")
    private Integer ipcCount;

    @ApiModelProperty(value = "门架服务器数量", example = "1")
    @TableField("server_count")
    private Integer serverCount;

    @ApiModelProperty(value = "是否省界", example = "1")
    @TableField("is_province")
    private Integer isProvince;

    @ApiModelProperty(value = "设施ID")
    @TableField("facilities_id")
    private String facilitiesId;

    @ApiModelProperty(value = "邻省")
    @TableField("nei_provinces")
    private String neiProvinces;

    @ApiModelProperty(value = "邻省通往方向")
    @TableField("loc_direction")
    private String locDirection;

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
