package com.example.springrestful.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.example.springrestful.annotation.AuthCheck;
import com.example.springrestful.common.DeleteRequest;
import com.example.springrestful.constant.UserConstant;
import com.example.springrestful.domain.User;
import com.example.springrestful.domain.dto.user.*;
import com.example.springrestful.domain.vo.UserVO;
import com.example.springrestful.exception.BusinessException;
import com.example.springrestful.resp.ResponseResult;
import com.example.springrestful.resp.ResultCodeEnum;
import com.example.springrestful.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
     * @param userRegisterRequest 用户注册请求
     * @return {@code ResponseResult<Long>}
     */
    @PostMapping("/userRegister")
    public ResponseResult<Long>userRegister(@RequestBody  UserRegisterRequest userRegisterRequest){
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
     *
     * @param userLoginRequest 用户登录请求
     * @param request          要求
     * @return {@code ResponseResult<User> }
     * @author 张三丰
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
     * 获取用户最新信息
     * @param request
     * @return {@code ResponseResult<User>}
     */
    @GetMapping("/userCurrent")
    public ResponseResult<User>getUserCurrent(HttpServletRequest request){
        User loginUser = userService.getLoginUser(request);
        if(loginUser==null){
            throw new BusinessException(ResultCodeEnum.NOT_LOGIN);
        }
        return ResponseResult.success(loginUser);
    }

    /**
     * 用户注销
     *
     * @param request 要求
     * @return {@code ResponseResult<Boolean> }
     * @author 张三丰
     */
    @GetMapping("/userLogout")
    public ResponseResult<Boolean>userLogOut(HttpServletRequest request){
        Boolean result = userService.userLogOut(request);
        request.getSession().removeAttribute(UserConstant.DEFAULT_ROLE);
        return ResponseResult.success(result,"退出登录");
    }


    /**
     * 创建用户
     *
     * @param userAddRequest 用户添加请求
     * @param request        要求
     * @return {@code ResponseResult<Long> }
     * @author 张三丰
     */
    @PostMapping("/createUser")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResponseResult<Long>createUser(@RequestBody UserAddRequest userAddRequest,HttpServletRequest request){
        if(userAddRequest==null){
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest,user);
        boolean save = userService.save(user);
        if(!save){
            throw new BusinessException(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return ResponseResult.success("创建成功",user.getId());
    }

    /**
     * 删除用户
     *
     * @param deleteRequest 删除请求
     * @return {@code ResponseResult<Boolean> }
     * @author 张三丰
     */
    @PostMapping("/deleteUser")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResponseResult<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest){
        if(deleteRequest==null){
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL);
        }
        boolean result = userService.removeById(deleteRequest.getId());
        if(!result){
            throw new BusinessException(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return ResponseResult.success(true,"删除成功");
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest 用户更新请求
     * @return {@code ResponseResult<Boolean> }
     * @author 张三丰
     */
    @PostMapping("/updateUser")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResponseResult<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest){
        if(userUpdateRequest==null){
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL);
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest,user);
        boolean result = userService.updateById(user);
        if(!result){
            throw new BusinessException(ResultCodeEnum.INTERNAL_SERVER_ERROR);
        }
        return ResponseResult.success(true,"更新成功");
    }

    /**
     * 按id获取用户
     *
     * @param id id
     * @return {@code ResponseResult<UserVO> }
     * @author 张三丰
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResponseResult<UserVO> getUserById(Long id){
        if(id<=0){
            throw new BusinessException(ResultCodeEnum.PARAM_ERROR);
        }
        User user = userService.getById(id);
        if(user==null){
            throw new BusinessException(ResultCodeEnum.NULL_ERROR,"当前用户不存在");
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return ResponseResult.success(userVO);
    }

    /**
     * 用户列表
     *
     * @param userQueryRequest 要求
     * @return {@code ResponseResult<List<UserVO>> }
     * @author 张三丰
     */
    @GetMapping("/listUser")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public ResponseResult<List<UserVO>>listUser(UserQueryRequest userQueryRequest){
        User queryUser = new User();
        if(userQueryRequest!=null){
            BeanUtils.copyProperties(userQueryRequest,queryUser);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>(queryUser);
        List<User> userList = userService.list(userQueryWrapper);
        List<UserVO> userVOList = userList.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            return userVO;
        }).collect(Collectors.toList());
        return ResponseResult.success(userVOList);
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
