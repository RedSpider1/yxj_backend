package com.redspider.pss.service.db.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redspider.pss.constant.enums.Audit.AuditStatus;
import com.redspider.pss.exception.PssValidationException;
import com.redspider.pss.mapper.LabelMapper;
import com.redspider.pss.mapper.expand.LabelExtMapper;
import com.redspider.pss.domain.Label;
import com.redspider.pss.repository.db.spi.GroupRepository;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.utils.DateTimeUtils;
import com.redspider.pss.utils.JsonUtils;
import com.redspider.pss.converter.SavePropertiesUtils;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.domain.PssGroupTeamLable;
import com.redspider.pss.domain.PssGroupTeamUser;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import com.redspider.pss.mapper.expand.PssGroupTeamV1Mapper;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.PssGroupTeamLableService;
import com.redspider.pss.service.db.spi.PssGroupTeamQueryService;
import com.redspider.pss.service.db.spi.PssGroupTeamService;
import com.redspider.pss.service.db.spi.PssGroupTeamUserService;
import com.redspider.pss.vo.team.PssGroupTeamDetailsVO;
import com.redspider.pss.vo.team.PssGroupTeamExamineVO;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.vo.team.PssGroupTeamVO;
import com.redspider.pss.constant.enums.Group.GroupConfirm;
import com.redspider.pss.constant.enums.Group.GroupContainType;
import com.redspider.pss.constant.enums.Group.GroupUserStatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: pss
 * @description: ??????????????????
 * @author: zzy
 * @create: 2021-05-26 22:50
 **/
@Service
@Slf4j
public class PssGroupTeamServiceImpl implements PssGroupTeamService {

    /**
     * ?????????Mapper
     */
    @Autowired
    private PssGroupTeamV1Mapper pssGroupTeamMapper;
    /**
     * ???????????????????????????
     */
    @Resource
    private PssGroupTeamLableService teamLableService;
    /**
     * ???????????????????????????
     */
    @Resource
    private PssGroupTeamUserService pssGroupTeamUserService;
    @Resource
    private PssGroupTeamQueryService pssGroupTeamQueryService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private LabelMapper pssLabelMapper;
    @Autowired
    private LabelExtMapper labelExtMapper;

    /**
     * ???????????????????????????
     *
     * @return ????????????
     */
    @Override
    public ResponseResult selectOne() {
        Long userId = UserUtil.getUserId();
        PssGroupTeam pssGroupTeam = this.selectGroupTeam(userId, null);
        PssGroupTeamVO pssGroupTeamVo = new PssGroupTeamVO();
        if (pssGroupTeam != null && Objects.equals(pssGroupTeam.getReleaseStatus(), GroupStatus.DRAFT.getCode())) {
            BeanUtils.copyProperties(pssGroupTeam, pssGroupTeamVo);
            // ???????????????
            pssGroupTeamVo.setPictureUrlArray(
                JsonUtils.strToList(pssGroupTeam.getPictureUrl(), String.class));
            //????????????ID??????
            List<PssGroupTeamLable> teamLables = teamLableService.selectTeamLableList(pssGroupTeam.getId());
            List<Long> labelIdArray = teamLables.stream().map(PssGroupTeamLable::getId).collect(Collectors.toList());
            pssGroupTeamVo.setLabelIdArray(labelIdArray);
        }
        return ResponseResult.success(pssGroupTeamVo);
    }

