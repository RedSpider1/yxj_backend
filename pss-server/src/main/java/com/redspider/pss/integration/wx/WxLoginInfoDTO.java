package com.redspider.pss.integration.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 微信登录信息
 * @author: Tony
 * @date: 2021/8/22
 */
@Data
public class WxLoginInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String openId;

    private String sessionKey;

    private String unionId;

}
