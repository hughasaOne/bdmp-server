package com.rhy.bdmp.system.modules.gateway.domain.vo;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author author
 * @version V1.0
 * @description 实体
 * @date 2022-05-30 17:13
 **/
@ApiModel(value = "gateway配置", description = "信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_gateway_route")
public class GatewayRouteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "网关id")
    @TableId("id")
    private String id;


    @ApiModelProperty(value = "网关id")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "路由地址（可选服务名/ip+端口）")
    @TableField("uri")
    private String uri;

    @ApiModelProperty(value = "断言数组对象")
    @TableField("predicates")
    private String predicates;

    @ApiModelProperty(value = "过滤器数组对象")
    @TableField("filters")
    private String filters;

    @ApiModelProperty(value = "优先级", example = "1")
    @TableField("`order`")
    private Integer order;

    @ApiModelProperty(value = "环境")
    @TableField("env_id")
    private String envId;

    @ApiModelProperty(value = "附加信息字段")
    @TableField("metadata")
    private String metadata;

    @ApiModelProperty(value = "断言")
    @TableField(exist = false)
    private List<ListVO> assertList;

    @ApiModelProperty(value = "过滤器")
    @TableField(exist = false)
    private List<ListVO> filterList;

    @ApiModelProperty(value = "附加信息字段")
    @TableField(exist = false)
    private List<ListVO> metadataList;

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
