package com.example.springrestful.resp;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 结果：结果实用程序
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Data
public class ResultUtils {
    private Boolean success;

    private Integer code;

    private String message;
    /**
     * 接口请求时间戳
     */
    private Long timestamp;

    private Map<String, Object> data = new HashMap<>();
    private ResultUtils setSuccess(Boolean success) {
        this.success = success;
        return this;
    }


    private ResultUtils setMessage(String message) {
        this.message = message;
        return this;
    }

    private ResultUtils setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    private ResultUtils setCode(Integer code) {
        this.code = code;
        return this;
    }

    private ResultUtils() {
    }
    private  ResultUtils(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 通用返回成功
     * @return {@link ResultUtils}
     */
    public static ResultUtils success(){
        return new ResultUtils(System.currentTimeMillis())
                .setSuccess(ResultCodeEnum.SUCCESS.getSuccess())
                .setCode(ResultCodeEnum.SUCCESS.getCode())
                .setMessage(ResultCodeEnum.SUCCESS.getMessage());
    }

    /**
     * 通用返回失败
     * @return {@link ResultUtils}
     */
    public static ResultUtils failure() {
        return new ResultUtils(System.currentTimeMillis())
                .setSuccess(ResultCodeEnum.FAIL.getSuccess())
                .setCode(ResultCodeEnum.FAIL.getCode())
                .setMessage(ResultCodeEnum.FAIL.getMessage());
    }

    /**
     * 设置自己想要返回的枚举结果
     * @param result
     * @return {@link ResultUtils}
     */
    public static ResultUtils setResult(ResultCodeEnum result) {
        return new ResultUtils(System.currentTimeMillis())
                .setSuccess(result.getSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }


    //自定义返回数据
    public   ResultUtils data(Map<String,Object> map){
        return this.setData(map);
    }

    //通用设置data
    public ResultUtils data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    //自定义状态信息
    public ResultUtils  code(Integer code){
       return this.setCode(code);
    }
    // 自定义状态信息
    public ResultUtils message(String message) {
        return this.setMessage(message);

    }

    // 自定义返回结果
    public ResultUtils success(Boolean success) {
        return this.setSuccess(success);
    }

}
