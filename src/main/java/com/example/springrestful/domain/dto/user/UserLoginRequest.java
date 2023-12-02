package com.example.springrestful.domain.dto.user;

import lombok.Data;

import java.io.Serializable;


/**
 * 用户登录请求
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;

    private String userPassword;
}
