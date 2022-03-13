package com.redspider.pss.controller;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.domain.PssGroupTeamSearchHistoryDO;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.PssGroupTeamSearchHistoryService;
import com.redspider.pss.vo.team.SearchVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author QingChen
 * @date 2021/7/21
 * @description 搜索历史controller
 */

@RestController
@RequestMapping("/groupTeam/search/history")
@Api(tags = "组队单查询历史记录")
public class PssGroupTeamSearchHistoryController {

    @Autowired
    private PssGroupTeamSearchHistoryService historyService;

    @PostMapping("/add")
    @ApiOperation("新增查询记录")
    public ResponseResult<String> add(@RequestBody PssGroupTeamSearchHistoryDO historyDO) {
        return historyService.addHistory(historyDO);
    }

    @PostMapping("/pageQuery")
    @ApiOperation("分页获取查询记录")
    public ResponseResult<JSONObject> pageQuery(@RequestBody SearchVO<PssGroupTeamSearchHistoryDO> queryVO) {
        SearchDTO<PssGroupTeamSearchHistoryDO> query = new SearchDTO<>();
        query.setKeyword(queryVO.getKeyword());
        query.setPageNum(query.getPageNum());
        query.setPageSize(query.getPageSize());
        return historyService.queryHistories(query);
    }

    @PostMapping("/remove")
    @ApiOperation("删除查询记录")
    public ResponseResult<Integer> remove(@RequestBody PssGroupTeamSearchHistoryDO historyDO) {
        return historyService.removeHistory(historyDO);
    }

    @GetMapping("/clear")
    @ApiOperation("清空查询记录")
    public ResponseResult<Integer> clear() {
        return historyService.clearHistory();
    }
}
