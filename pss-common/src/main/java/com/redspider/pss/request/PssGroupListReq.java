package com.redspider.pss.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PssGroupListReq extends BasePageQuery {
    /**
     * 组队单列表类型
     */
    @ApiModelProperty(value = "组队单列表类型 0首页, 1我参与的, 2我浏览过, 3收藏列表 4我创建的 5搜索页面")
    private Integer type;
    
    /**
     * 关键字
     */
    private String keyWord;
}
