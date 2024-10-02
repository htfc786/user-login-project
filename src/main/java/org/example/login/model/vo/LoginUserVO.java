package org.example.login.model.vo;

import lombok.Data;
import org.example.login.model.entity.User;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 已登录用户视图（脱敏）
 */
@Data
public class LoginUserVO implements Serializable {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * jwtToken
     */
    private String token;


    /**
     *  转换函数
     * @param user User
     * @return LoginUserVO
     */
    public static LoginUserVO toLoginUserVO(User user, String token) {
        if (user == null) { return null; }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        loginUserVO.setToken(token);
        return loginUserVO;
    }

}