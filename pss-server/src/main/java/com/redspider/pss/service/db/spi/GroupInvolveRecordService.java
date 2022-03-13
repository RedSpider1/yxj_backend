package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.request.PssGroupTeamReq;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName GroupCollectService
 * @Description
 * @Author hf
 * @Version V1.0
 **/
public interface GroupInvolveRecordService {
   
   Optional<Long> create(GroupInvolveRecordDTO groupInvolveRecordDTO);
   
   Optional<List<GroupInvolveRecordDTO>> list(PssGroupTeamReq groupTeamReq);
   
}
