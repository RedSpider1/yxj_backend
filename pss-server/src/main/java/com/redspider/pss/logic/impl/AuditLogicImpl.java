package com.redspider.pss.logic.impl;

import com.redspider.pss.logic.spi.AuditLogic;
import com.redspider.pss.response.ResponseResult;
import com.redspider.pss.security.UserUtil;
import com.redspider.pss.service.AuditGroupService;
import com.redspider.pss.service.db.spi.AuditService;
import com.redspider.pss.vo.audit.AuditVO;
import com.redspider.pss.wrapper.audit.AuditWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogicImpl implements AuditLogic {

    @Autowired
    private AuditGroupService auditGroupService;

    @Autowired
    private AuditService auditService;

    @Override
    public ResponseResult<AuditVO> get(Long id) {
       return auditService.get(id)
                .map(AuditWrapper::wrap)
                .map(ResponseResult::success)
                .orElse(ResponseResult.fail("failed to get audit"));
    }

    @Override
    public ResponseResult<List<AuditVO>> query(Integer pageSize, Integer pageIndex, Integer status) {
        return ResponseResult.success(
                auditService.query(pageSize, pageIndex, status)
                .stream().map(AuditWrapper::wrap)
                .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseResult pass(Long id) {
        return auditGroupService.pass(id, UserUtil.getUserId()) ?
                ResponseResult.success() : ResponseResult.fail("failed to pass audit");
    }

    @Override
    public ResponseResult refuse(Long id, String remark) {
        return auditGroupService.refuse(id, remark, UserUtil.getUserId()) ?
                ResponseResult.success() : ResponseResult.fail("failed to refuse audit");
    }
}