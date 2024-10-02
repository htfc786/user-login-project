package org.example.login.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.example.login.common.ErrorCode;
import org.example.login.exception.BusinessException;
import org.example.login.model.dto.user.WxLoginUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class WxUtils {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${app.wx.appId:defaultAppId}")
    private String appId;

    @Value("${app.wx.appSecret:defaultAppSecret}")
    private String appSecret;

    public WxLoginUserInfo getUserInfo(String code) {
        if (appId.equals("defaultAppId") || appSecret.equals("defaultAppSecret")) {
            throw new RuntimeException("请先设置微信接口的appId和appSecret才能使用！");
        }
        // 1. 通过code换取网页授权access_token
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appId}&secret={appSecret}&code={code}&grant_type=authorization_code";
        JSONObject accessTokenResponse = getForObject(accessTokenUrl, appId, appSecret, code);

        if (accessTokenResponse.getIntValue("errcode") == 40029) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "code无效！");
        }
        if (accessTokenResponse.getIntValue("errcode") != 0) {
            throw new RuntimeException("Failed to get access token: " + accessTokenResponse.getString("errmsg"));
        }

        String accessToken = accessTokenResponse.getString("access_token");
        String openid = accessTokenResponse.getString("openid");

        // 2. 拉取用户信息（需scope为snsapi_userinfo）
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token={access_token}&openid={openid}&lang=zh_CN";
        JSONObject userInfoResponse = getForObject(userInfoUrl, accessToken, openid);

        if (userInfoResponse.getIntValue("errcode") != 0) {
            throw new RuntimeException("Failed to get user info: " + userInfoResponse.getString("errmsg"));
        }

        return WxLoginUserInfo.toWxLoginUserInfo(userInfoResponse);
    }

    private JSONObject getForObject(String url, Object... uriVariables) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);
        if (response.getStatusCode().is2xxSuccessful()) {
            return JSON.parseObject(response.getBody());
        } else {
            throw new RuntimeException("Request failed with status: " + response.getStatusCode());
        }
    }
}