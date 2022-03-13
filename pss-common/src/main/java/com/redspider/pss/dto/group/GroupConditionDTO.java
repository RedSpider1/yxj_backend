package com.redspider.pss.dto.group;


import lombok.Data;

@Data
public class GroupConditionDTO {

    /**
     * 最小组队人数
     */
    private Integer minTeamSize;
    /**
     * 最大组队人数
     */
    private Integer maxTeamSize;
}
