package com.redspider.pss.service.impl;

import com.redspider.pss.service.AuditGroupService;
import com.redspider.pss.service.db.spi.AuditService;
import com.redspider.pss.service.db.spi.PssGroupTeamService;
import lombok.extern.slf4j.Slf4j;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AuditGroupServiceImpl implements AuditGroupService {

    @Autowired
    private AuditService auditService;

    @Autowired
    private PssGroupTeamService pssGroupTeamService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Boolean pass(Long id, Long operatorId) {
        if (auditService.pass(id, operatorId)){
            return pssGroupTeamService.updateStatusByAuditId(id, GroupStatus.FINDING);
        }
        log.error("failed to pass audit, id = {}", id);
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Boolean refuse(Long id, String remark, Long operatorId) {
        if (auditService.refuse(id, remark, operatorId) &&
                pssGroupTeamService.updateStatusByAuditId(id, GroupStatus.DRAFT)){
            // todo wanghao 发消息
            return true;
        }
        log.error("failed to refuse audit, id = {}, remark = {}", id, remark);
        return false;
    }
}
