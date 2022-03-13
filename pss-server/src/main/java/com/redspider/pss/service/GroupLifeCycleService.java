package com.redspider.pss.service;

import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.vo.group.GroupAttendeesVO;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.group.GroupQuitRemarkVO;
import com.redspider.pss.vo.group.UserGroupRelationVO;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;

import java.util.List;
import java.util.Optional;

public interface GroupLifeCycleService {

    Optional<Long> createGroup(GroupDTO groupDto, AuditDTO auditDto);

    Optional<GroupDTO> getInfo(Long groupId);

    Optional<Long> quit(GroupQuitRemarkVO groupQuitRemarkVO);

    Optional<Integer> view(Long groupId);

    Optional<Long> collect(Long groupId);
    
    Optional<Long> cancelCollect(Long groupId);
    
    Optional<List<GroupBundleVO>> collectList(PssGroupListReq pssGroupListReq);
    
    Optional<Long> cancel(Long groupId);

    Optional<String> joinGroup(PssGroupTeamUserVO groupTeamUserVo, Long userId);
    
    Optional<List<GroupBundleVO>> list(PssGroupListReq pssGroupListReq);

    Optional<GroupAttendeesVO> attendeesInfo(Long groupId);

    Optional<UserGroupRelationVO> userGroupRelation(Long groupId, Long userId);

}
