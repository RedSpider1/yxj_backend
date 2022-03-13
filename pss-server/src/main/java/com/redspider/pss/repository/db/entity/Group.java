package com.redspider.pss.repository.db.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Group {
    private Long id;

    private String bizType;

    private String title;

    private String introduction;

    private String condition;

    private String status;

    private Date startTime;

    private Date endTime;

    private Byte anonymous;

    private Byte containMe;

    private String resource;

    private Long ownerId;

    private Long auditId;

    private Long addressId;

    private String labelIds;

    private Date createTime;

    private Date updateTime;
}