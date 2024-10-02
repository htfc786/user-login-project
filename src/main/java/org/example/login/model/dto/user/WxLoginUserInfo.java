package org.example.login.model.dto.user;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public class WxLoginUserInfo {
    private String openid;
    private String nickname;
    private Integer sex;
    private String province;
    private String city;
    private String country;
    private String headimgurl;
    private String unionid;

    public static WxLoginUserInfo toWxLoginUserInfo(JSONObject args) {
        WxLoginUserInfo wxLoginUserInfo = new WxLoginUserInfo();
        wxLoginUserInfo.setOpenid(args.getString("openid"));
        wxLoginUserInfo.setNickname(args.getString("nickname"));
        wxLoginUserInfo.setSex(args.getIntValue("sex"));
        wxLoginUserInfo.setProvince(args.getString("province"));
        wxLoginUserInfo.setCity(args.getString("city"));
        wxLoginUserInfo.setCountry(args.getString("country"));
        wxLoginUserInfo.setHeadimgurl(args.getString("headimgurl"));
        wxLoginUserInfo.setUnionid(args.getString("unionid"));
        return wxLoginUserInfo;
    }
}
