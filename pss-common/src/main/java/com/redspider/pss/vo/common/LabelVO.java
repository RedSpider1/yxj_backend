package com.redspider.pss.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author tony
 * @date 2021/6/15 22:04
 * @since
 */
@Data
@ApiModel(description = "标签信息")
public class LabelVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("标签id")
    private Long id;
    @ApiModelProperty("标签名")
    private String labelName;
}
