package com.redspider.pss.logic.spi;

import com.redspider.pss.domain.PssComment;
import com.redspider.pss.request.PssCommentReq;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;

/**
 * @description:
 * @author: 11
 * @date: 2021/9/919:46
 */
public interface PssCommentLogic {

     ResponseResult saveComment(PssComment pssCommentDto);

    ResponseResult delete(Long id);

    PageResult select(PssCommentReq pssGroupTeamReq);

    PageResult selectSubCommentByRootId(PssCommentReq queryPageRequest);
}
