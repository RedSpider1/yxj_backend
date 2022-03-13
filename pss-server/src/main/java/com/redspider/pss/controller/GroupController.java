package com.redspider.pss.controller;

import com.redspider.pss.constant.ApiConstant;
import com.redspider.pss.dto.team.GroupInvolveRecordDTO;
import com.redspider.pss.logic.spi.GroupLogic;
import com.redspider.pss.request.PssGroupListReq;
import com.redspider.pss.request.PssGroupTeamReq;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import com.redspider.pss.vo.group.*;
import com.redspider.pss.vo.team.PssGroupTeamUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ApiConstant.GROUP)
@Api(tags = "组队单")
public class GroupController {

    @Autowired
    private GroupLogic groupLogic;

    @PostMapping
    @ApiOperation("创建")
    public ResponseResult<Long> create(@RequestBody @Validated(Create.class) GroupBundleVO groupBundleVo) {
        return groupLogic.create(groupBundleVo, UserUtil.getUserId());
    }

    @PutMapping("id/{groupId}")
    @ApiOperation("修改")
    public ResponseResult<Long> update(@RequestBody @Validated(Update.class) GroupBundleVO groupBundleVo,
        @PathVariable Long groupId) {
        groupBundleVo.setId(groupId);
        return groupLogic.create(groupBundleVo, UserUtil.getUserId());
    }

    @PostMapping("id/{groupId}")
    @ApiOperation("发布")
    @Deprecated
    public ResponseResult<Long> release(
            @RequestBody GroupBundleVO groupBundleVo,
            @PathVariable String groupId) {
        return groupLogic.create(groupBundleVo, UserUtil.getUserId());
    }

    @GetMapping("id/{groupId}")
    @ApiOperation("组队单基础信息")
    public ResponseResult<GroupInfoVO> getInfo(@PathVariable Long groupId) {
        return groupLogic.getInfo(groupId, UserUtil.getUserId());
    }

    @PostMapping("/quit")
    @ApiOperation("退出组队单")
    public ResponseResult<Long> create(@RequestBody @Validated(Create.class) GroupQuitRemarkVO groupQuitRemarkVO) {
        return groupLogic.quit(groupQuitRemarkVO);
    }
    
    @PutMapping("/cancel/id/{groupId}")
    @ApiOperation("废弃组队单")
    public ResponseResult<Long> cancel(@PathVariable Long groupId) {
        return groupLogic.cancel(groupId);
    }

    @PostMapping("/view/id/{groupId}")
    @ApiOperation("浏览组队单")
    public ResponseResult<Integer> view(@PathVariable Long groupId) {
        return groupLogic.view(groupId);
    }

    @PostMapping("/collect/id/{groupId}")
    @ApiOperation("收藏组队单")
    public ResponseResult<Long> collect(@PathVariable Long groupId) {
        return groupLogic.collect(groupId);
    }
    
    @PutMapping("/cancelCollect/id/{groupId}")
    @ApiOperation("取消收藏组队单")
    public ResponseResult<Long> cancelCollect(@PathVariable Long groupId) {
        return groupLogic.cancelCollect(groupId);
    }
    
    @PostMapping("/collectList")
    @ApiOperation("组队单收藏列表")
    public ResponseResult<List<GroupBundleVO>> collectList(@RequestBody PssGroupListReq pssGroupListReq) {
        return groupLogic.collectList(pssGroupListReq);
    }
    
    @PostMapping("/involveList")
    @ApiOperation("组队单参与退出记录")
    public ResponseResult<List<GroupInvolveRecordDTO>> involveList(@RequestBody PssGroupTeamReq groupTeamReq) {
        return groupLogic.involveList(groupTeamReq);
    }

    @PostMapping("join")
    @ApiOperation("加入组队单")
    public ResponseResult<String> join(@RequestBody @Validated PssGroupTeamUserVO groupTeamUserVo) {
        return groupLogic.join(groupTeamUserVo, UserUtil.getUserId());
    }
    
    @ApiOperation("组队单列表")
    @GetMapping("/list")
    public ResponseResult<List<GroupBundleVO>> list(@Validated PssGroupListReq pssGroupListReq){
        return groupLogic.list(pssGroupListReq);
    }

    @ApiOperation("组队单参与者信息")
    @GetMapping("/attendees/{groupId}")
    public ResponseResult<GroupAttendeesVO> attendees(@PathVariable Long groupId){
        return groupLogic.attendeesInfo(groupId);
    }

    @ApiOperation("用户组队单关系查询")
    @GetMapping("/{groupId}/user-group-relation")
    public ResponseResult<UserGroupRelationVO> userGroupRelation(@PathVariable Long groupId){
        return groupLogic.userGroupRelation(groupId);
    }
}
