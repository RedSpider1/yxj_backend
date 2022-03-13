package com.redspider.pss.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

/**
 * @program: pss
 * @description: 组队单和标签关联
 * @author: txy
 * @create: 2021-06-06 16:08
 **/
@Data
@ToString
@TableName(value="pss_group_team_lable")
public class PssGroupTeamLable extends CommonDO {
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
     * 标签ID
     */
    private Long labelId;

    public PssGroupTeamLable(Long groupTeamId, Long labelId) {
        this.groupTeamId = groupTeamId;
        this.labelId = labelId;
    }

    public PssGroupTeamLable() {
    }
}
