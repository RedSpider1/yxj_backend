package com.redspider.pss.dto.team;

import com.redspider.pss.request.BasePageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PssGroupTempDTO extends BasePageQuery {

    /**
     * 组队单id
     */
    private String id;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 发布状态
     */
    private Integer releaseStatus;
    /**
     * 关键字
     */
    private String keyWord;


}
