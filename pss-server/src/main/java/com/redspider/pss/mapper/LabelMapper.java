package com.redspider.pss.mapper;

import com.redspider.pss.domain.Label;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Label record);

    int insertSelective(Label record);

    Label selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Label record);

    int updateByPrimaryKey(Label record);

}