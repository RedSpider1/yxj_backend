package com.redspider.pss.vo.group;

import com.redspider.pss.dto.user.AttendUserDTO;
import com.redspider.pss.vo.user.AttendeeVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author QingChen
 * @date 2021/12/20
 * @description 组队单参与者
 * @since
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupAttendeesVO {

    private Long groupId;
    /**
     * 参与者信息
     */
    private List<AttendeeVO> attendeeVOS;
    private int count;
}
