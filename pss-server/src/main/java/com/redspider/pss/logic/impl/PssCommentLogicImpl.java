package com.redspider.pss.logic.impl;

import com.redspider.pss.domain.PssComment;
import com.redspider.pss.logic.spi.PssCommentLogic;
import com.redspider.pss.request.PssCommentReq;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.PssCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 11
 * @date: 2021/9/919:51
 */

@Service
@Slf4j
public class PssCommentLogicImpl  implements PssCommentLogic {

    @Autowired
    private PssCommentService pssCommentService;

    @Override
    public ResponseResult saveComment(PssComment pssCommentDto) {
        return pssCommentService.saveComment(pssCommentDto);
    }

    @Override
    public ResponseResult delete(Long id) {
        return pssCommentService.delete(id);
    }

    @Override
    public PageResult select(PssCommentReq pssCommentReq) {
        return pssCommentService.select(pssCommentReq);
    }

    @Override
    public PageResult selectSubCommentByRootId(PssCommentReq queryPageRequest) {
        return pssCommentService.selectSubCommentByRootId(queryPageRequest);
    }
}
