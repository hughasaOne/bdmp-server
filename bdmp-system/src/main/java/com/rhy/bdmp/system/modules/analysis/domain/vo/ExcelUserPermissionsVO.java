package com.rhy.bdmp.system.modules.analysis.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;


/**
 * 导出结构下用户权限明细Excel
 *
 * @author PSQ
 */
@HeadStyle(fillPatternType = FillPatternType.NO_FILL,
        horizontalAlignment = HorizontalAlignment.CENTER,
        verticalAlignment = VerticalAlignment.CENTER
)
@HeadFontStyle(bold = false)
@HeadRowHeight(24)
@Data
public class ExcelUserPermissionsVO {

    @ExcelProperty("所属部门名称")
    @ColumnWidth(20)
    @ApiModelProperty("所属部门名称")
    private String orgName;

    @ExcelProperty("用户名")
    @ColumnWidth(20)
    @ApiModelProperty("用户昵称")
    private String nickName;

    @ExcelProperty("账号")
    @ColumnWidth(20)
    @ApiModelProperty("用户名")
    private String username;

    @ExcelProperty("台账权限级别")
    @ColumnWidth(20)
    @ApiModelProperty("台账权限类型 1机构 2路段 3设施")
    private String dataPermissionsLevel;

    @ExcelProperty("台账数据权限")
    @ColumnWidth(20)
    @ApiModelProperty("台账数据权限")
    private String dataName;


    @ApiModelProperty("菜单权限")
    @ColumnWidth(20)
    @ExcelProperty("菜单权限")
    private String menuName;


    @ExcelProperty("应用访问权限")
    @ColumnWidth(20)
    @ApiModelProperty("应用访问权限")
    private String appName;

    @ExcelProperty("应用数据权限")
    @ColumnWidth(20)
    @ApiModelProperty("应用数据权限")
    private String appDataName;

    @ExcelProperty("部门权限")
    @ColumnWidth(20)
    @ApiModelProperty("部门数据权限")
    private String departmentName;

}
