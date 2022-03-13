package com.redspider.pss.service.db.spi;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.domain.PssGroupTeamSearchHistoryDO;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.response.ResponseResult;

/**
 * @author QingChen
 * @date 2021/7/18
 * @description 拼团业务搜索历史
 * @since
 */

public interface PssGroupTeamSearchHistoryService {

    ResponseResult<String> addHistory(PssGroupTeamSearchHistoryDO historyDO);

    ResponseResult<JSONObject> queryHistories(SearchDTO<PssGroupTeamSearchHistoryDO> queryDTO);

    ResponseResult<Integer> removeHistory(PssGroupTeamSearchHistoryDO historyDO);

    ResponseResult<Integer> clearHistory();

}
