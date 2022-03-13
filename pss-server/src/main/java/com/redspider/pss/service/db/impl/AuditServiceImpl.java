package com.redspider.pss.service.db.impl;

import com.redspider.pss.constant.enums.Audit.AuditStatus;
import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.repository.db.spi.AuditRepository;
import com.redspider.pss.service.db.spi.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditServiceImpl implements AuditService {

    private static final String PASS = "审核通过";
    public static final long DEFAULT_AUDIT_ID = 0L;

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public Optional<Long> create(AuditDTO auditDto) {
        return Optional.of(DEFAULT_AUDIT_ID);
    }

    @Override
    public Optional<AuditDTO> get(Long id) {
        return auditRepository.get(id);
    }

    @Override
    public List<AuditDTO> query(Integer pageSize, Integer pageIndex, Integer status) {
        return null;
    }

    @Override
    public Boolean pass(Long id, Long operatorId) {
        return auditRepository.updateStatusAndRemarkById(id, PASS, AuditStatus.SUCCESS, operatorId);
    }

    @Override
    public Boolean refuse(Long id, String remark, Long operatorId) {
        return auditRepository.updateStatusAndRemarkById(id, remark, AuditStatus.FAIL, operatorId);
    }
}
