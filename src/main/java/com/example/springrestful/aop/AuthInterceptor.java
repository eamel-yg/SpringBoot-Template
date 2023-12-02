package com.example.springrestful.aop;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.example.springrestful.annotation.AuthCheck;
import com.example.springrestful.domain.User;
import com.example.springrestful.exception.BusinessException;
import com.example.springrestful.resp.ResultCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 身份验证拦截器
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Aspect
@Component
public class AuthInterceptor {
    /**
     * 环绕通知拦截
     * @param joinPoint
     * @param authCheck
     * @return {@link Object}
     * @throws Throwable
     */
    //被@AuthCheck注解标记的都进行拦截
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint,AuthCheck authCheck) throws Throwable{
        System.out.println("开始doInterceptor.................");
        //输入多个值
        List<String> collect = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());

        String  mustRole= authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //判断用户信息
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            throw new BusinessException(ResultCodeEnum.NOT_LOGIN);
        }
        //拥有任意权限通过
        if(CollectionUtils.isNotEmpty(collect)){
            String userRole = user.getUserRole();
            if (!collect.contains(userRole)) {
                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
            }
        }
        // 必须有所有权限才通过
        if (StringUtils.isNotBlank(mustRole)) {
            String userRole = user.getUserRole();
            if (!mustRole.equals(userRole)) {
                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }

//    /**
//     * 前置通知拦截
//     * @param joinPoint
//     * @param authCheck
//     * @throws Throwable
//     */
//    @Before("@annotation(authCheck)")
//    public void before(JoinPoint joinPoint, AuthCheck authCheck) throws Throwable{
//        System.out.println("before.................");
//        List<String> collect = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//        String  mustRole= authCheck.mustRole();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        User user = (User) request.getSession().getAttribute("user");
//        if(user==null){
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
//        }
//        //拥有任意权限通过
//        if(CollectionUtils.isNotEmpty(collect)){
//            String userRole = user.getUserRole();
//            if (!collect.contains(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 必须有所有权限才通过
//        if (StringUtils.isNotBlank(mustRole)) {
//            String userRole = user.getUserRole();
//            if (!mustRole.equals(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 通过权限校验，放行
//    }
//
//    /**
//     * 后置通知拦截
//     * @param joinPoint
//     * @param authCheck
//     * @throws Throwable
//     */
//    @After("@annotation(authCheck)")
//    public void after(JoinPoint joinPoint, AuthCheck authCheck) throws Throwable{
//        System.out.println("开始after.................");
//        List<String> collect = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//        String  mustRole= authCheck.mustRole();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        User user = (User) request.getSession().getAttribute("user");
//        if(user==null){
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
//        }
//        //拥有任意权限通过
//        if(CollectionUtils.isNotEmpty(collect)){
//            String userRole = user.getUserRole();
//            if (!collect.contains(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 必须有所有权限才通过
//        if (StringUtils.isNotBlank(mustRole)) {
//            String userRole = user.getUserRole();
//            if (!mustRole.equals(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 通过权限校验，放行
//    }
//
//    /**
//     * 后置返回通知拦截
//     * @param joinPoint
//     * @param authCheck
//     * @throws Throwable
//     */
//    @AfterReturning("@annotation(authCheck)")
//    public void afterReturn(JoinPoint joinPoint, AuthCheck authCheck) throws Throwable{
//        System.out.println("afterReturn.................");
//        List<String> collect = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//        String  mustRole= authCheck.mustRole();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        User user = (User) request.getSession().getAttribute("user");
//        if(user==null){
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
//        }
//        //拥有任意权限通过
//        if(CollectionUtils.isNotEmpty(collect)){
//            String userRole = user.getUserRole();
//            if (!collect.contains(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 必须有所有权限才通过
//        if (StringUtils.isNotBlank(mustRole)) {
//            String userRole = user.getUserRole();
//            if (!mustRole.equals(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 通过权限校验，放行
//    }
//
//
//    /**
//     * 后置异常通知拦截
//     * @param joinPoint
//     * @param authCheck
//     * @throws Throwable
//     */
//    @AfterThrowing("@annotation(authCheck)")
//    public void afterThrowing(JoinPoint joinPoint, AuthCheck authCheck) throws Throwable{
//        System.out.println("afterThrowing.................");
//        List<String> collect = Arrays.stream(authCheck.anyRole()).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//
//        String  mustRole= authCheck.mustRole();
//        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        User user = (User) request.getSession().getAttribute("user");
//        if(user==null){
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
//        }
//        //拥有任意权限通过
//        if(CollectionUtils.isNotEmpty(collect)){
//            String userRole = user.getUserRole();
//            if (!collect.contains(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 必须有所有权限才通过
//        if (StringUtils.isNotBlank(mustRole)) {
//            String userRole = user.getUserRole();
//            if (!mustRole.equals(userRole)) {
//                throw new BusinessException(ResultCodeEnum.NOT_AUTH);
//            }
//        }
//        // 通过权限校验，放行
//    }
}
