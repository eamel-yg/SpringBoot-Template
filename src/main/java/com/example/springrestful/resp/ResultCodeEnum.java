package com.example.springrestful.resp;

/**
 * 结果代码枚举
 *
 * @author 张三丰
 * @date 2023/12/02
 */
public enum ResultCodeEnum {
    SUCCESS(true,200,"成功"),
    FAIL(false,400,"失败"),
    NOT_FOUND(false,404,"接口不存在"),
    FORBIDDEN(false,403,"拒绝访问"),
    UNAUTHORIZED(false,401,"未认证（签名错误）"),
    INTERNAL_SERVER_ERROR(false,500,"服务器内部错误"),
    NULL_POINT(false, 200002, "空指针异常"),
    PARAM_ERROR(false, 200001, "参数错误"),
    PARAMS_ERROR(false,40000,"请求参数错误"),
    PARAMS_NULL(false,40000,"请求参数为空"),
    NULL_ERROR(false,40001,"请求数据为空"),
    NOT_LOGIN(false,40100,"未登录"),
    NOT_AUTH(false,40101,"没有权限");


    /**
     * 是否响应成功
     */
    private Boolean success;
    /**
     *响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;
    ResultCodeEnum(Boolean success,Integer code,String message){
        this.success=success;
        this.code=code;
        this.message=message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
