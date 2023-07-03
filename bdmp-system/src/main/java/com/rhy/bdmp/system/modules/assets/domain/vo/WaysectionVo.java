package com.rhy.bdmp.system.modules.assets.domain.vo;

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
 * @auther xiabei
 * @Description
 * @date 2021/4/23
 */
@ApiModel(value="路段", description="路段信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WaysectionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路段ID")
    private String waysectionId;

    @ApiModelProperty(value = "路段名称")
    private String waysectionName;

    @ApiModelProperty(value = "路段简称")
    private String waysectionSName;

    @ApiModelProperty(value = "路段代码")
    private String waysectionCode;

    @ApiModelProperty(value = "区域编号")
    private String areaNo;

    @ApiModelProperty(value = "所属路网ID")
    private String waynetId;

    @ApiModelProperty(value = "管理机构ID")
    private String manageId;

    @ApiModelProperty(value = "所属收费区域")
    private String tollRegion;

    @ApiModelProperty(value = "所属行政区域")
    private String barrio;

    @ApiModelProperty(value = "里程数")
    private BigDecimal mileage;

    @ApiModelProperty(value = "管理类型(自建自营,委托经营,代管)")
    private String manageType;

    @ApiModelProperty(value = "路段队伍ID")
    private String roadTeamId;

    @ApiModelProperty(value = "路段队伍信息")
    private String roadTeamInfo;

    @ApiModelProperty(value = "求援队伍ID")
    private String rescueTeamId;

    @ApiModelProperty(value = "求援队伍信息")
    private String rescueTeamInfo;

    @ApiModelProperty(value = "是否全程监控", example = "1")
    private Integer ifWholeMonitor;

    @ApiModelProperty(value = "是否收费", example = "1")
    private Integer ifCharge;

    @ApiModelProperty(value = "是否采集", example = "1")
    private Integer isCollection;

    @ApiModelProperty(value = "是否为数据报送主体", example = "1")
    private Integer isSendDataBody;

    @ApiModelProperty(value = "起点站ID")
    private String beginStationId;

    @ApiModelProperty(value = "中心站ID")
    private String centreStationId;

    @ApiModelProperty(value = "终点站ID")
    private String endStationId;

    @ApiModelProperty(value = "起点桩号")
    private String beginStakeNo;

    @ApiModelProperty(value = "起点桩号_k", example = "1")
    private Integer beginStakeNoK;

    @ApiModelProperty(value = "起点桩号_m", example = "1")
    private Integer beginStakeNoM;

    @ApiModelProperty(value = "终点桩号")
    private String endStakeNo;

    @ApiModelProperty(value = "终点桩号_k", example = "1")
    private Integer endStakeNoK;

    @ApiModelProperty(value = "终点桩号_m", example = "1")
    private Integer endStakeNoM;

    @ApiModelProperty(value = "路段ID(旧)")
    private String waysectionIdOld;

    @ApiModelProperty(value = "路段ID来源(旧)")
    private String waysectionIdSource;

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

    @ApiModelProperty(value = "路网名称")
    private String waynetName;
    @ApiModelProperty(value = "路网简称")
    private String waynetSName;

    @ApiModelProperty(value = "管理机构名称")
    private String manageName;
    @ApiModelProperty(value = "管理机构简称")
    private String manageSName;
}
