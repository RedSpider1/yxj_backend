package com.redspider.pss.service.db.spi;

import com.redspider.pss.domain.PssGroupTeamLable;

import java.util.List;

public interface PssGroupTeamLableService {


    /**
     * 根据组队单ID查询关联标签ID信息
     *
     * @param groupTeamId 组队单ID
     * @return 标签数据集合
     */
    List<PssGroupTeamLable> selectTeamLableList(Long groupTeamId);

    /**
     * 保存组队单
     *
     * @param teamId   团队ID
     * @param lableIds 标签ID集合
     */
    void saveGroupTeamUser(Long teamId, List<Long> lableIds);

    /**
     * 删除组队单关联的标签ID
     * @param groupTeamId 组队单ID
     */
    void deleteTeamUserByTeamId(Long groupTeamId);
}
