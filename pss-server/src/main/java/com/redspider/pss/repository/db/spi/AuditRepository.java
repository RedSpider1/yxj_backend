package com.redspider.pss.repository.db.spi;

import com.redspider.pss.constant.enums.Audit.AuditStatus;
import com.redspider.pss.dto.audit.AuditDTO;

import java.util.Optional;

public interface AuditRepository {

    Optional<AuditDTO> get(Long id);

    Boolean updateStatusAndRemarkById(Long id, String remark, AuditStatus targetStatus, Long operatorId);
}
