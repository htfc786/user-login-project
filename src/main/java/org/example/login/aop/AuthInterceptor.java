package org.example.login.aop;

import org.example.login.annotation.AuthCheck;
import org.example.login.common.ErrorCode;
import org.example.login.exception.BusinessException;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.login.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 权限校验 AOP
 * 代码感谢鱼皮springboot-init模板
 */
@Aspect
@Component
public class AuthInterceptor {

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // 获取token
        String token = JWTUtils.getToken(request);
        if (token == null) { // 没有token
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!JWTUtils.verifyToken(token)) { // 验证token
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 通过权限校验，放行
        return joinPoint.proceed();

    }
}

