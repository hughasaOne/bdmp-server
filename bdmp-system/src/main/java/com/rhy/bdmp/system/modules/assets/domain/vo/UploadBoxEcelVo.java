package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 解析上传的excel
 * @author 魏财富
 */
@Data
public class UploadBoxEcelVo {
    @TableField("桩号")
    private String stake;

    @TableField("设备IP")
    private String IP;

    @TableField("方向")
    private String direction;

    @TableField("经度")
    private String longitude;

    @TableField("纬度")
    private String latitude;
}
