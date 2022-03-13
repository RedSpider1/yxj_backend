package com.redspider.pss.controller;

import com.redspider.pss.constant.ApiConstant;
import com.redspider.pss.logic.spi.UserContactInformationLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.vo.Create;
import com.redspider.pss.vo.Update;
import com.redspider.pss.vo.group.GroupBundleVO;
import com.redspider.pss.vo.user.UserContactInformationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: pss
 * @description: controller
 * @author: 小夜
 * @create: 2021-10-10 23:44
 **/
@RestController
@RequestMapping(value = ApiConstant.CONTACTINFORMATION)
@Api(tags = "联系方式")
public class UserContactInformationController {
    @Autowired
    private UserContactInformationLogic userContactInformationLogic;

    @PostMapping
    @ApiOperation("创建")
    public ResponseResult<Long> create(@RequestBody @Validated UserContactInformationVO informationVO) {
        return userContactInformationLogic.create(informationVO, UserUtil.getUserId());
    }

    @PutMapping("id/{id}")
    @ApiOperation("修改")
    public ResponseResult<Long> update(@RequestBody @Validated UserContactInformationVO informationVO,
                                       @PathVariable Long id) {
        informationVO.setId(id);
        return userContactInformationLogic.update(informationVO, UserUtil.getUserId());
    }

    @GetMapping
    @ApiOperation("查询联系方式")
    public ResponseResult<List<UserContactInformationVO>> list() {
        return userContactInformationLogic.getInfoByUserId(UserUtil.getUserId());
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation("删除联系方式")
    public ResponseResult<GroupBundleVO> getInfo(@PathVariable Long id) {
        return userContactInformationLogic.deleteById(id, UserUtil.getUserId());
    }
}
