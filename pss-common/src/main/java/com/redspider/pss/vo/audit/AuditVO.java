package com.redspider.pss.vo.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditVO {

    private String status;

    private Integer type;

    private String bizType;

    private Long operatorId;

    private String remark;
}
