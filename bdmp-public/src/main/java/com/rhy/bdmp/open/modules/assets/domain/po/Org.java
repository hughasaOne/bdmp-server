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
 * @description 组织机构 实体
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@ApiModel(value="组织机构", description="组织机构信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_sys_org")
public class Org implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织ID")
    @TableId("org_id")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    @TableField("org_name")
    private String orgName;

    @ApiModelProperty(value = "组织代码")
    @TableField("org_code")
    private String orgCode;

    @ApiModelProperty(value = "组织简称")
    @TableField("org_short_name")
    private String orgShortName;

    @ApiModelProperty(value = "上级ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "组织类型")
    @TableField("org_type")
    private String orgType;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "数据ID")
    @TableField("id_old")
    private String idOld;

    @ApiModelProperty(value = "数据源")
    @TableField("id_source")
    private String idSource;

    @ApiModelProperty(value = "关联地图")
    @TableField("node_id")
    private String nodeId;

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

    @ApiModelProperty(value = "是否是叶子节点")
    @TableField(exist = false)
    private Boolean isLeaf;
}
