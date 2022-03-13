package com.redspider.pss.service.db.spi;

import com.redspider.pss.dto.audit.AuditDTO;

import java.util.List;
import java.util.Optional;

public interface AuditService {

    Optional<Long> create(AuditDTO auditDto);

    Optional<AuditDTO> get(Long id);

    List<AuditDTO> query(Integer pageSize, Integer pageIndex, Integer status);

    Boolean pass(Long id, Long operatorId);

    Boolean refuse(Long id, String remark, Long operatorId);
}
