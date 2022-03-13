package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.PssGroupTeamUser;

public interface PssGroupTeamUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PssGroupTeamUser record);

    int insertSelective(PssGroupTeamUser record);

    PssGroupTeamUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PssGroupTeamUser record);

    int updateByPrimaryKey(PssGroupTeamUser record);
}