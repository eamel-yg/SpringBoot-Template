package com.example.springrestful;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.example.my_springboot_starter.config.MyStarterService;
import com.example.springrestful.domain.User;
import com.example.springrestful.domain.vo.UserVO;
import com.example.springrestful.mapper.UserMapper;
import com.example.springrestful.resp.ResultCodeEnum;
import com.example.springrestful.resp.ResultUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class SpringRestFulApplicationTests {
    @Autowired
    private MyStarterService myStarterService;

    @Resource
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }
    @Test
    public void test(){
        //返回通用成功
        ResultUtils success = ResultUtils.success();
        System.out.println(success+"通用成功");

        ResultUtils failure = ResultUtils.failure();
        System.out.println(failure+"通用失败");
        ResultUtils hello = failure.message("你好");
        System.out.println("hello"+hello);

        ResultUtils resultUtils = ResultUtils.setResult(ResultCodeEnum.FORBIDDEN);
        System.out.println(resultUtils+"设置返回枚举类结果");
//        User user = new User("张三","女");
//
//        ResultUtils message = ResultUtils.success().data("user",user).code(200).message("查询觉果");
//        System.out.println(message+"自定义返回数据类型");

    }
    @Test
    public void test2(){
        String property1 = myStarterService.getProperty1();
        System.out.println(property1);
    }
    @Test
    public void test3(){
        //设置分页参数：页码、显示的条数
        //第2个参数是条件构造器，如果设置为null，即查询所有
        //返回值就是一个Page对象，即返回上面所new出来的page对象，所有数据都在其中
        //对应的sql语句：SELECT id,name,age,email FROM user LIMIT ?
        Page<User> userPage = new Page<>(2,3);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        Page<User> page = userMapper.selectPage(userPage, userQueryWrapper);
        PageDTO<UserVO> userVOPageDTO = new PageDTO<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<UserVO> records = userVOPageDTO.getRecords();
        records.forEach(System.out::println);

//        List<UserVO> collect = page.getRecords().stream().map(user -> {
//            UserVO userVO = new UserVO();
//            BeanUtils.copyProperties(user, userVO);
//            return userVO;
//        }).collect(Collectors.toList());
//        userVOPageDTO.setRecords(collect);
//        List<UserVO> records = userVOPageDTO.getRecords();
//        records.forEach(System.out::println);

//        List<UserVO> records = userVOPageDTO.getRecords();
//        records.forEach(System.out::println);
//        List<User> users= page.getRecords();
//        users.forEach(System.out::println);
//        System.out.println("当前页："+page.getCurrent());
//        System.out.println("每页显示的条数："+page.getSize());
//        System.out.println("总记录数："+page.getTotal());
//        System.out.println("总页数："+page.getPages());
//        System.out.println("是否有上一页："+page.hasPrevious());
//        System.out.println("是否有下一页："+page.hasNext());
    }



}
