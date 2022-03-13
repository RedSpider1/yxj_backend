package com.redspider.pss.query;

import com.redspider.pss.request.BasePageQuery;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description: 搜索功能查询对象
 * @author: tony
 * @date: 22021年9月4日13:45:59
 */
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SearchQuery<T> extends BasePageQuery {
    private T keyword;
}
