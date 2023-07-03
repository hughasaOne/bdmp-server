package com.rhy.bdmp.open.modules.assets.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description 视频资源(云台) 实体
 * @author yanggj
 * @date 2021-10-20 15:31
 * @version V1.0
 **/
@ApiModel(value="视频资源(云台)", description="视频资源(云台)信息")
@Data
public class CameraResourceYtBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "视频资源ID")
    private String id;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "父节点ID")
    private String parentId;

    @ApiModelProperty(value = "资源类型(10:目录、20:资源)", example = "1")
    private Integer type;

    @ApiModelProperty(value = "视频资源数", example = "1")
    private Integer carSum;

    @ApiModelProperty(value = "层级（如：1:湖北交投、2:运营集团、3:运营公司、4:路段、5:设施、6:视频资源）")
    private String cameraLevel;

    @ApiModelProperty(value = "视频资源状态(10:在线、20:离线)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "是否有云台控制(0:无，1:有)", example = "1")
    private Integer hasPtz;

    @ApiModelProperty(value = "坐标X")
    private String coordX;

    @ApiModelProperty(value = "坐标Y")
    private String coordY;

    @ApiModelProperty(value = "排序", example = "1")
    private Long sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField("update_by")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "运营路段ID")
    private String wayId;

    @ApiModelProperty(value = "路段编号")
    private String oriWaysectionNo;

    @ApiModelProperty(value = "运营路段名称")
    private String wayName;

    @ApiModelProperty(value = "公司名称")
    private String compName;

    @ApiModelProperty(value = "设施ID")
    private String geographyinfoId;

    @ApiModelProperty(value = "安装位置")
    private String location;

    @ApiModelProperty(value = "数据来源(1.科技, 2.楚天)")
    private String dataSource;

    @ApiModelProperty(value = "设备状态(1:在线, 0:离线)")
    private String onlineStatus;

    @ApiModelProperty(value = "位置类型(10:收费站、11:收费站广场、 20:隧道、  30:外场、40:服务区)",example = "1")
    private Integer locationType;

    @ApiModelProperty(value = "公司简称")
    private String shortName;

}
