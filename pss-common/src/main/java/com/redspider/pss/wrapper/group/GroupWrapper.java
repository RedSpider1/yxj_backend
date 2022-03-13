package com.redspider.pss.wrapper.group;

import com.redspider.pss.constant.enums.Group;
import com.redspider.pss.constant.enums.Group.GroupContainType;
import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.group.GroupInfoVO;
import com.redspider.pss.vo.user.UserInfoVO;
import com.redspider.pss.wrapper.common.ConditionWrapper;
import com.redspider.pss.wrapper.common.ResourceWrapper;
import java.util.Objects;
import org.springframework.beans.BeanUtils;

public class GroupWrapper {

    public static GroupBundleVO wrap(GroupDTO dto) {
        GroupBundleVO vo = new GroupBundleVO();
        BeanUtils.copyProperties(dto, vo);
        vo.setCondition(ConditionWrapper.wrap(dto.getCondition(), dto.getCurrentSize()));
        vo.setResourceObjList(ResourceWrapper.wrap(dto.getResourceObjList()));
        UserInfoVO userInfoVO = UserInfoVO.builder().id(dto.getOwnerId()).build();
        vo.setOwnerInfo(userInfoVO);
        vo.setStatus(Group.GroupStatus.valueOf(dto.getStatus()).getCode());
        return vo;
    }

    public static GroupInfoVO wrap2Info(GroupBundleVO groupBundleVO) {
        GroupInfoVO vo = new GroupInfoVO();
        BeanUtils.copyProperties(groupBundleVO, vo);
        return vo;
    }

    public static GroupDTO unwrap(GroupBundleVO vo, Long userId) {
        GroupDTO dto = new GroupDTO();
        BeanUtils.copyProperties(vo, dto);
        dto.setOwnerId(userId);
        dto.setCondition(ConditionWrapper.unwrap(vo.getCondition()));
        dto.setResourceList(vo.getResourceList());
        return dto;
    }
}
