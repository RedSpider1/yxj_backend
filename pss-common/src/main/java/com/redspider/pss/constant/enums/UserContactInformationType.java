package com.redspider.pss.constant.enums;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

/**
 * 个人信息联系方式类型
 *
 */
@Getter
@EnumDescription("联系方式类型")
public enum UserContactInformationType {

    PHONE(0, "手机"),
    WE_CHAT(1, "微信"),
    QQ(2, "QQ"),
    EMAIL(3,"邮箱");

    private int code;
    private String description;

    UserContactInformationType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static String getDescriptionByCode(int code) {
        for (UserContactInformationType value : UserContactInformationType.values()) {
            if (value.getCode() == code) {
                return value.description;
            }
        }
        return "无法识别的联系类型";
    }
}
