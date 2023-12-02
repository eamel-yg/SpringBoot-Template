package com.example.springrestful.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springrestful.constant.UserConstant;
import com.example.springrestful.domain.User;
import com.example.springrestful.exception.BusinessException;
import com.example.springrestful.mapper.UserMapper;
import com.example.springrestful.resp.ResultCodeEnum;
import com.example.springrestful.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.springrestful.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户服务impl
 *
 * @author 张三丰
 * @date 2023/12/02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User>
        implements UserService {
    private static final String SALT = "abc";
    @Resource
    private UserMapper userMapper;
    @Override
    public User login(String username, String password) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userName",username);
        userQueryWrapper.eq("userPassword",password);
        User user= this.getOne(userQueryWrapper);
        return user;
    }

    /** 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 密码
     * @return {@link Long}
     */
    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        //todo 1.校验参数是否为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "用户密码过短");
        }
        //todo 2. 校验2次密码是否一致
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "两次输入的密码不一致");
        }
        //todo 3.校验参数是否符合规则
        /**
         * 校验密码的正则表达式
         * ^ 表示匹配字符串开始位置。
         * (?=.*[a-z]) 至少包含一个小写字母。
         * (?=.*[A-Z]) 至少包含一个大写字母。
         * (?=.*\d) 至少包含一个数字.
         * [A-Za-z\d]: 允许大小写英文字母和数字组合
         * {8,}: 密码最短为8位
         */
//        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$";
//        Matcher matcher = Pattern.compile(passwordRegex).matcher(userPassword);
//        if(matcher.find()){
//            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "密码至少包含一个小写和大写字母和一个数字,不少于8位");
//        }
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "账号包含特殊字符");
        }
        //todo 加锁防止大量用户同时注册
        synchronized (userAccount.intern()) {
            //todo 4.查询数据库是否存在相同用户名的用户
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = userMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ResultCodeEnum.PARAMS_ERROR, "账号重复");
            }
           //todo 5.对密码进行加密处理
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            String accessKey=  DigestUtil.md5Hex(SALT+userAccount+ RandomUtil.randomNumbers(5));
            String secretKey=  DigestUtil.md5Hex(SALT+userAccount+ RandomUtil.randomNumbers(8));
            // //todo 6.插入到数据库中
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setAccessKey(accessKey);
            user.setSecretKey(secretKey);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ResultCodeEnum.INTERNAL_SERVER_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request
     * @return {@link User}
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //todo 1.校验参数是否合法
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL, "账号错误");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ResultCodeEnum.PARAMS_NULL, "密码错误");
        }
        //todo 2.查询数据库是否存在
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ResultCodeEnum.NULL_ERROR, "用户不存在或密码错误");
        }

        //todo 3.用户信息脱敏
//        UserVO userVO = safetyUser(user);
        User safetyUser = getSafetyUser(user);
        //todo 4. 记录用户的登录态
        request.getSession().setAttribute(UserConstant.DEFAULT_ROLE, safetyUser);
        //todo 5.返回
        return safetyUser;
    }

    /**
     * 获取登录用户
     *
     * @param request 要求
     * @return {@code User }
     * @author 张三丰
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null){
            throw new BusinessException(ResultCodeEnum.NOT_LOGIN);
        }
        return user;
    }

    /**
     * 用户注销
     *
     * @param request 要求
     * @return {@code Boolean }
     * @author 张三丰
     */
    @Override
    public Boolean userLogOut(HttpServletRequest request) {
        User loginUser = getLoginUser(request);
        if(loginUser==null){
            throw new BusinessException(ResultCodeEnum.NOT_LOGIN);
        }
        request.getSession().removeAttribute(UserConstant.DEFAULT_ROLE);
        return true;
    }

    public User getSafetyUser(User user) {
        if (user == null) {
            throw new BusinessException(ResultCodeEnum.NULL_ERROR, "账户为空");
        }
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUserName(user.getUserName());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setUserAvatar(user.getUserAvatar());
        safetyUser.setGender(user.getGender());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUpdateTime(user.getUpdateTime());
        return safetyUser;
    }

}




