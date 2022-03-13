package com.redspider.pss.mapper.expand;

import com.redspider.pss.domain.PssGroupTeamSearchHistoryDO;
import com.redspider.pss.query.SearchQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author QingChen
 * @date 2021/7/20
 * @description 搜索历史mapper
 * @since
 */

public interface PssGroupTeamSearchHistoryV1Mapper {

    int insert(@Param("historyDO") PssGroupTeamSearchHistoryDO historyDO);

    List<PssGroupTeamSearchHistoryDO> queryList(@Param("query") SearchQuery<PssGroupTeamSearchHistoryDO> query);

    int count(@Param("query") SearchQuery<PssGroupTeamSearchHistoryDO> query);

    int removeHistory(@Param("userId") Long userId, @Param("keywords") List<String> keywords);
}
