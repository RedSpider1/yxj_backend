package com.redspider.pss.constant.enums;

import com.redspider.pss.utils.annotation.EnumDescription;
import lombok.Getter;

public enum Audit {

    ;

    public enum AuditType {

        AUTOMATIC(0, "automatic"),
        MANUAL(1, "manual");

        private int code;
        private String description;

        AuditType(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }

    @Getter
    @EnumDescription("审核单状态")
    public enum AuditStatus {
    
        @EnumDescription("待审核")
        UN_AUDIT(0, "pending"),
        @EnumDescription("审核中")
        AUDITING(1, "auditing"),
        @EnumDescription("成功")
        SUCCESS(1, "success"),
        @EnumDescription("失败")
        FAIL(2, "fail");

        private int code;
        private String description;

        AuditStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }
    }
}
