package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.PssGroupTeamSearchHistory;

public interface PssGroupTeamSearchHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PssGroupTeamSearchHistory record);

    int insertSelective(PssGroupTeamSearchHistory record);

    PssGroupTeamSearchHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PssGroupTeamSearchHistory record);

    int updateByPrimaryKey(PssGroupTeamSearchHistory record);
}