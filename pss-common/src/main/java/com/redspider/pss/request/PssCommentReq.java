package com.redspider.pss.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PssCommentReq extends BasePageQuery {


    /**
     * 组队单id
     */
    private Long relationId;

    /**
     * 顶级评论id
     */
    private Long id;
}
