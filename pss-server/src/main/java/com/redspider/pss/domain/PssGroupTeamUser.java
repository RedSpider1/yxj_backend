package com.redspider.pss.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * @program: pss
 * @description: 组队单和组队人关联
 * @author: txy
 * @create: 2021-06-06 16:04
 **/
@Data
@ToString
@TableName(value="pss_group_team_user")
@ApiModel(value = "组队单和组队人关联")
public class PssGroupTeamUser extends CommonDO {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    /**
     * 组队单ID
     */
    private Long groupTeamId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 参加组队状态 0申请加入组团 1 组队成功 2 退出组队
     */
    private Integer status;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    private String contactInformation;

    /**
     * 联系方式类型
     */
    @ApiModelProperty(value = "联系方式类型 0电话 1微信")
    private Integer type;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String comment;

    private String resourceIds;

    public JSONArray getResourceIds() {
        return StringUtils.isBlank(resourceIds) ? new JSONArray() : JSON.parseArray(resourceIds);
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public PssGroupTeamUser(Long groupTeamId, Long userId, Integer status, String contactInformation, Integer type, String comment) {
        this.groupTeamId = groupTeamId;
        this.userId = userId;
        this.status = status;
        this.contactInformation = contactInformation;
        this.comment = comment;
        this.type = type;
    }

    public PssGroupTeamUser() {
    }
}
