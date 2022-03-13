package com.redspider.pss.mapper;

import com.redspider.pss.repository.db.entity.Group;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
@Repository
public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
    
    List<Group> list(@Param("keyWord")String keyWord, @Param("offset")Integer offset, @Param("pageSize")Integer pageSize);
    
    List<Long> groupsByLabelId(@Param("labelId")Long labelId, @Param("offset")Long offset, @Param("pageSize")Long pageSize);
    
    List<Long> groupIdsByOwnerId(@Param("ownerId")Long ownerId, @Param("offset")Long offset, @Param("pageSize")Long pageSize);

    List<Group> groupsByStatus(@Param("statusS") Collection<String> statusS);

    List<Long> selectViewedGroupIdsByUserId(Long userId, int offset, int pageSize);
}