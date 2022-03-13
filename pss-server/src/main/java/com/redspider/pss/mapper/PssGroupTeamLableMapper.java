package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.PssGroupTeamLable;

public interface PssGroupTeamLableMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PssGroupTeamLable record);

    int insertSelective(PssGroupTeamLable record);

    PssGroupTeamLable selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PssGroupTeamLable record);

    int updateByPrimaryKey(PssGroupTeamLable record);
}