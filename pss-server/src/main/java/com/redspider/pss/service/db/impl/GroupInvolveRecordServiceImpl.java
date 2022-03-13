package com.redspider.pss.service.db.impl;

import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.mapper.GroupInvolveRecordMapper;
import com.redspider.pss.repository.db.entity.GroupInvolveRecord;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.service.db.spi.GroupInvolveRecordService;
import com.redspider.pss.service.db.spi.ResourceService;
import com.redspider.pss.service.db.spi.UserService;
import com.redspider.pss.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName GroupInvolveRecordServiceImpl
 * @Description
 * @Author hf
 * @Version V1.0
 **/
@Service
@Slf4j
public class GroupInvolveRecordServiceImpl implements GroupInvolveRecordService {
   
   @Autowired
   GroupInvolveRecordMapper groupInvolveRecordMapper;
   @Autowired
   ResourceService resourceService;
   @Autowired
   UserService userService;
   
   @Override
   public Optional<Long> create(GroupInvolveRecordDTO groupInvolveRecordDTO) {
      log.debug("create:{}", groupInvolveRecordDTO);
      
      GroupInvolveRecord groupInvolveRecord = buildGroupInvolveRecord(groupInvolveRecordDTO);
      groupInvolveRecordMapper.insertSelective(groupInvolveRecord);
      
      return Optional.of(groupInvolveRecord.getGroupTeamId());
   }
   
   @Override
   public Optional<List<GroupInvolveRecordDTO>> list(PssGroupTeamReq groupTeamReq) {

      List<GroupInvolveRecord> groupInvolveRecords = groupInvolveRecordMapper.selectByGroupId(
       groupTeamReq.getId(), (long)groupTeamReq.getOffset(), (long)groupTeamReq.getPageSize());

      List<GroupInvolveRecordDTO> recordDTOS = groupInvolveRecords.stream()
       .map(this::buildGroupInvolveRecordDTO)
       .collect(Collectors.toList());
      
      return Optional.of(recordDTOS);
   }
   
   private GroupInvolveRecord buildGroupInvolveRecord(GroupInvolveRecordDTO groupInvolveRecordDTO) {
      GroupInvolveRecord groupInvolveRecord = new GroupInvolveRecord();
      BeanUtils.copyProperties(groupInvolveRecordDTO,groupInvolveRecord);
      groupInvolveRecord.setCreatorId(groupInvolveRecordDTO.getUserInfo().getId());
      groupInvolveRecord.setCreateTime(new Date());
      groupInvolveRecord.setPictureUrl(JsonUtils.write(groupInvolveRecordDTO.getPictureUrlArray()));
      groupInvolveRecord.setRemark(groupInvolveRecordDTO.getRemark());
      return groupInvolveRecord;
   }
   
   private GroupInvolveRecordDTO buildGroupInvolveRecordDTO(GroupInvolveRecord record){
   
      List<Long> resourceIds = JsonUtils.strToList(record.getPictureUrl(), String.class)
       .stream()
       .map(Long::parseLong).collect(Collectors.toList());
      
      List<String> pictureResourceList =  resourceService.pathListByIds(resourceIds);

      return GroupInvolveRecordDTO.builder()
              .id(record.getId())
              .groupTeamId(record.getGroupTeamId())
              .userInfo(userService.getUserInfoById(record.getCreatorId()))
              .remark(record.getRemark())
              .pictureUrlArray(JsonUtils.strToArray(JsonUtils.listToStr(pictureResourceList)))
              .flag(record.getFlag())
              .createTime(record.getCreateTime().getTime())
              .build();
   }
   
   
}
