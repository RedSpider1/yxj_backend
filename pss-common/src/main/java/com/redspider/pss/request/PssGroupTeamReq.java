package com.redspider.pss.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PssGroupTeamReq extends BasePageQuery {
    /**
     * 组队单id
     */
    private Long id;

    /**
     * 关键字
     */
    private String keyWord;
}
