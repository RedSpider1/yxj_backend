package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.Record;

public interface RecordMapper {
    int insert(Record record);

    int insertSelective(Record record);
}