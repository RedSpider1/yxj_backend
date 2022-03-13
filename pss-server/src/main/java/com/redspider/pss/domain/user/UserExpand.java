package com.redspider.pss.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @description: 用户扩展信息
 * @author: Tony
 * @date: 2021/8/22
 */
@Data
@TableName(value="user_expand")
public class UserExpand {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String openId;
    private String unionId;
    private String sessionKey;
    private Long updaterId;
    private Date createTime;
    private Date updateTime;
}
