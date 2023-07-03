package com.rhy.bdmp.system.modules.logging.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BaseMessage {

    @ApiModelProperty("操作者ip")
    private String ip;

    @ApiModelProperty("操作者")
    private String operator;

    @ApiModelProperty("操作时间")
    private String operationTime;

    @ApiModelProperty("请求/响应参数")
    private Object params;

    @ApiModelProperty("日志类型")
    private String logType;
}
