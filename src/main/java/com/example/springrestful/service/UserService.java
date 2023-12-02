package com.example.springrestful.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springrestful.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author 86151
* &#064;description  针对表【User】的数据库操作Service
* &#064;createDate  2023-11-15 16:06:07
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
     * @return {@link User}
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);


    /**
     * 获取登录用户
     *
     * @param request 要求
     * @return {@code User }
     * @author 张三丰
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 要求
     * @return {@code Boolean }
     * @author 张三丰
     */
    Boolean userLogOut(HttpServletRequest request);
}
