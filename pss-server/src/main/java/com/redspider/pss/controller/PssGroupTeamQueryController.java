package com.redspider.pss.controller;

import com.redspider.pss.base.PageResult;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.PssGroupTeamQueryService;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.vo.team.PssGroupTeamQueryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/groupTeam/query")
@Api(tags = "组队单接口API")
public class PssGroupTeamQueryController {

    @Autowired
    private PssGroupTeamQueryService pssGroupTeamQueryService;

    @ApiOperation("根据Id查询组队单")
    @GetMapping(value = "/{id}")
    public ResponseResult queryOne(@PathVariable(value = "id",required = true) Long id){
        return pssGroupTeamQueryService.selectById(id);
    }
    @ApiOperation("分页查询所有组队单")
    @GetMapping(value = "/list")
    public PageResult queryList(@Validated PssGroupTeamReq queryPageRequest){
        return pssGroupTeamQueryService.queryList(queryPageRequest);
    }

    @ApiOperation("分页当前用户所有组队单")
    @GetMapping(value = "/listByUser")
    public PageResult<PssGroupTeamQueryVO> queryListByUser(@Validated PssGroupTeamReq queryPageRequest){
        //PSS-100 我发起的组队单 创建成功不包含草稿
        return pssGroupTeamQueryService.queryListByUser(queryPageRequest);
    }

    @ApiOperation("参与过的组队单")
    @GetMapping (value = "/involveGroups")
    public PageResult involveGroups(@Validated PssGroupTeamReq queryPageRequest) {
        return pssGroupTeamQueryService.involveGroups(queryPageRequest);
    }
    
    @ApiOperation("我浏览过的组队单")
    @GetMapping (value = "/viewedGroups")
    public PageResult viewedGroups(@Validated PssGroupTeamReq queryPageRequest) {
        return pssGroupTeamQueryService.viewedGroups(queryPageRequest);
    }
    
    @ApiOperation("组队单参与者信息")
    @GetMapping (value = "/queryUsersByGroupId")
    public PageResult queryUsersByGroupId(long groupId) {
        return pssGroupTeamQueryService.queryUsersByGroupId(groupId);
    }
}
