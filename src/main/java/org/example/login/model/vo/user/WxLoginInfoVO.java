package org.example.login.model.vo.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxLoginInfoVO implements Serializable {

    // 微信appid
    private String wxAppId;

    public static WxLoginInfoVO toWxLoginInfoVO(String wxAppId) {
        WxLoginInfoVO wxLoginInfoVO = new WxLoginInfoVO();
        wxLoginInfoVO.setWxAppId(wxAppId);
        return wxLoginInfoVO;
    }

}
