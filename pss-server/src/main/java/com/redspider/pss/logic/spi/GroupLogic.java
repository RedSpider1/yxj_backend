package com.redspider.pss.logic.spi;

import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.group.*;

import java.util.List;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;

public interface GroupLogic {

    ResponseResult<Long> create(GroupBundleVO groupBundleVo, Long userId);

    /** 获取组队单基本信息
     * @param groupId
     * @param userId
     * @return
     */
    ResponseResult<GroupInfoVO> getInfo(Long groupId, Long userId);

    /**
     * 退出组队单
     * @param groupQuitRemarkVO
     * @return
     */
    ResponseResult<Long> quit(GroupQuitRemarkVO groupQuitRemarkVO);
    
    /**
     * 废弃组队单
     * @param groupId
     * @return
     */
    ResponseResult<Long> cancel(Long groupId);
    
    /**
     * 收藏组队单
     * @param groupId
     * @return
     */
    ResponseResult<Long> collect(Long groupId);

    ResponseResult<Integer> view(Long groupId);

    /**
     * 取消收藏组队单
     * @param groupId
     * @return
     */
    ResponseResult<Long> cancelCollect(Long groupId);
    
    /**
     * 组队单收藏列表
     * @param pssGroupListReq
     * @return
     */
    ResponseResult<List<GroupBundleVO>> collectList(PssGroupListReq pssGroupListReq);
    
    /**
     * 组队单参与退出记录
     * @param groupTeamReq
     * @return
     */
    ResponseResult<List<GroupInvolveRecordDTO>> involveList(PssGroupTeamReq groupTeamReq);
    
    
    /**
     * 加入组队单
     * @param groupTeamUserVo
     * @param userId
     * @return
     */
    ResponseResult<String> join(PssGroupTeamUserVO groupTeamUserVo, Long userId);
    
    /**
     * 查询组队单列表
     * @param pssGroupListReq
     * @return
     */
   ResponseResult<List<GroupBundleVO>> list(PssGroupListReq pssGroupListReq);

   /**
    * 组队单参与者信息
    * @param groupId
    * @return
    */
   ResponseResult<GroupAttendeesVO> attendeesInfo(Long groupId);

    ResponseResult<UserGroupRelationVO> userGroupRelation(Long groupId);
}
