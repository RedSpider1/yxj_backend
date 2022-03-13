package com.redspider.pss.constant.enums;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

/**
 * @ClassName TeamUserTypeEnum
 * @Description 联系方式类型
 * @Author hf
 * @Version V1.0
 **/
@Getter
@EnumDescription("联系方式类型_old")
public enum ContactType {

    PHONE(0, "phone"),
    WE_CHAT(1, "we_chat");

    private int code;
    private String description;

    ContactType(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
