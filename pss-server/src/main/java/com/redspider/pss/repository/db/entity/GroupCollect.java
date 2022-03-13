package com.redspider.pss.repository.db.entity;

import lombok.Data;

import java.util.Date;
@Data
public class GroupCollect {
    private Long id;

    private Long userId;

    private Long groupTeamId;

    private Long creatorId;

    private Long updaterId;

    private Date createTime;

    private Date updateTime;

    private Integer deleted;
}