package com.redspider.pss.constant.enums;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

@EnumDescription("登录方式")
@Getter
public enum LoginType {

    WX_MINI(0, "wx_mini"),
    H5(1, "h5");

    private int code;
    private String description;

    LoginType(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
