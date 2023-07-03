package com.rhy.bdmp.base.modules.sys.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.sql.Blob;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @description 文件 实体
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@ApiModel(value="文件", description="文件信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_bdmp_sys_file")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    @TableId("file_id")
    private String fileId;

    @ApiModelProperty(value = "文件名称")
    @TableField("file_name")
    private String fileName;

    @ApiModelProperty(value = "格式类型")
    @TableField("content_type")
    private String contentType;

    @ApiModelProperty(value = "文件大小", example = "1")
    @TableField("file_size")
    private Long fileSize;

    @ApiModelProperty(value = "创建者")
    @TableField("create_by")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "关联表")
    @TableField("r_table")
    private String rTable;

    @ApiModelProperty(value = "关联数据ID")
    @TableField("r_id")
    private String rId;

    @ApiModelProperty(value = "开始位置", example = "1")
    @TableField("start_data")
    private Long startData;

    @ApiModelProperty(value = "结束位置", example = "1")
    @TableField("end_data")
    private Long endData;

    @ApiModelProperty(value = "数据类型")
    @TableField("data_type")
    private String dataType;

    @ApiModelProperty(value = "文件内容")
    @TableField("file_data")
    private Blob fileData;

    @ApiModelProperty(value = "存放路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "真实文件名")
    @TableField("real_file_name")
    private String realFileName;

    @ApiModelProperty(value = "文件类型")
    @TableField("file_type")
    private String fileType;

    @ApiModelProperty(value = "文件MD5")
    @TableField("file_md5")
    private String fileMd5;
}
