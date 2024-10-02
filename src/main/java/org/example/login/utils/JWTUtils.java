package org.example.login.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.example.login.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * jwt工具类
 * https://www.jb51.net/program/323429oi0.htm
 */

@Slf4j
public class JWTUtils {

    // TODO JWTtoken密钥
    private static final String tokenSecret = "user-login-project";

    /**
     * 获取token
     */
    public static String genToken(User u) {
        // 7天有效期
        Date expireAt = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000);

        return JWT.create()
                // 发行人
                .withIssuer("auth0")
                // 数据
                .withClaim("id", u.getId())
                .withClaim("userAccount", u.getUserAccount())
                .withClaim("userName", u.getUserName())
                // 过期时间
                .withExpiresAt(expireAt)
                .sign(Algorithm.HMAC256(tokenSecret));
    }

    /**
     * 对token进行验证
     */
    public static Boolean verifyToken(String token) {
        try {
            //创建token验证器
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer("auth0").build();
            jwtVerifier.verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }

    public static Long getUserId(String token) {
        if (token == null) return null;
        return JWT.decode(token).getClaims().get("id").asLong();
    }

    public static String getUserAccount(String token) {
        if (token == null) return null;
        return JWT.decode(token).getClaims().get("userAccount").asString();
    }

    public static String getUserName(String token) {
        if (token == null) return null;
        return JWT.decode(token).getClaims().get("userName").asString();
    }

    /**
     * 从请求头获取token
     */
    public static String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }
        return null;
    }
}