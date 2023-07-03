package com.rhy.bdmp.system.modules.analysis.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用户权限明细实体类
 *
 * @author 庞盛权
 */
@Data
public class UserPermissionsVO {

    @ApiModelProperty("运营公司id")
    private String orgId;
    @ApiModelProperty("运营公司名称")
    private String orgName;
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("管理员")
    private String isAdmin;
    @ApiModelProperty("台账权限类型 1机构 2路段 3设施")
    private String dataPermissionsLevel;

    @ApiModelProperty("菜单权限id")
    private List<String> menuId;
    @ApiModelProperty("菜单权限名称")
    private List<String> menuName;

    @ApiModelProperty("以app分组菜单")
    private Map<String, List<String>> appGroupMenu;


    @ApiModelProperty("应用访问权限id")
    private List<String> appId;
    @ApiModelProperty("应用访问名称")
    private List<String> appName;

    @ApiModelProperty("应用数据权限id")
    private List<String> appDataId;
    @ApiModelProperty("应用权限名称")
    private List<String> appDataName;

    @ApiModelProperty("台账数据id")
    private List<String> dataId;
    @ApiModelProperty("台账数据名称")
    private List<String> dataName;

    @ApiModelProperty("以公司分组台账权限")
    private Map<String, List<String>> orgGroupData;

    @ApiModelProperty("部门数据id")
    private List<String> departmentId;
    @ApiModelProperty("部门数据名称")
    private List<String> departmentName;

    /**
     * 临时数据参数
     */
    private String valueId;
    private String valueName;
    private String tempValueName;

}
