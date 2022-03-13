package com.redspider.pss.controller;

import com.redspider.pss.aop.annotation.RoleValidation;
import com.redspider.pss.constant.ApiConstant;
import com.redspider.pss.constant.enums.UserRole;
import com.redspider.pss.logic.spi.AuditLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.vo.audit.AuditVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = ApiConstant.AUDIT)
@Api(tags = "后台审核")
@RoleValidation(role = UserRole.ADMINISTRATOR)
public class AuditController {

    @Autowired
    private AuditLogic auditLogic;

    @ApiOperation("查询")
    @GetMapping(value = "/{id}")
    public ResponseResult<AuditVO> get(@PathVariable(value = "id") Long id) {
        return auditLogic.get(id);
    }

    @ApiOperation("批量查询")
    @GetMapping
    public ResponseResult<List<AuditVO>> query(
            @RequestParam Integer pageSize, @RequestParam Integer pageIndex, @RequestParam Integer status) {
        return auditLogic.query(pageSize, pageIndex, status);
    }

    @ApiOperation("审核通过")
    @PostMapping(value = "/{id}")
    public ResponseResult pass(@PathVariable(value = "id") Long id) {
        return auditLogic.pass(id);
    }

    @ApiOperation("审核拒绝")
    @PostMapping(value = "/{id}/refuse")
    public ResponseResult refuse(@PathVariable(value = "id") Long id,
                                 @RequestBody String remark) {
        return auditLogic.refuse(id, remark);
    }
}
