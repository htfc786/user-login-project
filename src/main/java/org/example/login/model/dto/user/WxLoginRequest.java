package org.example.login.model.dto.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxLoginRequest implements Serializable {

    private String code;

    private String state;

}