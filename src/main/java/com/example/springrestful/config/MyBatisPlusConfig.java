package com.example.springrestful.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * MyBatis Plus 配置
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Configuration
@MapperScan("com.yupi.project.mapper")
public class MyBatisPlusConfig {

    /**
     *
     * 1 怎么来配置mybatis-plus中的插件？
     *   这里所需要的类型是MybatisPlusInterceptor，这是mybatis-plus的一个拦截器，用于配置mybatis-plus中的插件。
     * 2 为什么要使用拦截器MybatisPlusInterceptor呢？
     *    这里边的原理和mybatis分页插件的功能是一样的，工作流程如下 ：
     *   （1）第一步：执行查询功能。
     *   （2）第二步：拦截器对查询功能进行拦截。
     *   （3）第三步：拦截器对查询功能的基础上做了额外的处理，达到分页的效果（功能）。
     * 3 对比配置mybatis中的插件？
     *   用的也是拦截器的方式。
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}