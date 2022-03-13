package com.redspider.pss.exception;

import com.redspider.pss.response.ResponseCode;

public class UserNotExistException extends CommonBizException {

    public UserNotExistException() {
        super(ResponseCode.USER_DATA_NOT_FOUND);
    }

    public UserNotExistException(ResponseCode responseCode) {
        super(responseCode);
    }
}
