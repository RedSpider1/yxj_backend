package com.redspider.pss.service;

public interface AuditGroupService {

    Boolean pass(Long id, Long operatorId);

    Boolean refuse(Long id, String remark, Long operatorId);
}
