package com.redspider.pss.service;

import com.redspider.pss.domain.PssComment;
import com.redspider.pss.request.PssCommentReq;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;

/**
 * @description:
 * @author: 11
 * @date: 2021/9/9  19:53
 */

public interface PssCommentService {
    ResponseResult saveComment(PssComment pssComment);

    ResponseResult delete(Long id);

    PageResult select(PssCommentReq id);

    PageResult selectSubCommentByRootId(PssCommentReq queryPageRequest);
}
