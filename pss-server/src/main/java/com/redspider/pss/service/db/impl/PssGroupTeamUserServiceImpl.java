package com.redspider.pss.service.db.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.redspider.pss.constant.enums.ContactType;
import com.redspider.pss.constant.enums.DeletedStatus;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import com.redspider.pss.constant.enums.Group.GroupUserStatus;
import com.redspider.pss.converter.SavePropertiesUtils;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.domain.PssGroupTeamUser;
import com.redspider.pss.domain.user.UserDO;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.mapper.expand.PssGroupTeamUserV1Mapper;
import com.redspider.pss.mapper.expand.PssGroupTeamV1Mapper;
import com.redspider.pss.mapper.expand.UserV1Mapper;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;

import com.redspider.pss.service.db.spi.PssGroupTeamUserService;
import com.redspider.pss.service.db.spi.UserApplicationService;
import com.redspider.pss.vo.team.PssGroupTeamExamineVO;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;
import com.redspider.pss.constant.enums.Group.GroupConfirm;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: pss
 * @description: 参加组队单
 * @author: txy
 * @create: 2021-07-06 21:42
 **/
@Service
@Slf4j
public class PssGroupTeamUserServiceImpl implements PssGroupTeamUserService {


    @Autowired
    private UserApplicationService userApplicationService;

    /**
     * 用户和组队单关联表Mapper
     */
    @Resource
    private PssGroupTeamUserV1Mapper teamUserMapper;

    /**
     * 组队单Mapper
     */
    @Autowired
    private PssGroupTeamV1Mapper pssGroupTeamMapper;

    @Resource
    private UserV1Mapper userMapper;

    /**
     * 申请加入组团
     *
     * @param groupTeamUserVo 参加组队单数据
     * @return 返回结果
     */
    @Override
    public ResponseResult joinGroupTeam(PssGroupTeamUserVO groupTeamUserVo) {
        return this.saveTeamUser(groupTeamUserVo);
    }

    /**
     * 申请加入组团
     *
     * @param teamId 登录人加入组队单方法
     * @return 返回结果
     */
    @Override
    public void joinGroupTeam(Long teamId) {
        PssGroupTeamUserVO pssGroupTeamUserVo = new PssGroupTeamUserVO(teamId, ContactType.PHONE.getCode(), "创建人默认加入");
        this.saveTeamUser(pssGroupTeamUserVo);
    }

    /**
     * 根据组队单ID查询参与状态
     *
     * @param groupTeamId 组队单ID
     * @return 返回状态值
     */
    @Override
    public ResponseResult selelctGroupTeamUserStatus(Long groupTeamId) {
        PssGroupTeamUser groupTeamUser = this.selectByGroupTeamId(groupTeamId);
        Map<String, Object> map = new HashMap<>();
        if (groupTeamUser == null) {
            map.put("status", GroupUserStatus.FAIL);
        } else {
            map.put("status", groupTeamUser.getStatus());
        }
        return ResponseResult.success(map);
    }


