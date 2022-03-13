package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.UserExpand;

public interface UserExpandMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserExpand record);

    int insertSelective(UserExpand record);

    UserExpand selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserExpand record);

    int updateByPrimaryKey(UserExpand record);
}