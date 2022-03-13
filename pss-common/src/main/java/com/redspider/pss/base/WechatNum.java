package com.redspider.pss.base;

import lombok.Data;

/**
 * @Description: 微信号
 * @create: 2021-06-20 17:31
 */
@Data
public class WechatNum {
    private String value;

    public WechatNum(String wechatNum) {
        this.value = wechatNum;
    }

    public String getValue() {
        return this.value;
    }
}