    /**
     * 保存参加组队单
     *
     * @param groupTeamUserVo
     * @return
     */
    private ResponseResult saveTeamUser(PssGroupTeamUserVO groupTeamUserVo) {

        Boolean groupSuccessFlag = false;

        Long userId = UserUtil.getUserId();
        long groupTeamId = groupTeamUserVo.getTeamId();
        PssGroupTeam groupTeam = this.selectGroupTeam(groupTeamId);
        //PSS-113 如果组队单当前不是组队中，不能加入组队
        if (!Objects.equals(groupTeam.getTeamStatus(), GroupStatus.FINDING)) {
            return ResponseResult.fail("无法参加该组队单！");
        }
        PssGroupTeamUser groupTeamUser = this.selectByGroupTeamId(groupTeamId);
        if (groupTeamUser != null) {
            return ResponseResult.fail("您已经参与过该组队单！");
        }
        Integer teamUserCount = this.selectTeamUserCount(groupTeamId);
        Integer maxTeamSize = groupTeam.getMaxTeamSize();

        if (maxTeamSize <= teamUserCount) {
            return ResponseResult.fail("该组队单已满,无法参加！");
        }
        int status = GroupUserStatus.APPLY.getCode();
        if (groupTeam.getConfirmStatus() == GroupConfirm.NO.getCode() || userId.equals(groupTeam.getCreatorId())) {
            status = GroupUserStatus.SUCCESS.getCode();
        }
        UserDO userDO = userMapper.selectById(userId);
        String contactInformation = "";
        if (groupTeamUserVo.getType() == ContactType.PHONE.getCode()) {
            contactInformation = userDO.getPhone();
        } else {
            contactInformation = userDO.getWechatNum();
        }
        if (StringUtils.isEmpty(contactInformation)) {
            return ResponseResult.fail("您的联系方式未完善，请完善后在加入！");
        }
        //最大人数等于当前人数则设置组队完成
        if (maxTeamSize.equals(teamUserCount + 1)) {
            groupTeam.setTeamStatus(GroupStatus.SUCCESS.getCode());
            // 组队成功
            int i = pssGroupTeamMapper.updateById(groupTeam);
            if (i == 1) {
                groupSuccessFlag = true;
            }
        }
        groupTeamUser = new PssGroupTeamUser(groupTeamId, userId, status, contactInformation, groupTeamUserVo.getType(),
            groupTeamUserVo.getRemarks());
        SavePropertiesUtils.saveProperties(groupTeamUser, userId);
        this.saveGroupTeamUser(groupTeamUser);
        if (groupSuccessFlag) {
            try {
                this.sendGroupTeamUsersSMS(groupTeamId, groupSuccessFlag);
            } catch (Exception e) {
                log.error("组队单状态短信发送失败 e:", e);
            }
        }
        return ResponseResult.success("参与成功!");
    }

    /**
     * 根据组队单ID给所有参与人发送信息
     *
     * @param groupTeamId      组队单ID
     * @param groupSuccessFlag 组队成功/失败
     * @return
     */

    @Override
    public void sendGroupTeamUsersSMS(Long groupTeamId, Boolean groupSuccessFlag) throws Exception {
        List<PssGroupTeamUser> pssGroupTeamUsers = this.selectListByGroupTeamId(groupTeamId);
        List<Long> userIds = pssGroupTeamUsers.stream()
            .map(PssGroupTeamUser::getUserId)
            .collect(Collectors.toList());
        List<String> phones = new ArrayList<>();
        userIds.forEach(id -> {
            phones.add(userMapper.selectById(id)
                .getPhone());
        });
        userApplicationService.groupSMS(phones, groupSuccessFlag, groupTeamId);
    }


    /**
     * 根据组队单ID查询当前用户的参与数据
     *
     * @param groupTeamId 组队单ID
     * @return
     */
    private PssGroupTeamUser selectByGroupTeamId(Long groupTeamId) {
        Long userId = UserUtil.getUserId();
        return new LambdaQueryChainWrapper<>(teamUserMapper).eq(PssGroupTeamUser::getGroupTeamId, groupTeamId)
            .eq(PssGroupTeamUser::getUserId, userId)
            .ne(PssGroupTeamUser::getStatus, GroupUserStatus.FAIL)
            .one();
    }

    /**
     * 根据组队单ID查询所有用户的参与数据
     *
     * @param groupTeamId 组队单ID
     * @return
     */
    private List<PssGroupTeamUser> selectListByGroupTeamId(Long groupTeamId) {
        return new LambdaQueryChainWrapper<>(teamUserMapper).eq(PssGroupTeamUser::getGroupTeamId, groupTeamId)
            .ne(PssGroupTeamUser::getStatus, GroupUserStatus.FAIL)
            .list();
    }

