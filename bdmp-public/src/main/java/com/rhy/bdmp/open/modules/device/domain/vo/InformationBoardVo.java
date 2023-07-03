package com.rhy.bdmp.open.modules.device.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InformationBoardVo extends DeviceVo {

    @ApiModelProperty(value = "像素宽", example = "1")
    private Integer pixelX;

    @ApiModelProperty(value = "像素高", example = "1")
    private Integer pixelY;

    @ApiModelProperty(value = "图片像素", example = "1")
    private Integer pictureSize;

    @ApiModelProperty(value = "显示效果")
    private String style;

    @ApiModelProperty(value = "显示时长(单位秒)", example = "1")
    private Integer dwellTime;

    @ApiModelProperty(value = "对齐方式(0左上1左中2左下3中上4 居中5中下6 右上7 右中8 右下)", example = "1")
    private Integer alignment;

    @ApiModelProperty(value = "情报板类型(1 常用2 天气3 其他)", example = "1")
    private Integer commonType;

    @ApiModelProperty(value = "发送次数", example = "1")
    private Integer sendCount;

    @ApiModelProperty(value = "使用类型(0 常用库 1播放类表 2 当前包访)", example = "1")
    private Integer useType;
}
