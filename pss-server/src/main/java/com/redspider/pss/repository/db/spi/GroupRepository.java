package com.redspider.pss.repository.db.spi;

import com.redspider.pss.constant.enums.Group.GroupStatus;

public interface GroupRepository {

    Boolean updateStatusByAuditId(Long auditId, GroupStatus targetStatus, GroupStatus sourceStatus);
}
