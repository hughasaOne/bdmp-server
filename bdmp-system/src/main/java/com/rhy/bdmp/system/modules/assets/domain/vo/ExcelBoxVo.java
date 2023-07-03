package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * excel下载
 * @author 魏财富
 */
@HeadStyle(fillPatternType = FillPatternType.NO_FILL,
        horizontalAlignment = HorizontalAlignment.CENTER,
        verticalAlignment = VerticalAlignment.CENTER
)
@HeadFontStyle(fontHeightInPoints = 12,bold = false)
@HeadRowHeight(23)
@Data
public class ExcelBoxVo {
    @ExcelProperty("桩号")
    @ColumnWidth(15)
    private String stake;

    @ExcelProperty("设备IP*")
    @HeadFontStyle(color = 0xa)
    @ColumnWidth(15)
    private String IP;

    @ExcelProperty("方向")
    @ColumnWidth(15)
    @TableField("方向")
    private String direction;

    @ExcelProperty("经度")
    @ColumnWidth(15)
    @TableField("经度")
    private String longitude;

    @ExcelProperty("纬度")
    @ColumnWidth(15)
    @TableField("纬度")
    private String latitude;
}
