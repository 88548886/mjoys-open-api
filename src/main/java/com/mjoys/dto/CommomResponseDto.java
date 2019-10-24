package com.mjoys.dto;


import com.mjoys.constant.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CommomResponseDto<T> {
    private int retCode;
    private String retMsg;
    private T data;

    public CommomResponseDto(int code, String msg) {
        this.retCode = code;
        this.retMsg = msg;
    }

    public void Success(T data){
        this.retCode = StatusCode.OK;
        this.retMsg = "成功";
        this.data = data;
    }
    public void Exception(String exceptionMsg){
        this.retCode = StatusCode.ERROR;
        this.retMsg = exceptionMsg;
    }
}
