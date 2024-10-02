package org.example.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.example.login.common.ErrorCode;
import org.example.login.exception.BusinessException;
import org.example.login.model.dto.user.WxLoginUserInfo;
import org.example.login.model.entity.User;
import org.example.login.model.vo.user.LoginUserVO;
import org.example.login.service.UserService;
import org.example.login.mapper.UserMapper;
import org.example.login.utils.JWTUtils;
import org.example.login.utils.WxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author htfc786
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2024-09-28 10:26:27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 盐值，混淆密码
     */
    @Value("${app.salt}")
    private String SALT;

    @Autowired
    private WxUtils wxUtils;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号过短，账号名必须大于4字符");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 上锁，不能让多个用户同时注册造成重复
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            System.out.println(queryWrapper.getSqlSelect());
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. MD5摘要
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUserName(userAccount);
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误！");
            }
            return user.getId();
        }

    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验信息
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请输入账号和密码");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误！");
        }
        // 2. 重新对密码摘要
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 查询用户
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.eq("userAccount", userAccount);
        userQuery.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(userQuery);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误！");
        }
        // 4. 生成token
        String token = JWTUtils.genToken(user);
        return LoginUserVO.toLoginUserVO(user, token);
    }

    @Override
    public LoginUserVO wxLogin(String code, String state, HttpServletRequest request) {
        // 1. 从微信服务器获取用户信息
        WxLoginUserInfo userInfo = wxUtils.getUserInfo(code);
        String wxOpenId = userInfo.getOpenid();
        // 单机锁
        synchronized (wxOpenId.intern()) {
            // 2.查询用户是否已存在
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wxOpenId", wxOpenId);
            User user = this.getOne(queryWrapper);
            // 用户不存在则自动注册
            if (user == null) {
                user = new User();
                user.setWxOpenId(wxOpenId);
                user.setUserAvatar(userInfo.getHeadimgurl());
                user.setUserName(userInfo.getNickname());
                user.setUserAccount(user.getUserName());
                boolean result = this.save(user);
                if (!result) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
                }
            }
            // 3. 生成token
            String token = JWTUtils.genToken(user);
            return LoginUserVO.toLoginUserVO(user, token);
        }
    }

    @Override
    public Long getLoginUserId(HttpServletRequest request) {
        // 先获取token
        String token = JWTUtils.getToken(request);
        if (token == null) return null;
        // userid
        Long userId = JWTUtils.getUserId(token);
        if (userId == null) return null;
        return userId;

    }
}