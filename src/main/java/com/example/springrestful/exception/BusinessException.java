package com.example.springrestful.exception;

import com.example.springrestful.resp.ResultCodeEnum;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private Integer code;
    private String description;


    public BusinessException(String message, Integer code) {
       super(message);
        this.code = code;
    }
    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
    public BusinessException(ResultCodeEnum resultCodeEnum,String description)
    {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.description=description;
    }
}
