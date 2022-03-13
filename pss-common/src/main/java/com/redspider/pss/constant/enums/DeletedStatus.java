package com.redspider.pss.constant.enums;

import cn.hutool.core.lang.Assert;
import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DeletedStatusEnum
 * @Description 删除状态码
 * @Author hf
 * @Version V1.0
 **/
@Getter
@EnumDescription("删除状态码")
public enum DeletedStatus {

    UN_DELETE(0, "un_delete"),
    DELETE(1, "delete");

    private int code;
    private String description;

    DeletedStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static DeletedStatus getByCode(int code) {
        List<DeletedStatus> collect = Arrays.stream(DeletedStatus.values()).filter(en -> en.getCode() == code).collect(Collectors.toList());
        Assert.isTrue(collect.size() != 0, "给定code有误：" + code);
        return collect.get(0);
    }
}
