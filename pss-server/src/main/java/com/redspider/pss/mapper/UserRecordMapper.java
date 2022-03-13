package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.UserRecord;

public interface UserRecordMapper {
    int insert(UserRecord record);

    int insertSelective(UserRecord record);
}