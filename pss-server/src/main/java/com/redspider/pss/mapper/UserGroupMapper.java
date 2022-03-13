package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.UserGroup;

public interface UserGroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserGroup record);

    int insertSelective(UserGroup record);

    UserGroup selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserGroup record);

    int updateByPrimaryKeyWithBLOBs(UserGroup record);

    int updateByPrimaryKey(UserGroup record);
    
    int deleteByUserIdAndGroupId(UserGroup record);
}