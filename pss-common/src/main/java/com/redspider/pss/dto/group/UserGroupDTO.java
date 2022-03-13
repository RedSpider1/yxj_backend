package com.redspider.pss.dto.group;

import com.redspider.pss.constant.enums.DeletedStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserGroupDTO {

    private Long id;

    private Long groupId;

    private Long userId;

    private DeletedStatus deletedStatus;

    private Integer contactType;

    private String contactInfo;

    private String comment;

    private String resourceIds;
}
