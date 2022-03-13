package com.redspider.pss.logic.impl;

import com.redspider.pss.constant.enums.Audit;
import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.exception.CommonBizException;
import com.redspider.pss.exception.PssServerException;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.logic.spi.GroupLogic;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.GroupLifeCycleService;
import com.redspider.pss.service.db.spi.GroupInvolveRecordService;
import com.redspider.pss.service.db.spi.UserGroupService;
import com.redspider.pss.vo.group.*;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;
import com.redspider.pss.wrapper.group.GroupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.redspider.pss.response.ResponseCode.*;
import static com.redspider.pss.response.ResponseCode.GROUP_DATA_NOT_FOUND;
import static com.redspider.pss.response.ResponseCode.SYSTEM_ERROR;

@Service
public class GroupLogicImpl implements GroupLogic {

    @Autowired
    private GroupLifeCycleService groupLifeCycleService;
    @Autowired
    private GroupInvolveRecordService groupInvolveRecordService;
    @Autowired
    UserGroupService userGroupService;

    @Override
    public ResponseResult<Long> create(GroupBundleVO groupBundleVo, Long userId) {
        return groupLifeCycleService.createGroup(
                GroupWrapper.unwrap(groupBundleVo, userId),
                buildCreate(groupBundleVo))
            .map(ResponseResult::success)
            .orElseThrow(() -> new PssServerException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<GroupInfoVO> getInfo(Long groupId, Long userId) {
        return groupLifeCycleService.getInfo(groupId) .map(GroupWrapper::wrap) .map(userGroupService::getUserInfo)
            .map(GroupWrapper::wrap2Info)
            .map(ResponseResult::success)
            .orElseThrow(() -> new PssValidationException(GROUP_DATA_NOT_FOUND));
    }

    @Override
    public ResponseResult<String> join(PssGroupTeamUserVO groupTeamUserVo, Long userId) {
        return groupLifeCycleService.joinGroup(groupTeamUserVo, userId)
            .map(ResponseResult::success)
            .orElseThrow(() -> new PssServerException(JOIN_GROUP_FAIL));
    }

    @Override
    public ResponseResult<List<GroupBundleVO>> list(PssGroupListReq pssGroupListReq) {
        return groupLifeCycleService.list(pssGroupListReq)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<GroupAttendeesVO> attendeesInfo(Long groupId) {
        return groupLifeCycleService.attendeesInfo(groupId)
                .map(ResponseResult::success)
                .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<UserGroupRelationVO> userGroupRelation(Long groupId) {
        // 兼容没登录的情况
        Long userId = Optional.ofNullable(UserUtil.getUserId()).orElse(0L);
        return groupLifeCycleService.userGroupRelation(groupId, userId)
                .map(ResponseResult::success)
                .orElseThrow(() -> new PssValidationException(GROUP_DATA_NOT_FOUND));
    }

    private static AuditDTO buildCreate(GroupBundleVO groupBundleVo) {
        return AuditDTO.builder()
            .type(groupBundleVo.getAuditType())
            .bizType(groupBundleVo.getBizType())
            .status(Audit.AuditStatus.UN_AUDIT)
            .build();
    }

    private static UserGroupDTO buildCreate(GroupBundleVO groupBundleVo, Long userId) {
        return UserGroupDTO.builder()
            .userId(userId)
            .deletedStatus(DeletedStatus.UN_DELETE)
            .contactInfo(groupBundleVo.getContactInfo())
            .contactType(groupBundleVo.getContactType())
            .build();
    }

    @Override
    public ResponseResult<Long> quit(GroupQuitRemarkVO groupQuitRemarkVO) {
        return groupLifeCycleService.quit(groupQuitRemarkVO)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<Long> cancel(Long groupId) {
        return groupLifeCycleService.cancel(groupId)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<Long> collect(Long groupId) {
        return groupLifeCycleService.collect(groupId)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<Integer> view(Long groupId) {
        return groupLifeCycleService.view(groupId)
                .map(ResponseResult::success)
                .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<Long> cancelCollect(Long groupId) {
        return groupLifeCycleService.cancelCollect(groupId)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<List<GroupBundleVO>> collectList(PssGroupListReq pssGroupListReq) {
        return groupLifeCycleService.collectList(pssGroupListReq)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

    @Override
    public ResponseResult<List<GroupInvolveRecordDTO>> involveList(PssGroupTeamReq groupTeamReq) {
        return groupInvolveRecordService.list(groupTeamReq)
            .map(ResponseResult::success)
            .orElseThrow(() -> new CommonBizException(SYSTEM_ERROR));
    }

}