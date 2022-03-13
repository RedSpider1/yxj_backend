package com.redspider.pss.vo.user;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author QingChen
 * @date 2021/12/20
 * @description 组队单参与者vo
 * @since
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeVO {

    private Long userGroupId;
    /**
     * 组队单id
     */
    private Long groupId;
    /**
     * 联系方式
     */
    private AttendeeContactVO attendeeContactVO;
    private Long userId;
    private String name;
    private Date birthday;
    private Integer sex;
    private String avatar;
    private String slogan;

    private String comment;
    private String resourceIds;
}
