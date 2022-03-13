package com.redspider.pss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redspider.pss.converter.SavePropertiesUtils;
import com.redspider.pss.domain.PssComment;
import com.redspider.pss.mapper.PssCommentMapper;
import com.redspider.pss.request.PssCommentReq;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.PssCommentService;
import com.redspider.pss.vo.comment.PssCommentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: 11
 * @date: 2021/9/9  19:54
 */
@Service
@Slf4j
public class pssCommentServiceImpl implements PssCommentService {

    @Autowired
    private PssCommentMapper pssCommentMapper;

    @Override
    public ResponseResult saveComment(PssComment pssComment) {
        if (ObjectUtils.isEmpty(pssComment.getPid())) {
            //如果是顶层评论，前端传递空。
            pssComment.setPid(0L);
            pssComment.setRepliedId(0L);
        }
        pssComment.setHide(1);

        SavePropertiesUtils.saveProperties(pssComment, UserUtil.getUserId());
//        SavePropertiesUtils.saveProperties(pssComment,111L);
        int insertResult = pssCommentMapper.insert(pssComment);
        if (insertResult > 0) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.fail("评论失败");
        }
    }

    @Override
    public ResponseResult delete(Long id) {
        if (pssCommentMapper.logicDeleteById(id) > 0) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.fail("删除失败");
        }
    }

    @Override
    public PageResult select(PssCommentReq pssCommentReq) {
        IPage<PssCommentVO> page = new Page<>(pssCommentReq.getPageNum(), pssCommentReq.getPageSize());
        // 父分页查询
        IPage<PssCommentVO> rootNodeByRelationId = pssCommentMapper.getRootNodeByRelationId(page, pssCommentReq.getRelationId());
        if (rootNodeByRelationId.getTotal() == 0) {
            return new PageResult(200, "当前组队单暂无评论");
        }

        List<PssCommentVO> pssCommentVos = this.basicQuery(rootNodeByRelationId.getRecords(), pssCommentReq);

        return new PageResult(200, rootNodeByRelationId.getTotal(), pssCommentVos);
    }

    @Override
    public PageResult selectSubCommentByRootId(PssCommentReq queryPageRequest) {
        IPage<PssCommentVO> page = new Page<>(queryPageRequest.getPageNum(), queryPageRequest.getPageSize());
        IPage<PssCommentVO> pssCommentVoIpage = pssCommentMapper.selectSubCommentByRootId(page,queryPageRequest.getId());
        return new PageResult<>(200,pssCommentVoIpage.getTotal(),pssCommentVoIpage.getRecords());
    }

    private List<PssCommentVO> basicQuery(List<PssCommentVO> commentVoList, PssCommentReq pssCommentReq) {
        //拿到顶层评论的id集合
        List<Long> rootIds = new ArrayList<>();
        commentVoList.stream().forEach(commentVo ->
                rootIds.add(commentVo.getId())
        );
        //根据顶层id 查询 每个 顶级评论下面 子评论的 数量
        List<PssCommentVO> subCount = pssCommentMapper.getSubNodeCountByRootId(rootIds);
        commentVoList.stream().forEach(parentComVo -> {
            for (PssCommentVO subCountVo : subCount) {
                if (parentComVo.getId().equals(subCountVo.getPid())){
                    parentComVo.setSubLen(subCountVo.getSubLen());
                }
            }
        });
        //根据顶层id 查询 所有下面 的子评论
        List<PssCommentVO> subNodeList = pssCommentMapper.getSubNodeListByRootId(rootIds);
        //拿到每个子评论的  被回复人id  进行转码
        Set<Long> repliedIdList = new HashSet<>();
        subNodeList.stream().forEach(commentVo ->
                repliedIdList.add(commentVo.getRepliedId())
        );
        List<PssCommentVO> subRepliedName = pssCommentMapper.getSubRepliedName(repliedIdList);
        subNodeList.stream().forEach(commentVo -> {
            for (PssCommentVO vo : subRepliedName) {
                if (commentVo.getRepliedId().equals(vo.getRepliedId())) {
                    commentVo.setRepliedName(vo.getRepliedName());
                }
            }
        });
        //对每个子评论 和顶级评论进行 关联
        commentVoList.stream().forEach(parentComVo -> {

            List<PssCommentVO> collect = subNodeList.stream().filter(subvo -> subvo.getPid().equals(parentComVo.getId())).collect(Collectors.toList());
            parentComVo.setSubList(collect);

        });
        return commentVoList;
    }
}