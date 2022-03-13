package com.redspider.pss.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 微信信息
 * @author: Tony
 * @date: 2021/8/22
 */
@Data
public class WxDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String encryptedData;

    private String iv;

    private String cloudId;

    private String jsCode;
}
