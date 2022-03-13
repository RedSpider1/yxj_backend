package com.redspider.pss.converter;

import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.repository.db.entity.UserGroup;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;

/**
 * @author QingChen
 * @date 2021/10/10
 * @description
 * @since
 */

public class UserGroupConverter {

    private UserGroupConverter() {}

    public static UserGroup convert(PssGroupTeamUserVO userVO) {
        UserGroup group = new UserGroup();
        group.setComment(userVO.getRemarks());
        group.setContractType(userVO.getType().byteValue());
        group.setGroupId(userVO.getTeamId());
        group.setContractInfo(userVO.getContactInfo());
        return group;
    }

    public static UserGroupDTO convertToDTO(UserGroup userGroup) {
        Byte contractType = userGroup.getContractType();
        return UserGroupDTO.builder()
                .groupId(userGroup.getGroupId())
                .comment(userGroup.getComment())
                .contactInfo(userGroup.getContractInfo())
                .contactType(contractType != null ? new Integer(contractType) : null)
                // .deletedStatus(DeletedStatus.getByCode(userGroup.getDelete()))
                .resourceIds(userGroup.getResourceIds())
                .userId(userGroup.getUserId())
                .id(userGroup.getId())
                .build();
    }
}
