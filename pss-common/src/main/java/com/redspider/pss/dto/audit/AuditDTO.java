package com.redspider.pss.dto.audit;

import com.redspider.pss.constant.enums.Audit.AuditStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditDTO {

    private Long id;

    private AuditStatus status;

    private Integer type;

    private String bizType;

    private Long operatorId;

    private String remark;
}
