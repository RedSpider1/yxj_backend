package com.redspider.pss.service.db.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.redspider.pss.domain.PssGroupTeamQueryDO;
import com.redspider.pss.dto.team.PssGroupTeamQueryDTO;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.mapper.expand.PssGroupTeamV1Mapper;
import com.redspider.pss.response.ResponseCode;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.PssGroupTeamQueryService;
import com.redspider.pss.dto.team.InvolveUserDTO;
import com.redspider.pss.dto.team.PssGroupTempDTO;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.vo.team.PssGroupTeamDetailsVO;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.dto.user.AttendUserDTO;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PssGroupTeamQueryServiceImpl implements PssGroupTeamQueryService {
    @Autowired
    private PssGroupTeamV1Mapper pssGroupTeamMapper;

    @Override
    public ResponseResult<PssGroupTeamQueryVO> selectById(Long id) {
        PssGroupTeamQueryVO pssGroupTeamQueryVo= pssGroupTeamMapper.queryById(id);
        return ResponseResult.success(pssGroupTeamQueryVo) ;
    }

    @Override
    public PageResult<PssGroupTeamQueryVO> queryList(PssGroupTeamReq queryPageRequest) {
        IPage<PssGroupTeamQueryVO> pssGroupTeamVoIPage = this.basicQuery(null, queryPageRequest);
        return new PageResult(200,pssGroupTeamVoIPage.getTotal(),pssGroupTeamVoIPage.getRecords());
    }

    @Override
    public PageResult<PssGroupTeamQueryVO> queryListByUser(PssGroupTeamReq queryPageRequest) {
        Long id = UserUtil.getUserId();//用户ID
        IPage<PssGroupTeamQueryVO> pssGroupTeamVoIPage = this.basicQuery(id, queryPageRequest);
        return new PageResult<>(200,pssGroupTeamVoIPage.getTotal(),pssGroupTeamVoIPage.getRecords());
    }

    @Override
    public PageResult<PssGroupTeamQueryVO> involveGroups(PssGroupTeamReq queryPageRequest) {
        //todo 可以基于basicQuery
        Long userId = UserUtil.getUserId();
        if (ObjectUtils.isEmpty(userId)) {
            return new PageResult(ResponseCode.USER_NOT_LOGIN.getCode(),"用户未登录，不能查看参与过的组队单哦~");
        }
        log.info("查询用户,请求参数id={}", userId);
        IPage<PssGroupTeam> page = new Page<>(queryPageRequest.getPageNum(),queryPageRequest.getPageSize());
        PssGroupTempDTO pssGroupTempDto = this.assembleQueryParam(queryPageRequest);
        pssGroupTempDto.setUserId(userId);
        IPage<PssGroupTeamQueryVO> pssGroupTeamVoIPage = pssGroupTeamMapper.involveGroups(page, pssGroupTempDto);
        List<PssGroupTeamQueryVO> groupList = pssGroupTeamVoIPage.getRecords();
        if (!CollectionUtils.isEmpty(groupList)) {
            List<Long> teamIdList = Lists.newArrayList();
            groupList.stream().forEach(groupTeam -> {
                groupTeam.setNeedNum(groupTeam.getMinTeamSize());
                teamIdList.add(groupTeam.getId());
            });
            // 查询每个组队单实际参与人数
            List<InvolveUserDTO> joinUserList = pssGroupTeamMapper.involveUserCount(teamIdList);
            Map<Long, Integer> teamUserCountMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(joinUserList)) {
                joinUserList.stream().forEach(
                    involveUser -> teamUserCountMap.put(involveUser.getGroupTeamId(), involveUser.getCurrentJoinNum()));
                for (PssGroupTeamQueryVO groupTeam : groupList) {
                    groupTeam.setCurrentJoinNum(teamUserCountMap.getOrDefault(groupTeam.getId(), 0));
                }
            }
        }
        return new PageResult(200,pssGroupTeamVoIPage.getTotal(),pssGroupTeamVoIPage.getRecords());
    }
    
    @Override
    public PageResult viewedGroups(PssGroupTeamReq queryPageRequest) {
        Long userId = UserUtil.getUserId();
        if (ObjectUtils.isEmpty(userId)) {
            return new PageResult(ResponseCode.USER_NOT_LOGIN.getCode(),"用户未登录，不能查看参与过的组队单哦~");
        }
        log.info("查询用户,请求参数id={}", userId);
        IPage<PssGroupTeam> page = new Page<>(queryPageRequest.getPageNum(),queryPageRequest.getPageSize());
        PssGroupTempDTO pssGroupTempDto = this.assembleQueryParam(queryPageRequest);
        pssGroupTempDto.setUserId(userId);
        IPage<PssGroupTeamQueryVO> pssGroupTeamVoIPage = pssGroupTeamMapper.viewedGroups(page, pssGroupTempDto);
        List<PssGroupTeamQueryVO> groupList = pssGroupTeamVoIPage.getRecords();
        if (pssGroupTeamVoIPage.getTotal()==0) {
            return new PageResult(200,"您还没有浏览过的组队单，赶快去首页逛逛吧~");
        }
        if (!CollectionUtils.isEmpty(groupList)) {
            List<Long> teamIdList = Lists.newArrayList();
            groupList.stream().forEach(groupTeam -> {
                groupTeam.setNeedNum(groupTeam.getMinTeamSize());
                teamIdList.add(groupTeam.getId());
            });
            // 查询每个组队单实际参与人数
            //todo mysql直接map或者使用collectors.groupby
            List<InvolveUserDTO> joinUserList = pssGroupTeamMapper.involveUserCount(teamIdList);
            Map<Long, Integer> teamUserCountMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(joinUserList)) {
                joinUserList.stream().forEach(
                 involveUser -> teamUserCountMap.put(involveUser.getGroupTeamId(), involveUser.getCurrentJoinNum()));
                for (PssGroupTeamQueryVO groupTeam : groupList) {
                    groupTeam.setCurrentJoinNum(teamUserCountMap.getOrDefault(groupTeam.getId(), 0));
                }
            }
        }
        return new PageResult(200,pssGroupTeamVoIPage.getTotal(),pssGroupTeamVoIPage.getRecords());
    }
    
    @Override
    public PageResult queryUsersByGroupId(long groupId) {
        List<AttendUserDTO> userDTOS = pssGroupTeamMapper.queryUsersByGroupId(groupId);
        // 非组队单创建人，后端隐藏联系方式
        PssGroupTeamDetailsVO detailsVo = pssGroupTeamMapper.groupTeamDetails(groupId);
        Long userId = UserUtil.getUserId() == null ? -1 : UserUtil.getUserId();
        if(detailsVo != null) {
            if(!userId.equals(detailsVo.getCreatorId())) {
                userDTOS.forEach(user -> {
                    user.setPhone("******");
                    user.setWechatNum("******");
                    user.setEmail("******");
                });
            }else {
                userDTOS.forEach(user -> {
                    if(user.getType() == 0) {
                        user.setWechatNum(null);
                    }else if(user.getType() == 1){
                        user.setPhone(null);
                    }
                });
            }
        }
        return new PageResult(200,userDTOS.size(),userDTOS);
    }
    
    @Override
    public int insertViewedGroups(long groupId) {
        log.info("添加浏览记录 groupId={}", groupId);
        Long userId = UserUtil.getUserId();
        if (ObjectUtils.isEmpty(userId)) {
            return -1;
        }
        log.info("查询用户,请求参数id={}", userId);
        return pssGroupTeamMapper.insertViewedGroups(userId, groupId);
    }
    
    @Override
    public int deleteViewedGroups(long groupId) {
        return pssGroupTeamMapper.deleteViewedGroups(groupId);
    }

    @Override
    public PageResult<PssGroupTeamQueryDTO> queryByLabel(Long labelId, Integer pageNum, Integer pageSize) {
        log.info("queryByLabel:{}, {}, {}", labelId, pageNum, pageSize);
        PageResult<PssGroupTeamQueryDTO> pageResult = new PageResult<>(0, new ArrayList<>());
        if (Objects.isNull(labelId)) {
            return pageResult;
        }

        IPage<PssGroupTeam> page = new Page<>(pageNum, pageSize);
        IPage<PssGroupTeamQueryDO> originPage = pssGroupTeamMapper.queryByLabel(page, labelId);
        if (originPage.getTotal() > 0) {
            List<PssGroupTeamQueryDTO> data = originPage.getRecords()
                .stream()
                .map(t -> {
                    PssGroupTeamQueryDTO dto = new PssGroupTeamQueryDTO();
                    BeanUtils.copyProperties(t, dto);
                    return dto;
                }).collect(Collectors.toList());
            pageResult.setData(data);
        }
        return pageResult;
    }

    private IPage<PssGroupTeamQueryVO> basicQuery(Long userId, PssGroupTeamReq queryPageRequest) {
        IPage<PssGroupTeam> page = new Page<>(queryPageRequest.getPageNum(),queryPageRequest.getPageSize());
        PssGroupTempDTO pssGroupTempDto = this.assembleQueryParam(queryPageRequest);
        if (userId != null) {
            pssGroupTempDto.setUserId(userId);
        }
        return pssGroupTeamMapper.queryList(page,pssGroupTempDto);
    }

    private PssGroupTempDTO assembleQueryParam(PssGroupTeamReq queryPageRequest) {
        PssGroupTempDTO pssGroupTempDto = new PssGroupTempDTO();
        BeanUtils.copyProperties(queryPageRequest,pssGroupTempDto);
        pssGroupTempDto.setReleaseStatus(1);
        return pssGroupTempDto;
    }
}
