package com.redspider.pss.service.db.impl;

import com.redspider.pss.converter.SavePropertiesUtils;
import com.redspider.pss.mapper.expand.PssGroupTeamLableV1Mapper;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.PssGroupTeamLableService;
import com.redspider.pss.domain.PssGroupTeamLable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: pss
 * @description: 组队单实现类
 * @author: zzy
 * @create: 2021-05-26 22:50
 **/
@Service
@Slf4j
public class PssGroupTeamLableServiceImpl implements PssGroupTeamLableService {
    /**
     * 标签和组队单关联表Mapper
     */
    @Resource
    private PssGroupTeamLableV1Mapper teamLableMapper;

    /**
     * 根据组队单ID查询关联标签ID信息
     *
     * @param groupTeamId 组队单ID
     * @return 标签数据集合
     */
    @Override
    public List<PssGroupTeamLable> selectTeamLableList(Long groupTeamId) {
        Map<String, Object> teamIdMap = new HashMap<>();
        teamIdMap.put("group_team_id", groupTeamId);
        return teamLableMapper.selectByMap(teamIdMap);
    }

    /**
     * 保存组队单
     *
     * @param teamId   团队ID
     * @param lableIds 标签ID集合
     */
    @Override
    public void saveGroupTeamUser(Long teamId, List<Long> lableIds) {
        if (lableIds == null) {
            return;
        }

        for (Long lableId : lableIds) {
            this.saveTeamLable(setUp(teamId, lableId));
        }
    }
    /**
     * 删除组队单关联的标签ID
     * @param groupTeamId 组队单ID
     */
    @Override
    public void deleteTeamUserByTeamId(Long groupTeamId){
        List<PssGroupTeamLable> teamLables = this.selectTeamLableList(groupTeamId);
        this.deleteTeamUser(teamLables);
    }

    /**
     * 删除组队单关联的标签ID
     * @param teamUserIds 标签ID
     */
    public void deleteTeamUser(List<PssGroupTeamLable> teamUserIds){
        Long userId = UserUtil.getUserId();
        for (PssGroupTeamLable pssGroupTeamLable : teamUserIds) {
            pssGroupTeamLable.setDeleted(1);
            pssGroupTeamLable.setUpdateTime(new Date());
            pssGroupTeamLable.setUpdaterId(userId);
            this.saveTeamLable(pssGroupTeamLable);
        }
    }

    /**
     * 保存方法
     * @param groupTeamLable 组队单数据
     */
   private void saveTeamLable(PssGroupTeamLable groupTeamLable) {
       groupTeamLable.setUpdateTime(new Date());
       if (groupTeamLable.getId() == null) {
           teamLableMapper.insert(groupTeamLable);
       } else {
           teamLableMapper.updateById(groupTeamLable);
       }
    }

    public PssGroupTeamLable setUp(Long teamId, Long lableId) {
        Long userId = UserUtil.getUserId();
        PssGroupTeamLable pssGroupTeamLable = new PssGroupTeamLable(teamId, lableId);
        SavePropertiesUtils.saveProperties(pssGroupTeamLable, userId);
        return pssGroupTeamLable;
    }


}
