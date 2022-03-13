package com.redspider.pss.base;


import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class PageResult<T> implements Serializable {

    private int code;

    private String message;
    /**
     * 当前页结果
     */
    private List<T> data;
    /**
     * 总记录数
     */
    private long total;

    public PageResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public PageResult(long total, List<T> data) {
        super();
        this.total = total;
        this.data = data;
    }

    public PageResult(int code, long total, List<T> data) {
        super();
        this.code = code;
        this.total = total;
        this.data = data;
    }

    public static <T> PageResult<T> of(long total, List<T> rows) {
        return new PageResult<>(total, rows);
    }
}
