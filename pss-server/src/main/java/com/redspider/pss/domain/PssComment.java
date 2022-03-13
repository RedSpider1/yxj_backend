package com.redspider.pss.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: 11
 * @date: 2021/9/9  20:10
 */
@Data
@ToString
@TableName(value = "pss_comment")
public class PssComment extends CommonDO  implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
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
    /**
     * 默认是1，删除为0
     */
    private Integer hide;
//    /**
//     * 创建时间
//     */
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    private Date createTime;
}
