package com.redspider.pss.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.redspider.pss.domain.PssComment;
import com.redspider.pss.vo.comment.PssCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: 11
 * @date: 2021/9/9  20:09
 */
@Mapper
public interface PssCommentMapper  extends BaseMapper<PssComment> {
    /**
     * 逻辑删除评论
     * @param id
     * @return
     */
    Integer  logicDeleteById(Long id);

    /**
     * 查询顶层评论
     * @param page
     * @param id
     * @return
     */
    IPage<PssCommentVO> getRootNodeByRelationId(IPage<PssCommentVO> page, @Param("id") Long id);

    /**
     * 根据顶层id 查询 所有下面 的子评论
     * @param rootId
     * @return
     */
    List<PssCommentVO> getSubNodeListByRootId(List<Long> rootId);
    /**
     *  拿到每个子评论的被回复人名称
     * @return
     */
    List<PssCommentVO> getSubRepliedName(Set<Long> repliedIdList);
    /**
     *  根据顶级评论id拿到分页子评论
     * @return
     */
    IPage<PssCommentVO> selectSubCommentByRootId(IPage<PssCommentVO> page, Long id);
    /**
     * 根据顶层id 查询 所有下面 的子评论
     * @param rootId
     * @return
     */
    List<PssCommentVO> getSubNodeCountByRootId(List<Long> rootId);
}
