package com.example.springrestful.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONObjectCodec;
import com.example.springrestful.domain.User;
import com.example.springrestful.exception.BusinessException;
import com.example.springrestful.resp.ResponseResult;
import com.example.springrestful.resp.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//设置拦截路径
//@javax.servlet.annotation.WebFilter(urlPatterns = ("/*"))
public class WebFilter  implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取请求url
        String url = request.getRequestURI().toString();
        log.info("请求路径:{}",url);

        //2.如果是登录请求则放行
        if(url.contains("/user/login")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //3.获取登录用户信息(使用的是redis就从redis从获取数据)
        User user = (User) request.getSession().getAttribute("user");
        //4.判断用户信息是否存在
        if(user==null||!user.getUserRole().equals("admin")){
            //5.不存在就拦截并返回未登录信息
            ResponseResult<Object> responseResult = ResponseResult.fail(ResultCodeEnum.NOT_LOGIN);
            String jsonString = JSONObject.toJSONString(responseResult);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonString);
            return;
        }
        //6.登录信息存在就放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
