package com.redspider.pss.service.db.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.redspider.pss.domain.PssGroupTeam;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.mapper.expand.PssGroupTeamV1Mapper;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.service.db.spi.PssGroupTeamSearchHistoryService;
import com.redspider.pss.service.db.spi.PssGroupTeamSearchService;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author QingChen
 * @date 2021/7/29
 * @description
 * @since
 */

@Service
public class PssGroupTeamSearchServiceImpl implements PssGroupTeamSearchService {

    private Logger logger = LoggerFactory.getLogger(PssGroupTeamSearchServiceImpl.class);

    @Autowired
    private PssGroupTeamV1Mapper pssGroupTeamMapper;

    @Autowired
    private PssGroupTeamSearchHistoryService historyService;

    @Autowired
    private PssGroupTeamQueryServiceImpl pssGroupTeamQueryService;

    @Override
    public PageResult<PssGroupTeamQueryVO> searchGroupTeam(SearchDTO<JSONObject> query) {
        // 标签id
        JSONArray labels = query.getKeyword().getJSONArray("lables");
        PssGroupTeamReq teamReq = new PssGroupTeamReq();
        teamReq.setKeyWord(query.getKeyword().getString("keyword"));
        teamReq.setPageNum(query.getPageSize());
        teamReq.setPageNum(query.getPageNum());
        // historyService.addHistory(new PssGroupTeamSearchHistoryDO(query.getQ()));
        // 搜索历史暂时记录在前端
        PageResult<PssGroupTeamQueryVO> pageResult;
        if(labels == null || labels.size() == 0) {
            pageResult = pssGroupTeamQueryService.queryList(teamReq);
        }else {
            pageResult = searchWithLabelAndKeyword(query, labels);
        }
        return pageResult;
    }

    /**
     * 标签及关键字查询
     * @param query
     * @param labels
     * @return
     */
    private PageResult<PssGroupTeamQueryVO> searchWithLabelAndKeyword(SearchDTO<JSONObject> query, JSONArray labels) {
        IPage<PssGroupTeam> page = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<PssGroupTeamQueryVO> pssGroupTeamIPage = pssGroupTeamMapper.searchByKeyword(
                page,
                query.getKeyword().getString("keyword"),
                labels.toJavaList(Long.class),
                labels.size()
        );
        return new PageResult(200, pssGroupTeamIPage.getTotal(), pssGroupTeamIPage.getRecords());
    }
}
