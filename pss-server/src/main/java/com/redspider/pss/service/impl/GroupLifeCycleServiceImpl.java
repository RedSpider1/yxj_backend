package com.redspider.pss.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.constant.enums.Group;
import com.redspider.pss.constant.enums.Group.GroupContainType;
import com.redspider.pss.domain.Label;
import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.dto.group.GroupCollectDTO;
import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.exception.CommonBizException;
import com.redspider.pss.exception.UserNotExistException;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.GroupLifeCycleService;
import com.redspider.pss.service.db.spi.*;
import com.redspider.pss.utils.JsonUtils;
import com.redspider.pss.vo.group.GroupAttendeesVO;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.group.GroupQuitRemarkVO;
import com.redspider.pss.vo.group.UserGroupRelationVO;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;
import com.redspider.pss.vo.user.AttendeeVO;
import com.redspider.pss.wrapper.group.GroupWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.redspider.pss.response.ResponseCode.GROUP_NOT_FINDING_NOT_QUIT;
import static com.redspider.pss.response.ResponseCode.USER_NOT_JOIN;

@Slf4j
@Service
public class GroupLifeCycleServiceImpl implements GroupLifeCycleService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private GroupCollectService groupCollectService;

    @Autowired
    private GroupViewService groupViewService;

    @Autowired
    private GroupInvolveRecordService groupInvolveRecordService;

    @Autowired
    private UserService userService;

    @Autowired
    private LabelService labelService;
    @Autowired
    PssGroupTeamService pssGroupTeamService;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Optional<Long> createGroup(GroupDTO groupDto, AuditDTO auditDto) {
        // 这里的optional 有两种处理方案 一种是像我一样直接返回empty
        // 还有一种就是抛异常给框架处理 但是这种就会定义N个异常Code出来
        // Q: 这些异常code需要暴露给web嘛？
        Long groupId = groupDto.getId();
        if (Objects.nonNull(groupId)) {
            Integer count = userGroupService.count(groupId);
            Assert.isTrue(groupDto.getCondition().getMinTeamSize() > count,
                    "最小组队人数需大于当前已参与人数");
        }

        Optional<Long> auditIdOp = auditService.create(auditDto);
        if (!auditIdOp.isPresent()) {
            log.error("failed to create audit, auditDto = {}", JsonUtils.write(auditDto));
            return Optional.empty();
        }
        groupDto.setAuditId(auditIdOp.get());
        Optional<Long> groupIdOp = groupService.createGroup(groupDto);
        if (!groupIdOp.isPresent()) {
            log.error("failed to create group, auditDto = {}", JsonUtils.write(groupDto));
            return Optional.empty();
        }

        if (GroupContainType.CONTAIN.getCode() == groupDto.getContainMe()) {
            UserGroupDTO userGroupDto = UserGroupDTO.builder()
                    .groupId(groupIdOp.get())
                    .userId(groupDto.getOwnerId())
                    .contactInfo(groupDto.getContactInfo())
                    .contactType(groupDto.getContactType())
                    .build();
            userGroupService.create(userGroupDto);
            GroupQuitRemarkVO groupQuitRemarkVO = new GroupQuitRemarkVO();
            groupQuitRemarkVO.setRemark("创建组队单");
            groupQuitRemarkVO.setResourceList(Collections.emptyList());
            groupQuitRemarkVO.setId(groupIdOp.get());
            //添加参与记录
            groupInvolveRecordService.create(buildGroupInvolveRecordDTO(groupDto.getOwnerId(), groupQuitRemarkVO, Group.GroupInvolveFlag.INVOLVE));
        }
        return groupIdOp;
    }

    @Override
    public Optional<GroupDTO> getInfo(Long groupId) {
        Optional<GroupDTO> info = groupService.getInfo(groupId);
        info.ifPresent(group -> {
            if (Objects.equals(GroupContainType.CONTAIN.getCode(), group.getContainMe())) {
                Optional<UserGroupDTO> userGroupOp = userGroupService.getByGroupIdAndUserId(groupId, group.getOwnerId());
                if (userGroupOp.isPresent()) {
                    UserGroupDTO userGroupDto = userGroupOp.get();
                    group.setContactInfo(userGroupDto.getContactInfo());
                    group.setContactType(userGroupDto.getContactType());
                }
            }
            Integer currentSize = userGroupService.count(groupId);
            group.setCurrentSize(currentSize);
        });
        return info;
    }

    @Override
    public Optional<Long> quit(GroupQuitRemarkVO groupQuitRemarkVO) {

        long userId = Optional.ofNullable(UserUtil.getUserId())
                .orElseThrow(UserNotExistException::new);

        UserGroupDTO userGroupDTO = userGroupService.getByGroupIdAndUserId(groupQuitRemarkVO.getId(), userId)
                .orElseThrow(() -> new CommonBizException(USER_NOT_JOIN));

        GroupDTO groupDTO = groupService.getById(groupQuitRemarkVO.getId())
                .filter(g ->
                        Group.GroupStatus.FINDING.name().equals(g.getStatus()))
                .orElseThrow(() -> new CommonBizException(GROUP_NOT_FINDING_NOT_QUIT));

        // 添加退出校验：
        // 1、如果是发起人，不能退出
        // 2、已经过了截止时间，不能退出
        Assert.isTrue(groupDTO.getOwnerId() != userId, "发起人不能退出组队单");
        Long endTime = groupDTO.getEndTime();
        Assert.isTrue(endTime != null && endTime > System.currentTimeMillis(), "当前组队单已过期，不能退出");

        Optional<Long> userGroupId = userGroupService.quit(userGroupDTO);

        //添加退出记
        groupInvolveRecordService.create(buildGroupInvolveRecordDTO(userId, groupQuitRemarkVO, Group.GroupInvolveFlag.QUIT));
        return userGroupId;
    }

    @Override
    public Optional<Integer> view(Long groupId) {
        return Optional.of(groupViewService.create(UserUtil.getUserId(), groupId));
    }

    @Override
    public Optional<Long> collect(Long groupId) {

        GroupCollectDTO groupCollectDTO = GroupCollectDTO.builder()
                .deletedStatus(DeletedStatus.UN_DELETE)
                .groupTeamId(groupId)
                .userId(Optional.ofNullable(UserUtil.getUserId())
                        .orElseThrow(UserNotExistException::new))
                .build();

        return groupCollectService.create(groupCollectDTO);
    }

    @Override
    public Optional<Long> cancelCollect(Long groupId) {

        GroupCollectDTO groupCollectDTO = GroupCollectDTO.builder()
                .deletedStatus(DeletedStatus.DELETE)
                .groupTeamId(groupId)
                .userId(Optional.ofNullable(UserUtil.getUserId())
                        .orElseThrow(UserNotExistException::new))
                .build();

        return groupCollectService.delete(groupCollectDTO);
    }

    @Override
    public Optional<List<GroupBundleVO>> collectList(PssGroupListReq pssGroupListReq) {
        List<GroupBundleVO> groupBundleVOS = new ArrayList<>();

        Long userId = Optional.ofNullable(UserUtil.getUserId())
                .orElseThrow(UserNotExistException::new);

        Optional<List<GroupCollectDTO>> groupCollectDTOS = groupCollectService.list(userId, pssGroupListReq);

        groupCollectDTOS.ifPresent((List<GroupCollectDTO> groupCollectDTOList) ->
                groupCollectDTOList.forEach(groupCollectDTO -> {
                    groupBundleVOS.add(
                            getInfo(groupCollectDTO.getGroupTeamId())
                                    .map(GroupWrapper::wrap)
                                    .map(userGroupService::getUserInfo).get());
                }));
        return Optional.of(groupBundleVOS);
    }

    @Override
    public Optional<Long> cancel(Long groupId) {

        return groupService.cancel(groupId);
    }

    private GroupInvolveRecordDTO buildGroupInvolveRecordDTO(Long userId, GroupQuitRemarkVO groupQuitRemarkVO, Group.GroupInvolveFlag flag) {

        return GroupInvolveRecordDTO.builder()
                .userInfo(userService.getUserInfoById(userId))
                .remark(groupQuitRemarkVO.getRemark())
                .groupTeamId(groupQuitRemarkVO.getId())
                .pictureUrlArray(JsonUtils.strToArray(JsonUtils.listToStr(groupQuitRemarkVO.getResourceList())))
                .flag(flag.getCode())
                .build();

    }

    @Override
    public Optional<String> joinGroup(PssGroupTeamUserVO groupTeamUserVo, Long userId) {
        Optional<String> join = groupService.join(groupTeamUserVo, userId);
        if (join.isPresent()) {
            GroupQuitRemarkVO groupQuitRemarkVO = new GroupQuitRemarkVO();
            groupQuitRemarkVO.setId(groupTeamUserVo.getTeamId());
            groupQuitRemarkVO.setRemark(groupTeamUserVo.getRemarks());
            groupQuitRemarkVO.setResourceList(groupTeamUserVo.getResources());
            groupInvolveRecordService.create(buildGroupInvolveRecordDTO(userId, groupQuitRemarkVO, Group.GroupInvolveFlag.INVOLVE));
        }
        return join;
    }

    @Override
    public Optional<List<GroupBundleVO>> list(PssGroupListReq pssGroupListReq) {
        List<GroupBundleVO> groupBundleVOS = new ArrayList<>();

        List<Long> groupIds = new ArrayList<>();

        //首页
        if (Group.GroupListType.HOME.getCode() == pssGroupListReq.getType()) {
            groupService.list(pssGroupListReq).ifPresent(groupDTOS ->
                    groupIds.addAll(
                            groupDTOS.stream().map(GroupDTO::getId).collect(Collectors.toList()))
            );
        }

        //搜索页面
        if (Group.GroupListType.SEARCH.getCode() == pssGroupListReq.getType()) {
            String labelName = pssGroupListReq.getKeyWord();
            Label label = labelService.getOne(labelName);
            if (Objects.isNull(label)) {
                //如果没有标签,则按关键字搜索
                groupService.list(pssGroupListReq).ifPresent(groupDTOS ->
                        groupIds.addAll(
                                groupDTOS.stream().map(GroupDTO::getId).collect(Collectors.toList()))
                );
            } else {
                groupService.groupsByLabelId(label.getId(), pssGroupListReq)
                        .ifPresent(
                                groupList -> groupIds.addAll(groupList)
                        );
            }
        }

        Long userId = UserUtil.getUserId();

        //收藏列表
        if (Group.GroupListType.COLLECT.getCode() == pssGroupListReq.getType()) {

            groupCollectService.list(userId, pssGroupListReq).ifPresent((groupCollectDTOList) ->
                    groupIds.addAll(
                            groupCollectDTOList.stream().map(GroupCollectDTO::getGroupTeamId).collect(Collectors.toList()))
            );
        }

        //我创建的
        if (Group.GroupListType.CREATE.getCode() == pssGroupListReq.getType()) {
            groupService.groupIdsByOwnerId(userId, pssGroupListReq)
                    .ifPresent(
                            groupIds::addAll
                    );
        }

        //我浏览的
        // todo 七叔 改成group service
        if (Group.GroupListType.VIEWED.getCode() == pssGroupListReq.getType()) {
            groupIds.addAll(groupService.selectViewedGroupIdsByUserId(userId, pssGroupListReq));
        }

        //我参与的
        if (Group.GroupListType.INVOLVE.getCode() == pssGroupListReq.getType()) {
            groupIds.addAll(userGroupService.selectGroupIdsByUserId(userId, pssGroupListReq));
        }


        groupIds.forEach(groupId -> {
            getInfo(groupId).map(GroupWrapper::wrap)
                    .map(userGroupService::getUserInfo)
                    .ifPresent(
                            groupBundleVO -> groupBundleVOS.add(groupBundleVO)
                    );
        });
        return Optional.of(groupBundleVOS);
    }

    @Override
    public Optional<GroupAttendeesVO> attendeesInfo(Long groupId) {
        // 获取组队单信息
        Optional<GroupDTO> optional = groupService.getById(groupId);
        if (optional.isPresent()) {
            GroupDTO dto = optional.get();
            Long ownerId = dto.getOwnerId();
            // 查询组队单参与人
            Optional<List<AttendeeVO>> attendeeVOS = userGroupService.attendeesInfo(groupId);
            List<AttendeeVO> vos = attendeeVOS.orElseGet(ArrayList::new);
            if (CollectionUtil.isNotEmpty(vos)) {
                // 处理参与人联系信息
                // 兼容没登录的情况
                long userId = Optional.ofNullable(UserUtil.getUserId()).orElse(0L);
                dealContact(vos, userId == ownerId);
            }
            return Optional.ofNullable(GroupAttendeesVO.builder()
                    .attendeeVOS(vos)
                    .groupId(groupId)
                    .count(vos.size())
                    .build());
        } else {
            Assert.isTrue(false, String.format("未获取到ID为：%s的组队单信息", groupId));
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserGroupRelationVO> userGroupRelation(Long groupId, Long userId) {
        // 获取组队单信息
        Optional<GroupDTO> groupOptional = groupService.getById(groupId);
        if (!groupOptional.isPresent()) {
            return Optional.empty();
        }
        UserGroupRelationVO userGroupRelationVO = new UserGroupRelationVO();
        userGroupRelationVO.setCreated(groupOptional.get().getOwnerId().equals(userId));
        userGroupRelationVO.setJoined(userGroupService.joined(groupId, userId));
        userGroupRelationVO.setCollected(groupCollectService.collected(groupId, userId));
        return Optional.of(userGroupRelationVO);
    }

    private void dealContact(List<AttendeeVO> vos, boolean owner) {
        if (!owner) {
            vos.forEach(vo -> {
                vo.getAttendeeContactVO().setValue("******");
            });
        }
    }
}
