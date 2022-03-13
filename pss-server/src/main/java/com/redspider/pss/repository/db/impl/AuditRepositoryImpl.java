package com.redspider.pss.repository.db.impl;

import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.constant.enums.Audit.AuditStatus;
import com.redspider.pss.repository.db.spi.AuditRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuditRepositoryImpl implements AuditRepository {

    @Override
    public Optional<AuditDTO> get(Long id) {
        return null;
    }

    @Override
    public Boolean updateStatusAndRemarkById(Long id, String remark, AuditStatus targetStatus, Long operatorId) {
        return null;
    }
}
