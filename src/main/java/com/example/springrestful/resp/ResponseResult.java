package com.example.springrestful.resp;


import lombok.Data;

/**
 * 返回结果集
 * @author 张三丰
 * @date 2023/11/15
 */
@Data
public class ResponseResult<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private Boolean success;

    /**
     * 返回信息
     */
    private String message;
    /**
     * 错误信息
     */
    private String errMsg;


    /**
     * 数据
     */
    private T data;

    private ResponseResult<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }


    private ResponseResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    private ResponseResult<T> setErrorMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    private ResponseResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    private ResponseResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    /**
     * 全参数方法
     * @param code    状态码
     * @param success  状态
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T>ResponseResult<T> response(Integer code, Boolean success, String message, T data,String errMsg){
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setSuccess(success);
        responseResult.setMessage(message);
        responseResult.setData(data);
        responseResult.setErrorMsg(errMsg);
        return responseResult;
    }

    /**
     * 全参数方法
     * @param code    状态码
     * @param success  状态
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T>ResponseResult<T> response(Integer code, Boolean success, String message, T data){
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setSuccess(success);
        responseResult.setMessage(message);
        responseResult.setData(data);
        return responseResult;
    }
    /**
     * 全参数方法
     * @param code    状态码
     * @param success  状态
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T>ResponseResult<T> response(Integer code, Boolean success, String message, String errMsg){
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setSuccess(success);
        responseResult.setMessage(message);
        responseResult.setErrMsg(errMsg);
        return responseResult;
    }


    /**
     * 全参数方法
     *
     * @param code    状态码
     * @param success  状态
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    private static <T> ResponseResult<T> response(Integer code, Boolean success, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setSuccess(success);
        responseResult.setMessage(message);
        return responseResult;
    }

    /**
     * 成功返回（无参）
     *
     * @param <T> 泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(){
        return new ResponseResult<T>()
                .setSuccess(ResultCodeEnum.SUCCESS.getSuccess())
                .setCode(ResultCodeEnum.SUCCESS.getCode())
                .setMessage(ResultCodeEnum.SUCCESS.getMessage());
    }


    /**
     * 成功返回（枚举参数）
     *
     * @param resultCodeEnum 枚举参数
     * @param <T>              泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(ResultCodeEnum resultCodeEnum) {
        return response(resultCodeEnum.getCode(), true, resultCodeEnum.getMessage());
    }

    /**
     * 成功返回（状态码+返回信息）
     *
     * @param code    状态码
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(Integer code, String message) {
        return response(code, true, message);
    }


    /**
     * 成功返回（返回信息 + 数据）
     *
     * @param message 返回信息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(String message, T data) {
        return response(ResultCodeEnum.SUCCESS.getCode(), true, message, data);
    }

    /**
     * 成功返回（状态码+返回信息+数据）
     *
     * @param code    状态码
     * @param message 返回信息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(Integer code, String message, T data) {
        return response(code, true, message, data);
    }

    /**
     * 成功返回（数据）
     *
     * @param data 数据
     * @param <T>  泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(T data) {
        return response(ResultCodeEnum.SUCCESS.getCode(), true, ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回（返回信息）
     *
     * @param message 返回信息
     * @param <T>  泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> success(String message) {
        return response(ResultCodeEnum.SUCCESS.getCode(), true, message, null);
    }

    /**
     * 失败返回（无参）
     *
     * @param <T> 泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> fail() {
        return response(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), false, ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    /**
     * 失败返回（枚举）
     *
     * @param httpResponseEnum 枚举
     * @param <T>              泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> fail(ResultCodeEnum httpResponseEnum) {
        return response(httpResponseEnum.getCode(), false, httpResponseEnum.getMessage());
    }

    /**
     * 失败返回（状态码+返回信息）
     *
     * @param code    状态码
     * @param message 返回信息
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> fail(Integer code, String message) {
        return response(code, false, message);
    }

    /**
     * 失败返回（返回信息+数据）
     *
     * @param message 返回信息
     * @param data    数据
     * @param <T>     泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> fail(String message, T data) {
        return response(ResultCodeEnum.FAIL.getCode(), false, message, data);
    }

    /**
     * 失败返回（状态码+返回信息+数据）
     *
     * @param code    状态码
     * @param message 返回消息
     * @param data    数据
     * @return {@link ResponseResult}<{@link T}>
     */
    public static <T> ResponseResult<T> fail(Integer code, String message, T data) {
        return response(code, false, message, data);
    }

    /** 失败返回(状态码+返回信息+错误信息)
     * @return {@link ResponseResult}<{@link T}>
     */
    public static <T> ResponseResult<T> fail(Integer code, String message, String errMsg) {
        return response(code, false, message, errMsg);
    }

    /**
     * 失败返回（数据）
     *
     * @param data 数据
     * @param <T>  泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> fail(T data) {
        return response(ResultCodeEnum.FAIL.getCode(), false, ResultCodeEnum.FAIL.getMessage(), data);
    }

    /**
     * 失败返回（返回信息）
     *
     * @param message 返回信息
     * @param <T>  泛型
     * @return {@link ResponseResult<T>}
     */
    public static <T> ResponseResult<T> fail(String message) {
        return response(ResultCodeEnum.FAIL.getCode(), false, message, null);
    }

    /**
     * 设置自己想要返回的枚举结果
     * @param result
     * @return {@link ResultUtils}
     */
    public static <T> ResponseResult<T> setResult(ResultCodeEnum result) {
        return new ResponseResult<T>()
                .setSuccess(result.getSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }

    /**
     * 通用返回失败
     * @return {@link ResultUtils}
     */
    public static <T> ResponseResult<T> failure() {
        return new ResponseResult<T>()
                .setSuccess(ResultCodeEnum.FAIL.getSuccess())
                .setCode(ResultCodeEnum.FAIL.getCode())
                .setMessage(ResultCodeEnum.FAIL.getMessage());
    }

    //通用设置data
    public ResponseResult<T> data(T data){
        return this.setData(data);
    }
    //自定义状态信息
    public ResponseResult<T>  code(Integer code){
        return this.setCode(code);
    }
    // 自定义状态信息
    public ResponseResult<T> message(String message) {
        return this.setMessage(message);

    }

    // 自定义返回结果
    public  ResponseResult isSuccess(Boolean success) {
        return this.setSuccess(success);
    }

}
