package com.redspider.pss.service.db.spi;

import com.redspider.pss.base.PageResult;
import com.redspider.pss.dto.team.PssGroupTeamQueryDTO;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;

/**
 * 组队单查询服务
 */
public interface PssGroupTeamQueryService {

    ResponseResult<PssGroupTeamQueryVO> selectById(Long id);

    PageResult<PssGroupTeamQueryVO> queryList(PssGroupTeamReq queryPageRequest);

    PageResult<PssGroupTeamQueryVO> queryListByUser(PssGroupTeamReq queryPageRequest);

    /**
     * @description: 参与过的组队单
     * @author: xiaoA
     * @date: 2021/7/18 3:39 PM
     * @param:
     * @return: com.redspider.pss.result.PssResult
     */
    PageResult<PssGroupTeamQueryVO> involveGroups(PssGroupTeamReq queryPageRequest);
    
    /**
     * 我浏览过的组队单
     * @param queryPageRequest
     * @return
     */
   PageResult viewedGroups(PssGroupTeamReq queryPageRequest);
   
   /**
    * 查询组队单参与者信息
    * @param groupId
    * @return
    */
    PageResult queryUsersByGroupId(long groupId);
   
   /**
    * 添加组队单浏览记录
    * @param groupId
    * @return
    */
   int insertViewedGroups(long groupId);
   
   /**
    * 删除组队单记录
    * @param groupId
    * @return
    */
   int deleteViewedGroups(long groupId);

    /** 通过关联的标签查询组队单
     * @param labelId
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResult<PssGroupTeamQueryDTO> queryByLabel(Long labelId, Integer pageNum, Integer pageSize);
}
