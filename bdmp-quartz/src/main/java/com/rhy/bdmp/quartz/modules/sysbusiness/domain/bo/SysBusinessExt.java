package com.rhy.bdmp.quartz.modules.sysbusiness.domain.bo;

import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author shuaichao
 * @create 2022-03-16 15:14
 */
@Data
@ApiModel("应用树形实体")
public class SysBusinessExt extends SysBusiness implements Serializable {
    @ApiModelProperty("是否有子节点")
    private int hasChildren;
    @ApiModelProperty("子节点")
    List<SysBusinessExt> sysBusinessList;
}
