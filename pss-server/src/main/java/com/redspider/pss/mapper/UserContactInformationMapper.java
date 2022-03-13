package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.UserContactInformation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserContactInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserContactInformation record);

    int insertSelective(UserContactInformation record);

    UserContactInformation selectByPrimaryKey(Long id);

    List<UserContactInformation> selectByUserId(Long userId);

    Integer count(@Param("userId") Long userId);

    int updateByPrimaryKeySelective(UserContactInformation record);

    int updateByPrimaryKey(UserContactInformation record);

    UserContactInformation selectByContactInformation(@Param("information") UserContactInformation information);

}