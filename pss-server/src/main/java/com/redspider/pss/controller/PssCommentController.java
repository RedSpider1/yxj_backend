package com.redspider.pss.controller;

//import com.redspider.pss.aop.NoRepeatSubmit;
import com.redspider.pss.domain.PssComment;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.logic.spi.PssCommentLogic;
import com.redspider.pss.request.PssCommentReq;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
@Api(tags = "评论")
public class PssCommentController {


    @Autowired
    private PssCommentLogic pssCommentLogic;

    @ApiOperation("保存/修改评论")
//    @NoRepeatSubmit(lockTime = 60)
    @PostMapping(value = "/saveComment")
    public ResponseResult saveComment(@RequestBody PssComment pssComment) {
        if (pssComment == null) {
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER,"评论内容为空，请检查！");
        }

        return pssCommentLogic.saveComment(pssComment);
    }
    @ApiOperation("删除评论")
    @DeleteMapping(value = "/del/{id}")
    public ResponseResult del(@PathVariable("id") Long id) {
        if (id == null) {
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER,"删除评论时传入ID为空，请检查！");
        }
        return pssCommentLogic.delete(id);
    }
    @ApiOperation("查询组队单评论")
    @RequestMapping(value = "/{relationId}")
    public PageResult selectCommentById(@Validated PssCommentReq queryPageRequest) {
        return pssCommentLogic.select(queryPageRequest);
    }
    @ApiOperation("查询组队单评论")
    @RequestMapping(value = "query/{id}")
    public PageResult selectSubCommentByRootId(@Validated PssCommentReq queryPageRequest) {
        return pssCommentLogic.selectSubCommentByRootId(queryPageRequest);
    }

}
