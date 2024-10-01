package org.example.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.login.model.entity.User;
import org.example.login.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author htfc786
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2024-09-28 10:26:27
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      请求原始数据
     * @return 用户登录信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

}
