package com.redspider.pss.vo.group;

import com.redspider.pss.vo.user.AttendeeVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Qishu
 * @description 用户组队单关系
 * @since
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRelationVO {
    @ApiModelProperty(value = "我已经参与")
    public boolean joined;

    @ApiModelProperty(value = "我收藏的")
    public boolean collected;

    @ApiModelProperty(value = "是我创建的")
    public boolean created;
}
