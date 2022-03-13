package com.redspider.pss.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class BasePageQuery {

    @ApiModelProperty(value = "查询页码", name = "pageNum")
    @Max(value = 9999999, message = "请传入范围小于9999999的数字")
    @JsonAlias({"currentPage", "pageNum"})
    protected int pageNum = 1;
    @ApiModelProperty(value = "查询大小", name = "pageSize")
    @Max(value = 9999999, message = "请传入范围小于9999999的数字")
    protected int pageSize = 10;

    public BasePageQuery() {

    }

    public BasePageQuery(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return pageNum > 0 ? (pageNum - 1) * pageSize : 0;
    }
}
