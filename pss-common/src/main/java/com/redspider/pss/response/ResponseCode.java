package com.redspider.pss.response;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

@Getter
@EnumDescription("响应code码")
public enum ResponseCode {

    // 10000 - 19999 系统错误
    SYSTEM_ERROR(10000, "系统异常"),

    REDIS_ERROR(10100, "Redis异常"),

    GROUP_DEL_DUPLICATE(100300, "删除失败，组队单不存在"),

    LABEL_DEL_DUPLICATE(10400, "删除失败，标签不存在"),
    LABEL_DATA_NOT_FOUND(10401, "标签不存在"),
    LABEL_DATA_DUPLICATE(10402,"标签名已存在，创建失败"),
    LABEL_ADD_NOT_SUPPORT(10403,"不支持标签创建"),

    ACCESS_ILLEGAL(10500, "无通行证"),
    ACCESS_ROLE_ILLEGAL(10501, "通行证权限不允许"),
    ACCESS_EXPIRED_ERROR(10502, "通行证逾期"),

    LOGIN_OR_REGISTER_FAIL(10600, "登录/注册失败，请重试"),

    USER_ROLE_NOT_FOUND(10700,"当前用户权限无法匹配"),
    INSUFFICIENT_PERMISSIONS_FOR_CURRENT_USER(10701,"当前用户权限不足"),

    // 20000 - 29999 输入错误
    ILLEGAL_PARAMETER(20000, "参数不合法"),

    USER_DATA_NOT_FOUND(20001, "当前用户不存在"),
    USER_NOT_LOGIN(20002, "当前用户未登录"),
    USER_PASSWORD_ERROR(20003, "输入密码错误"),
    USER_ROLE_ERROR(20004, "当前用户无权限"),

    MODIFY_AUDIT_DATA_FAIL(20100,"修改审核单失败"),
    AUDIT_DATA_NOT_FOUND(20101, "当前审核单不存在"),

    MODIFY_GROUP_DATA_FAIL(20200,"修改组队单失败"),
    GROUP_DATA_NOT_FOUND(20201, "当前组队单不存在"),
    USER_NOT_JOIN(20202, "您还未参与该组队单"),
    GROUP_NOT_FINDING_NOT_QUIT(20203, "组队单状态不在组队中,无法退出"),

    VERIFICATION_CODE_EMPTY(20300, "验证码不能为空"),
    VERIFICATION_CODE_ILLEGAL(20301, "验证码不合法"),

    PHONE_DATA_EMPTY(20400, "手机号不能为空"),
    PHONE_DATA_NOT_FOUND(20401, "当前手机号不存在"),
    PHONE_DATA_DUPLICATE(20402, "当前手机号已重复"),
    PHONE_DATA_FORMAT_ILLEGAL(20403, "当前手机号格式有误"),

    // 30000 - 31000 操作失败
    OPERATE_FAIL(30000, "操作失败"),
    JOIN_GROUP_FAIL(30001, "加入组队单失败"),
    REMARK_REPETITION(20500,"您已举报过该业务"),


    CONTACTINFORMATION_DATA_DUPLICATE(20600, "联系方式已存在"),
    CONTACTINFORMATION_DELE_ERROR(20601, "联系方式删除失败"),
    CONTACTINFORMATION_DATA_MAX(20602, "联系方式已达到最大"),
    ;

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseCode findByCode(Integer code) {
        for (ResponseCode item : values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
