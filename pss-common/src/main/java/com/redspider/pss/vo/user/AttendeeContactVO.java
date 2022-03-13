package com.redspider.pss.vo.user;

import com.redspider.pss.constant.enums.UserContactInformationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author QingChen
 * @date 2021/12/20
 * @description
 * @since
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeContactVO {

    /**
     * 微信-0，电话-1
     */
    private Integer code;
    /**
     * 微信，电话
     */
    private String name;
    private String value;

    public String getName() {
        if(code == null) {
            return "未知";
        }
        return UserContactInformationType.getDescriptionByCode(code);
    }
}
