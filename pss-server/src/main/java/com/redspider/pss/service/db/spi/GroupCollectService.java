package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.group.GroupCollectDTO;
import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.request.PssGroupTeamReq;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName GroupCollectService
 * @Description
 * @Author hf
 * @Version V1.0
 **/
public interface GroupCollectService {
   
   Optional<Long> create(GroupCollectDTO userGroupDto);
   
   Optional<Long> delete(GroupCollectDTO userGroupDto);
   
   Optional<List<GroupCollectDTO>> list(Long userId, PssGroupListReq pssGroupListReq);

   Boolean collected(Long groupId, Long userId);
   
}
