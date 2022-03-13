package com.redspider.pss.vo.team;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.redspider.pss.request.BasePageQuery;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
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
public class SearchVO<T> extends BasePageQuery {
    @JsonAlias({"q", "keyword"})
    @NotNull(message = "搜索条件不能为空")
    private T keyword;
}