    /**
     * ?????????????????????/?????????????????????
     *
     * @param groupTeamVo ???????????????
     * @return ??????
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Long> save(PssGroupTeamVO groupTeamVo) {
        log.debug("save group team info:{}", groupTeamVo);
        boolean isSaveFlag = false;
        if (groupTeamVo.getReleaseStatus() != null && Objects.equals(groupTeamVo.getReleaseStatus(), GroupStatus.AUDITING.getCode())) {
            if (groupTeamVo.getMinTeamSize() > groupTeamVo.getMaxTeamSize() || groupTeamVo.getMinTeamSize() < 1
                || StrUtil.isEmpty(groupTeamVo.getTitle()) || StrUtil.isEmpty(groupTeamVo.getIntroduce())
                || groupTeamVo.getMaxTeamSize() > 999) {
                throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER);
            }
            isSaveFlag = true;
        } else {
            throw new PssValidationException("????????????????????????????????????");
        }

        Long userId = UserUtil.getUserId();
        //?????????????????????????????????
        PssGroupTeamQueryVO oldGroupTeam = null;
        if (Objects.nonNull(groupTeamVo.getId())) {
            oldGroupTeam = pssGroupTeamMapper.queryById(groupTeamVo.getId());
        }
        PssGroupTeam groupTeam = new PssGroupTeam();
        BeanUtils.copyProperties(groupTeamVo, groupTeam);
        if (oldGroupTeam != null) {
            Integer currentUserCount = pssGroupTeamUserService.selectTeamUserCount(oldGroupTeam.getId());
            if (currentUserCount > groupTeam.getMaxTeamSize()) {
                throw new PssValidationException(ResponseCode.MODIFY_GROUP_DATA_FAIL,"????????????????????????????????????????????????????????????");
            }

            groupTeam.setId(oldGroupTeam.getId());
            // PSS-102 ?????????????????????????????????????????????
            groupTeam.setExpireTime(oldGroupTeam.getExpireTime());
        }
        // ????????????
        if (groupTeam.getId() != null) {
            // ??????????????????
            teamLableService.deleteTeamUserByTeamId(groupTeam.getId());
        } else {
            // ??????????????????
            SavePropertiesUtils.saveProperties(groupTeam, userId);
        }
        //????????????
        groupTeam.setPictureUrl(JsonUtils.listToStr(groupTeamVo.getPictureUrlArray()));
        //????????????
        if (isSaveFlag) {
            if (groupTeamVo.getConfirmStatus() == null
                || groupTeamVo.getConfirmStatus() != GroupConfirm.YES.getCode()
                || groupTeamVo.getConfirmStatus() != GroupConfirm.NO.getCode()) {
                groupTeam.setConfirmStatus(GroupConfirm.NO.getCode());
            }
            groupTeam.setExamineTime(new Date());
            if (groupTeamVo.getExpireTime() == null) {
                // ???????????????????????????
                groupTeam.setExpireTime(DateTimeUtils.getOneHundred());
            }
            groupTeam.setTeamStatus(GroupStatus.FINDING.getCode());
            // TODO ???????????????????????????
            groupTeam.setExamineStatus(AuditStatus.SUCCESS.getCode());
        }
        if (oldGroupTeam != null) {
            pssGroupTeamMapper.updateById(groupTeam);
            if (!Objects.equals(groupTeam.getContainMe(), oldGroupTeam.getContainMe())) {
                if (GroupContainType.CONTAIN.getCode() == groupTeam.getContainMe().byteValue()) {
                    pssGroupTeamUserService.joinGroupTeam(groupTeam.getId());
                } else {
                    pssGroupTeamUserService.signOutGroupTeam(groupTeam.getId());
                }
            }
        } else {
            pssGroupTeamMapper.insert(groupTeam);
            if (GroupContainType.CONTAIN.getCode() == groupTeam.getContainMe().byteValue()) {
                pssGroupTeamUserService.joinGroupTeam(groupTeam.getId());
            }
        }
        // ??????????????????
        List<Long> labelIds = Objects.nonNull(groupTeamVo.getLabelIdArray()) ? groupTeamVo.getLabelIdArray() : new ArrayList<>();
        labelIds.addAll(addLabels(groupTeamVo.getLabels(), userId));
        labelIds = labelIds.stream()
            .distinct()
            .collect(Collectors.toList());
        teamLableService.saveGroupTeamUser(groupTeam.getId(), labelIds);
        return ResponseResult.success(groupTeam.getId());
    }

    /** ???????????????
     * @param labels ??????????????????
     * @return ??????id
     */
    private Collection<Long> addLabels(Collection<String> labels, Long operatorId) {
        log.debug("addLabels:{}", labels);
        List<Long> ids = new ArrayList<>();
        if (Objects.isNull(labels)) {
            return ids;
        }
        for (String label : labels) {
            Label existLabel = labelExtMapper.selectByLabel(label);
            if (Objects.isNull(existLabel)) {
                Label record = new Label();
                record.setDeleted(0);
                record.setCreatorId(operatorId);
                record.setLabelName(label);
                record.setUpdaterId(operatorId);
                pssLabelMapper.insertSelective(record);
                ids.add(record.getId());
            } else {
                ids.add(existLabel.getId());
            }
        }
        return ids;
    }

