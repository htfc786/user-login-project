package org.example.login.controller;

import org.apache.commons.lang3.StringUtils;
import org.example.login.annotation.AuthCheck;
import org.example.login.common.BaseResponse;
import org.example.login.common.ErrorCode;
import org.example.login.common.ResponseUtils;
import org.example.login.exception.BusinessException;
import org.example.login.model.dto.user.UserLoginRequest;
import org.example.login.model.dto.user.UserRegisterRequest;
import org.example.login.model.dto.user.WxLoginRequest;
import org.example.login.model.entity.User;
import org.example.login.model.vo.user.LoginUserVO;
import org.example.login.model.vo.user.WxLoginInfoVO;
import org.example.login.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @Value("${app.wx.appId:defaultAppId}")
    private String WxAppId;

    @PostMapping("/auth/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResponseUtils.success(result);
    }

    @PostMapping("/auth/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResponseUtils.success(loginUserVO);
    }

    @GetMapping("/auth/wx/info")
    public BaseResponse<WxLoginInfoVO> wxLoginInfo() {
        if (WxAppId.equals("defaultAppId")) {
            throw new RuntimeException("请先设置微信接口的appId和appSecret才能使用！");
        }
        WxLoginInfoVO wxLoginInfoVO = WxLoginInfoVO.toWxLoginInfoVO(WxAppId);
        return ResponseUtils.success(wxLoginInfoVO);
    }

    @PostMapping("/auth/wx/login")
    public BaseResponse<LoginUserVO> wxLogin(@RequestBody WxLoginRequest wxLoginRequest, HttpServletRequest request) {
        if (WxAppId.equals("defaultAppId")) {
            throw new RuntimeException("请先设置微信接口的appId和appSecret才能使用！");
        }
        if (wxLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String code = wxLoginRequest.getCode();
        String state = wxLoginRequest.getState();
        if (StringUtils.isAnyBlank(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.wxLogin(code, state, request);
        return ResponseUtils.success(loginUserVO);
    }

    @GetMapping("/test1")
    public BaseResponse<String> test1() {
        return ResponseUtils.success("公开接口");
    }

    @GetMapping("/test2")
    @AuthCheck
    public BaseResponse<String> test2(HttpServletRequest request) {
        Long userId = userService.getLoginUserId(request);
        User user = userService.getById(userId);
        return ResponseUtils.success("受保护的接口，登录用户：" + user.getUserAccount());
    }

}
