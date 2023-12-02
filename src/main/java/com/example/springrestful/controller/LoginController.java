package com.example.springrestful.controller;

import com.example.springrestful.annotation.AuthCheck;
import com.example.springrestful.domain.LoginRequest;
import com.example.springrestful.domain.User;
import com.example.springrestful.exception.BusinessException;
import com.example.springrestful.resp.ResponseResult;
import com.example.springrestful.resp.ResultCodeEnum;
import com.example.springrestful.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/user")
@Api(tags = "用户模块")
public class LoginController {
    @Resource
    private UserService UserService;

    /**
     * 登录方法
     *
     * @param loginRequest 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginRequest loginRequest,HttpServletRequest request) {
        // 生成令牌
        User user = UserService.login(loginRequest.getUsername(), loginRequest.getPassword());
        request.getSession().setAttribute("user",user);
        if(user==null){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        return ResponseResult.success().data(user);
    }
    @GetMapping("/current")
    public ResponseResult<User> current(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return ResponseResult.success(user);
    }
}
