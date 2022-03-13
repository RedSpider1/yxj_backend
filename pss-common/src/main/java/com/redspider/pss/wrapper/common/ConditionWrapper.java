package com.redspider.pss.wrapper.common;

import com.redspider.pss.dto.group.GroupConditionDTO;
import com.redspider.pss.vo.group.GroupConditionVO;
import org.springframework.beans.BeanUtils;

public class ConditionWrapper {

    public static GroupConditionDTO unwrap(GroupConditionVO vo) {
        GroupConditionDTO dto = new GroupConditionDTO();
        BeanUtils.copyProperties(vo, dto);
        return dto;
    }

    public static GroupConditionVO wrap(GroupConditionDTO dto, Integer currentSize) {
        GroupConditionVO vo = new GroupConditionVO();
        BeanUtils.copyProperties(dto, vo);
        vo.setCurrentTeamSize(currentSize);
        return vo;
    }
}
