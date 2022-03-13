package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.repository.db.entity.Group;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupService {

    Optional<Long> createGroup(@Validated GroupDTO groupDto);

    Optional<GroupDTO> getById(Long groupId);

    Optional<GroupDTO> getInfo(Long groupId);

    Optional<Long> cancel(Long groupId);

    Optional<String> join(PssGroupTeamUserVO groupTeamUserVo, Long userId);

    /**
     * 根据组团发送成功/失败短信
     * @param groupTeamId 组团ID
     * @return 返回结果
     */
    void sendGroupTeamUsersSMS(Long groupTeamId, Boolean groupSuccessFlag) throws Exception;
    
    /**
     * 组队单列表
     * @param groupListReq
     * @return
     */
    Optional<List<GroupDTO>> list(PssGroupListReq groupListReq);
    
    Optional<List<Long>> groupsByLabelId(Long id, PssGroupListReq pssGroupListReq);
    
    Optional<List<Long>> groupIdsByOwnerId(Long ownerId, PssGroupListReq groupListReq);

    Optional<List<Group>> groupsByStatus(Collection<String> statusS);

    void updateByPrimaryKey(Group group);

    List<Long> selectViewedGroupIdsByUserId(Long userId, PssGroupListReq pssGroupListReq);
}