    /**
     * 查询当前组队单参与人数
     *
     * @param groupTeamId
     * @return
     */
    @Override
    public Integer selectTeamUserCount(Long groupTeamId) {
        return new LambdaQueryChainWrapper<>(teamUserMapper).eq(PssGroupTeamUser::getGroupTeamId, groupTeamId)
            .eq(PssGroupTeamUser::getDeleted, DeletedStatus.DELETE.getCode())
            .ne(PssGroupTeamUser::getStatus, GroupUserStatus.FAIL)
            .count();
    }

    /**
     * 退出组团
     *
     * @param groupTeamId 组团ID
     * @return 返回结果
     */
    @Override
    public ResponseResult signOutGroupTeam(Long groupTeamId) {
        Long userId = UserUtil.getUserId();
        LambdaQueryChainWrapper<PssGroupTeamUser> queryChainWrapper = new LambdaQueryChainWrapper<>(teamUserMapper);
        PssGroupTeamUser groupTeamUser = queryChainWrapper.eq(PssGroupTeamUser::getGroupTeamId, groupTeamId)
            .eq(PssGroupTeamUser::getUserId, userId)
            .in(PssGroupTeamUser::getStatus, GroupUserStatus.APPLY, GroupUserStatus.SUCCESS)
            .one();
        if (groupTeamUser == null) {
            return ResponseResult.fail("您还未参与该组队单！");
        }
        PssGroupTeam groupTeam = this.selectGroupTeam(groupTeamId);

        if (GroupStatus.SUCCESS.getCode()
            .equals(groupTeam.getTeamStatus())) {
            return ResponseResult.fail("組队已经成功,无法退出！");
        }
        groupTeamUser.setStatus(GroupUserStatus.FAIL.getCode());
        groupTeamUser.setUpdateTime(new Date());
        this.saveGroupTeamUser(groupTeamUser);
        return ResponseResult.success();
    }

    /**
     * 保存组队单
     *
     * @param pssGroupTeamUser 组队单数据
     */
    private void saveGroupTeamUser(PssGroupTeamUser pssGroupTeamUser) {
        pssGroupTeamUser.setUpdateTime(new Date());
        if (pssGroupTeamUser.getId() == null) {
            teamUserMapper.insert(pssGroupTeamUser);
        } else {
            teamUserMapper.updateById(pssGroupTeamUser);
        }

    }

    public PssGroupTeam selectGroupTeam(Long groupTeamId) {
        PssGroupTeam groupTeam = pssGroupTeamMapper.selectById(groupTeamId);
        if (groupTeam == null) {
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER);
        }
        return groupTeam;
    }

    @Override
    public Integer updateById(PssGroupTeam groupTeam) {
        return pssGroupTeamMapper.updateById(groupTeam);
    }

    @Override
    public ResponseResult groupTeamExamine(PssGroupTeamExamineVO groupTeamExamineDTO) {
        Long userId = UserUtil.getUserId();
        PssGroupTeam groupTeam = this.selectGroupTeam(groupTeamExamineDTO.getPssGroupTeamId());
        if (!userId.equals(groupTeam.getCreatorId())) {
            return ResponseResult.fail("不是组队单创建人,无法确认");
        }
        for (Long groupTeamUserId : groupTeamExamineDTO.getPssGroupTeamUserIds()) {
            PssGroupTeamUser pssGroupTeamUser = new PssGroupTeamUser();
            pssGroupTeamUser.setId(groupTeamUserId);
            pssGroupTeamUser.setStatus(GroupUserStatus.SUCCESS.getCode());
            pssGroupTeamUser.setUpdateTime(new Date());
            teamUserMapper.updateById(pssGroupTeamUser);
        }
        return ResponseResult.success();
    }

    /**
     * 根据组队单ID查询所有用户的参与数据
     *
     * @param groupTeamId 组队单ID
     * @param status      组队单状态
     * @return
     */
    public List<PssGroupTeamUser> selectListByGroupTeamId(Long groupTeamId, int status) {
        return new LambdaQueryChainWrapper<>(teamUserMapper)
            .eq(PssGroupTeamUser::getGroupTeamId, groupTeamId)
            .eq(PssGroupTeamUser::getStatus, status)
            .list();
    }

}
