package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.repository.db.entity.UserGroup;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.user.AttendeeVO;

import java.util.List;
import java.util.Optional;

public interface UserGroupService {

    Optional<Long> create(UserGroupDTO userGroupDto);

    Integer count(Long groupId);

    Optional<UserGroupDTO> getByGroupIdAndUserId(Long groupId, Long userId);

    Optional<Long> quit(UserGroupDTO userGroupDTO);
    
    List<UserGroup> selectByGroupId(Long groupId);
    
    GroupBundleVO getUserInfo(GroupBundleVO groupBundleVO);
    
    List<Long> selectGroupIdsByUserId(Long userId, PssGroupListReq pssGroupListReq);

    Optional<List<AttendeeVO>> attendeesInfo(Long groupId);

    Boolean joined(Long groupId, Long userId);
    
}
