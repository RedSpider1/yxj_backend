package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.UserBasic;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBasicMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBasic record);

    int insertSelective(UserBasic record);

    UserBasic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBasic record);

    int updateByPrimaryKey(UserBasic record);
}