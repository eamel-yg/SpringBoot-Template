package com.example.springrestful.Interceptor;

import com.example.springrestful.resp.ResponseResult;
import com.example.springrestful.resp.ResultCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1.获取请求头中的token
        String token = request.getHeader("Authorization");
        Object user = request.getSession().getAttribute("user");
        if(StringUtils.isBlank(token)||user==null){
            ResponseResult error = ResponseResult.fail(ResultCodeEnum.NOT_LOGIN);
            // 将对象转换为JSON格式
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(error);
            response.setStatus(403);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonResponse);
            return false;
        }
        return true;
    }
}
