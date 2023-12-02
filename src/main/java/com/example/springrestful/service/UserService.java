package com.example.springrestful.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springrestful.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86151
* @description 针对表【User】的数据库操作Service
* @createDate 2023-11-15 16:06:07
*/
public interface UserService extends IService<User> {

    User login(String username, String password);

    /** 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 密码
     * @return {@link Long}
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request
     * @return {@link User}
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
}
