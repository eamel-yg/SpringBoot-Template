package com.example.springrestful.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.example.springrestful.annotation.AuthCheck;
import com.example.springrestful.constant.UserConstant;
import com.example.springrestful.domain.User;
import com.example.springrestful.domain.dto.user.UserLoginRequest;
import com.example.springrestful.domain.dto.user.UserQueryRequest;
import com.example.springrestful.domain.dto.user.UserRegisterRequest;
import com.example.springrestful.domain.vo.UserVO;
import com.example.springrestful.exception.BusinessException;
import com.example.springrestful.resp.ResponseResult;
import com.example.springrestful.resp.ResultCodeEnum;
import com.example.springrestful.resp.ResultUtils;
import com.example.springrestful.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理接口
 * @author 张三丰
 * @date 2023/12/01
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理接口")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param userRegisterRequest
     * @return {@link ResponseResult}<{@link Long}>
     */
    @PostMapping("/userRegister")
    public ResponseResult<Long>register(@RequestBody  UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest==null){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR);
        }
        Long result = userService.userRegister(userAccount,userPassword, checkPassword);
        return ResponseResult.success(result).message("注册成功");
    }

    /**
     * 用户登录
     * @param userLoginRequest
     * @param request
     * @return {@link ResponseResult}<{@link User}>
     */
    @PostMapping("/userLogin")
    public ResponseResult<User>userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request){
            if(userLoginRequest==null){
                throw new BusinessException(ResultCodeEnum.PARAMS_NULL);
            }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL);
        }
        User user= userService.userLogin(userAccount,userPassword,request);
        return ResponseResult.success(user);
    }



    /**
     * 分页查询用户信息
     * @param userQueryRequest
     * @param request
     * @return {@link ResponseResult}<{@link List}<{@link UserVO}>>
     */
    @GetMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResponseResult<Page<UserVO>> listUserPage(UserQueryRequest userQueryRequest, HttpServletRequest request){
        long current=1;
        long pageSize=10;
        User queryUser = new User();
        if(userQueryRequest!=null){
            BeanUtils.copyProperties(userQueryRequest,queryUser);
            current=userQueryRequest.getCurrent();
            pageSize=userQueryRequest.getPageSize();
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(queryUser);
        Page<User> page = userService.page(new Page<>(current, pageSize), userQueryWrapper);
        PageDTO<UserVO> userVOPageDTO = new PageDTO<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<UserVO> userPage = page.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            return userVO;
        }).collect(Collectors.toList());
        userVOPageDTO.setRecords(userPage);
        return ResponseResult.success(userVOPageDTO);
    }
}
