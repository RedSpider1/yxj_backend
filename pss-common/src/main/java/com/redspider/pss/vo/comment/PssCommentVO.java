package com.redspider.pss.vo.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 组队单评论
 * @author: 11
 * @date: 2021-09-10
 */
@Data
@ApiModel(value = "组队单评论")
public class PssCommentVO implements Serializable {

    private Long id;
    /**
     * '顶层 pid就是0，其他的pid都是顶层的评论id'
     */
    private Long pid;

    /**
     * 关系id 比如组队单 就是存放 当前组队单的id
     */
    private Long relationId;

    /**
     * 评论人名称
     */
    private String commentatorName;
    /**
     * 评论内容
     */
    private String content;
//    /**
//     * 评论人id
//     */
//    private Long commentatorId;
    /**
     * 被回复人id
     */
    private Long repliedId;
    /**
     * 被回复人名称
     */
    private String repliedName;
//    /**
//     * 默认是1，删除为0
//     */
//    private int deleted;

    /**
     *评论人头像
     */
    private String avatar;
//    /**
//     * 更新时间
//     */
//    private Date updateTime;

    /**
     * 创建人id
     */
    private Long creatorId;

//    /**
//     * 更新人id
//     */
//    private Long updaterId;

    /**
     * 是否删除
     */
    private int hide;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 子节点
     */
    private List<PssCommentVO> subList;
    /**
     * 子评论数量
     */
    private Integer subLen;
    /**
     * 无用字段
     */
    private String countryRank;

}
