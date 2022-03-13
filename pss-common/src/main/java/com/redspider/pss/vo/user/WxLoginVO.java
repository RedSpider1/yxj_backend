package com.redspider.pss.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 微信登录
 * @author: Tony
 * @date: 2021/8/22
 */
@Data
public class WxLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String jsCode;
}
