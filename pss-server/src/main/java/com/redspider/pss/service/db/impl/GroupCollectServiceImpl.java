package com.redspider.pss.service.db.impl;

import com.redspider.pss.dto.group.GroupCollectDTO;
import com.redspider.pss.mapper.GroupCollectMapper;
import com.redspider.pss.repository.db.entity.GroupCollect;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.service.db.spi.GroupCollectService;
import com.redspider.pss.service.db.spi.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName GroupCollectServiceImpl
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Service
@Slf4j
public class GroupCollectServiceImpl implements GroupCollectService {
   
   @Autowired
   GroupService groupService;
   @Autowired
   GroupCollectMapper groupCollectMapper;
   
   @Override
   public Optional<Long> create(GroupCollectDTO userGroupDto) {
      log.debug("create:{}", userGroupDto);
      GroupCollect groupCollect = buildGroupCollect(userGroupDto);
      int i = groupCollectMapper.insertUniqueSelective(groupCollect);
      return i==0?Optional.empty():Optional.of(userGroupDto.getGroupTeamId());
   }
   
   private GroupCollect buildGroupCollect(GroupCollectDTO userGroupDto) {
      GroupCollect groupCollect = new GroupCollect();
      BeanUtils.copyProperties(userGroupDto, groupCollect);
      groupCollect.setDeleted(userGroupDto.getDeletedStatus().getCode());
      return groupCollect;
   }
   
   @Override
   public Optional<Long> delete(GroupCollectDTO userGroupDto) {
      log.debug("delete:{}", userGroupDto);
      GroupCollect groupCollect = buildGroupCollect(userGroupDto);
      int i = groupCollectMapper.updateByUserIdAndGroupId(groupCollect);
      return i==0?Optional.empty():Optional.of(userGroupDto.getGroupTeamId());
   }
   
   @Override
   public Optional<List<GroupCollectDTO>> list(Long userId, PssGroupListReq pssGroupListReq) {
      log.debug("list:{},{}", userId, pssGroupListReq);
      List<GroupCollectDTO> groupCollectDTOS = new ArrayList<>();
      List<GroupCollect> groupCollects = groupCollectMapper.selectByUserId(userId,
       (long) pssGroupListReq.getOffset(), (long) pssGroupListReq.getPageSize());
      groupCollects.forEach((groupCollect)->
       groupCollectDTOS.add(buildGroupCollectDTO(groupCollect)));
      return Optional.of(groupCollectDTOS);
   }

    @Override
    public Boolean collected(Long groupId, Long userId) {
       return groupCollectMapper.collected(groupId, userId);
    }

    private GroupCollectDTO buildGroupCollectDTO(GroupCollect groupCollect) {
      GroupCollectDTO groupCollectDTO = GroupCollectDTO.builder().
       groupTeamId(groupCollect.getGroupTeamId())
       .userId(groupCollect.getUserId())
       .id(groupCollect.getId()).build();
      return groupCollectDTO;
   }
}
