package com.redspider.pss.service.db.impl;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.domain.PssGroupTeamSearchHistoryDO;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.mapper.expand.PssGroupTeamSearchHistoryV1Mapper;
import com.redspider.pss.query.SearchQuery;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.db.spi.PssGroupTeamSearchHistoryService;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author QingChen
 * @date 2021/7/18
 * @description 搜索历史实现
 * @since
 */
@Service
public class PssGroupTeamSearchHistoryServiceImpl implements PssGroupTeamSearchHistoryService {

    @Autowired
    private PssGroupTeamSearchHistoryV1Mapper historyMapper;

    @Override
    public ResponseResult<String> addHistory(PssGroupTeamSearchHistoryDO historyDO) {
        if (StringUtils.isBlank(historyDO.getKeyword())) {
            return ResponseResult.success();
        }
        try {
            historyDO.setCreatorId(UserUtil.getUserId());
            historyDO.setUpdaterId(UserUtil.getUserId());
            historyMapper.insert(historyDO);
            return ResponseResult.success();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("新增查询历史失败，原因：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult<JSONObject> queryHistories(SearchDTO<PssGroupTeamSearchHistoryDO> queryDTO) {
        JSONObject result = new JSONObject();
        try {
            Long userId = UserUtil.getUserId();
            SearchQuery<PssGroupTeamSearchHistoryDO> query = new SearchQuery<>();
            query.setKeyword(queryDTO.getKeyword());
            query.setPageNum(query.getPageNum());
            query.setPageSize(query.getPageSize());
            query.getKeyword().setCreatorId(userId);

            List<PssGroupTeamSearchHistoryDO> histories = historyMapper.queryList(query);
            int count = historyMapper.count(query);
            result.put("data", histories);
            result.put("total", count);
            return ResponseResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("查询历史失败，原因：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult<Integer> removeHistory(PssGroupTeamSearchHistoryDO historyDO) {
        try {
            String keyword = historyDO.getKeyword();
            int count = historyMapper.removeHistory(UserUtil.getUserId(), Collections.singletonList(keyword));
            return ResponseResult.success(count);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("删除历史记录异常，原因：" + e.getMessage());
        }
    }

    @Override
    public ResponseResult<Integer> clearHistory() {
        try {
            int count = historyMapper.removeHistory(UserUtil.getUserId(), null);
            return ResponseResult.success(count);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("清除历史记录异常，原因：" + e.getMessage());
        }
    }
}
