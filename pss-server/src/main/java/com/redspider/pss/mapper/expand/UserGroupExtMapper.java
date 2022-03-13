package com.redspider.pss.mapper.expand;

import com.redspider.pss.repository.db.entity.UserGroup;
import java.util.List;

import com.redspider.pss.vo.user.AttendeeVO;
import org.apache.ibatis.annotations.Param;

public interface UserGroupExtMapper {

    List<UserGroup> listByGroupId(@Param("groupId") Long groupId);

    UserGroup getByGroupIdAndUserId(@Param("groupId") Long groupId, @Param("userId") Long userId);

    Integer count(@Param("groupId") Long groupId);

    List<UserGroup> selectByGroupId(Long groupId);
    
    List<Long> selectGroupIdsByUserId(@Param("userId")Long userId, @Param("offset")Long offset, @Param("pageSize")Long pageSize);

    List<AttendeeVO> attendeesByGroupId(@Param("groupId") Long groupId);

    Boolean joined(@Param("groupId") Long groupId, @Param("userId")Long userId);
}
