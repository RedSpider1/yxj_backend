package com.redspider.pss.exception;

import com.redspider.pss.response.ResponseCode;

/**
 * there is file description
 *
 * @author 千般婉转皆戏言丶
 * @date 2021/9/3 1:24
 */
public class UserRolePermissionForbiddenException extends CommonBizException {

    public UserRolePermissionForbiddenException() {
        super("权限不足");
    }

    public UserRolePermissionForbiddenException(String message) {
        super(message);
    }

    public UserRolePermissionForbiddenException(ResponseCode responseCode) {
        super(responseCode);
    }
}
