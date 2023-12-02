package com.example.springrestful.annotation;

import java.lang.annotation.*;


/**
 * 身份验证检查
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {
    /**
     * 有任何一个角色
     *
     * @return
     */
    String[] anyRole() default "";

    /**
     * 必须有某个角色
     *
     * @return
     */
    String mustRole() default "";
}
