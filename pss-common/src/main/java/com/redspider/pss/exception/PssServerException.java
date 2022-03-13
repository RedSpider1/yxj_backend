package com.redspider.pss.exception;

import com.redspider.pss.response.ResponseCode;
import lombok.Data;

/**
 * @author ：tensix
 * @date ：Created in 2021/9/29 13:02
 * @description：服务执行异常类
 * @version: 1.0
 */
@Data
public class PssServerException extends RuntimeException{

    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误描述信息
     */
    protected String message;


    /**
     * 通用异常构造方法。code统一为SYSTEM_ERROR 描述信息message可在抛出位置构造，GlobalExceptionHandler中会统一打印
     * @param message
     */
    public PssServerException(String message){
        super(message);
        this.code = ResponseCode.SYSTEM_ERROR.getCode();
        this.message = message;
    }

    /**
     * 业务异常构造方法
     * @param responseCode
     */
    public PssServerException(ResponseCode responseCode){
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    /**
     * 业务异常构造方法 在抛出异常时需要将重要监控信息打印到error日志时使用此构造方法
     * @param responseCode
     * @param bizMessage
     */
    public PssServerException(ResponseCode responseCode, String bizMessage){
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
        this.message = bizMessage;
    }
}
