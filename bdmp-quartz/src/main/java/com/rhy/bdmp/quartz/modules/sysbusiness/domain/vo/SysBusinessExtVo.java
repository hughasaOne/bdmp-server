package com.rhy.bdmp.quartz.modules.sysbusiness.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.bo.SysBusinessExt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author PSQ
 */
@Data
@ApiModel("应用树形vo对象")
public class SysBusinessExtVo implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "父ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty(value = "目录名称")
    private String label;

    @ApiModelProperty(value = "所属应用id")
    @TableField("app_id")
    private String appId;

    @ApiModelProperty("节点类型")
    private String nodeType;

    @ApiModelProperty("主键")
    @TableField("business_id")
    private String businessId;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("子节点")
    private List<SysBusinessExtVo> children;

}
