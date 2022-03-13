package com.redspider.pss.dto.team;

import java.util.Date;
import lombok.Data;

/**
 * @description: 参与过的组队单信息
 * @author: xiaoA
 * @create: 2021-07-18 16:01
 */
@Data
public class InvolveGroupDTO {

    /**
     * 组队单ID
     */
    private Long groupTeamId;

    /**
     * 组队单标题
     */
    private String groupTitle;

    /**
     * 组队单solgan
     */
    private String groupSlogan;

    /**
     * 参与组队单时间
     */
    private Date involveTime;

    /**
     * 参与组队单的状态
     */
    private Date involveGroupStatus;
}
