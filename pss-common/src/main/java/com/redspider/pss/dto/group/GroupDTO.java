package com.redspider.pss.dto.group;

import lombok.Data;

import java.util.List;

@Data
public class GroupDTO {

    private Long id;

    private Integer contactType;

    private String contactInfo;

    private String bizType;

    private String title;

    private String introduction;

    private GroupConditionDTO condition;

    private Long startTime;

    private Long endTime;

    private Byte containMe;

    private List<String> resourceList;

    private Long ownerId;

    private Long auditId;

    private Long addressId;

    private List<String> labels;
    
    private String status;

    private Integer currentSize;

    private List<ResourceDTO> resourceObjList;
}
