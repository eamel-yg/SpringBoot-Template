package com.example.springrestful.controller;

import com.example.springrestful.annotation.AuthCheck;
import com.example.springrestful.resp.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @AuthCheck(mustRole = "admin")
    @GetMapping("/test")
    public ResponseResult test(){
        return  ResponseResult.success().message("hello");
    }
}
