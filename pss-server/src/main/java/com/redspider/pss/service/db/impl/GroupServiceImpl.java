package com.redspider.pss.service.db.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.TypeReference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import com.redspider.pss.converter.UserGroupConverter;
import com.redspider.pss.dto.common.LabelDTO;
import com.redspider.pss.dto.group.GroupConditionDTO;
import com.redspider.pss.dto.group.GroupDTO;
import com.redspider.pss.dto.group.ResourceDTO;
import com.redspider.pss.dto.group.UserGroupDTO;
import com.redspider.pss.mapper.GroupMapper;
import com.redspider.pss.mapper.UserBasicMapper;
import com.redspider.pss.repository.db.entity.Group;
import com.redspider.pss.repository.db.entity.UserBasic;
import com.redspider.pss.repository.db.entity.UserGroup;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.service.db.spi.*;
import com.redspider.pss.utils.JsonUtils;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.redspider.pss.response.ResponseCode.GROUP_DATA_NOT_FOUND;
import static com.redspider.pss.response.ResponseCode.ILLEGAL_PARAMETER;

@Service
@Slf4j
public class GroupServiceImpl implements GroupService {

    public static final String GROUP_BIZ_TYPE = "group";
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private UserBasicMapper userBasicMapper;
    @Autowired
    private UserApplicationService userApplicationService;

    @Override
    public Optional<Long> createGroup(GroupDTO groupDto) {
        log.debug("createGroup:{}", groupDto);
        if (Objects.nonNull(groupDto) && Objects.nonNull(groupDto.getId())) {
            return updateGroup(groupDto);
        } else {
            //保存基本信息
            validGroupForCreate(groupDto);
            //保存resource信息
            Long ownerId = groupDto.getOwnerId();
            //保存label信息
            List<Long> labelIds = new ArrayList<>(labelService.resolveLabels(groupDto.getLabels(), ownerId));
            Group group = buildBaseGroup(groupDto);
            group.setResource(JsonUtils.listToStr(groupDto.getResourceList()));
            group.setLabelIds(JsonUtils.listToStr(labelIds));
            groupMapper.insertSelective(group);
            Long groupId = group.getId();
            log.info("groupId:{}", groupId);
            return Optional.of(groupId);
        }
    }

    /**
     * 更新组队单
     *
     * @param groupDto
     * @return
     */
    private Optional<Long> updateGroup(GroupDTO groupDto) {
        log.debug("updateGroup:{}", groupDto);
        Group group = groupMapper.selectByPrimaryKey(groupDto.getId());
        validGroupForUpdate(groupDto, group);

        //保存label信息
        Long ownerId = groupDto.getOwnerId();
        List<Long> labelIds = new ArrayList<>(labelService.resolveLabels(groupDto.getLabels(), ownerId));
        group = buildBaseGroup(groupDto);
        group.setResource(JsonUtils.listToStr(groupDto.getResourceList()));
        group.setLabelIds(JsonUtils.listToStr(labelIds));
        groupMapper.updateByPrimaryKeySelective(group);

        return Optional.of(group.getId());
    }

    private void validGroupForUpdate(GroupDTO dto, Group group) {
        validGroupForCreate(dto);
        Assert.isTrue(Objects.equals(group.getStatus(), GroupStatus.FINDING.name()),
            "组队单状态不支持修改");
        if (Objects.nonNull(dto.getEndTime())) {
            long deadline = System.currentTimeMillis() + 10 * 60 * 1000;
            Assert.isTrue(deadline < dto.getEndTime(),
                "截止时间不能晚于当前时间10分钟");
        }
        Assert.isTrue(Objects.equals(group.getContainMe(), dto.getContainMe()), "不能修改“自己也参与”选项");
        Assert.isTrue(Objects.equals(group.getOwnerId(), dto.getOwnerId()),
            ILLEGAL_PARAMETER.getMessage());
        //todo Tony 缺少需要手动确认的字段
    }

