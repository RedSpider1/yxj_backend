package com.redspider.pss.response;

import lombok.Data;

/**
 * 统一结果返回
 *
 * @param <T> 结果数据类型
 */
@Data
public class ResponseResult<T> {

    private int code = 1;

    private String message;

    private T data;

    private ResponseResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<T> success() {
        return success(null);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<>(200, "success", data);
    }

    public static <T> ResponseResult<T> successMsg(String message) {
        return new ResponseResult<>(200, message, null);
    }

    public static <T> ResponseResult<T> ok() {
        return new ResponseResult<>(200, "成功!", null);
    }

    public static <T> ResponseResult<T> fail(String message) {
        return fail(-1, message);
    }

    public static <T> ResponseResult<T> fail(int code, String message) {
        return fail(code, message, null);
    }

    public static <T> ResponseResult<T> fail(int code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }
}
