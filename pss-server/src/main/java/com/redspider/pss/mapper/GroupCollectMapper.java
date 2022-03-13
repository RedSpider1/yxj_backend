package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.GroupCollect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName GroupCollectMapper
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Repository
public interface GroupCollectMapper {
   
   int deleteByPrimaryKey(Long id);
   
   int insert(GroupCollect record);
   
   int insertSelective(GroupCollect record);
   
   GroupCollect selectByPrimaryKey(Long id);
   
   int updateByPrimaryKeySelective(GroupCollect record);
   
   int updateByPrimaryKey(GroupCollect record);
   
   int insertUniqueSelective(GroupCollect record);
   
   int updateByUserIdAndGroupId(GroupCollect record);
   
   List<GroupCollect> selectByUserId(@Param("userId") long userId, @Param("offset") long offset, @Param("pageSize") long pageSize);

   Boolean collected(@Param("groupId") long groupId, @Param("userId") long userId);
   
   
}
