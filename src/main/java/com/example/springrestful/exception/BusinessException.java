package com.example.springrestful.exception;

import com.example.springrestful.resp.ResultCodeEnum;
import lombok.Data;

/**
 * 业务异常
 *
 * @author 张三丰
 * @date 2023/12/02
 */
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
