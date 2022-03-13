package com.redspider.pss.service.db.spi;

import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.domain.PssGroupTeamUser;
import com.redspider.pss.vo.team.PssGroupTeamExamineVO;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;

import java.util.List;

public interface PssGroupTeamUserService {
    /**
     * 申请加入组团
     * @param groupTeamUserVo 参加组队单数据
     * @return 返回结果
     */
    ResponseResult joinGroupTeam(PssGroupTeamUserVO groupTeamUserVo);

    /**
     * 获取登录人的信息 并参加组队单
     * @param id 组队单ID
     * @return 返回结果
     */
    void joinGroupTeam(Long id);

    /**
      * 根据组队单ID查询参与状态
      * @param groupTeamId 组队单ID
      * @return 返回状态值
      */
    ResponseResult selelctGroupTeamUserStatus(Long groupTeamId);

    /**
     * 退出组团
     * @param groupTeamId 组团ID
     * @return 返回结果
     */
    ResponseResult signOutGroupTeam(Long groupTeamId);

    Integer selectTeamUserCount(Long groupTeamId);

    /**
     * 根据组团发送成功/失败短信
     * @param groupTeamId 组团ID
     * @return 返回结果
     */
    void sendGroupTeamUsersSMS(Long groupTeamId, Boolean groupSuccessFlag) throws Exception;
    
    /**
     * 根据组队单id查询组队信息
     *
     * @param groupTeamId
     * @return
     */
    PssGroupTeam selectGroupTeam(Long groupTeamId);
    
    Integer updateById(PssGroupTeam groupTeam);
    
    /**
     * 组队单确认
     * @param groupTeamExamineDTO
     * @return
     */
    ResponseResult groupTeamExamine(PssGroupTeamExamineVO groupTeamExamineDTO);
    
    /**
     * 根据组队单ID查询所有用户的参与数据
     *
     * @param groupTeamId 组队单ID
     * @param status      组队单状态
     * @return
     */
    List<PssGroupTeamUser> selectListByGroupTeamId(Long groupTeamId, int status);
}
