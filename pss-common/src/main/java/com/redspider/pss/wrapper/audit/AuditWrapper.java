package com.redspider.pss.wrapper.audit;

import com.redspider.pss.dto.audit.AuditDTO;
import com.redspider.pss.vo.audit.AuditVO;
import org.springframework.beans.BeanUtils;

public class AuditWrapper {

    public static AuditVO wrap(AuditDTO auditDto) {
        AuditVO vo = new AuditVO();
        BeanUtils.copyProperties(auditDto, vo);
        return vo;
    }
}
