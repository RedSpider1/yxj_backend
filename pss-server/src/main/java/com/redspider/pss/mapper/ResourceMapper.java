package com.redspider.pss.mapper;


import com.redspider.pss.repository.db.entity.Resource;
import java.util.Collection;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity generator.domain.Resource
 */
public interface ResourceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);
}