    private void validGroupForCreate(GroupDTO dto) {
        Assert.notNull(dto, GROUP_DATA_NOT_FOUND.getMessage());
        if (Objects.nonNull(dto.getEndTime())) {
            Assert.isTrue(dto.getStartTime() < dto.getEndTime(),
                "开始时间不能晚于结束时间");
        }
    }

    private Group buildBaseGroup(GroupDTO dto) {
        Group group = new Group();
        BeanUtils.copyProperties(dto, group);
        group.setBizType(GROUP_BIZ_TYPE);
        group.setStatus(GroupStatus.FINDING.name());
        group.setStartTime(new Date(dto.getStartTime()));
        group.setEndTime(new Date(dto.getEndTime()));
        group.setContainMe(dto.getContainMe());
        group.setCondition(JsonUtils.write(dto.getCondition()));
        return group;
    }

    @Override
    public Optional<GroupDTO> getById(Long groupId) {
        Group group = groupMapper.selectByPrimaryKey(groupId);
        if (Objects.isNull(group)) {
            return Optional.empty();
        }
        GroupDTO dto = new GroupDTO();
        BeanUtils.copyProperties(group, dto);
        Date startDate = Optional.ofNullable(group.getStartTime()).orElseThrow(() -> new IllegalArgumentException(String.format("ID：%s，组队单开始时间为空", groupId)));
        dto.setStartTime(startDate.getTime());
        Date endDate = Optional.ofNullable(group.getEndTime()).orElseThrow(() -> new IllegalArgumentException(String.format("ID：%s，组队单结束时间为空", groupId)));
        dto.setEndTime(endDate.getTime());
        return Optional.of(dto);
    }

    @Override
    public Optional<GroupDTO> getInfo(Long groupId) {
        log.debug("getInfo:{}", groupId);
        Group group = groupMapper.selectByPrimaryKey(groupId);
        if (Objects.isNull(group)) {
            return Optional.empty();
        }
        GroupDTO dto = new GroupDTO();
        BeanUtils.copyProperties(group, dto);
        if (Objects.nonNull(group.getStartTime())) {
            dto.setStartTime(group.getStartTime().getTime());
        }
        if (Objects.nonNull(group.getEndTime())) {
            dto.setEndTime(group.getEndTime().getTime());
        }
        dto.setCondition(JsonUtils.strToBean(group.getCondition(), GroupConditionDTO.class));

        if (StringUtils.isNoneBlank(group.getLabelIds())) {
            List<Long> labelIds = JsonUtils.strToBean(group.getLabelIds(), new TypeReference<List<Long>>() {
            });
            List<String> labels = labelService.listByIds(labelIds)
                .stream()
                .map(LabelDTO::getLabelName)
                .collect(Collectors.toList());
            dto.setLabels(labels);
        }

        if (StringUtils.isNoneBlank(group.getResource())) {
            List<Long> resourceIds = JsonUtils.strToBean(group.getResource(), new TypeReference<List<Long>>() {
            });
            Collection<ResourceDTO> resourceObjList = resourceService.listByIds(resourceIds);
            dto.setResourceObjList(new ArrayList<>(resourceObjList));
            dto.setResourceList(resourceObjList.stream().map(t->t.getId().toString()).collect(Collectors.toList()));
        } else {
            dto.setResourceObjList(new ArrayList<>());
        }

        return Optional.of(dto);
    }

    @Override
    public Optional<Long> cancel(Long groupId) {
        log.debug("cancel:{}", groupId);

        Group group = new Group();
        group.setId(groupId);
        group.setStatus(GroupStatus.CANCEL.name());
        groupMapper.updateByPrimaryKeySelective(group);

        return Optional.of(groupId);
    }

