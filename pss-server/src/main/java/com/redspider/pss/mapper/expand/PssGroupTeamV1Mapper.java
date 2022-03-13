package com.redspider.pss.mapper.expand;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.redspider.pss.domain.PssGroupTeamQueryDO;
import com.redspider.pss.dto.team.InvolveUserDTO;
import com.redspider.pss.dto.team.PssGroupTempDTO;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.vo.team.PssGroupTeamDetailsVO;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.dto.user.AttendUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小夜
 */
@Repository
public interface PssGroupTeamV1Mapper extends BaseMapper<PssGroupTeam> {

    IPage<PssGroupTeamQueryVO> queryList(IPage<PssGroupTeam> page, @Param("queryParam") PssGroupTempDTO pssGroupTempDto);

    IPage<PssGroupTeamQueryVO> involveGroups(IPage<PssGroupTeam> page, @Param("queryParam") PssGroupTempDTO pssGroupTempDto);

    List<InvolveUserDTO> involveUserCount(@Param("teamIdList") List<Long> teamIdList);

    PssGroupTeamQueryVO queryById(Long id);

    Integer queryIfDelete(Long id);

    PssGroupTeamDetailsVO groupTeamDetails(Long id);

    List<String> groupTeamLabels(Long id);

    Integer queryCurrentTeamNumbers(Long id);

    IPage<PssGroupTeamQueryVO> searchByKeyword(IPage<PssGroupTeam> page,
                                        @Param("keyword") String keyword,
                                        @Param("labelIds") List<Long> labelIds,
                                        @Param("size") int labelSize);
   
   IPage<PssGroupTeamQueryVO> viewedGroups(IPage<PssGroupTeam> page, @Param("queryParam") PssGroupTempDTO pssGroupTempDto);
   
   ArrayList<AttendUserDTO> queryUsersByGroupId(long groupId);
   
   int insertViewedGroups(@Param("userId") long userId, @Param("groupId") long groupId);
   
   int deleteViewedGroups(long groupId);
   
   IPage<PssGroupTeamQueryDO> queryByLabel(IPage<PssGroupTeam> page, @Param("labelId") Long labelId);
   
   List<Long> viewedGroupIds(@Param("userId") Long userId, @Param("offset") Long offset, @Param("pageSize") Long pageSize);
   
}
