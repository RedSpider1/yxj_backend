package com.redspider.pss.constant.enums;

import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.response.ResponseCode;

/**
 * there is file description
 *
 * @author 千般婉转皆戏言丶
 * @date 2021/9/3 0:12
 */
public enum UserRole {

    /**
     * 管理员
     */
    ADMINISTRATOR(1, 20, "administrator"),
    /**
     * 注册用户
     */
    REGISTERED_USER(2, 10, "registered_user"),
    /**
     * 游客
     */
    TOURIST(3, 0, "tourist");
    /**
     * 数据库字段id
     */
    private final int type;
    /**
     * 权限等级，值越大 = 等级越高
     * 目前尚不确定数据库type值是否与权限等级成线性相关，暂且保留
     * 后续确认数据库type值与权限等级成线性相关的话，可以考虑删除
     */
    private final int level;
    /**
     * 权限释义
     */
    private final String des;

    UserRole(int dataBaseType, int permissionLevel, String introduction) {
        this.type = dataBaseType;
        this.level = permissionLevel;
        this.des = introduction;
    }

    public int getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public String getDes() {
        return des;
    }

    public static UserRole findUserRoleByDbType(int type) {
        for (UserRole e : UserRole.values()) {
            if (e.type == type) {
                return e;
            }
        }
        throw new PssValidationException(ResponseCode.USER_ROLE_NOT_FOUND);
    }

    public static UserRole findUserRoleByLevel(int permissionLevel) {
        for (UserRole e : UserRole.values()) {
            if (e.level == permissionLevel) {
                return e;
            }
        }
        throw new PssValidationException(ResponseCode.USER_ROLE_NOT_FOUND);
    }

    public static void verifyUserRole(UserRole userRole, UserRole targetRole) {
        if (userRole.level < targetRole.level) {
            throw new PssValidationException(ResponseCode.INSUFFICIENT_PERMISSIONS_FOR_CURRENT_USER);
        }
    }
}
