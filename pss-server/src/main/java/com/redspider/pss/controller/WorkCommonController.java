package com.redspider.pss.controller;

import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.service.db.spi.ApiCodeService;
import com.redspider.pss.vo.team.CustomerExceptionVO;
import com.redspider.pss.vo.team.EnumDescriptionListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/workcommon")
@Api(tags = "公共区接口")
public class WorkCommonController {

    @Autowired
    private ApiCodeService apiCodeService;

    @ApiOperation("查询code码")
    @GetMapping("/apicodes")
    public ResponseResult<List<CustomerExceptionVO>> list() {
        return ResponseResult.success(apiCodeService.getAll());
    }

    @ApiOperation("查询枚举类型")
    @GetMapping("/apiEnums")
    public ResponseResult<List<EnumDescriptionListVO>> enumList() {
        return ResponseResult.success(apiCodeService.getAllEnums());
    }

}
