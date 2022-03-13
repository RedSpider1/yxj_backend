package com.redspider.pss.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PssRemarkReq extends BasePageQuery {
    
    /**
     * 业务类型
     */
    @ApiModelProperty(value = "业务类型(0组队单, 1评论, 2用户)", name = "bizType")
    private Integer bizType;
}
