package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.Audit;

public interface AuditMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Audit record);

    int insertSelective(Audit record);

    Audit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Audit record);

    int updateByPrimaryKey(Audit record);
}