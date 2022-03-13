package com.redspider.pss.service.db.spi;

import com.redspider.pss.constant.enums.Group.GroupStatus;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.vo.team.PssGroupTeamVO;

import java.util.List;

public interface PssGroupTeamService {


    /**
     * 获取组队单草稿数据
     * @return 草稿数据
     */
    ResponseResult selectOne();

    /**
     * 保存组队单数据
     *
     * @param groupTeamVo 组队单数据
     * @return 结果
     */
    ResponseResult<Long> save(PssGroupTeamVO groupTeamVo);

    /**
     * 删除组队单
     *
     * @param id 组队单ID
     * @return 返回
     */
    ResponseResult delete(Long id);

    /**
     * 查询组队单
     * @param groupTeam 查询数据
     * @return 组队单数据
     */
    PssGroupTeam getOne(PssGroupTeam groupTeam);

    /**
     * 组队单详情
     * @param id  组队单ID
     * @return
     */
    ResponseResult groupTeamDetails(Long id);

    /**
     * 组队单提前完成
     * @param groupTeamId
     * @return
     */
   ResponseResult groupTeamPass(Long groupTeamId);
   
   /**
    * 组队单是否自动确认
    *
    * @param groupTeamId
    * @param status
    * @return
    */
   ResponseResult groupTeamAutoExamine(Long groupTeamId, int status);
   
   Boolean updateStatusByAuditId(Long auditId, GroupStatus targetStatus);
   
   /**
    * 获取浏览过的组队的
    *
    * @param userId
    * @param pssGroupListReq
    * @return
    */
   List<Long> selectViewedGroupIdsByUserId(Long userId, PssGroupListReq pssGroupListReq);
   
}