    /**
     * ???????????????
     *
     * @param id ?????????ID
     * @return ??????
     */
    @Override
    public ResponseResult delete(Long id) {
        PssGroupTeam groupTeam = this.selectGroupTeam(null, id);
        if (groupTeam == null) {
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER);
        }
        groupTeam.setDeleted(1);
        pssGroupTeamMapper.updateById(groupTeam);
        //???????????????????????????????????????
        pssGroupTeamMapper.deleteViewedGroups(id);
        return ResponseResult.ok();
    }

    /**
     * ???????????????
     *
     * @param groupTeam ????????????
     * @return ???????????????
     */
    @Override
    public PssGroupTeam getOne(PssGroupTeam groupTeam) {
        QueryWrapper<PssGroupTeam> wrapper = new QueryWrapper<>();
        return pssGroupTeamMapper.selectOne(wrapper.setEntity(groupTeam));
    }

    @Override
    public ResponseResult groupTeamDetails(Long id) {
        log.info("====???????????????:{}", id);

        if (ObjectUtils.isEmpty(id)) {
            throw new PssValidationException(ResponseCode.ILLEGAL_PARAMETER);
        }
        Integer ifDelete = pssGroupTeamMapper.queryIfDelete(id);
        //  ???????????? 0 ?????????????????????  ????????????404??????
        if (ifDelete != 0) {
            throw new PssValidationException(ResponseCode.GROUP_DEL_DUPLICATE);
        }
        //??????????????????
        pssGroupTeamQueryService.insertViewedGroups(id);
        PssGroupTeamDetailsVO detailsVo = pssGroupTeamMapper.groupTeamDetails(id);
        //??????????????????????????????
        List<String> labels = pssGroupTeamMapper.groupTeamLabels(id);
        detailsVo.setLabelIdArray(labels);
        detailsVo.setCurrentTeamNumbers(pssGroupTeamMapper.queryCurrentTeamNumbers(id));
        return ResponseResult.success(detailsVo);
    }

    @Override
    public Boolean updateStatusByAuditId(Long auditId, GroupStatus targetStatus) {
        return groupRepository.updateStatusByAuditId(auditId, targetStatus, GroupStatus.AUDITING);
    }

    private PssGroupTeam selectGroupTeam(Long userId, Long id) {
        PssGroupTeam team = new PssGroupTeam();
        team.setCreatorId(userId);
        // ??????????????????????????????
        // team.setReleaseStatus(0);
        // team.setExamineStatus(0);
        team.setDeleted(0);
        team.setId(id);
        return this.getOne(team);

    }

    @Override
    public ResponseResult groupTeamPass(Long groupTeamId) {
        PssGroupTeam groupTeam = pssGroupTeamUserService.selectGroupTeam(groupTeamId);
        groupTeam.setTeamStatus(GroupStatus.SUCCESS.getCode());
        int currentTeamNumber = pssGroupTeamMapper.queryCurrentTeamNumbers(groupTeamId);
        groupTeam.setMaxTeamSize(currentTeamNumber);
        if (groupTeam.getMinTeamSize() > currentTeamNumber) {
            groupTeam.setMinTeamSize(currentTeamNumber);
        }
        int i = pssGroupTeamUserService.updateById(groupTeam);
        if (i != 1) {
            return ResponseResult.fail("????????????");
        }
        try {
            pssGroupTeamUserService.sendGroupTeamUsersSMS(groupTeamId, true);
        } catch (Exception e) {
            log.error("????????????????????????????????? e:", e);
        }
        return ResponseResult.ok();
    }

    @Override
    public ResponseResult groupTeamAutoExamine(Long groupTeamId, int status) {
        Long userId = UserUtil.getUserId();
        PssGroupTeam groupTeam = pssGroupTeamUserService.selectGroupTeam(groupTeamId);
        if (!userId.equals(groupTeam.getCreatorId())) {
            return ResponseResult.fail("????????????????????????,????????????");
        }
        if (groupTeam.getConfirmStatus() == status) {
            return ResponseResult.ok();
        }
        //?????????????????????????????????,??????????????????????????????????????????
        if (GroupConfirm.NO.getCode() == status) {
            List<PssGroupTeamUser> groupTeamUsers = pssGroupTeamUserService.selectListByGroupTeamId(groupTeamId, GroupUserStatus.APPLY.getCode());
            List<Long> teamUserIds = new ArrayList<>();
            for (PssGroupTeamUser teamUser : groupTeamUsers) {
                teamUserIds.add(teamUser.getId());
            }
            pssGroupTeamUserService.groupTeamExamine(new PssGroupTeamExamineVO(groupTeamId, teamUserIds));
        }
        groupTeam.setConfirmStatus(status);
        pssGroupTeamUserService.updateById(groupTeam);
        return ResponseResult.ok();
    }
    
    @Override
    public List<Long> selectViewedGroupIdsByUserId(Long userId, PssGroupListReq req) {
        return pssGroupTeamMapper.viewedGroupIds(userId, (long) req.getOffset(), (long) req.getPageSize());
    }
}
