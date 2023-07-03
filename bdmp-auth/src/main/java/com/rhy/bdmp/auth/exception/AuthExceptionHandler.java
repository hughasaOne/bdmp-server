package com.rhy.bdmp.auth.exception;

import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.Result;
import com.rhy.bcp.common.resutl.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author: lipeng
 * @Date: 2021/1/4
 * @description: 全局异常处理器
 *
 * @version: 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class AuthExceptionHandler {

    /**
     * 用户名和密码异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidGrantException.class)
    public Result handleInvalidGrantException(InvalidGrantException e) {
        return Result.failed(ResultCode.USERNAME_OR_PASSWORD_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public Result handleInvalidGrantException(BadRequestException e) {
        return Result.failed(e.getMessage());
    }


    /**
     * 账户异常(禁用、锁定、过期)
     *
     * @param e
     * @return
     */
    @ExceptionHandler({InternalAuthenticationServiceException.class})
    public Result handleInternalAuthenticationServiceException(InternalAuthenticationServiceException e) {
        return Result.failed(e.getMessage());
    }

}
