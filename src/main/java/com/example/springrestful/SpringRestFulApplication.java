package com.example.springrestful;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * spring rest ful应用程序
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@SpringBootApplication
@MapperScan("com.example.springrestful.mapper")
@EnableAspectJAutoProxy
@ServletComponentScan
public class SpringRestFulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestFulApplication.class, args);
    }

}
