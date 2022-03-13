package com.redspider.pss.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author QingChen
 * @date 2021/8/14
 * @description 拼单参与用户
 * @since
 */

@Data
@Deprecated
public class AttendUserDTO extends UserDTO {

    /**
     * 联系方式类型
     */
    @ApiModelProperty(value = "联系方式类型 0电话 1微信")
    private Integer type;
}
