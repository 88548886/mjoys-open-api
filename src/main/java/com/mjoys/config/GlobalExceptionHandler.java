package com.mjoys.config;

import com.auth0.jwt.exceptions.InvalidClaimException;
import com.mjoys.constant.StatusCode;
import com.mjoys.dto.CommomResponseDto;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public CommomResponseDto handle(Exception e) {
        e.printStackTrace();
        CommomResponseDto vo = new CommomResponseDto();
        if (e instanceof NoHandlerFoundException) {
            vo.setRetCode(StatusCode.NOT_FOUND);
            vo.setRetMsg(e.getMessage());
        } else {
            vo.setRetCode(StatusCode.ERROR);
            vo.setRetMsg(e.getMessage());
        }
        return vo;
    }

    @ExceptionHandler(RuntimeException.class)
    public CommomResponseDto handle(RuntimeException e) {

        e.printStackTrace();
        CommomResponseDto vo = new CommomResponseDto();
        if (e instanceof IncorrectCredentialsException) {
            vo.setRetCode(StatusCode.PASSWORD_ERROR);
            vo.setRetMsg(e.getMessage());
        }else if (e instanceof InvalidClaimException) {
            vo.setRetCode(StatusCode.NOT_FOUND);
            vo.setRetMsg(e.getMessage());
        } else {
            vo.setRetCode(StatusCode.ERROR);
            vo.setRetMsg(e.getMessage());
        }
        return vo;
    }
}
