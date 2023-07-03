package com.rhy.bdmp.open.modules.assets.util;

import com.rhy.bcp.common.resutl.ResultCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: yanggj
 * @Description: api返回结果
 * @Date: 2021/9/27 9:02
 * @Version: 1.0.0
 */
@ApiModel(value = "api返回结果", description = "结果对象")
public class ApiResult<T> implements Serializable {

    @ApiModelProperty(value = "返回代码", position = 1, example = "200")
    private String status;

    @ApiModelProperty(value = "提示信息", position = 2, example = "成功")
    private String message;

    @ApiModelProperty(value = "返回结果", position = 3)
    private T data;

    @ApiModelProperty(value = "异常信息", position = 4)
    private String exception;

    @ApiModelProperty(value = "rel", position = 5)
    private Boolean rel;

    public ApiResult(String status, String message, T data, String exception) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.exception = exception;
    }

    public static <T> ApiResult<T> ok() {
        return new ApiResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null, null);
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data, null);
    }

    public static <T> ApiResult<T> error() {
        return new ApiResult<>(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), null, null);
    }

    public static <T> ApiResult<T> error(String exception) {
        return new ApiResult<>(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), null, exception);
    }

    public static <T> ApiResult<T> error(String message, String exception) {
        return new ApiResult<>(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), message, null, exception);
    }

    public static <T> ApiResult<T> error(String status, String message, String exception) {
        return new ApiResult<>(status, message, null, exception);
    }

    public String getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public String getException() {
        return this.exception;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