    @Override
    public Optional<String> join(PssGroupTeamUserVO groupTeamUserVo, Long userId) {
        // 组队单
        Long teamId = groupTeamUserVo.getTeamId();
        Group group = groupMapper.selectByPrimaryKey(teamId);
        // 用户
        UserBasic userBasic = userBasicMapper.selectByPrimaryKey(userId);
        // 加入校验
        boolean success = checkJoin(groupTeamUserVo, userBasic, group);
        // 加入
        try {
            join(groupTeamUserVo, userBasic, group);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Optional.empty();
        }
        // 组队单状态处理
        // 更新成功，发送短信
        if (groupMapper.updateByPrimaryKey(group) == 1 && success) {
            try {
                this.sendGroupTeamUsersSMS(teamId, true);
            } catch (Exception e) {
                log.error("组队单状态短信发送失败 e:", e);
            }
        }
        return Optional.of("参加组队单成功");
    }

    @Override
    public void sendGroupTeamUsersSMS(Long groupTeamId, Boolean groupSuccessFlag) throws Exception {
        Group group = groupMapper.selectByPrimaryKey(groupTeamId);
//        List<UserGroup> userGroups = userGroupService.selectByGroupId(groupTeamId);
//        List<Long> userIds = userGroups.stream()
//            .map(UserGroup::getUserId)
//            .collect(Collectors.toList());
//        List<String> phones = new ArrayList<>();
//        userIds.forEach(id -> {
//            phones.add(userBasicMapper.selectByPrimaryKey(id)
//                .getPhone());
//        });
        Long ownerId = group.getOwnerId();
        UserBasic userBasic = userBasicMapper.selectByPrimaryKey(ownerId);
        userApplicationService.groupSMS(Collections.singletonList(userBasic.getPhone()), groupSuccessFlag, groupTeamId);
    }

    @Override
    public Optional<List<GroupDTO>> list(PssGroupListReq groupListReq) {
        List<Group> list = groupMapper.list(groupListReq.getKeyWord(), groupListReq.getOffset(), groupListReq.getPageSize());
        List<GroupDTO> groupDTOS = new ArrayList<>();
        list.forEach(group -> {
            GroupDTO dto = new GroupDTO();
            BeanUtils.copyProperties(group, dto);
            if (Objects.nonNull(group.getStartTime())) {
                dto.setStartTime(group.getStartTime().getTime());
            }
            if (Objects.nonNull(group.getEndTime())) {
                dto.setEndTime(group.getEndTime().getTime());
            }
            dto.setCondition(JsonUtils.strToBean(group.getCondition(), GroupConditionDTO.class));

            if (StringUtils.isNoneBlank(group.getLabelIds())) {
                List<Long> labelIds = JsonUtils.strToBean(group.getLabelIds(), new TypeReference<List<Long>>() {
                });
                List<String> labels = labelService.listByIds(labelIds)
                    .stream()
                    .map(LabelDTO::getLabelName)
                    .collect(Collectors.toList());
                dto.setLabels(labels);
            }
            if (StringUtils.isNoneBlank(group.getResource())) {
                List<Long> resourceIds = JsonUtils.strToBean(group.getResource(), new TypeReference<List<Long>>() {
                });
                dto.setResourceList(resourceService.pathListByIds(resourceIds));
            }
            groupDTOS.add(dto);
        });

        return Optional.of(groupDTOS);
    }

    @Override
    public Optional<List<Long>> groupsByLabelId(Long id, PssGroupListReq groupListReq) {
        return Optional.of(
            groupMapper.groupsByLabelId(id, (long) groupListReq.getOffset(), (long) groupListReq.getPageSize())
        );
    }

    @Override
    public Optional<List<Long>> groupIdsByOwnerId(Long ownerId, PssGroupListReq groupListReq) {
        return Optional.of(
            groupMapper.groupIdsByOwnerId(ownerId, (long) groupListReq.getOffset(), (long) groupListReq.getPageSize())
        );
    }

    @Override
    public Optional<List<Group>> groupsByStatus(Collection<String> statusS) {
        return Optional.of(groupMapper.groupsByStatus(statusS));
    }

