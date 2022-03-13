package com.redspider.pss.vo.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * @author tony
 * @date 2021/6/15 22:03
 * @since
 */
@Data
@ApiModel(description = "标签对象")
public class LabelAddVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotBlank(message = "标签名字不能为空")
    @Size(max = 32, min = 1, message = "标签名长度非法")
    @ApiModelProperty(value = "标签名")
    private String labelName;

}
