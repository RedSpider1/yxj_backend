package com.redspider.pss.security.exception;

import com.redspider.pss.response.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.AuthenticationException;

/**
 * @author dylan
 */
@Getter
@Setter
public class ParamAuthenticationException extends AuthenticationException {
    protected int code;
    protected String message;

    public ParamAuthenticationException() {
        super(ResponseCode.ACCESS_ILLEGAL.getMessage());
        this.code = ResponseCode.ACCESS_ILLEGAL.getCode();
        this.message = ResponseCode.ACCESS_ILLEGAL.getMessage();
    }

    public ParamAuthenticationException(int code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }
}