    @Override
    public void updateByPrimaryKey(Group group) {
        groupMapper.updateByPrimaryKey(group);
    }

    @Override
    public List<Long> selectViewedGroupIdsByUserId(Long userId, PssGroupListReq pssGroupListReq) {
        return groupMapper.selectViewedGroupIdsByUserId(userId, pssGroupListReq.getOffset(), pssGroupListReq.getPageSize());
    }


    /**
     * 校验是否可加入组队单
     *
     * @param groupTeamUserVo
     * @param userBasic
     * @param group
     * @return
     */
    private boolean checkJoin(PssGroupTeamUserVO groupTeamUserVo, UserBasic userBasic, Group group) {

        long teamId = groupTeamUserVo.getTeamId();

        // 非FINDING状态的组队单无法加入
        Assert.isFalse(Objects.equals(
            group.getStatus(),
            GroupStatus.FINDING.getDescription()), "当前组队单为非待组队状态，无法加入");

        // 已参加过该组队单
        Optional<UserGroupDTO> optionalUserGroupDTO = userGroupService.getByGroupIdAndUserId(teamId, userBasic.getId());
        Assert.isFalse(optionalUserGroupDTO.isPresent(), "已在当前组队当中，请勿重复加入");
        // 不能参加自己创建的组队单
        Assert.isFalse(userBasic.getId().equals(group.getOwnerId()),"不可以参与自己创建的组队单!");
        // 不能参与已经过期的组队单
        Assert.isFalse(group.getEndTime().before(new Date()),"该组队单已过期!");
        // 组队单人数处理
        Integer teamUserCount = userGroupService.count(teamId);
        String condition = group.getCondition();
        JSONObject conditionObj = JSON.parseObject(condition);
        Integer maxTeamSize = conditionObj.getInteger("maxTeamSize");
        Assert.isTrue(maxTeamSize != null, "组队单参与人数参数异常！");
        Assert.isTrue(maxTeamSize > teamUserCount, "该组队单已满,无法参加！");

        // 联系方式未完善
//        if (groupTeamUserVo.getType() == ContactType.PHONE.getCode()) {
//            Assert.isTrue(StringUtils.isNotBlank(userBasic.getPhone()), "电话不存在，请先完善后再加入");
//        } else {
//            Assert.isTrue(StringUtils.isNotBlank(userBasic.getWechatNum()), "微信不存在，请先完善后再加入");
//        }

        boolean success = false;
        // 最大人数等于当前人数则设置组队完成
        if (maxTeamSize.equals(teamUserCount + 1)) {
            // 组队成功
            success = true;
            group.setStatus(GroupStatus.SUCCESS.name());
        }
        return success;
    }

    private void join(PssGroupTeamUserVO groupTeamUserVo, UserBasic userBasic, Group group) {
        Long userId = userBasic.getId();
        // 处理资源
        // 用户加入组队单状态设置
        // int status = GroupUserStatus.APPLY.getCode();
        // if (GroupConfirm.NO.name().equals(group.getStatus()) || userId.equals(group.getOwnerId())) {
        //     status = GroupUserStatus.SUCCESS.getCode();
        // }
        // 先添加资源表
//        List<ResourceDTO> resources = groupTeamUserVo.getResources();
//        Collection<Long> ids = new ArrayList<>();
//        if(CollectionUtil.isNotEmpty(resources)) {
//            ids = resourceService.resolveResource(resources, userId);
//        }
        // 加入组队单
        UserGroup userGroup = UserGroupConverter.convert(groupTeamUserVo);
        userGroup.setResourceIds(JsonUtils.listToStr(groupTeamUserVo.getResources()));
        userGroup.setUserId(userId);
        // 用户联系方式
        userGroup.setContractInfo(groupTeamUserVo.getContactInfo());
        // 新增用户
        userGroupService.create(UserGroupConverter.convertToDTO(userGroup));
    }
}
