package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.PssGroupTeam;

public interface PssGroupTeamMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PssGroupTeam record);

    int insertSelective(PssGroupTeam record);

    PssGroupTeam selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PssGroupTeam record);

    int updateByPrimaryKey(PssGroupTeam record);
}