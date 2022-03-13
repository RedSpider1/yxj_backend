package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.PssGroupTeamViewed;

public interface PssGroupTeamViewedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PssGroupTeamViewed record);

    int insertSelective(PssGroupTeamViewed record);

    PssGroupTeamViewed selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PssGroupTeamViewed record);

    int updateByPrimaryKey(PssGroupTeamViewed record);
}