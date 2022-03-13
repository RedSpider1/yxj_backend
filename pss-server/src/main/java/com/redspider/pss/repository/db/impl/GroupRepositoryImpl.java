package com.redspider.pss.repository.db.impl;

import com.redspider.pss.repository.db.spi.GroupRepository;
import com.redspider.pss.constant.enums.Group.GroupStatus;
import org.springframework.stereotype.Repository;

@Repository
public class GroupRepositoryImpl implements GroupRepository {

    @Override
    public Boolean updateStatusByAuditId(Long auditId, GroupStatus targetStatus, GroupStatus sourceStatus) {
        return null;
    }
}
