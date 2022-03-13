package com.redspider.pss.exception;

import com.redspider.pss.response.ResponseCode;

/**
 * there is file description
 *
 * @author 千般婉转皆戏言丶
 * @date 2021/9/4 20:52
 */
public class UnHandleUserRoleException extends CommonBizException {

    public UnHandleUserRoleException(){
        super("无法匹配权限");
    }

    public UnHandleUserRoleException(String message) {
        super(message);
    }

    public UnHandleUserRoleException(ResponseCode responseCode) {
        super(responseCode);
    }
}
