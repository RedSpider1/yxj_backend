package com.redspider.pss.repository.db.entity;

import lombok.Data;

import java.util.Date;

@Data
public class GroupInvolveRecord {
    private Long id;

    private Long groupTeamId;
    
    private String remark;
    
    private String pictureUrl;
    
    private Integer flag;

    private Long creatorId;

    private Long updaterId;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;
}