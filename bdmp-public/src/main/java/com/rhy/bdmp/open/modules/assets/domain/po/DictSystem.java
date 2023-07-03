package com.rhy.bdmp.open.modules.assets.domain.po;

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
import java.util.Date;

/**
 * @description 系统字典 实体
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
@ApiModel(value="系统字典", description="系统字典信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_dict_system")
public class DictSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "系统ID")
    @TableId("system_id")
    private String systemId;

    @ApiModelProperty(value = "编号")
    @TableField("system_no")
    private String systemNo;

    @ApiModelProperty(value = "序列号")
    @TableField("system_serial")
    private String systemSerial;

    @ApiModelProperty(value = "名称")
    @TableField("system_name")
    private String systemName;

    @ApiModelProperty(value = "类型")
    @TableField("system_type")
    private String systemType;

    @ApiModelProperty(value = "父节点")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "数据源")
    @TableField("id_source")
    private String idSource;

    @ApiModelProperty(value = "数据ID")
    @TableField("id_old")
    private String idOld;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField("sort")
    private Long sort;

    @ApiModelProperty(value = "数据状态", example = "1")
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
}
