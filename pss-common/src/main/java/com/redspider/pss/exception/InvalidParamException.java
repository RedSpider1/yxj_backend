package com.redspider.pss.exception;

import com.redspider.pss.response.ResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 参数异常 Mark
 *
 * @author dylan
 */
@Getter
@Setter
@ToString
public class InvalidParamException extends CommonBizException {

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(ResponseCode responseCode) {
        super(responseCode);
    }
}
