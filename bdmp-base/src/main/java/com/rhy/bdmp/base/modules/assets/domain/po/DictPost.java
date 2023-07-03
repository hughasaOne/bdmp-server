package com.rhy.bdmp.base.modules.assets.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 岗位字典 实体
 * @author weicaifu
 * @date 2022-03-16 09:22
 * @version V1.0
 **/
@ApiModel(value="岗位字典", description="岗位字典信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_assets_dict_post")
public class DictPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位ID")
    @TableId("post_id")
    private String postId;

    @ApiModelProperty(value = "岗位名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "岗位编码")
    @TableField("code")
    private String code;

    @ApiModelProperty(value = "所属机构类型编码")
    @TableField("org_type_code")
    private String orgTypeCode;

    @ApiModelProperty(value = "排序", example = "1")
    @TableField(value = "sort",fill = FieldFill.INSERT)
    private Long sort;

    @ApiModelProperty(value = "数据状态", example = "1")
    @TableField(value = "datastatusid",fill = FieldFill.INSERT)
    private Integer datastatusid;

    @ApiModelProperty(value = "创建者")
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新者")
    @TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
