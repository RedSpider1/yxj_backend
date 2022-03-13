package com.redspider.pss.exception;

import com.redspider.pss.response.ResponseCode;
import lombok.Data;

/**
 * 参数异常 Mark
 *
 * @author dylan
 */
@Data
public class CommonBizException extends RuntimeException {

    protected Integer code;
    protected String message;

    public CommonBizException(String message) {
        super(message);
        this.code = ResponseCode.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    public CommonBizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public CommonBizException(ResponseCode responseCode, String bizMessage) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = bizMessage;
    }
}
