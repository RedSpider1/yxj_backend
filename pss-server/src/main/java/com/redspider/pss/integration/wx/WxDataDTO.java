package com.redspider.pss.integration.wx;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 微信加密信息
 * @author: Tony
 * @date: 2021/8/22
 */
@Data
public class WxDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sessionKey;

    private String encryptedData;

    private String iv;

    private String cloudId;

    private String jsCode;
}
