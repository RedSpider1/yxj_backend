package com.redspider.pss.service.db.impl;

import cn.hutool.core.lang.Assert;
import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.mapper.UserGroupMapper;
import com.redspider.pss.mapper.expand.UserGroupExtMapper;
import com.redspider.pss.repository.db.entity.UserGroup;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.service.db.spi.UserGroupService;
import com.redspider.pss.service.db.spi.UserService;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.user.AttendeeVO;
import com.redspider.pss.vo.user.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.redspider.pss.response.ResponseCode.*;

@Service
@Slf4j
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private UserGroupExtMapper userGroupExtMapper;
    @Autowired
    UserService userService;

    @Override
    public Optional<Long> create(UserGroupDTO userGroupDto) {
        log.info("create:{}", userGroupDto);
        validForCreate(userGroupDto);

        Long userId = userGroupDto.getUserId();
        UserGroup userGroup = userGroupExtMapper.getByGroupIdAndUserId(userGroupDto.getGroupId(), userId);
        Long id = 0L;
        if (Objects.isNull(userGroup)) {
            UserGroup record = new UserGroup();
            record.setGroupId(userGroupDto.getGroupId());
            record.setUserId(userId);
            if (StringUtils.isBlank(userGroupDto.getContactInfo())) {
                fillCreatorContactInfo(record, userId);
            } else {
                record.setContractInfo(userGroupDto.getContactInfo());
                record.setContractType(userGroupDto.getContactType().byteValue());
            }
            // 资源和评论
            record.setResourceIds(userGroupDto.getResourceIds());
            record.setComment(userGroupDto.getComment());
            userGroupMapper.insertSelective(record);
            id = record.getId();
        } else {
            UserGroup updateRecord = new UserGroup();
            updateRecord.setId(userGroup.getId());
            this.fillCreatorContactInfo(updateRecord, userId);
            // updateRecord.setContractInfo(userGroupDto.getContactInfo());
            // updateRecord.setContractType(userGroupDto.getContactType().byteValue());
            // 资源和评论
            updateRecord.setResourceIds(userGroupDto.getResourceIds());
            updateRecord.setComment(userGroupDto.getComment());
            userGroupMapper.updateByPrimaryKeySelective(updateRecord);
            id = updateRecord.getId();
        }
        return Optional.ofNullable(id);
    }

    private void fillCreatorContactInfo(UserGroup record, Long userId) {
        UserDO userDO = userService.getById(userId);
        Assert.isTrue(userDO != null, USER_DATA_NOT_FOUND.getMessage());
        record.setContractType((byte) 0);
        record.setContractInfo(userDO.getPhone());
    }

    private void validForCreate(UserGroupDTO dto) {
        Assert.notNull(dto, ILLEGAL_PARAMETER.getMessage());
        Assert.notNull(dto.getGroupId(), GROUP_DATA_NOT_FOUND.getMessage());
        Assert.notNull(dto.getUserId(), ILLEGAL_PARAMETER.getMessage());
        // 2021.12.18 创建时不传递联系方式及信息，默认使用当前用户的电话
        // Assert.notNull(dto.getContactType(), ILLEGAL_PARAMETER.getMessage());
        // Assert.notBlank(dto.getContactInfo(), ILLEGAL_PARAMETER.getMessage());
    }

    @Override
    public Integer count(Long groupId) {
        if (Objects.isNull(groupId)) {
            return 0;
        }
        return userGroupExtMapper.count(groupId);
    }

    @Override
    public Optional<UserGroupDTO> getByGroupIdAndUserId(Long groupId, Long userId) {
        log.debug("getByGroupId:{}, {}", groupId, userId);
        UserGroup userGroup = userGroupExtMapper.getByGroupIdAndUserId(groupId, userId);
        if (Objects.isNull(userGroup)) {
            return Optional.empty();
        }
        UserGroupDTO dto = UserGroupDTO.builder()
                .groupId(userGroup.getGroupId())
                .userId(userGroup.getUserId())
                .contactType(userGroup.getContractType().intValue())
                .contactInfo(userGroup.getContractInfo())
                .build();
        return Optional.of(dto);
    }

    @Override
    public Optional<Long> quit(UserGroupDTO userGroupDto) {
        log.debug("quit:{},", userGroupDto);
        UserGroup record = new UserGroup();
        record.setId(userGroupDto.getId());
        record.setGroupId(userGroupDto.getGroupId());
        record.setUserId(userGroupDto.getUserId());
        record.setDelete((byte) DeletedStatus.DELETE.getCode());
        userGroupMapper.deleteByUserIdAndGroupId(record);
        return Optional.of(record.getGroupId());
    }

    /**
     * 根据组队单ID查询所有用户的参与数据
     *
     * @param groupId 组队单ID
     * @return
     */
    public List<UserGroup> selectByGroupId(Long groupId) {
        return userGroupExtMapper.selectByGroupId(groupId);
    }

    @Override
    public GroupBundleVO getUserInfo(GroupBundleVO groupBundleVO) {
        UserInfoVO ownerInfo = userService.getUserInfoById(groupBundleVO.getOwnerInfo().getId());
        groupBundleVO.setOwnerInfo(ownerInfo);
        return groupBundleVO;
    }

    @Override
    public List<Long> selectGroupIdsByUserId(Long userId, PssGroupListReq groupListReq) {
        return userGroupExtMapper.selectGroupIdsByUserId(userId, (long) groupListReq.getOffset(), (long) groupListReq.getPageSize());
    }

    @Override
    public Optional<List<AttendeeVO>> attendeesInfo(Long groupId) {
        return Optional.of(userGroupExtMapper.attendeesByGroupId(groupId));
    }

    @Override
    public Boolean joined(Long groupId, Long userId) {
        return userGroupExtMapper.joined(groupId, userId);
    }
}
