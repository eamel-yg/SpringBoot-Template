package com.example.springrestful;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.example.springrestful.mapper")
@EnableAspectJAutoProxy
@ServletComponentScan
public class SpringRestFulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestFulApplication.class, args);
        System.out.println("哈哈");
    }

}
