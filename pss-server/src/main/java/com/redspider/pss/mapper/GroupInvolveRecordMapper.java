package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.GroupInvolveRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName GroupInvolveRecordMapper
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Repository
public interface GroupInvolveRecordMapper {
   
   int insert(GroupInvolveRecord record);
   
   int insertSelective(GroupInvolveRecord record);
   
   List<GroupInvolveRecord> selectByGroupId(@Param("groupId")Long groupId,@Param("offset")Long offset,@Param("pageSize")Long pageSize);
   
   
}
