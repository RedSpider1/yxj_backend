package com.redspider.pss.dto.es;

import lombok.Data;

/**
 * @author QingChen
 * @date 2021/8/4
 * @description querySource条件对象化
 * @since
 */

@Data
public class SearchSourceDTO {

    private Integer from;
    private Integer size;

    /**
     * 排序字段
     */
    private String sortKey;

    /**
     * 是否正序
     */
    private boolean asc = false;

    /**
     * 查询的字段
     */
    private String[] includes;

    /**
     * 排除的字段
     */
    private String[] excludes;
}
