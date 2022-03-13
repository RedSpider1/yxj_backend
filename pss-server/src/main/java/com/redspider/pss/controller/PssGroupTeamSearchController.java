package com.redspider.pss.controller;

import com.alibaba.fastjson.JSONObject;
import com.redspider.pss.base.PageResult;
import com.redspider.pss.converter.SearchConverter;
import com.redspider.pss.dto.team.SearchDTO;
import com.redspider.pss.logic.spi.GroupSearchLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.PssGroupTeamSearchService;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import com.redspider.pss.vo.team.SearchVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author QingChen
 * @date 2021/7/25
 * @description 组队单搜索功能
 * @since
 */

@RestController
@RequestMapping("/groupTeam/search")
@Api(tags = "组队单搜索功能")
public class PssGroupTeamSearchController {

    @Autowired
    private PssGroupTeamSearchService searchService;
    @Autowired
    private GroupSearchLogic groupSearchLogic;

    @PostMapping("/byKeyWord")
    public ResponseResult<PageResult<PssGroupTeamQueryVO>> search(@RequestBody SearchVO<JSONObject> queryVO) {
        SearchDTO<JSONObject> query = SearchConverter.convert2DTO(queryVO);
        return ResponseResult.success(searchService.searchGroupTeam(query));
    }

    @GetMapping("/label")
    public ResponseResult<PageResult<PssGroupTeamQueryVO>> searchByLabel(@Validated SearchVO<String> labelVO) {
        return ResponseResult.success(groupSearchLogic.searchByLabel(labelVO));
    }
}

