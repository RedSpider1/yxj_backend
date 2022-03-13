package com.redspider.pss.controller;

import com.redspider.pss.constant.ApiConstant;
import com.redspider.pss.logic.spi.LabelLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.LabelService;
import com.redspider.pss.vo.team.IdentityVO;
import com.redspider.pss.vo.common.LabelAddVO;
import com.redspider.pss.vo.common.LabelVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiConstant.LABEL)
@Api(tags = "标签API")
public class LabelController {

    @Autowired
    private LabelLogic labelLogic;

    @ApiOperation("新增标签")
    @PostMapping
    public ResponseResult<Long> add(@Validated @RequestBody LabelAddVO labelAddVo) {
        return labelLogic.add(labelAddVo);
    }

    @ApiOperation("查询标签")
    @GetMapping("/list")
    public ResponseResult<List<LabelVO>> list(@RequestParam(required = false) String labelName) {
        return labelLogic.list(labelName);
    }

    @ApiOperation("删除标签")
    @DeleteMapping("/{labelId}")
    public ResponseResult<Long> delete(@PathVariable Long labelId) {
        return labelLogic.delete(labelId);
    }
}
